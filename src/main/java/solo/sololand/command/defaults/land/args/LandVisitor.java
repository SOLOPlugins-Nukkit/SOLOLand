package solo.sololand.command.defaults.land.args;

import cn.nukkit.level.Level;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.sololand.external.Message;

public class LandVisitor extends SubCommand{

	public LandVisitor(){
		super("방문자", "현재 땅을 방문중인 유저 목록을 확인합니다.");
		this.setPermission("sololand.command.land.visitor");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		Level level = player.getLevel();
		World world = World.get(level);
		Land land = world.getLand(player);

		if(land == null){
			Message.alert(player, "현재 위치에서 땅을 찾을 수 없습니다.");
			return true;
		}

		StringBuilder sb = new StringBuilder();
		int count = 0;
		for(Player p : player.getLevel().getPlayers().values()){
			if(land.isInside(p)){
				if(count++ > 0){
					sb.append(", ");
				}
				sb.append(p.getName());
			}
		}

		Message.normal(player, Integer.toString(land.getNumber()) + "번 땅 방문자 : " + sb.toString() + " ( " + Integer.toString(count) + "명 )");
		return true;
	}
}