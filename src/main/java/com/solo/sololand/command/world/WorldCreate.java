package com.solo.sololand.command.world;

import cn.nukkit.Server;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.generator.Normal;
import cn.nukkit.level.generator.Flat;
import cn.nukkit.utils.Config;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.generator.*;
import com.solo.sololand.util.Message;

import java.util.LinkedHashMap;
import java.io.File;

public class WorldCreate extends SubCommand{

  public WorldCreate(MainCommand mCmd){
    super(mCmd, "생성", "월드를 생성합니다. 제너레이터에는 야생, 평지, 섬, 스카이블럭, 평야, 빈월드, 스카이그리드 중 하나를 입력하세요. 월드 이름은 영어로 하는 것을 권장합니다.");
    this.setUsage("/" + mCmd.getName() + " " + this.getName() + " <타입(제너레이터)> <옵션>");
    this.setAliases(new String[]{"만들기", "추가"});
    this.setInGameOnly(false);
    this.setPermission("sololand.command.world.create");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    if(args.length < 1){
      return false;
    }
    args[0] = args[0].toLowerCase();

    World isExist = World.get(args[0]);
    if(Server.getInstance().loadLevel(args[0]) || isExist != null){
      Message.fail(sender, args[0] + "(" + isExist.getCustomName()  + ") 월드는 이미 생성되어 있습니다.");
      return true;
    }
    switch(args[0]){
      case "월드":
      case "땅":
      case "help":
        Message.fail(sender, "해당 이름으로 월드 이름을 지을 수 없습니다.");
        return true;
    }

    Class generator = Normal.class;
    int worldType = World.TYPE_NORMAL;
    if(args.length > 1){
      switch(args[1]){
        case "평지":
          generator = Flat.class;
          break;
        case "섬":
          generator = IslandGenerator.class;
          worldType = World.TYPE_ISLAND;
          break;
        case "스카이블럭":
          generator = SkyBlockGenerator.class;
          worldType = World.TYPE_ISLAND;
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
    LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();
    data.put("type", worldType);
    Config conf = new Config(new File(dir + File.separator + "properties.yml"), Config.YAML, data);
    conf.save();
    boolean isCreated = Server.getInstance().generateLevel(args[0], 404l, generator);
    if(!isCreated){
      if(!Server.getInstance().loadLevel(args[0])){
        Message.fail(sender, "알 수 없는 오류로 월드 생성에 실패하였습니다.");
        return true;
      }
      Message.success(sender, "성공적으로 " + args[0] + " 월드를 로드하였습니다.");
      return true;
    }
    Message.success(sender, "성공적으로 " + args[0] + " 월드를 생성하였습니다.");
    return true;
  }
}