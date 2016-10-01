package com.solo.sololand.command.world;

import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class InvenSave {
  public static void execute(Player player, String[] args) {
    World world = World.get(player.getLevel());
    if(world.isInvenSave()){
      world.setInvenSave(false);
      Message.success(player, world.getCustomName() + " 월드의 인벤세이브를 해제하였습니다.");
    }else{
      world.setInvenSave(true);
      Message.success(player, world.getCustomName() + " 월드의 인벤세이브를 켰습니다.");
    }
    return;
  }
}
