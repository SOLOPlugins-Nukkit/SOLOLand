package solo.sololand.command.defaults.room.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.sololand.land.Room;
import solo.sololand.queue.Queue;
import solo.solobasepackage.util.Message;

public class RoomSell extends SubCommand{

	public RoomSell(){
		super("판매", "방을 매물에 등록합니다.");
		this.setPermission("sololand.command.room.sell");
		this.addCommandParameters(new CommandParameter[]{
			new CommandParameter("가격", CommandParameter.ARG_TYPE_INT, false)
		});
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
			Message.alert(player, "땅 주인이 아니므로 방을 판매할 수 없습니다.");
			return true;
		}
		if(room.isSail()){
			Message.alert(player, "방이 이미 판매중입니다.");
			return true;
		}
		double sell;
		switch(Queue.get(player)){
			case Queue.ROOM_SELL:
				Room queueRoom = Queue.getRoom(player);
				if(queueRoom == room){
					sell = Queue.getDouble(player);
					Message.normal(player, "방을 " + Double.toString(sell) + "원으로 매물에 등록하였습니다.");
					Message.normal(player, "방 판매를 중단하고 싶으시다면 /방 판매취소 명령어를 입력해주세요.");
					room.setPrice(sell);
					room.setSail(true);
					Queue.clean(player);
				}else{
					Message.alert(sender, "방 판매중에 오류가 발생하였습니다. 다시 시도해주세요.");
					Queue.clean(player);
				}
				break;
				
			case Queue.NULL:
				if(args.length < 1){
					return false;
				}
				try{
					sell = Double.parseDouble(args[0]);
				}catch(Exception e){
					return false;
				}
				Message.normal(player, "방을 정말로 매물에 등록하시겠습니까? 방이 매물에 등록되어있는 동안엔 방을 수정할 수 없습니다.");
				Message.normal(player, Double.toString(sell) + "원으로 매물에 등록하려면 /방 판매 명령어를 다시 한번 입력해주세요.");
				Queue.set(player, Queue.ROOM_SELL);
				Queue.setRoom(player, room.getAddress());
				Queue.setDouble(player, sell);
				break;
		}
		return true;
	}
}