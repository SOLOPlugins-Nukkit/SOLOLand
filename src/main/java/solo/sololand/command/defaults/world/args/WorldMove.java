package solo.sololand.command.defaults.world.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.external.Message;

public class WorldMove extends SubCommand{

	public WorldMove(){
		super("이동", "해당 월드로 이동합니다.");
		this.setPermission("sololand.command.world.move");
		this.addCommandParameters(new CommandParameter[]{
			new CommandParameter("월드 이름", CommandParameter.ARG_TYPE_STRING, false)
		});
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		if(args.length < 1){
			return false;
		}
		World target;
		target = World.getByName(args[0]);
		if(target == null){
			target = World.get(args[0]);
			if(target == null){
				Message.alert(player, args[0] + "은(는) 존재하지 않는 월드입니다.");
				return true;
			}
		}
		player.teleport(target.getLevel().getSpawnLocation());
		Message.success(player, target.getCustomName() + " 월드로 이동하였습니다.");
		return true;
	}
}