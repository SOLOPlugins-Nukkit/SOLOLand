package solo.sololand.command.defaults.custom.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.solobasepackage.util.Message;

public class CustomDefaultLandPrice extends SubCommand{

	public World world;
	
	public CustomDefaultLandPrice(World world){
		super("땅가격", "월드의 기본 땅 가격을 설정합니다.");
		this.setPermission("sololand.command.custom.defaultlandprice", false);
		this.addCommandParameters(new CommandParameter[]{
				new CommandParameter("가격", CommandParameter.ARG_TYPE_INT, false)
		});
		this.world = world;
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		
		double price;
		try{
			price = Double.parseDouble(args[0]);
		}catch (Exception e){
			return false;
		}
		this.world.setDefaultLandPrice(price);
		Message.normal(player, this.world.getCustomName() + " 월드의 기본 땅 가격을 " + args[0] + "원으로 설정하였습니다.");
		return true;
	}
}