package solo.sololand.command.defaults.world.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.solobasepackage.util.Message;

public class WorldSetSpawn extends SubCommand{

	public WorldSetSpawn(){
		super("스폰", "월드의 스폰을 설정합니다.");
		this.setPermission("sololand.command.world.setspawn");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);
		world.getLevel().setSpawnLocation(player);
		Message.normal(player, world.getName() + " 월드의 스폰위치를 변경하였습니다.");
		return true;
	}
}