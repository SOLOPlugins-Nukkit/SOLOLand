package solo.sololand.command.defaults.custom.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.land.Land;
import solo.sololand.world.Island;
import solo.sololand.world.World;
import solo.solobasepackage.util.Message;
import solo.solobasepackage.util.Economy;

public class CustomBuy extends SubCommand{

	public World world;
	
	public CustomBuy(World world){
		super("구매", world.getName() + " 월드 땅을 구매합니다.", "sololand.command.custom.buy");
		this.world = world;
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;

		if(! (this.world instanceof Island)){
			Message.alert(player, "해당 월드에서는 지원하지 않는 명령어입니다.");
			return true;
		}

		double price = this.world.getDefaultLandPrice();

		if(!player.isOp()){
			if(Economy.getMoney(player) < price){
				Message.alert(player, "섬을 구매할 돈이 부족합니다. 비용 : " + Double.toString(price) + "원");
				return true;
			}
			if(this.world.getMaxLandCount() < this.world.getLands(player).size()){
				Message.alert(player, "해당 월드에서 소유할 수 있는 땅의 최대 갯수를 초과하였습니다. (최대 " + Integer.toString(this.world.getMaxLandCount()) + "개)");
				return true;
			}
			Economy.reduceMoney(player, price);
		}

		Land land;
		Island island = (Island) this.world;
		land = island.createLand(player);

		Message.normal(player, "성공적으로 " + this.world.getCustomName() +" 땅을 구매하였습니다. 땅 번호 : " + Integer.toString(land.getNumber()));
		return true;
	}
}