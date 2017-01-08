package solo.sololand.command.defaults.world.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.external.Message;

public class WorldSetCustomName extends SubCommand{

	public WorldSetCustomName(){
		super("이름변경", "플러그인에서 사용될 월드 이름을 변경합니다.");
		this.setPermission("sololand.command.world.setcustomname");
		this.addCommandParameters(new CommandParameter[]{
				new CommandParameter("변경할 이름", CommandParameter.ARG_TYPE_RAW_TEXT, false)
			});
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		if(args.length < 1){
			return false;
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < (args.length - 1); i++){
			sb.append(args[i]);
			if(i < args.length - 1){
				sb.append(" ");
			}
		}
		String customName = sb.toString();
		switch(customName){
			case "월드":
			case "땅":
				Message.alert(player, "해당 이름으로 월드 이름을 지을 수 없습니다.");
				return true;
		}
		World world = World.get(player);
		world.setCustomName(customName);
		Message.success(player, world.getName() + " 월드의 이름을 " + customName + " 으로 변경하였습니다.");
		return true;
	}
}