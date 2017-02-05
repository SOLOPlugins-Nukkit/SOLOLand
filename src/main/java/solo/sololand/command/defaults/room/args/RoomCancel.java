package solo.sololand.command.defaults.room.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.solobasepackage.util.Message;
import solo.sololand.queue.Queue;

public class RoomCancel extends SubCommand{

	public RoomCancel(){
		super("취소", "진행중인 방 작업을 취소합니다.");
		this.setPermission("sololand.command.room.cancel");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		switch(Queue.get(player)){
			case Queue.NULL:
				Message.alert(player, "진행중인 작업이 없습니다.");
				break;
				
			case Queue.LAND_CREATE_FIRST:
			case Queue.LAND_CREATE_SECOND:
			case Queue.LAND_CREATE_THIRD:
			case Queue.LAND_REMOVE:
			case Queue.LAND_EXPAND_FIRST:
			case Queue.LAND_EXPAND_SECOND:
			case Queue.LAND_REDUCE_FIRST:
			case Queue.LAND_REDUCE_SECOND:
			case Queue.LAND_SELL:
				Message.normal(player, "땅 작업이 진행중입니다. /땅 취소 명령어로 작업을 중단해주세요.");
				break;
				
			case Queue.ROOM_CREATE_FIRST:
			case Queue.ROOM_CREATE_SECOND:
			case Queue.ROOM_CREATE_THIRD:
				Message.normal(player, "진행중이던 방 생성 작업을 취소하였습니다.");
				break;
				
			case Queue.ROOM_REMOVE:
				Message.normal(player, "진행중이던 방 삭제 작업을 취소하였습니다.");
				break;
				
			case Queue.ROOM_SELL:
				Message.normal(player, "진행중이던 방 판매 작업을 취소하였습니다.");
				break;
				
			default:
				Message.normal(player, "진행중이던 작업을 취소하였습니다.");
		}
		Queue.clean(player);
		return true;
	}
}