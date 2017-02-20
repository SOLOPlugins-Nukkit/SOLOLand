package solo.sololand.command.defaults.land.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.land.Land;
import solo.sololand.world.World;
import solo.solobasepackage.util.Message;
import solo.solobasepackage.util.EncryptUtil;

public class LandTest extends SubCommand{

	public LandTest(){
		super("테스트", "땅을 생성합니다. (테스트)");
		this.setPermission("sololand.command.land.test", false);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);

		for(int x = 1; x < 100; x++){
			for(int z = 1; z < 100; z++){
				Land land = world.createLand(x * 6 - 5, z * 6 - 5, x * 6, z * 6, EncryptUtil.certificationCode(10));
				world.setLand(land);
			}
		}
		Message.normal(sender, "테스트 완료");
		return true;
	}
}