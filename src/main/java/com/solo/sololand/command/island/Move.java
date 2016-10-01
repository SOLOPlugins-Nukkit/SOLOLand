package com.solo.sololand.command.island;

import cn.nukkit.Player;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;

import com.solo.sololand.land.Land;
import com.solo.sololand.world.Island;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;
import com.solo.sololand.util.Economy;

public class Move{
  public static void execute(Player player, String[] args){
    World world = World.get("island");
    if(world == null || !(world instanceof Island)){
      Message.fail(player, "섬 월드가 생성되어 있지 않습니다.");
      return;
    }
    Island island = (Island) world;
    if(args.length < 2){
      Message.usage(player, "/섬 이동 [섬 번호]");
      return;
    }
    int num;
    try{
      num = Integer.parseInt(args[1]);
    } catch (Exception e){
      Message.usage(player, "/섬 이동 [섬 번호]");
      return;
    }
    if(num < 1){
      Message.usage(player, "/섬 이동 [섬 번호]");
      return;
    }
    Land land = island.getLand(num);
    if(land == null){
      Message.fail(player, args[1] + "번 섬은 존재하지 않습니다.");
      return;
    }
    if(
      !player.isOp() &&
      !land.isOwner(player.getName().toLowerCase()) &&
      !land.isAllowAccess()
    ){
      Message.fail(player, args[1] + "번 섬은 현재 출입이 제한되어 있습니다.");
      return;
    }
    Vector3 sp = land.getSpawnPoint();
    player.teleport(new Position(sp.x, sp.y, sp.z, island.getLevel()));
    Message.success(player, args[1] + "번 섬으로 이동하였습니다.");
  }
}