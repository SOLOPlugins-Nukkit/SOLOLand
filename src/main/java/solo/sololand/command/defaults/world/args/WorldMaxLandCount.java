package solo.sololand.command.defaults.world.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.external.Message;

public class WorldMaxLandCount extends SubCommand{

	public WorldMaxLandCount(){
		super("땅최대갯수", "소지 가능한 땅 최대 갯수를 설정합니다.");
		this.setPermission("sololand.command.world.maxlandcount");
		this.addCommandParameters(new CommandParameter[]{
			new CommandParameter("갯수", CommandParameter.ARG_TYPE_INT, false)
		});
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		if(args.length < 1){
			return false;
		}
		int maxCount;
		try{
			maxCount = Integer.parseInt(args[0]);
		}catch (Exception e){
			return false;
		}
		Player player = (Player) sender;
		World world = World.get(player);
		world.setMaxLandCount(maxCount);
		Message.success(player, world.getCustomName() + " 월드의 땅 소지 가능 최대 갯수를 " + args[0] + "개로 설정하였습니다.");
		return true;
	}
}