package solo.sololand.command.defaults.land.args;

import cn.nukkit.Server;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.solobasepackage.util.Message;
import solo.solobasepackage.util.Notification;

public class LandGive extends SubCommand{

	public LandGive(){
		super("양도", "다른 유저에게 땅을 양도합니다.");
		this.setPermission("sololand.command.land.give");
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
		if(!player.isOp() && !land.isOwner(player)){
			Message.alert(player, "땅 주인이 아니므로 땅을 다른 유저에게 양도할 수 없습니다.");
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
			Message.alert(player, "땅 주인에게 땅을 줄 수 없습니다.");
			return true;
		}
		if(world.getMaxLandCount() <= world.getLands(targetName).size()){
			Message.alert(player, target.getName() + "님이 해당 월드에서 소유할 수 있는 땅의 최대 갯수를 초과하였습니다. (최대 " + Integer.toString(world.getMaxLandCount()) + "개)");
			return true;
		}
		land.clear();
		land.setOwner(target);
		Message.normal(player, target.getName() + "님에게 " + Integer.toString(land.getNumber()) + "번 땅을 양도 처리 하였습니다.");
		
		Notification.addNotification(target, player.getName() + "님이 " + world.getCustomName() + " 월드의 " + Integer.toString(land.getNumber()) + "번 땅을 양도하셨습니다.");
		return true;
	}
}