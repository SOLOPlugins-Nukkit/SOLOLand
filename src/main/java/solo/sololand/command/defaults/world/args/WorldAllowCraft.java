package solo.sololand.command.defaults.world.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.external.Message;

public class WorldAllowCraft extends SubCommand{

	public WorldAllowCraft(){
		super("조합대허용", "월드의 조합대 허용 여부를 설정합니다.");
		this.setPermission("sololand.command.world.allowcraft");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);
		world.setAllowCraft(! world.isAllowCraft());
		Message.success(player, world.isAllowCraft() ? world.getCustomName() + "월드에서 조합대를 허용하도록 설정하였습니다." : world.getCustomName() + "월드에서 조합대를 허용하지 않도록 설정하였습니다.");
		return true;
	}
}
