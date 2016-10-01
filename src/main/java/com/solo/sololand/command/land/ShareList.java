package com.solo.sololand.command.land;

import cn.nukkit.Server;
import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class ShareList{
  public static void execute(Player player, String[] args){
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
    Message.normal(player, "<====== " + targetName + " 님의 공유받은 땅 목록 ======>");
    StringBuilder sb;
    boolean isZero = true;
    for(World world : World.getAll().values()){
      sb = new StringBuilder();
      boolean comma = false;
      for(Land land : world.getLands(targetName, true).values()){
        if(comma){
          sb.append(", ");
        }else{
          comma = true;
        }
        sb.append(Integer.toString(land.getNumber()) + "번");
      }
      if(comma){
        Message.normal(player, world.getCustomName() + " 월드 : " + sb.toString());
        isZero = false;
      }
    }
    if(isZero){
      Message.normal(player, "공유받은 땅이 없습니다.");
    }
  }
}