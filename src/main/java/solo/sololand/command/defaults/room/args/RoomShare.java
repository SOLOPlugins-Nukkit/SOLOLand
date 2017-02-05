package solo.sololand.command.defaults.room.args;

import cn.nukkit.Server;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.sololand.land.Room;
import solo.solobasepackage.util.Message;

public class RoomShare extends SubCommand{

	public RoomShare(){
		super("공유", "다른 유저와 방을 공유합니다.");
		this.setPermission("sololand.command.land.share");
		this.addCommandParameters(new CommandParameter[]{
			new CommandParameter("유저", CommandParameter.ARG_TYPE_STRING, false)
		});
		this.addCommandParameters(new CommandParameter[]{
			new CommandParameter("유저", CommandParameter.ARG_TYPE_STRING, false),
			new CommandParameter("유저", CommandParameter.ARG_TYPE_STRING, true),
			new CommandParameter("유저...", CommandParameter.ARG_TYPE_RAW_TEXT, true)
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
		if(!player.isOp() && !land.isOwner(player) && !room.isOwner(player)){
			Message.alert(player, "방 주인이 아니므로 방 공유목록을 수정할 수 없습니다.");
			return true;
		}
		if(args.length < 1){
			return false;
		}
		Player target;
		String targetName;
		for(String arg : args){
			target = Server.getInstance().getPlayer(arg);
			if(target == null){
				Message.alert(player, arg + "님은 현재 온라인이 아닙니다.");
				continue;
			}else{
				targetName = target.getName().toLowerCase();
			}
			if(room.isOwner(targetName)){
				Message.alert(player, "방을 주인에게 공유할 수 없습니다.");
				continue;
			}
			if (room.isMember(targetName)){
				Message.alert(player, target.getName() + "님은 이미 공유 되어있습니다.");
				continue;
			}
			room.addMember(targetName);
			Message.normal(player, target.getName() + "님에게 방을 공유하였습니다.");
			Message.normal(target, player.getName() + "님이 " + world.getCustomName() + " 월드의 " + room.getAddress() + "번 방을 공유하셨습니다.");
		}
		return true;
	}
}