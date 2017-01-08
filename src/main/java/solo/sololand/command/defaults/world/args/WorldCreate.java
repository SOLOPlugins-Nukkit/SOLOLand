package solo.sololand.command.defaults.world.args;

import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.generator.Normal;
import cn.nukkit.level.generator.Flat;
import cn.nukkit.level.generator.Generator;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.generator.*;
import solo.sololand.external.Message;

import java.io.File;

public class WorldCreate extends SubCommand{

	public WorldCreate(){
		super("생성", "월드를 생성합니다. 제너레이터에는 야생, 평지, 섬, 스카이블럭, 평야, 빈월드, 스카이그리드 중 하나를 입력하세요.");
		this.setInGameOnly(false);
		this.setPermission("sololand.command.world.create");
		this.addCommandParameters(new CommandParameter[]{
			new CommandParameter("월드 이름", CommandParameter.ARG_TYPE_STRING, false),
			new CommandParameter("타입(제너레이터)", CommandParameter.ARG_TYPE_STRING, false)
		});
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		if(args.length < 1){
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

		Class<? extends Generator> generator = Normal.class;
		if(args.length > 1){
			switch(args[1]){
				case "평지":
					generator = Flat.class;
					break;
					
				case "섬":
					generator = IslandGenerator.class;
					break;
					
				case "스카이블럭":
					generator = SkyBlockGenerator.class;
					break;
					
				case "평야":
					generator = GridLandGenerator.class;
					break;
					
				case "빈월드":
					generator = EmptyWorldGenerator.class;
					break;
					
				case "스카이그리드":
					generator = SkyGridGenerator.class;
					break;
			}
		}

		File dir = new File(Server.getInstance().getDataPath() + File.separator + "worlds" + File.separator + args[0]);
		dir.mkdir();

		boolean isCreated = Server.getInstance().generateLevel(args[0], 404l, generator);
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