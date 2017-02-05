package solo.sololand.command.defaults.land.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.solobasepackage.util.Message;

public class LandAllowFight extends SubCommand{

	public LandAllowFight(){
		super("pvp허용", "땅 pvp 허용 여부를 설정합니다.");
		this.setPermission("sololand.command.land.allowfight");
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
		if(!player.isOp() && !land.isOwner(player.getName().toLowerCase())){
			Message.alert(player, "땅 주인이 아니므로 땅 전투가능 여부를 수정할 수 없습니다.");
			return true;
		}
		if(land.isAllowFight()){
			land.setAllowFight(false);
			Message.normal(player, "PVP를 허용하지 않도록 설정하였습니다.");
		}else{
			land.setAllowFight(true);
			Message.normal(player, "PVP를 허용하도록 설정하였습니다.");
		}
		return true;
	}
}