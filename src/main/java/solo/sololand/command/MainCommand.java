package solo.sololand.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import solo.solobasepackage.util.Message;

import java.util.ArrayList;

public abstract class MainCommand extends BaseCommand{

	private CommandMap<SubCommand> subCommandMap = new CommandMap<SubCommand>();

	public MainCommand(String name){
		this(name, null);
	}

	public MainCommand(String name, String description){
		this(name, description, null);
	}
	
	public MainCommand(String name, String description, String permission){
		super(name, description, permission);
	}
	
	public String getFullDescription(){
		return "/" + this.name;
	}
	
	public String getFullDescription(SubCommand subCommand){
		String usage = "§2§l" + this.getUsage(subCommand) + "§r§7";
		if(subCommand.getDescription() != null){
			usage += " - " + subCommand.getDescription();
		}
		return usage;
	}
	
	public String getUsage(SubCommand subCommand){
		StringBuilder sb = new StringBuilder();
		sb.append("/" + this.getName() + " " + subCommand.getName());
		boolean f = true;
		for(CommandParameter[] commandParams : subCommand.getCommandParameters()){
			if(f){
				f = false;
			}else{
				sb.append(" 또는 /" + this.getName() + " " + subCommand.getName());
			}
			for(CommandParameter commandParam : commandParams){
				sb.append(" ");
				if(commandParam.optional){
					sb.append("<");
				}else{
					sb.append("[");
				}
				sb.append(commandParam.name);
				if(commandParam.optional){
					sb.append(">");
				}else{
					sb.append("]");
				}
			}
		}
		return sb.toString();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		StringBuilder sb = new StringBuilder();
		boolean space = false;
		for(String arg : args){
			if(space){
				sb.append(" ");
			}else{
				space = true;
			}
			sb.append(arg);
		}
		if(!this.subCommandMap.dispatch(sender, this, sb.toString())){
			int page = 1;
			try{
				page = Integer.parseInt(args[0]);
			}catch(Exception e){
				
			}
			this.sendHelp(sender, page);
		}
		return true;
	}

	public void registerSubCommand(SubCommand subCommand){
		this.subCommandMap.register(subCommand);
	}

	public void unregisterSubCommand(SubCommand subCommand){
		this.subCommandMap.unregister(subCommand);
	}

	public void sendHelp(CommandSender sender){
		this.sendHelp(sender, 1);
	}
	public void sendHelp(CommandSender sender, int page){
		ArrayList<String> lines = new ArrayList<String>();
		for(SubCommand subCommand : this.getAvailable(sender)){
			lines.add(this.getFullDescription(subCommand));
		}
		Message.page(sender, this.getName() + " 명령어 도움말", lines, page);
	}
	
	public ArrayList<SubCommand> getSubCommands(){
		ArrayList<SubCommand> subCommands = new ArrayList<SubCommand>();
		for(SubCommand cmd : this.subCommandMap.getAll().values()){
			subCommands.add(cmd);
		}
		return subCommands;
	}

	public ArrayList<SubCommand> getAvailable(CommandSender sender){
		ArrayList<SubCommand> ret = new ArrayList<SubCommand>();
		for(SubCommand cmd : this.subCommandMap.getAll().values()){
			if(!(sender instanceof Player) && cmd.isInGameOnly()){
				continue;
			}
			if(cmd.getPermission() != null && !sender.hasPermission(cmd.getPermission())){
				continue;
			}
			ret.add(cmd);
		}
		return ret;
	}
	
	/*
	public Map<String, CommandDataVersions> generateSubCommandsData(Player player){
        Map<String, CommandDataVersions> ret = new HashMap<>();
		for(SubCommand sCmd : this.subCommandMap.getAll().values()){
			if(! player.hasPermission(sCmd.getPermission())){
				continue;
			}
			
			CommandData customData = new CommandData();
			customData.description = sCmd.getDescription();
			customData.permission = player.hasPermission(this.getPermission()) ? "any" : "false";
			int c = 1;
			if(sCmd.getCommandParameters() != null){
				for(CommandParameter[] commandParam : sCmd.getCommandParameters()){
					CommandOverload overload = new CommandOverload();
					overload.input.parameters = commandParam;
					customData.overloads.put(Integer.toString(c++), overload);
				}
			}
			if(customData.overloads.size() == 0){
				CommandOverload overload = new CommandOverload();
				overload.input.parameters = new CommandParameter[0];
				customData.overloads.put("1", overload);
			}
			CommandDataVersions versions = new CommandDataVersions();
			versions.versions.add(customData);
			ret.put(this.getName() + " " + sCmd.getName(), versions);
		}
		return ret;
	}
	*/
}
