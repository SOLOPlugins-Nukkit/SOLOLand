package com.solo.sololand.command.world;

import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class AllowCreateLand{
  public static void execute(Player player, String[] args){
    World world = World.get(player.getLevel());
    if(world.isAllowCreateLand()){
      world.setAllowCreateLand(false);
      Message.success(player, world.getCustomName() + " 월드의 땅 생성을 금지하였습니다.");
    }else{
      world.setAllowCreateLand(true);
      Message.success(player, world.getCustomName() + " 월드의 땅 생성을 허용하였습니다.");
    }
  }
}