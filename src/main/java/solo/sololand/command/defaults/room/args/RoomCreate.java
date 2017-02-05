package solo.sololand.command.defaults.room.args;

import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.land.Land;
import solo.sololand.land.Room;
import solo.sololand.world.World;
import solo.solobasepackage.util.Message;
import solo.sololand.queue.Queue;
import solo.solobasepackage.util.Economy;

public class RoomCreate extends SubCommand{

	public RoomCreate(){
		super("생성", "방을 생성합니다.");
		this.setPermission("sololand.command.room.create");
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
		
		if(!player.isOp()){
			if(! land.isOwner(player)){
				Message.alert(player, "땅 주인만 방 생성이 가능합니다.");
				return true;
			}
			if(!world.isAllowCreateRoom()){
				Message.alert(player, "해당 월드에서 방을 생성할 수 없습니다.");
				return true;
			}
			if(world.getMaxRoomCreateCount() < land.getRooms().size()){
				Message.alert(player, "해당 땅에서 생성할 수 있는 방의 최대 갯수를 초과하였습니다. (최대 " + Integer.toString(world.getMaxRoomCreateCount()) + "개)");
				return true;
			}
		}
		
		switch(Queue.get(player)){
			case Queue.NULL:
				Queue.set(player, Queue.ROOM_CREATE_FIRST);
				Queue.setLand(player, land);
				Message.normal(player, "방 생성을 시작합니다. 시작 지점을 터치해주세요.");
				break;

			case Queue.ROOM_CREATE_FIRST:
				Message.alert(player, "이미 방 생성이 진행중입니다. 블럭을 터치하여 첫번째 지점을 선택하여 주세요.");
				Message.alert(player, "진행중인 작업을 취소하려면 /방 취소 명령어를 입력하세요.");
				break;
				
			case Queue.ROOM_CREATE_SECOND:
				Message.alert(player, "이미 방 생성이 진행중입니다. 블럭을 터치하여 두번째 지점을 선택하여 주세요.");
				Message.alert(player, "진행중인 작업을 취소하려면 /방 취소 명령어를 입력하세요.");
				break;

			case Queue.ROOM_CREATE_THIRD:
				try{
					land = Queue.getLand(player);
					if(!player.isOp() && ! land.isOwner(player)){
						Message.alert(player, "땅 주인만 방 생성이 가능합니다.");
						return true;
					}
					Room room = Queue.getTemporaryRoom(player);
					ArrayList<Room> overlapRoomList = land.getOverlap(room);
					if(overlapRoomList.size() > 0){
						StringBuilder sb = new StringBuilder();
						boolean f = true;
						for(Room overlap : overlapRoomList){
							if(f){
								f = false;
							}else{
								sb.append(", ");
							}
							sb.append(Integer.toString(overlap.getNumber()) + "번");
						}
						Message.alert(player, sb.toString()+ " 방과 겹칩니다. 방 생성을 취소합니다.");
						Queue.clean(player);
						break;
					}
					if(!player.isOp()){
						double myMoney = Economy.getMoney(player);
						double price = world.getRoomCreatePrice(); 
						if(myMoney < price){
							Message.alert(player, "돈이 부족합니다. 현재 소지한 돈 : " + Double.toString(myMoney) + "원, 생성 시 필요한 돈 : " + Double.toString(price) + "원");
							Queue.clean(player);
							break;
						}
						Economy.reduceMoney(player, price);
					}
					land.setRoom(room.getLand().getNextRoomNumber(), room);
					Message.normal(player, "성공적으로 방을 생성하였습니다. 방 번호는 " + Integer.toString(room.getNumber()) + "번 입니다.");
					Queue.clean(player);
				}catch(Exception e){
					Message.alert(player, "방 생성중에 오류가 발생하였습니다. 방 생성을 다시 진행해주세요.");
					Queue.clean(player);
				}
				break;

			default:
				Message.alert(player, "이미 다른 작업을 진행중입니다. /방 취소 명령어를 입력한 뒤 시도해주세요.");
		}
		return true;
	}
}