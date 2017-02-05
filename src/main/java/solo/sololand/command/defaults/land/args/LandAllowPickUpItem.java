package solo.sololand.command.defaults.land.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.solobasepackage.util.Message;

public class LandAllowPickUpItem extends SubCommand{

	public LandAllowPickUpItem(){
		super("아이템줍기허용", "다른 유저가 아이템을 주울 수 있는지 여부를 설정합니다.");
		this.setPermission("sololand.command.land.allowpickupitem");
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
		if(
			!player.isOp() &&
			!land.isOwner(player.getName().toLowerCase())
		){
			Message.alert(player, "땅 주인이 아니므로 땅 아이템 드랍 여부를 수정할 수 없습니다.");
			return true;
		}
		if(land.isAllowPickUpItem()){
			land.setAllowPickUpItem(false);
			Message.normal(player, "다른 유저가 아이템을 주울 수 없도록 설정하였습니다.");
		}else{
			land.setAllowPickUpItem(true);
			Message.normal(player, "다른 유저가 아이템을 주울 수 있도록 설정하였습니다.");
		}
		return true;
	}
}