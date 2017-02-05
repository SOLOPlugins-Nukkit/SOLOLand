package solo.sololand.command.defaults.world.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.solobasepackage.util.Message;

public class WorldAllowBurn extends SubCommand{

	public WorldAllowBurn(){
		super("불번짐허용", "월드의 불 번짐 여부를 설정합니다.");
		this.setPermission("sololand.command.world.allowburn");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);
		if(world.isAllowBurn()){
			world.setAllowBurn(false);
			Message.normal(sender, world.getCustomName() + " 월드의 불 번짐을 껐습니다.");
		}else{
			world.setAllowBurn(true);
			Message.normal(sender, world.getCustomName() + " 월드의 불 번짐을 켰습니다.");
		}
		return true;
	}
}
