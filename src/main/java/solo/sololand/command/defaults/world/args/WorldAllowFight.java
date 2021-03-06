package solo.sololand.command.defaults.world.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.solobasepackage.util.Message;

public class WorldAllowFight extends SubCommand{

	public WorldAllowFight(){
		super("pvp허용", "월드의 전투 허용 여부를 설정합니다.");
		this.setPermission("sololand.command.world.allowfight");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);
		if(world.isAllowFight()){
			world.setAllowFight(false);
			Message.normal(sender, world.getCustomName() + " 월드의 pvp를 해제하였습니다.");
		}else{
			world.setAllowFight(true);
			Message.normal(sender, world.getCustomName() + " 월드의 pvp를 켰습니다.");
		}
		return true;
	}
}