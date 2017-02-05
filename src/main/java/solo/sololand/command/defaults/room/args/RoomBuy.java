package solo.sololand.command.defaults.room.args;

import cn.nukkit.Server;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.sololand.land.Room;
import solo.solobasepackage.util.Message;
import solo.solobasepackage.util.Economy;

public class RoomBuy extends SubCommand{

	public RoomBuy(){
		super("구매", "판매중인 방을 구매합니다.");
		this.setPermission("sololand.command.room.buy");
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
		if(!room.isSail()){
			Message.alert(player, "이 방은 현재 판매중이 아닙니다.");
			return true;
		}
		if(land.isOwner(player)){
			Message.alert(player, "자신의 땅에 있는 방을 구매할 수 없습니다. 만일 방 판매를 취소하는 것을 원하신다면, /방 판매취소 명령어를 입력해주세요.");
			return true;
		}
		double myMoney = Economy.getMoney(player);
		double roomPrice = room.getPrice();
		if(myMoney < roomPrice){ 
			Message.alert(player, "돈이 부족합니다. 내 돈 : " + Double.toString(myMoney) + "원");
			return true;
		}
		//if(!player.isOp() && world.getMaxLandCount() < world.getLands(name).size()){
		//	Message.alert(player, "해당 월드에서 소유할 수 있는 땅의 최대 갯수를 초과하였습니다. (최대 " + Integer.toString(world.getMaxLandCount()) + "개)");
		//	return true;
		//}
		Economy.reduceMoney(player, roomPrice);
		Economy.addMoney(land.getOwner(), roomPrice);
		Player landOwner = Server.getInstance().getPlayerExact(land.getOwner());
		if(landOwner != null){
			Message.normal(landOwner, player.getName() + "님이 " + world.getCustomName() + " 월드의 " + room.getAddress() + "번 방을 구매하셨습니다.");
		}

		room.clear();
		room.setOwner(player);
		Message.normal(player, room.getAddress() + "번 방을 구매하였습니다.");
		return true;
	}
}