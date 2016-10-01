package com.solo.sololand.command.land;

import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class SetSpawn{
  public static void execute(Player player, String[] args){
    World world = World.get(player.getLevel());
    Land land = world.getLand(player.getFloorX(), player.getFloorZ());
    if(land == null){
      Message.fail(player, "현재 위치에서 땅을 찾을 수 없습니다.");
      return;
    }
    if(!player.isOp() && !land.isOwner(player.getName().toLowerCase())){
      Message.fail(player, "땅 주인이 아니므로 땅 스폰을 수정할 수 없습니다.");
      return;
    }
    land.setSpawnPoint(player.getFloorX() + 0.5, player.getFloorY() + 0.05, player.getFloorZ() +0.5);
    Message.success(player, "땅 스폰 위치를 변경하였습니다.");
  }
}
