package solo.sololand.command.defaults.world.args;

import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.sololand.external.Message;

public class WorldLandInfo extends SubCommand{

	public WorldLandInfo(){
		super("땅정보", "월드의 땅 정보를 확인합니다.");
		this.setPermission("sololand.command.world.landinfo");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);
		ArrayList<String> information = new ArrayList<String>();
		int all = 0;
		int sail = 0;
		int notowned = 0;
		int owned = 0;
		for(Land land : world.getLands().values()){
			++all;
			if(land.isSail()){
				++sail;
			}
			if(land.isOwner("")){
				++notowned;
			}else{
				++owned;
			}
		}
		information.add("전체 땅 갯수 : " + Integer.toString(all) + "개");
		information.add("유저가 소유중인 땅 갯수 : " + Integer.toString(owned) + "개");
		information.add("판매중인 땅 갯수 : " + Integer.toString(sail) + "개");
		information.add("주인이 없는 땅 갯수 : " + Integer.toString(notowned) + "개");
		Message.info(player, world.getCustomName() + " 월드 땅 정보", information);
		return true;
	}
}