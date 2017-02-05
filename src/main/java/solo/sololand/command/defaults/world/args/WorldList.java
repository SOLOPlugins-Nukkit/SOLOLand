package solo.sololand.command.defaults.world.args;

import java.util.ArrayList;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.solobasepackage.util.Message;

public class WorldList extends SubCommand{

	public WorldList(){
		super("목록", "월드 목록을 확인합니다.");
		this.setInGameOnly(false);
		this.setPermission("sololand.command.world.list");
		this.addCommandParameters(new CommandParameter[]{
			new CommandParameter("페이지", CommandParameter.ARG_TYPE_INT, true)
		});
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		ArrayList<String> lines = new ArrayList<String>();
		int page = 1;
		try{
			page = Integer.parseInt(args[0]);
		}catch(Exception e){
			
		}
		for(World world : World.getAll().values()){
			lines.add(world.getCustomName() + " (폴더 이름 : " + world.getName() + "), 제너레이터 : " + world.getLevel().getProvider().getGenerator());
		}
		Message.page(sender, "월드 목록", lines, page);
		return true;
	}
}