package solo.sololand.command.defaults.room.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.sololand.land.Room;
import solo.solobasepackage.util.Message;

public class RoomSetSpawn extends SubCommand{

	public RoomSetSpawn(){
		super("스폰", "방 이동시 텔레포트될 지점을 설정합니다.");
		this.setPermission("sololand.command.room.setspawn");
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
		Room room = land.getRoom(player);
		if(room == null){
			Message.alert(player, "현재 위치에서 방을 찾을 수 없습니다.");
			return true;
		}
		if(!player.isOp() && !land.isOwner(player) && !room.isOwner(player)){
			Message.alert(player, "방 주인이 아니므로 방 스폰을 수정할 수 없습니다.");
			return true;
		}
		room.setSpawnPoint(player);
		Message.normal(player, "방 스폰 위치를 변경하였습니다.");
		return true;
	}
}
