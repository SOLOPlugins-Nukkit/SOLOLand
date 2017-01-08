package solo.sololand.command.defaults.land.args;

import cn.nukkit.Server;

import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.sololand.external.Message;

public class LandShareList extends SubCommand{

	public LandShareList(){
		super("공유목록", "공유받은 땅의 목록을 확인합니다.");
		this.setPermission("sololand.command.land.sharelist");
		this.addCommandParameters(new CommandParameter[]{
			new CommandParameter("페이지", CommandParameter.ARG_TYPE_INT, true)
		});
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
		for(World world : World.getAll().values()){
			for(Land land : world.getSharedLands(targetName).values()){
				String line = "§l§a[" + world.getCustomName() + " 월드] " + land.getOwner() + "님의 " + Integer.toString(land.getNumber()) + "번땅";
				if(! land.getWelcomeMessage().equals("")){
					line += "§r§7 - " + land.getWelcomeMessage();
				}
				information.add(line);
			}
		}
		if(information.size() < 1){
			Message.normal(player, targetName + "님은 공유받은 땅이 없습니다.");
		}else{
			Message.page(player, targetName + "님의 공유받은 땅 목록", information, page);
		}
		return true;
	}
}