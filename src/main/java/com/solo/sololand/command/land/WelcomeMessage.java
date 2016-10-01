package com.solo.sololand.command.land;

import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class WelcomeMessage{
  public static void execute(Player player, String[] args){
    World world = World.get(player.getLevel());
    Land land = world.getLand(player.getFloorX(), player.getFloorZ());
    if(land == null){
      Message.fail(player, "현재 위치에서 땅을 찾을 수 없습니다.");
      return;
    }
    if(!player.isOp() && !land.isOwner(player.getName().toLowerCase())){
      Message.fail(player, "땅 주인이 아니므로 땅 환영말을 수정할 수 없습니다.");
      return;
    }
    if(args.length < 2){
      Message.usage(player, "/땅 환영말 [환영말...]");
      return;
    }
    StringBuilder sb = new StringBuilder();
    for(int i = 1; i < args.length; i++){
      sb.append(args[i]);
      if(i < args.length){
        sb.append(" ");
      }
    }
    String welcomeMsg = sb.toString();
    land.setWelcomeMessage(welcomeMsg);
    Message.success(player, "성공적으로 환영말을 설정하였습니다 : " + welcomeMsg);
  }
}