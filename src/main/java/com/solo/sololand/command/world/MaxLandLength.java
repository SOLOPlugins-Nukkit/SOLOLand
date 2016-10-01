package com.solo.sololand.command.world;

import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class MaxLandLength {
  public static void execute(Player player, String[] args){
    if(args.length < 2){
      Message.usage(player, "/월드 땅최대길이 [길이(단위:블럭)]");
      return;
    }
    int maxLength;
    try{
      maxLength = Integer.parseInt(args[1]);
    }catch (Exception e){
      Message.usage(player, "/월드 땅최대길이 [길이(단위:블럭)]");
      return;
    }
    World world = World.get(player.getLevel());
    world.setMaxLandLength(maxLength);
    Message.success(player, world.getCustomName() + " 월드의 땅 생성 최대 길이를 " + args[1] + "블럭으로 설정하였습니다.");
    return;
  }
}