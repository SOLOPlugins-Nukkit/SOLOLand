package com.solo.sololand.command.land;

import cn.nukkit.level.Level;
import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class Visitor{
  public static void execute(Player player, String[] args){
    Level level = player.getLevel();
    World world = World.get(level);
    Land land = world.getLand(player.getFloorX(), player.getFloorZ());

    if(land == null){
      Message.fail(player, "현재 위치에서 땅을 찾을 수 없습니다.");
      return;
    }

    StringBuilder sb = new StringBuilder();
    int count = 0;
    for(Player p : level.getPlayers().values()){
      if(
        land.startX <= player.getFloorX() &&
        land.startZ <= player.getFloorZ() &&
        land.endX >= player.getFloorX() &&
        land.endZ >= player.getFloorZ() &&
        !(p.getName().equals(player.getName())) 
      ){
        if(count++ > 0){
          sb.append(", ");
        }
        sb.append(p.getName());
      }
    }

    Message.normal(player, Integer.toString(land.getNumber()) + "번 땅 방문자 : " + sb.toString() + " ( " + Integer.toString(count) + "명 )");

  }
}