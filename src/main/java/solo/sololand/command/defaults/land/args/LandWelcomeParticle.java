package solo.sololand.command.defaults.land.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.particle.Particle;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.sololand.external.Message;

public class LandWelcomeParticle extends SubCommand{

	public LandWelcomeParticle(){
		super("환영효과", "다른 유저가 땅 방문시 나타낼 효과를 설정합니다.");
		this.setPermission("sololand.command.land.welcomeparticle");
		this.addCommandParameters(new CommandParameter[]{
			new CommandParameter("물방울/반짝/연기/하트/용암/불/제거", CommandParameter.ARG_TYPE_STRING, false)
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
		int particle;
		switch(args[0]){
			case "물방울":
				particle = Particle.TYPE_BUBBLE;
				break;
			case "반짝":
				particle = Particle.TYPE_CRITICAL;
				break;
			case "연기":
				particle = Particle.TYPE_SMOKE;
				break;
			case "하트":
				particle = Particle.TYPE_HEART;
				break;
			case "용암":
				particle = Particle.TYPE_LAVA;
				break;
			case "불":
				particle = Particle.TYPE_FLAME;
				break;
			case "제거":
				particle = 0;
				break; 
			default:
				return false;
		}
		land.setWelcomeParticle(particle);
		Message.success(player, "성공적으로 환영 효과를 설정하였습니다 : " + args[0]);
		return true;
	}
}