package com.solo.sololand.command.world;

import cn.nukkit.Server;
import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.world.Island;
import com.solo.sololand.generator.IslandGenerator;
import com.solo.sololand.util.Message;

import java.io.File;

public class Create{
  public static void execute(Player player, String[] args){
    if(!player.isOp()){
      Message.fail(player, "권한이 없습니다.");
      return;
    }
    if(args.length < 2){
      Message.usage(player, "/월드 생성 [이름] <제너레이터> <옵션>");
      Message.normal(player, "TIP : 제너레이터 : 야생, 평지, 섬, 스카이블럭, 평야, 빈월드");
      Message.normal(player, "TIP : <>는 입력하지 않으셔도 됩니다.");
      Message.normal(player, "TIP : 섬, 스카이블럭, 평야는 월드 하나만 생성가능합니다.");
      return;
    }
    args[1] = args[1].toLowerCase();
    if(World.get(args[1]) != null){
      Message.fail(player, args[1] + " 월드는 이미 생성되어 있습니다.");
      return;
    }
    (new File(Server.getInstance().getDataPath() + File.separator + "worlds" + File.separator + args[1])).mkdir();
    Class generator;
    String options;
    if(args.length > 3){
      Class generator = IslandGenerator.class;
      if(args.length > 4){
      }
    }
    boolean created = Server.getInstance().generateLevel("island", 404l, IslandGenerator.class);
    if(!created){
      if(!Server.getInstance().loadLevel("island")){
        Message.fail(player, "알 수 없는 오류로 섬 월드 생성에 실패하였습니다.");
        return;
      }
      Message.success(player, "성공적으로 " + args[1] + " 월드를 로드하였습니다.");
      return;
    }
    Message.success(player, "성공적으로 " + args[1] + " 월드를 생성하였습니다.");
  }
}