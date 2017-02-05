package solo.sololand.command.defaults.world.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.solobasepackage.util.Message;

public class WorldMaxLandLength extends SubCommand{

	public WorldMaxLandLength(){
		super("땅최대길이", "땅 생성 가능 최대 길이를 설정합니다.");
		this.setPermission("sololand.command.world.maxlandlength");
		this.addCommandParameters(new CommandParameter[]{
			new CommandParameter("길이(단위:블럭)", CommandParameter.ARG_TYPE_INT, false)
		});
	}

	public boolean execute(CommandSender sender, String[] args){
		if(args.length < 1){
			return false;
		}
		int maxLength;
		try{
			maxLength = Integer.parseInt(args[0]);
		}catch (Exception e){
			return false;
		}
		Player player = (Player) sender;
		World world = World.get(player);
		world.setMaxLandLength(maxLength);
		Message.normal(player, world.getCustomName() + " 월드의 땅 생성 가능 최대 길이를 " + args[0] + "블럭으로 설정하였습니다.");
		return true;
	}
}