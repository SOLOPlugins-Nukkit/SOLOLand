package solo.sololand.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;

import java.util.LinkedHashMap;
import solo.sololand.Main;
import solo.solobasepackage.util.Debug;
import solo.solobasepackage.util.Message;

public class CommandMap<T extends BaseCommand>{
	
	private LinkedHashMap<String, T> commands = new LinkedHashMap<String, T>();

	@SuppressWarnings("rawtypes")
	public void register(T cmd){
		//check if command is already registered
		if(this.commands.containsKey(cmd.getName())){
			Debug.alert(Main.getInstance(), cmd.getName() + " 명령어 등록 실패 : 이미 존재하는 명령어 입니다.");
			return;
		}
		this.commands.put(cmd.getName(), cmd);
		
		//if command is instance of MainCommand, register pluginCommand
		if(cmd instanceof MainCommand){
			//create plugin command for register
			@SuppressWarnings("unchecked")
			PluginCommand pluginCommand = new PluginCommand(cmd.getName(), Main.getInstance());
			pluginCommand.setDescription(cmd.getDescription());
			pluginCommand.setUsage("/" + cmd.getName() + " [서브 커맨드]");
			
			//register plugin command at server
			Server.getInstance().getCommandMap().register(cmd.getName(), pluginCommand);
			for(Player player : Server.getInstance().getOnlinePlayers().values()){
				player.sendCommandData();
			}
		}
	}

	public boolean unregister(T cmd){
		return this.unregister(cmd.getName());
	}
	public boolean unregister(String name){
		if(!this.commands.containsKey(name)){
			return false;
		}
		this.commands.remove(name);
		return true;
	}

	public LinkedHashMap<String, T> getAll(){
		return this.commands;
	}

	public T get(String name){
		if(!this.commands.containsKey(name)){
			return null;
		}
		return this.commands.get(name);
	}
	
	public boolean dispatch(CommandSender sender, String cmdLine){
		return this.dispatch(sender, null, cmdLine);
	}

	public boolean dispatch(CommandSender sender, MainCommand mainCommand, String cmdLine){
		String[] args = cmdLine.split(" ");
		if(args.length == 0){
			return false;
		}
		String firstArgs = args[0];
		T cmd = this.get(firstArgs);
		if(cmd == null){
			return false;
		}
		if(!sender.hasPermission(cmd.getPermission())){
			Message.alert(sender, "권한이 없습니다.");
			return true;
		}
		if(!(sender instanceof Player) && cmd.isInGameOnly()){
			Message.alert(sender, "인게임에서만 가능합니다.");
			return true;
		}
		String[] newArgs = new String[args.length - 1];
		for(int i = 0; i<= args.length - 1; i++){
			try{
				newArgs[i] = args[i+1];
			}catch(Exception e){
				break;
			}
		}
		if(! cmd.execute(sender, newArgs) && mainCommand != null && cmd instanceof SubCommand){
			Message.usage(sender, mainCommand.getFullDescription((SubCommand) cmd));
		}
		return true;
	}
}