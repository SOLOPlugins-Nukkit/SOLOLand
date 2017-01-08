package solo.sololand.command.defaults.world.args;

import cn.nukkit.Server;

import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Level;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.external.Message;

public class WorldUserInfo extends SubCommand{

	public WorldUserInfo(){
		super("유저정보", "월드의 유저 정보를 확인합니다.");
		this.setPermission("sololand.command.world.userinfo");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		Level level = player.getLevel();
		World world = World.get(level);
		StringBuilder sb = new StringBuilder();
		ArrayList<String> information = new ArrayList<String>();
		information.add("전체 " + Integer.toString(Server.getInstance().getOnlinePlayers().size()) + "명의 유저 중 " + Integer.toString(level.getPlayers().size()) + "명의 유저가 " + world.getCustomName() + " 월드에 있습니다.");
		boolean comma = false;
		for(Player p : level.getPlayers().values()){
			if(comma){
				sb.append(", ");
			}else{
				comma = true;
			}
			sb.append(p.getName());
		}
		information.add(sb.toString());
		Message.info(player, world.getCustomName() + " 월드 유저 정보", information);
		return true;
	}
}