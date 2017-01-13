package solo.sololand.command.defaults.world.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.external.Message;

public class WorldAllowResizeLand extends SubCommand{

	public WorldAllowResizeLand(){
		super("땅크기변경허용", "월드의 땅 확장/축소 허용 여부를 설정합니다.");
		this.setPermission("sololand.command.world.allowresizeland");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);
		if(world.isAllowResizeLand()){
			world.setAllowResizeLand(false);
			Message.success(sender, world.getCustomName() + " 월드의 땅 크기변경을 금지하였습니다.");
		}else{
			world.setAllowResizeLand(true);
			Message.success(sender, world.getCustomName() + " 월드의 땅 크기변경을 허용하였습니다.");
		}
		return true;
	}
}