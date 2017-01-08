package solo.sololand.command.defaults.room.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.sololand.land.Room;
import solo.sololand.external.Message;

public class RoomLeave extends SubCommand{

	public RoomLeave(){
		super("나가기", "자신의 방 또는 공유받던 방에서 나갑니다.");
		this.setPermission("sololand.command.land.leave");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);
		Land land = world.getLand(player);
		if(land == null){
			Message.alert(player, "현재 위치에서 땅을 찾을 수 없습니다.");
			return true;
		}
		Room room = land.getRoom(player);
		if(room == null){
			Message.alert(player, "현재 위치에서 방을 찾을 수 없습니다.");
			return true;
		}
		if(room.isOwner(player)){
			room.setOwner("");
			Message.normal(player, "방에서 나갔습니다.");
			return true;
		}
		if(room.isMember(player)){
			room.removeMember(player);
			Message.normal(player, "공유받던 방에서 나갔습니다.");
			return true;
		}
		Message.alert(player, "이 방을 소유하고 있지 않거나 공유받고 있지 않습니다.");
		return true;
	}
}