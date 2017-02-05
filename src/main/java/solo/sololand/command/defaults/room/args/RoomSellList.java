package solo.sololand.command.defaults.room.args;

import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.sololand.land.Room;
import solo.solobasepackage.util.Message;

public class RoomSellList extends SubCommand{

	public RoomSellList(){
		super("매물", "판매중인 방의 목록을 확인합니다.");
		this.setPermission("sololand.command.room.selllist");
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
			if(!land.hasRoom()){
				continue;
			}
			for(Room room : land.getRooms().values()){
				if(room.isSail()){
					String line = "§l§a" + land.getOwner() + "님§r§a이 §l§a" + room.getAddress() + "번§r§a방을 §l§a" + Double.toString(room.getPrice()) + "원§r§a에 판매중입니다.";
					if(! room.getWelcomeMessage().equals("")){
						line += "§r§7 - " + room.getWelcomeMessage();
					}
					information.add(line);
				}
			}
		}
		Message.page(player, "판매중인 방 목록", information, page);
		return true;
	}
}