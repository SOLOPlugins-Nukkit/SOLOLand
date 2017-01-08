package solo.sololand.command.defaults.land.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.land.Land;
import solo.sololand.world.World;
import solo.sololand.external.Message;
import solo.sololand.queue.Queue;

public class LandReduce extends SubCommand{

	public LandReduce(){
		super("축소", "땅 크기를 축소합니다.");
		this.setPermission("sololand.command.land.reduce");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);
		Land land = world.getLand(player);

		if(!player.isOp() && !world.isAllowResizeLand()){
			Message.alert(player, "해당 월드에서 땅 크기변경을 할 수 없습니다.");
			return true;
		}

		switch(Queue.get(player)){
			case Queue.NULL:
				if(land == null){
					Message.alert(player, "현재 위치에서 땅을 찾을 수 없습니다.");
					return true;
				}
				if(!player.isOp() && !land.isOwner(player)){
					Message.alert(player, "땅 주인이 아니므로 땅 크기변경을 할 수 없습니다.");
					return true;
				}
				Queue.set(player, Queue.LAND_REDUCE_FIRST);
				Queue.setLand(player, land);
				Message.normal(player, "땅 축소를 시작합니다. 크기를 축소할 지점을 터치해주세요.");
				return true;

			case Queue.LAND_REDUCE_FIRST:
				Message.normal(player, "이미 땅 축소가 진행중입니다. 취소하려면 /땅 취소 명령어를 입력하세요.");
				return true;

			case Queue.LAND_REDUCE_SECOND:
				try{
					Land afterLand = Queue.getTemporaryLand(player);
					Land beforeLand = Queue.getLand(player);
					if(!player.isOp() && !beforeLand.isOwner(player)){
						Message.alert(player, "땅 주인이 아니므로 땅 크기변경을 할 수 없습니다.");
						return true;
					}
					if(beforeLand.size() <= afterLand.size()){
						Message.alert(player, "땅 크기에 변동이 없어 땅 확장을 중지합니다.");
						return true;
					}
					world.setLand(beforeLand.getNumber(), afterLand);
					Message.success(player, "성공적으로 땅 크기를 축소하였습니다. 변경된 크기 : " + Integer.toString(beforeLand.size() - afterLand.size()) + "블럭");
					Queue.clean(player);
				}catch(Exception e){
					Message.alert(player, "땅 축소중 오류가 발생하였습니다. 땅 축소 작업을 다시 시도해주세요.");
					Queue.clean(player);
				}
				return true;

			default:
				Message.alert(player, "현재 다른 작업이 진행중입니다. /땅 취소 명령어를 입력한 뒤 시도해주세요.");
		}
		return true;
	}
}