package com.solo.sololand.command.land;

import cn.nukkit.Player;

import com.solo.sololand.land.Land;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class Move{
  public static void execute(Player player, String[] args){
    if(args.length < 2){
      Message.usage(player, "/땅 이동 [땅 번호]");
      return;
    }
    int num;
    try{
      num = Integer.parseInt(args[1]);
    } catch (Exception e){
      Message.usage(player, "/땅 이동 [땅 번호]");
      return;
    }
    if(num < 1){
      Message.usage(player, "/땅 이동 [땅 번호]");
      return;
    }
    World world = World.get(player.getLevel());
    Land land = world.getLand(num);
    if(land == null){
      Message.fail(player, args[1] + "번 땅은 존재하지 않습니다.");
      return;
    }
    if(
      !player.isOp() &&
      !land.isOwner(player.getName().toLowerCase()) &&
      !land.isAllowAccess()
    ){
      Message.fail(player, args[1] + "번 땅은 현재 출입이 제한되어 있습니다.");
      return;
    }
    player.teleport(land.getSpawnPoint());
    Message.success(player, args[1] + "번 땅으로 이동하였습니다.");
  }
}