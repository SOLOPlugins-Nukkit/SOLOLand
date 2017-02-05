package solo.sololand.command.defaults.land.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.solobasepackage.util.Message;

public class LandSetSpawn extends SubCommand{

	public LandSetSpawn(){
		super("스폰", "땅 이동시 텔레포트될 지점을 설정합니다.");
		this.setPermission("sololand.command.land.setspawn");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);
		Land land = world.getLand(player);
		if(land == null){
			Message.alert(player, "현재 위치에서 땅을 찾을 수 없습니다.");
			return true;
		}
		if(!player.isOp() && !land.isOwner(player.getName().toLowerCase())){
			Message.alert(player, "땅 주인이 아니므로 땅 스폰을 수정할 수 없습니다.");
			return true;
		}
		land.setSpawnPoint(player);
		Message.normal(player, "땅 스폰 위치를 변경하였습니다.");
		return true;
	}
}
