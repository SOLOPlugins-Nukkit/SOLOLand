package com.solo.sololand.command.world;

import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class LandInfo{
  public static void execute(Player player, String[] args){
    World world = World.get(player.getLevel());
    StringBuilder sb = new StringBuilder();
    Message.normal(player, world.getCustomName() + " 월드 땅 정보");
    int all = 0;
    int sail = 0;
    int notowned = 0;
    int owned = 0;
    for(Land land : world.getLands().values()){
      ++all;
      if(land.isSail()){
        ++sail;
      }
      if(land.isOwner("")){
        ++notowned;
      }else{
        ++owned;
      }
    }
    Message.normal(player, "전체 땅 갯수 : " + Integer.toString(all) + "개");
    Message.normal(player, "유저가 소유중인 땅 갯수 : " + Integer.toString(owned) + "개");
    Message.normal(player, "판매중인 땅 갯수 : " + Integer.toString(sail) + "개");
    Message.normal(player, "주인이 없는 땅 갯수 : " + Integer.toString(notowned) + "개");
  }
}