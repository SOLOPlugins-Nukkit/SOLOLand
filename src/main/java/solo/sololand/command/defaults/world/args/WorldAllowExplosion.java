package solo.sololand.command.defaults.world.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.solobasepackage.util.Message;

public class WorldAllowExplosion extends SubCommand{

	public WorldAllowExplosion(){
		super("폭발허용", "월드의 폭발 허용 여부를 설정합니다.");
		this.setPermission("sololand.command.world.allowexplosion");
	}

	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);
		if(world.isAllowExplosion()){
			world.setAllowExplosion(false);
			Message.normal(sender, world.getCustomName() + " 월드의 폭발허용을 껐습니다.");
		}else{
			world.setAllowExplosion(true);
			Message.normal(sender, world.getCustomName() + " 월드의 폭발허용을 켰습니다.");
		}
		return true;
	}
}
