package solo.sololand.command.defaults.room.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.sololand.land.Room;
import solo.solobasepackage.util.Message;

public class RoomCancelSell extends SubCommand{
 
	public RoomCancelSell(){
		super("판매취소", "판매중인 방의 판매를 취소합니다.");
		this.setPermission("sololand.command.room.cancelsell");
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
		if(!player.isOp() && !land.isOwner(player)){
			Message.alert(player, "땅 주인이 아니므로 방 판매 여부를 수정할 수 없습니다.");
			return true;
		}
		if(!room.isSail()){
			Message.alert(player, "이 방은 현재 판매중이 아닙니다.");
			return true;
		}
		room.setSail(false);
		Message.normal(player, "방 판매를 취소하였습니다.");
		return true;
	}
}