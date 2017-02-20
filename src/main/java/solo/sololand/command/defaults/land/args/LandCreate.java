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

public class LandCreate extends SubCommand{

	public LandCreate(){
		super("생성", "땅을 생성합니다.");
		this.setPermission("sololand.command.land.create");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);

		if(!player.isOp()){
			if(!world.isAllowCreateLand()){
				Message.alert(player, "해당 월드에서 땅을 생성할 수 없습니다.");
				return true;
			}
			if(world.getMaxLandCount() <= world.getLands(player).size()){
				Message.alert(player, "해당 월드에서 소유할 수 있는 땅의 최대 갯수를 초과하였습니다. (최대 " + Integer.toString(world.getMaxLandCount()) + "개)");
				return true;
			}
		}
		
		switch(Queue.get(player)){
			case Queue.NULL:
				Queue.set(player, Queue.LAND_CREATE_FIRST);
				Message.normal(player, "땅 생성을 시작합니다. 시작 지점을 터치해주세요.");
				break;

			case Queue.LAND_CREATE_FIRST:
				Message.alert(player, "이미 땅 생성이 진행중입니다. 블럭을 터치하여 첫번째 지점을 선택하여 주세요.");
				Message.alert(player, "진행중인 작업을 취소하려면 /땅 취소 명령어를 입력하세요.");
				break;
				
			case Queue.LAND_CREATE_SECOND:
				Message.alert(player, "이미 땅 생성이 진행중입니다. 블럭을 터치하여 두번째 지점을 선택하여 주세요.");
				Message.alert(player, "진행중인 작업을 취소하려면 /땅 취소 명령어를 입력하세요.");
				break;

			case Queue.LAND_CREATE_THIRD:
				try{
					Land land = Queue.getTemporaryLand(player);
					ArrayList<Land> overlapList = world.getOverlap(land);
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
						Message.alert(player, sb.toString()+ " 땅과 겹칩니다. 땅 생성을 취소합니다.");
						Queue.clean(player);
						break;
					}
					if(!player.isOp()){
						double myMoney = Economy.getMoney(player);
						double price = world.getPricePerBlock() * land.xLength() * land.zLength(); 
						if(myMoney < price){
							Message.alert(player, "돈이 부족합니다. 현재 소지한 돈 : " + Double.toString(myMoney) + "원, 생성 시 필요한 돈 : " + Double.toString(price) + "원");
							Queue.clean(player);
							break;
						}
						Economy.reduceMoney(player, price);
					}
					world.setLand(world.getNextLandNumber(), land);
					Message.normal(player, "성공적으로 땅을 생성하였습니다. 땅 번호는 " + Integer.toString(land.getNumber()) + "번 입니다.");
					Queue.clean(player);
				}catch( Exception e){
					Message.alert(player, "땅 생성중에 오류가 발생하였습니다. 땅 생성을 다시 진행해주세요.");
					Queue.clean(player);
				}
				break;

			default:
				Message.alert(player, "이미 다른 작업을 진행중입니다. /땅 취소 명령어를 입력한 뒤 시도해주세요.");
		}
		return true;
	}
}