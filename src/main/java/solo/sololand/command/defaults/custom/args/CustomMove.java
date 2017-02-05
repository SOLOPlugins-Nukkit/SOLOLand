package solo.sololand.command.defaults.custom.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Position;

import solo.sololand.command.SubCommand;
import solo.sololand.land.Land;
import solo.sololand.world.World;
import solo.solobasepackage.util.Message;

public class CustomMove extends SubCommand{

	public World world;
	
	public CustomMove(World world){
		super("이동", "해당 번호의 " + world.getCustomName() + " 월드 땅으로 이동합니다.");
		this.setPermission("sololand.command.custom.move");
		this.addCommandParameters(new CommandParameter[]{
			new CommandParameter("땅 번호", CommandParameter.ARG_TYPE_INT, true)
		});
		this.world = world;
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		
		if(args.length < 1){
			player.teleport(this.world.getLevel().getSpawnLocation());
			return false;
		}
		int num;
		try{
			num = Integer.parseInt(args[0]);
		} catch (Exception e){
			return false;
		}
		if(num < 1){
			return false;
		}
		Land land = this.world.getLand(num);
		if(land == null){
			Message.alert(player, args[0] + "번 땅은 존재하지 않습니다.");
			return true;
		}
		if(
			!player.isOp() &&
			!land.isOwner(player) &&
			!land.isMember(player) &&
			!land.isAllowAccess()
		){
			Message.alert(player, args[0] + "번 땅은 현재 출입이 제한되어 있습니다.");
			return true;
		}
		player.teleport(Position.fromObject(land.getSpawnPoint(), this.world.getLevel()));
		Message.normal(player, args[0] + "번 땅으로 이동하였습니다.");
		return true;
	}
}