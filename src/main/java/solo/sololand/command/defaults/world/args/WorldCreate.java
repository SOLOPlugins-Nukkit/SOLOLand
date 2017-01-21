package solo.sololand.command.defaults.world.args;

import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.generator.Generator;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.external.ArrayUtil;
import solo.sololand.external.Message;

import java.io.File;

public class WorldCreate extends SubCommand{

	public WorldCreate(){
		super("생성", "월드를 생성합니다. 제너레이터에는 " + ArrayUtil.implode(", ", Generator.getGeneratorList()) + " 중 하나를 입력하세요.");
		this.setInGameOnly(false);
		this.setPermission("sololand.command.world.create");
		this.addCommandParameters(new CommandParameter[]{
			new CommandParameter("월드 이름", CommandParameter.ARG_TYPE_STRING, false),
			new CommandParameter("타입(제너레이터)", CommandParameter.ARG_TYPE_STRING, false)
		});
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		if(args.length < 2){
			return false;
		}
		args[0] = args[0].toLowerCase();

		World isExist = World.get(args[0]);
		if(Server.getInstance().loadLevel(args[0]) || isExist != null){
			Message.alert(sender, args[0] + "(" + isExist.getCustomName()	+ ") 월드는 이미 생성되어 있습니다.");
			return true;
		}
		switch(args[0]){
			case "월드":
			case "땅":
			case "help":
				Message.alert(sender, "해당 이름으로 월드 이름을 지을 수 없습니다.");
				return true;
		}

		// Cool. now all generator is available
		Class<? extends Generator> generator = null;
		for(String gen : Generator.getGeneratorList()){
			if(args[1].toLowerCase().equals(gen.toLowerCase())){
				generator = Generator.getGenerator(gen);
			}
		}
		if(generator == null){
			return false;
		}

		File dir = new File(Server.getInstance().getDataPath() + File.separator + "worlds" + File.separator + args[0]);
		dir.mkdir();

		//seed is currentTimeMillis
		boolean isCreated = Server.getInstance().generateLevel(args[0], System.currentTimeMillis(), generator);
		if(!isCreated){
			if(!Server.getInstance().loadLevel(args[0])){
				Message.alert(sender, "알 수 없는 오류로 월드 생성에 실패하였습니다.");
				return true;
			}
			Message.success(sender, "성공적으로 " + args[0] + " 월드를 로드하였습니다.");
			return true;
		}
		Message.success(sender, "성공적으로 " + args[0] + " 월드를 생성하였습니다.");
		return true;
	}
}