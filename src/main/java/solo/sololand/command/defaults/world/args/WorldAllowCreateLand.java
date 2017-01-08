package solo.sololand.command.defaults.world.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.external.Message;

public class WorldAllowCreateLand extends SubCommand{

	public WorldAllowCreateLand(){
		super("땅생성허용", "월드의 땅 생성 허용 여부를 설정합니다.");
		this.setPermission("sololand.command.world.allowcreateland");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);
		if(world.isAllowCreateLand()){
			world.setAllowCreateLand(false);
			Message.success(sender, world.getCustomName() + " 월드의 땅 생성을 금지하였습니다.");
		}else{
			world.setAllowCreateLand(true);
			Message.success(sender, world.getCustomName() + " 월드의 땅 생성을 허용하였습니다.");
		}
		return true;
	}
}