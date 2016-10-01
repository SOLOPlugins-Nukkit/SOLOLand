package com.solo.sololand.command.land;

import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class AllowFight {
  public static void execute(Player player, String[] args){
    World world = World.get(player.getLevel());
    Land land = world.getLand(player.getFloorX(), player.getFloorZ());
    if(land == null){
      Message.fail(player, "현재 위치에서 땅을 찾을 수 없습니다.");
      return;
    }
    if(!player.isOp() && !land.isOwner(player.getName().toLowerCase())){
      Message.fail(player, "땅 주인이 아니므로 땅 전투가능 여부를 수정할 수 없습니다.");
      return;
    }
    if(land.isAllowFight()){
      land.setAllowFight(false);
      Message.success(player, "PVP를 허용하지 않도록 설정하였습니다.");
    }else{
      land.setAllowFight(true);
      Message.success(player, "PVP를 허용하도록 설정하였습니다.");
    }
  }
}