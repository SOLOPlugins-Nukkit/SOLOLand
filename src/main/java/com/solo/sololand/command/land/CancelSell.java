package com.solo.sololand.command.land;

import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class CancelSell{
  public static void execute(Player player, String[] args){
    World world = World.get(player.getLevel());
    Land land = world.getLand(player.getFloorX(), player.getFloorZ());
    if(land == null){
      Message.fail(player, "현재 위치에서 땅을 찾을 수 없습니다.");
      return;
    }
    if(!player.isOp() && !land.isOwner(player.getName().toLowerCase())){
      Message.fail(player, "땅 주인이 아니므로 땅 판매 여부를 수정할 수 없습니다.");
      return;
    }
    if(!land.isSail()){
      Message.fail(player, "이 땅은 현재 판매중이 아닙니다.");
      return;
    }
    land.setSail(false);
    Message.success(player, "땅 판매를 취소하였습니다.");
  }
}