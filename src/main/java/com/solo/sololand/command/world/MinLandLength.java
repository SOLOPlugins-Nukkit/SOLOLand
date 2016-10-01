package com.solo.sololand.command.world;

import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class MinLandLength{
  public static void execute(Player player, String[] args){
    if(args.length < 2){
      Message.usage(player, "/월드 땅최소길이 [길이(단위:블럭)]");
      return;
    }
    int minLength;
    try{
      minLength = Integer.parseInt(args[1]);
    }catch (Exception e){
      Message.usage(player, "/월드 땅최소길이 [길이(단위:블럭)]");
      return;
    }
    World world = World.get(player.getLevel());
    world.setMinLandLength(minLength);
    Message.success(player, world.getCustomName() + " 월드의 땅 생성 최소 길이를 " + args[1] + "블럭으로 설정하였습니다.");
    return;
  }
}