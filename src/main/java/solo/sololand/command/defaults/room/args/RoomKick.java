package solo.sololand.command.defaults.room.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.sololand.land.Room;
import solo.sololand.external.Message;

public class RoomKick extends SubCommand{

	public RoomKick(){
		super("추방", "방 주인을 추방합니다.");
		this.setPermission("sololand.command.room.kick");
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
		if(!player.isOp() && !land.isOwner(player)){
			Message.alert(player, "땅 주인이 아니므로 방 주인을 추방할 수 없습니다.");
			return true;
		}
		Message.normal(player, room.getOwner() + "님을 방에서 추방시켰습니다.");
		room.setOwner("");
		return true;
	}
}