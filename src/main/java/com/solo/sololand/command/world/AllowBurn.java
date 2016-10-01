package com.solo.sololand.command.world;

import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class AllowBurn {
  public static void execute(Player player, String[] args) {
    World world = World.get(player.getLevel());
    if(world.isAllowBurn()){
      world.setAllowBurn(false);
      Message.success(player, world.getCustomName() + " 월드의 폭발허용을 껐습니다.");
    }else{
      world.setAllowBurn(true);
      Message.success(player, world.getCustomName() + " 월드의 폭발허용을 켰습니다.");
    }
    return;
  }
}
