package com.solo.sololand.command.world;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class WorldLandInfo extends SubCommand{

  public WorldLandInfo(MainCommand mCmd){
    super(mCmd, "땅정보", "월드의 땅 정보를 확인합니다.");
    this.setPermission("sololand.command.world.landinfo");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
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
    return true;
  }
}