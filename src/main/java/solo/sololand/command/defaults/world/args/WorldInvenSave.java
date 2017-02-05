package solo.sololand.command.defaults.world.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.solobasepackage.util.Message;

public class WorldInvenSave extends SubCommand{

	public WorldInvenSave(){
		super("인벤세이브", "월드의 인벤세이브 여부를 설정합니다.");
		this.setPermission("sololand.command.world.invemsave");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);
		if(world.isInvenSave()){
			world.setInvenSave(false);
			Message.normal(sender, world.getCustomName() + " 월드의 인벤세이브를 해제하였습니다.");
		}else{
			world.setInvenSave(true);
			Message.normal(sender, world.getCustomName() + " 월드의 인벤세이브를 켰습니다.");
		}
		return true;
	}
}
