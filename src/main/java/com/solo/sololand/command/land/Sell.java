package com.solo.sololand.command.land;

import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class Sell{
  public static void execute(Player player, String[] args){
    World world = World.get(player.getLevel());
    Land land = world.getLand(player.getFloorX(), player.getFloorZ());
    if(land == null){
      Message.fail(player, "현재 위치에서 땅을 찾을 수 없습니다.");
      return;
    }
    if(!player.isOp() && !land.isOwner(player.getName().toLowerCase())){
      Message.fail(player, "땅 주인이 아니므로 땅 가격을 수정할 수 없습니다.");
      return;
    }
    if(args.length < 2){
      Message.usage(player, "/땅 판매 [가격]");
      return;
    }
    double sell;
    try{
      sell = Double.parseDouble(args[1]);
    }catch(Exception e){
      Message.usage(player, "/땅 판매 [가격]");
      return;
    }
    land.setPrice(sell);
    land.setSail(true);
    Message.success(player, "땅을 " + args[1] + "원으로 매물에 등록하였습니다.");
    return;
  }
}