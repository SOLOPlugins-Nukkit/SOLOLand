package solo.sololand.command.defaults.land.args;

import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.land.Land;
import solo.sololand.world.World;
import solo.solobasepackage.util.Message;
import solo.sololand.queue.Queue;
import solo.solobasepackage.util.Economy;

public class LandExpand extends SubCommand{

	public LandExpand(){
		super("확장", "땅 크기를 확장합니다.");
		this.setPermission("sololand.command.land.expand");
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
				Queue.set(player, Queue.LAND_EXPAND_FIRST);
				Queue.setLand(player, land);
				Message.normal(player, "땅 확장을 시작합니다. 크기를 확장할 지점을 터치해주세요.");
				return true;

			case Queue.LAND_EXPAND_FIRST:
				Message.normal(player, "이미 땅 확장이 진행중입니다. 취소하려면 /땅 취소 명령어를 입력하세요.");
				return true;

			case Queue.LAND_EXPAND_SECOND:
				try{
					Land afterLand = Queue.getTemporaryLand(player);
					Land beforeLand = Queue.getLand(player);
					if(!player.isOp() && !beforeLand.isOwner(player)){
						Message.alert(player, "땅 주인이 아니므로 땅 크기변경을 할 수 없습니다.");
						return true;
					}
					ArrayList<Land> overlapList = world.getOverlap(afterLand);
					if(overlapList.size() > 0){
						StringBuilder sb = new StringBuilder();
						boolean f = true;
						for(Land overlap : overlapList){
							if(f){
								f = false;
							}else{
								sb.append(", ");
							}
							sb.append(Integer.toString(overlap.getNumber()) + "번");
						}
						Message.alert(player, sb.toString() + " 땅과 겹칩니다. 땅 확장을 취소합니다.");
						Queue.clean(player);
						break;
					}
					if(beforeLand.size() >= afterLand.size()){
						Message.alert(player, "땅 크기에 변동이 없어 땅 확장을 중지합니다.");
						return true;
					}
					if(!player.isOp()){
						double myMoney = Economy.getMoney(player);
						double price = world.getPricePerBlock() * (afterLand.size() - beforeLand.size());
						if(myMoney < price){
							Message.alert(player, "돈이 부족합니다. 현재 소지한 돈 : " + Double.toString(myMoney) + "원, 땅 확장 시 필요한 돈 : " + Double.toString(price) + "원");
							Queue.clean(player);
							return true;
						}
						Economy.reduceMoney(player, price);
					}
					world.setLand(beforeLand.getNumber(), afterLand);
					Message.normal(player, "성공적으로 땅를 확장하였습니다. 확장된 크기 : " + Integer.toString(afterLand.size() - beforeLand.size()) + "블럭");
					Queue.clean(player);
				}catch(Exception e){
					Message.alert(player, "땅 확장중 오류가 발생하였습니다. 땅 확장 작업을 다시 시도해주세요.");
					Queue.clean(player);
				}
				return true;

			default:
				Message.alert(player, "현재 다른 작업이 진행중입니다. /땅 취소 명령어를 입력한 뒤 시도해주세요.");
		}
		return true;
	}
}