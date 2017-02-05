package solo.sololand.command.defaults.world.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.solobasepackage.util.Message;

public class WorldAllowCombineLand extends SubCommand{

	public WorldAllowCombineLand(){
		super("땅합치기허용", "월드에서 땅을 합칠 수 있는지 여부를 설정합니다.");
		this.setPermission("sololand.command.world.allowcombineland");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);
		world.setAllowCombineLand(! world.isAllowCombineLand());
		Message.normal(player, world.isAllowCombineLand() ? world.getCustomName() + "월드에서 땅을 합칠 수 있도록 설정하였습니다." : world.getCustomName() + "월드에서 땅을 합칠 수 없도록 설정하였습니다.");
		return true;
	}
}
