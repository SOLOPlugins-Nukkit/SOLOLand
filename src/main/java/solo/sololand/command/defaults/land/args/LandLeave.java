package solo.sololand.command.defaults.land.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.sololand.external.Message;

public class LandLeave extends SubCommand{

	public LandLeave(){
		super("나가기", "공유받던 땅에서 나갑니다.");
		this.setPermission("sololand.command.land.leave");
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
		if(land.isOwner(player)){
			Message.alert(player, "땅 주인은 나갈 수 없습니다.");
			return true;
		}
		if(! land.isMember(player)){
			Message.alert(player, "이 땅을 공유받고 있지 않습니다.");
			return true;
		}
		land.removeMember(player);
		Message.success(player, "성공적으로 공유받던 땅에서 나갔습니다.");
		return true;
	}
}