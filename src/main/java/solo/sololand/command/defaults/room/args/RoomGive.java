package solo.sololand.command.defaults.room.args;

import cn.nukkit.Server;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.sololand.land.Room;
import solo.sololand.external.Message;

public class RoomGive extends SubCommand{

	public RoomGive(){
		super("양도", "다른 유저에게 방을 줍니다.");
		this.setPermission("sololand.command.room.give");
		this.addCommandParameters(new CommandParameter[]{
			new CommandParameter("유저", CommandParameter.ARG_TYPE_STRING, false)
		});
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
			Message.alert(player, "땅 주인이 아니므로 방을 다른 유저에게 줄 수 없습니다.");
			return true;
		}
		if(args.length < 1){
			return false;
		}
		Player target = Server.getInstance().getPlayer(args[0]);
		if(target == null){
			Message.alert(player, args[0] + "님은 현재 온라인이 아닙니다.");
			return true;
		}
		String targetName = target.getName().toLowerCase();
		if(land.isOwner(targetName)){
			Message.alert(player, "땅 주인에게 방을 줄 수 없습니다.");
			return true;
		}
		//if(world.getMaxLandCount() < world.getLands(targetName).size()){
		//	Message.alert(player, target.getName() + "님이 해당 월드에서 소유할 수 있는 땅의 최대 갯수를 초과하였습니다. (최대 " + Integer.toString(world.getMaxLandCount()) + "개)");
		//	return true;
		//}
		room.clear();
		room.setOwner(targetName);
		Message.success(player, target.getName() + "님에게 " + Integer.toString(land.getNumber()) + "번 방을 주었습니다.");
		Message.success(target, player.getName() + "님이 " + world.getCustomName() + " 월드의 " + room.getAddress() + "번 방을 양도하셨습니다.");
		return true;
	}
}