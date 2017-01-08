package solo.sololand.command.defaults.land.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.sololand.external.Message;
import solo.sololand.queue.Queue;

public class LandRemove extends SubCommand{

	public LandRemove(){
		super("삭제", "땅을 삭제합니다.");
		this.setPermission("sololand.command.land.remove");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);
		Land land = world.getLand(player);
		if(land == null){
			Message.normal(player, "현재 위치에서 땅을 찾을 수 없습니다.");
			return true;
		}
		if(!player.isOp() && !land.isOwner(player)){
			Message.alert(player, "땅 주인이 아니므로 땅을 삭제할 수 없습니다.");
			return true;
		}
		switch(Queue.get(player)){
			case Queue.NULL:
				Queue.set(player, Queue.LAND_REMOVE);
				Queue.setLand(player, land);
				Message.normal(player, "정말로 땅을 제거하시겠습니까? 제거하시려면 /땅 삭제 명령어를 한번 더 입력해주세요.");
				Message.normal(player, "취소하려면 /땅 취소 명령어를 입력해주세요.");
				break;

			case Queue.LAND_REMOVE:
				Land toRemove = Queue.getLand(player);
				if(land == toRemove){
					world.removeLand(land);
					Message.success(player, "성공적으로 " + Integer.toString(land.getNumber()) + "번 땅을 제거하였습니다.");
					Queue.clean(player);
				}else{
					Message.alert(player, "땅 삭제를 진행하던 중 오류가 발생하였습니다. 다시 진행해주세요.");
				}
				break;

			default:
				Message.alert(player, "다른 작업이 진행중입니다. /땅 취소 명령어 입력 후 다시 시도해주세요.");
		}
		return true;
	}
}
