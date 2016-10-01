package com.solo.sololand.command.land;

import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class SellList{
  public static void execute(Player player, String[] args){
    World world = World.get(player.getLevel());
    StringBuilder sb = new StringBuilder();
    boolean comma = false;
    for(Land land : world.getLands().values()){
      if(!land.isSail()){
        continue;
      }
      if(comma){
        sb.append(", ");
      }else{
        comma = true;
      }
      sb.append(Integer.toString(land.getNumber()) + "번 (가격 : " + Double.toString(land.getPrice()) + "원");
      if(!land.isOwner("")){
        sb.append(", 판매자 : " + land.getOwner());
      }
      sb.append(")");
    }
    Message.normal(player, "판매중인 땅 목록 : " + sb.toString());
  }
}