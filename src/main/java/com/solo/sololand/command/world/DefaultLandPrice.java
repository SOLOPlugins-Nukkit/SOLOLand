package com.solo.sololand.command.world;

import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class DefaultLandPrice{
  public static void execute(Player player, String[] args){
    if(args.length < 2){
      Message.usage(player, "/월드 땅가격 [가격]");
      return;
    }
    World world = World.get(player.getLevel());
    double price;
    try{
      price = Double.parseDouble(args[1]);
    }catch (Exception e){
      Message.usage(player, "/월드 땅가격 [가격]");
      return;
    }
    world.setDefaultLandPrice(price);
    Message.success(player, world.getCustomName() + " 월드의 기본 땅 가격을 " + args[1] + "원으로 설정하였습니다.");
  }
}