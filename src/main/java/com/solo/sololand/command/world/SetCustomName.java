package com.solo.sololand.command.world;

import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class SetCustomName{
  public static void execute(Player player, String[] args){
    if(args.length < 2){
      Message.usage(player, "/월드 이름 [변경할 이름]");
      return;
    }
    StringBuilder sb = new StringBuilder();
    for(int i = 1; i < args.length; i++){
      sb.append(args[i]);
      if(i < args.length - 1){
        sb.append(" ");
      }
    }
    String customName = sb.toString();
    World world = World.get(player.getLevel());
    world.setCustomName(customName);
    Message.success(player, world.getName() + " 월드의 이름을 " + customName + " 으로 변경하였습니다.");
  }
}