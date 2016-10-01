package com.solo.sololand.command.island;

import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class DefaultIslandPrice{
  public static void execute(Player player, String[] args){
    if(!World.isExistIsland()){
      Message.fail(player, "섬 월드가 생성되어 있지 않습니다.");
      return;
    }
    if(args.length < 2){
      Message.usage(player, "/섬 가격 [가격]");
      return;
    }
    World world = World.get(player.getLevel());
    double price;
    try{
      price = Double.parseDouble(args[1]);
    }catch (Exception e){
      Message.usage(player, "/섬 가격 [가격]");
      return;
    }
    world.setDefaultLandPrice(price);
    Message.success(player, "기본 섬 가격을 " + args[1] + "원으로 설정하였습니다.");
  }
}