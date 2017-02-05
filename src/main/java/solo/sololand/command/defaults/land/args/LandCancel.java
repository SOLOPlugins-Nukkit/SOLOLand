package solo.sololand.command.defaults.land.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.solobasepackage.util.Message;
import solo.sololand.queue.Queue;

public class LandCancel extends SubCommand{

	public LandCancel(){
		super("취소", "진행중인 땅 작업을 취소합니다.");
		this.setPermission("sololand.command.land.cancel");
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
				Message.normal(player, "진행중이던 땅 생성 작업을 취소하였습니다.");
				break;
				
			case Queue.LAND_REMOVE:
				Message.normal(player, "진행중이던 땅 제거 작업을 취소하였습니다.");
				break;
				
			case Queue.LAND_EXPAND_FIRST:
			case Queue.LAND_EXPAND_SECOND:
				Message.normal(player, "진행중이던 땅 확장 작업을 취소하였습니다.");
				break;
				
			case Queue.LAND_REDUCE_FIRST:
			case Queue.LAND_REDUCE_SECOND:
				Message.normal(player, "진행중이던 땅 축소 작업을 취소하였습니다.");
				break;
				
			case Queue.ROOM_CREATE_FIRST:
			case Queue.ROOM_CREATE_SECOND:
			case Queue.ROOM_CREATE_THIRD:
			case Queue.ROOM_REMOVE:
			case Queue.ROOM_SELL:
				Message.normal(player, "방 작업이 진행중입니다. /방 취소 명령어로 작업을 중단해주세요.");
				break;
				
			default:
				Message.normal(player, "진행중이던 작업을 취소하였습니다.");
		}
		Queue.clean(player);
		return true;
	}
}