package com.solo.sololand.command.island;

import cn.nukkit.Server;
import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.world.Island;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

import java.util.HashMap;

public class List{
  public static void execute(Player player, String[] args){
    if(!World.isExistIsland()){
      Message.fail(player, "섬 월드가 생성되어 있지 않습니다.");
      return;
    }
    Island island = (Island) World.get("island");
    String targetName;
    if(args.length < 2){
      targetName = player.getName();
    }else{
      Player target = Server.getInstance().getPlayer(args[1]);
      if(target != null){
        targetName = target.getName();
      }else{
        targetName = args[1];
      }
    }
    StringBuilder sb = new StringBuilder();
    boolean comma = false;
    boolean isZero = true;
    for(Land land : island.getLands(targetName).values()){
      if(comma){
        sb.append(", ");
      }else{
        comma = true;
      }
      sb.append(Integer.toString(land.getNumber()) + "번");
      isZero = false;
    }
    if(isZero){
      Message.normal(player, targetName + "님은 섬을 소유하고 있지 않습니다.");
    }else{
      Message.normal(player, targetName + "님의 섬 목록 : " + sb.toString());
    }
  }
}