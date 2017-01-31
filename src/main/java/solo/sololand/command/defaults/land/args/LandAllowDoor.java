package solo.sololand.command.defaults.land.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.sololand.external.Message;

public class LandAllowDoor extends SubCommand{

	public LandAllowDoor(){
		super("문허용", "다른 사람이 문을 열 수 있는지 여부를 설정합니다.");
		this.setPermission("sololand.command.land.allowdoor");
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
			Message.alert(player, "땅 주인이 아니므로 문 허용 여부를 수정할 수 없습니다.");
			return true;
		}
		land.setAllowDoor(! land.isAllowDoor());
		Message.success(player, land.isAllowDoor() ? "문을 허용하도록 설정하였습니다." : "문을 허용하지 않도록 설정하였습니다.");
		return true;
	}
}