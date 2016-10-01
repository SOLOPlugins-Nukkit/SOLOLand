package com.solo.sololand.command.world;

import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class MaxLandCount{
  public static void execute(Player player, String[] args){
    if(args.length < 2){
      Message.usage(player, "/월드 땅최대갯수 [숫자(단위:갯수)]");
      return;
    }
    int maxCount;
    try{
      maxCount = Integer.parseInt(args[1]);
    }catch (Exception e){
      Message.usage(player, "/월드 땅최대갯수 [숫자(단위:갯수)]");
      return;
    }
    World world = World.get(player.getLevel());
    world.setMaxLandCount(maxCount);
    Message.success(player, world.getCustomName() + " 월드의 땅 생성 최대 갯수를 " + args[1] + "개로 설정하였습니다.");
    return;
  }
}