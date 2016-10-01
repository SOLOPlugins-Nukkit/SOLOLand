package com.solo.sololand.command.land;

import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class Access{
  public static void execute(Player player, String[] args){
    World world = World.get(player.getLevel());
    Land land = world.getLand(player.getFloorX(), player.getFloorZ());
    if(land == null){
      Message.fail(player, "현재 위치에서 땅을 찾을 수 없습니다.");
      return;
    }
    String name = player.getName().toLowerCase();
    if(!player.isOp() && !land.isOwner(name)){
      Message.fail(player, "땅 주인이 아니므로 땅 출입가능 여부를 수정할 수 없습니다.");
      return;
    }
    if(land.isAllowAccess()){
      land.setAllowAccess(false);
      Message.success(player, "다른 유저의 출입을 허용하지 않도록 설정하였습니다.");
    }else{
      land.setAllowAccess(true);
      Message.success(player, "다른 유저의 출입을 허용하도록 설정하였습니다.");
    }
  }
}
