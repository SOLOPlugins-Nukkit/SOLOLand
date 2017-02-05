package solo.sololand.command.defaults.world.args;

import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.solobasepackage.util.Message;

public class WorldInfo extends SubCommand{

	public WorldInfo(){
		super("정보", "월드의 정보를 확인합니다.");
		this.setPermission("sololand.command.world.info");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);
		ArrayList<String> information = new ArrayList<String>();
		information.add("월드 이름 : " + world.getLevel().getName());
		information.add("월드 폴더 이름 : " + world.getName());
		information.add("설정 정보 보기 > /월드 설정정보");
		information.add("유저 정보 보기 > /월드 유저정보");
		information.add("땅 정보 보기 > /월드 땅정보");
		Message.info(player, world.getCustomName() + " 월드 정보", information);
		return true;
	}
}