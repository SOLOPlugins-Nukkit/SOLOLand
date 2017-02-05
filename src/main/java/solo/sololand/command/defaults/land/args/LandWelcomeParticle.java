package solo.sololand.command.defaults.land.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.solobasepackage.util.ArrayUtil;
import solo.solobasepackage.util.Message;
import solo.solobasepackage.util.ParticleUtil;

public class LandWelcomeParticle extends SubCommand{

	public LandWelcomeParticle(){
		super("환영효과", "다른 유저가 땅 방문시 나타낼 효과를 설정합니다.");
		this.setPermission("sololand.command.land.welcomeparticle");
		this.addCommandParameters(new CommandParameter[]{
			new CommandParameter(ArrayUtil.implode("/", ParticleUtil.getAvailable()) + " 또는 제거", CommandParameter.ARG_TYPE_STRING, false)
		});
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
			Message.alert(player, "땅 주인이 아니므로 땅 환영 효과를 수정할 수 없습니다.");
			return true;
		}
		if(args.length < 1){
			return false;
		}
		int particle = ParticleUtil.fromString(args[0]);
		if(particle == 0 && ! args[0].equals("제거")){
			return false;
		}
		land.setWelcomeParticle(particle);
		Message.normal(player, particle == 0 ? "성공적으로 환영 효과를 제거하였습니다" : "성공적으로 환영 효과를 설정하였습니다 : " + args[0]);
		return true;
	}
}