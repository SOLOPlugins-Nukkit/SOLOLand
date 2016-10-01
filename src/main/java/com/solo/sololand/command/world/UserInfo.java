package com.solo.sololand.command.world;

import cn.nukkit.Server;
import cn.nukkit.Player;
import cn.nukkit.level.Level;

import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class UserInfo{
  public static void execute(Player player, String[] args){
    Level level = player.getLevel();
    World world = World.get(level);
    StringBuilder sb = new StringBuilder();
    Message.normal(player, world.getCustomName() + " 월드 유저 정보");
    Message.normal(player, "전체 " + Integer.toString(Server.getInstance().getOnlinePlayers().size()) + "명의 유저 중 " + Integer.toString(level.getPlayers().size()) + "명의 유저가 " + world.getCustomName() + " 월드에 있습니다.");
    boolean comma = false;
    for(Player p : level.getPlayers().values()){
      if(comma){
        sb.append(", ");
      }else{
        comma = true;
      }
      sb.append(p.getName());
    }
  }
}