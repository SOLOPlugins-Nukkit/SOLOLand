package solo.sololand.command.defaults.custom.args;

import cn.nukkit.Server;

import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.solobasepackage.util.Message;

public class CustomList extends SubCommand{

	public World world;
	
	public CustomList(World world){
		super("목록", "소유한 " + world.getCustomName() + " 월드 땅을 확인합니다.");
		this.setPermission("sololand.command.custom.list");
		this.addCommandParameters(new CommandParameter[]{
			new CommandParameter("페이지", CommandParameter.ARG_TYPE_INT, true)
		});
		this.addCommandParameters(new CommandParameter[]{
			new CommandParameter("유저", CommandParameter.ARG_TYPE_STRING, false),
			new CommandParameter("페이지", CommandParameter.ARG_TYPE_INT, true)
		});
		this.world = world;
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		String targetName = player.getName();
		int page = 1;
		if(args.length == 1){
			try{
				page = Integer.parseInt(args[0]);
			}catch(Exception e){
				Player target = Server.getInstance().getPlayer(args[0]);
				if(target != null){
					targetName = target.getName();
				}else{
					targetName = args[0];
				}
			}
		}else if(args.length > 1){
			Player target = Server.getInstance().getPlayer(args[0]);
			if(target != null){
				targetName = target.getName();
			}else{
				targetName = args[0];
			}
			try{
				page = Integer.parseInt(args[1]);
			}catch(Exception e){
				
			}
		}
		
		ArrayList<String> information = new ArrayList<String>();
		for(Land land : this.world.getLands(targetName).values()){
			String line = "§l§a[" + this.world.getCustomName() + " 월드] " + Integer.toString(land.getNumber()) + "번땅 §r§7(" + Integer.toString(land.xLength()) + "x" + Integer.toString(land.zLength()) + ")";
			if(! land.getWelcomeMessage().equals("")){
				line += " - " + land.getWelcomeMessage();
			}
			information.add(line);
		}
		if(information.size() < 1){
			Message.normal(player, targetName + "님은 소유중인 땅이 없습니다.");
		}else{
			Message.page(player, targetName + "님의 " + this.world.getCustomName() + " 땅 목록", information, page);
		}
		return true;
	}
}