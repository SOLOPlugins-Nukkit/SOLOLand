package solo.sololand.command.defaults.world.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.external.Message;

public class WorldEnableCommand extends SubCommand{

	public WorldEnableCommand(){
		super("명령어사용", "월드 명령어 사용 여부를 설정합니다.");
		this.setPermission("sololand.command.world.enablecommand");
	}

	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);
		if(world.isEnableWorldCommand()){
			world.setEnableWorldCommand(false);
			Message.success(sender, world.getCustomName() + " 월드 명령어를 껐습니다.");
		}else{
			world.setEnableWorldCommand(true);
			Message.success(sender, world.getCustomName() + " 월드 명령어를 켰습니다.");
		}
		return true;
	}
}
