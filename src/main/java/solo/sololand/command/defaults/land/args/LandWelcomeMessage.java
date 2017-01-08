package solo.sololand.command.defaults.land.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.sololand.external.Message;

public class LandWelcomeMessage extends SubCommand{

	public LandWelcomeMessage(){
		super("환영말", "다른 유저가 땅 방문시 보낼 메세지를 설정합니다.");
		this.setPermission("sololand.command.land.welcomemessage");
		this.addCommandParameters(new CommandParameter[]{
			new CommandParameter("환영말...", CommandParameter.ARG_TYPE_RAW_TEXT, false)
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
			Message.alert(player, "땅 주인이 아니므로 땅 환영말을 수정할 수 없습니다.");
			return true;
		}
		if(args.length < 1){
			return false;
		}
		StringBuilder sb = new StringBuilder();
		boolean f = true;
		for(String arg : args){
			if(f){
				f = false;
			}else{
				sb.append(" ");
			}
			sb.append(arg);
		}
		String welcomeMsg = sb.toString();
		land.setWelcomeMessage(welcomeMsg);
		Message.success(player, "성공적으로 환영말을 설정하였습니다 : " + welcomeMsg);
		return true;
	}
}