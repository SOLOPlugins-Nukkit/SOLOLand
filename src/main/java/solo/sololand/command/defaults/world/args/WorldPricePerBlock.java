package solo.sololand.command.defaults.world.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.solobasepackage.util.Message;

public class WorldPricePerBlock extends SubCommand{

	public WorldPricePerBlock(){
		super("블럭당가격", "땅 생성시 1블럭당 받을 가격을 설정합니다.");
		this.setPermission("sololand.command.world.priceperblock");
		this.addCommandParameters(new CommandParameter[]{
			new CommandParameter("가격", CommandParameter.ARG_TYPE_INT, false)
		});
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		if(args.length < 1){
			return false;
		}
		double pricePerBlock;
		try{
			pricePerBlock = Double.parseDouble(args[0]);
		}catch (Exception e){
			return false;
		}
		World world = World.get(player);
		world.setPricePerBlock(pricePerBlock);
		Message.normal(player, world.getCustomName() + " 월드의 블럭 당 가격을 " + args[0] + "원으로 설정하였습니다.");
		return true;
	}
}