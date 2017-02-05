package solo.sololand.command.defaults.land.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.solobasepackage.util.Message;

public class LandCancelSell extends SubCommand{
 
	public LandCancelSell(){
		super("판매취소", "판매중인 땅의 판매를 취소합니다.");
		this.setPermission("sololand.command.land.cancelsell");
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
		if(!player.isOp() && !land.isOwner(player)){
			Message.alert(player, "땅 주인이 아니므로 땅 판매 여부를 수정할 수 없습니다.");
			return true;
		}
		if(!land.isSail()){
			Message.alert(player, "이 땅은 현재 판매중이 아닙니다.");
			return true;
		}
		land.setSail(false);
		Message.normal(player, "땅 판매를 취소하였습니다.");
		return true;
	}
}