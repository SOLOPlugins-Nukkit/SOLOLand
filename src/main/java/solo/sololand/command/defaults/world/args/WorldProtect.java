package solo.sololand.command.defaults.world.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.external.Message;

public class WorldProtect extends SubCommand{

	public WorldProtect(){
		super("보호", "월드의 보호 여부를 설정합니다.");
		this.setPermission("sololand.command.world.protect");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);
		if(world.isProtected()){
			world.setProtected(false);
			Message.success(player, world.getName() + " 월드의 보호를 해제하였습니다.");
		}else{
			world.setProtected(true);
			Message.success(player, world.getCustomName() + " 월드의 보호를 켰습니다.");
		}
		return true;
	}
}