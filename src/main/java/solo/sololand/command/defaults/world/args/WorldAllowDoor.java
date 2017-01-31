package solo.sololand.command.defaults.world.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.external.Message;

public class WorldAllowDoor extends SubCommand{

	public WorldAllowDoor(){
		super("문허용", "월드의 문 사용 허용 여부를 설정합니다.");
		this.setPermission("sololand.command.world.allowdoor");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);
		world.setAllowDoor(! world.isAllowDoor());
		Message.success(player, world.isAllowDoor() ? world.getCustomName() + "월드에서 문을 허용하도록 설정하였습니다." : world.getCustomName() + "월드에서 문을 허용하지 않도록 설정하였습니다.");
		return true;
	}
}
