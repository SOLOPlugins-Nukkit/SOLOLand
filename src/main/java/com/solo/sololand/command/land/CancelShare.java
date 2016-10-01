package com.solo.sololand.command.land;

import cn.nukkit.Server;
import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class CancelShare{
  public static void execute(Player player, String[] args){
    World world = World.get(player.getLevel());
    Land land = world.getLand(player.getFloorX(), player.getFloorZ());
    if(land == null){
      Message.fail(player, "현재 위치에서 땅을 찾을 수 없습니다.");
      return;
    }
    String name = player.getName().toLowerCase();
    if(!player.isOp() && !land.isOwner(name)){
      Message.fail(player, "땅 주인이 아니므로 땅 공유목록을 수정할 수 없습니다.");
      return;
    }
    if(args.length < 2){
      Message.usage(player, "/땅 공유취소 [플레이어] 또는 /땅 공유취소 [플레이어] [플레이어]...");
      return;
    }
    for(int i = 1; i < args.length; i++){
      Player target = Server.getInstance().getPlayer(args[i]);
      String targetName;
      if(target == null){
        targetName = args[i].toLowerCase();
      }else{
        targetName = target.getName().toLowerCase();
      }
      if(land.isOwner(targetName)){
        Message.fail(player, "땅 주인을 공유 취소할 수 없습니다.");
        return;
      }
      if (!land.isMember(targetName)){
        Message.fail(player, targetName + "님은 공유 목록에 없습니다.");
        return;
      }
      land.removeMember(targetName);
      Message.success(player, targetName + "님을 공유 취소 하였습니다.");
      if(target != null){
        Message.fail(target, world.getCustomName() + " 월드의 " + Integer.toString(land.getNumber()) + "번 땅 공유가 취소되었습니다.");
      }
    }
  }
}