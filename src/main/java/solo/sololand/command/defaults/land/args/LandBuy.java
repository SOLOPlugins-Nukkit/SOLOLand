package solo.sololand.command.defaults.land.args;

import cn.nukkit.Server;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.solobasepackage.util.Message;
import solo.solobasepackage.util.Notification;
import solo.solobasepackage.util.Economy;

public class LandBuy extends SubCommand{

	public LandBuy(){
		super("구매", "판매중인 땅을 구매합니다.");
		this.setPermission("sololand.command.land.buy");
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
		if(!land.isSail()){
			Message.alert(player, "이 땅은 현재 판매중이 아닙니다.");
			return true;
		}
		if(land.isOwner(player)){
			Message.alert(player, "땅 주인이 땅을 구매할 수 없습니다. 만일 판매를 취소하는 것을 원하신다면, /땅 판매취소 명령어를 입력해주세요.");
			return true;
		}
		double myMoney = Economy.getMoney(player);
		double landPrice = land.getPrice();
		if(! player.isOp()){
			if(myMoney < landPrice){ 
				Message.alert(player, "돈이 부족합니다. 내 돈 : " + Double.toString(myMoney) + "원");
				return true;
			}
			if(world.getMaxLandCount() <= world.getLands(player).size()){
				Message.alert(player, "해당 월드에서 소유할 수 있는 땅의 최대 갯수를 초과하였습니다. (최대 " + Integer.toString(world.getMaxLandCount()) + "개)");
				return true;
			}
			Economy.reduceMoney(player, landPrice);
		}
		Economy.addMoney(land.getOwner(), landPrice);
		Player prevOwner = Server.getInstance().getPlayerExact(land.getOwner());
		
		Notification.addNotification(prevOwner, player.getName() + "님이 " + world.getCustomName() + " 월드의 " + Integer.toString(land.getNumber()) + "번 땅을 구매하셨습니다.");

		land.clear();
		land.setOwner(player);
		
		//for gridland..
		if(world.getLand(land.getNumber()) == null){
			world.setLand(land);
		}
		Message.normal(player, Integer.toString(land.getNumber()) + "번 땅을 구매하였습니다.");
		return true;
	}
}