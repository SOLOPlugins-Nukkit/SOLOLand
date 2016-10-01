package com.solo.sololand.command.world;

import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class PricePerBlock {
  public static void execute(Player player, String[] args){
    if(args.length < 2){
      Message.usage(player, "/월드 블럭당가격 [숫자(단위:블럭)]");
      return;
    }
    double pricePerBlock;
    try{
      pricePerBlock = Double.parseDouble(args[1]);
    }catch (Exception e){
      Message.usage(player, "/월드 블럭당가격 [숫자(단위:블럭)]");
      return;
    }
    World world = World.get(player.getLevel());
    world.setPricePerBlock(pricePerBlock);
    Message.success(player, world.getCustomName() + " 월드의 블럭 당 가격을 " + args[1] + "원으로 설정하였습니다.");
    return;
  }
}