package com.solo.sololand.command.world;

import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class AllowFight {
  public static void execute(Player player, String[] args){
    World world = World.get(player.getLevel());
    if(world.isAllowFight()){
      world.setAllowFight(false);
      Message.success(player, world.getCustomName() + " 월드의 pvp를 해제하였습니다.");
    }else{
      world.setAllowFight(true);
      Message.success(player, world.getCustomName() + " 월드의 pvp를 켰습니다.");
    }
  }
}