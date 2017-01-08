package solo.sololand.command.defaults.room.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import solo.sololand.command.SubCommand;
import solo.sololand.land.Land;
import solo.sololand.land.Room;
import solo.sololand.world.World;
import solo.sololand.external.Message;

public class RoomMove extends SubCommand{

	public RoomMove(){
		super("이동", "해당 번호의 방으로 이동합니다.");
		this.setPermission("sololand.command.room.move");
		this.addCommandParameters(new CommandParameter[]{
			new CommandParameter("방 번호 또는 주소(땅번호-방번호)", CommandParameter.ARG_TYPE_STRING, false)
		});
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		if(args.length < 1){
			return false;
		}
		Player player = (Player) sender;
		World world = World.get(player);
		int landNum = -1;
		int roomNum = -1;
		String[] devide = args[0].split("-");
		if(devide.length == 1){
			try{
				Land land = world.getLand(player);
				landNum = land.getNumber();
				roomNum = Integer.parseInt(devide[0]);
			}catch(Exception e){
				return false;
			}
		}else if(devide.length == 2){
			try{
				landNum = Integer.parseInt(devide[0]);
				roomNum = Integer.parseInt(devide[1]);
			}catch(Exception e){
				return false;
			}
		}else{
			return false;
		}
		Land land = world.getLand(landNum);
		if(land == null){
			Message.alert(player, Integer.toString(landNum) + "번 땅은 존재하지 않습니다.");
			return true;
		}
		Room room = land.getRoom(roomNum);
		if(room == null){
			Message.alert(player, Integer.toString(landNum) + "-" + Integer.toString(roomNum) + "번 방은 존재하지 않습니다.");
			return true;
		}
		if(
			!player.isOp() &&
			!land.isOwner(player) &&
			!land.isAllowAccess() &&
			!room.isOwner(player) &&
			!room.isMember(player)
		){
			Message.alert(player, Integer.toString(landNum) + "번 땅은 현재 출입이 제한되어 있습니다.");
			return true;
		}
		player.teleport(room.getSpawnPoint());
		Message.success(player, room.getAddress() + "번 방으로 이동하였습니다.");
		return true;
	}
}