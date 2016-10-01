package com.solo.sololand.command.island;

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
    if(World.isExistIsland()){
      Message.fail(player, "섬 월드가 이미 생성되어 있습니다.");
      return;
    }
    (new File(Server.getInstance().getDataPath() + File.separator + "worlds" + File.separator + "island")).mkdir();
    boolean created = Server.getInstance().generateLevel("island", 404l, IslandGenerator.class);
    if(!created){
      if(!Server.getInstance().loadLevel("island")){
        Message.fail(player, "알 수 없는 오류로 섬 월드 생성에 실패하였습니다.");
        return;
      }
      Message.success(player, "성공적으로 섬 월드를 로드하였습니다.");
      return;
    }
    Message.success(player, "성공적으로 섬 월드를 생성하였습니다.");
  }
}