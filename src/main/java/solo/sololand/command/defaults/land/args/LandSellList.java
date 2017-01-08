package solo.sololand.command.defaults.land.args;

import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.sololand.external.Message;

public class LandSellList extends SubCommand{

	public LandSellList(){
		super("매물", "판매중인 땅의 목록을 확인합니다.");
		this.setPermission("sololand.command.land.selllist");
		this.addCommandParameters(new CommandParameter[]{
			new CommandParameter("페이지", CommandParameter.ARG_TYPE_INT, false)
		});
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player.getLevel());
		ArrayList<String> information = new ArrayList<String>();
		int page = 1;
		try{
			page = Integer.parseInt(args[0]);
		}catch(Exception e){
			
		}
		for(Land land : world.getLands().values()){
			if(!land.isSail()){
				continue;
			}
			String line = "";
			if(!land.isOwner("")){
				line = "§l§a" + land.getOwner() + "님§r§a이 §l§a" + Integer.toString(land.getNumber()) + "번§r§a땅을 §l§a" + Double.toString(land.getPrice()) + "원§r§a에 판매중입니다.";
			}else{
				line = "§l§a" + Integer.toString(land.getNumber()) + "번§r§a땅이 §l§a" + Double.toString(land.getPrice()) + "원§r§a에 판매중입니다.";
			}
			if(! land.getWelcomeMessage().equals("")){
				line += "§r§7 - " + land.getWelcomeMessage();
			}
			information.add(line);
		}
		Message.page(player, "판매중인 땅 목록", information, page);
		return true;
	}
}