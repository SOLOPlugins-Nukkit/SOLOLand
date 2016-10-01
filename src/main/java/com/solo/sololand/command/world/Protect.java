package com.solo.sololand.command.world;

import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class Protect {
  public static void execute(Player player, String[] args){
    World world = World.get(player.getLevel());
    if(world.isProtected()){
      world.setProtected(false);
      Message.success(player, world.getName() + " 월드의 보호를 해제하였습니다.");
    }else{
      world.setProtected(true);
      Message.success(player, world.getCustomName() + " 월드의 보호를 켰습니다.");
    }
  }
}