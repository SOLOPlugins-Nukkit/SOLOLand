package solo.sololand.command.defaults.land.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.sololand.external.Message;

public class LandAccess extends SubCommand{

	public LandAccess(){
		super("출입허용", "다른 유저의 땅 출입 허용 여부를 설정합니다.");
		this.setPermission("sololand.command.land.access");
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
			Message.alert(player, "땅 주인이 아니므로 땅 출입허용 여부를 설정할 수 없습니다.");
			return true;
		}
		if(land.isAllowAccess()){
			land.setAllowAccess(false);
			Message.success(player, "다른 유저의 출입을 허용하지 않도록 설정하였습니다.");
		}else{
			land.setAllowAccess(true);
			Message.success(player, "다른 유저의 출입을 허용하도록 설정하였습니다.");
		}
		return true;
	}
}
