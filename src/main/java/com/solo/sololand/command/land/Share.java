package com.solo.sololand.command.land;

import cn.nukkit.Server;
import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class Share {
  public static void execute(Player player, String[] args){
    World world = World.get(player.getLevel());
    Land land = world.getLand(player.getFloorX(), player.getFloorZ());
    if(land == null){
      Message.fail(player, "현재 위치에서 땅을 찾을 수 없습니다.");
      return;
    }
    if(!player.isOp() && !land.isOwner(player.getName().toLowerCase())){
      Message.fail(player, "땅 주인이 아니므로 땅 공유목록을 수정할 수 없습니다.");
      return;
    }
    if(args.length < 2){
      Message.usage(player, "/땅 공유 [플레이어] 또는 /땅 공유 [플레이어] [플레이어]...");
      return;
    }
    Player target;
    String targetName;
    for(int i = 1; i < args.length; i++){
      target = Server.getInstance().getPlayer(args[i]);
      if(target == null){
        Message.fail(player, args[i] + "님은 현재 온라인이 아닙니다.");
        return;
      }else{
        targetName = target.getName().toLowerCase();
      }
      if(land.isOwner(targetName)){
        Message.fail(player, "땅을 주인에게 공유할 수 없습니다.");
        return;
      }
      if (land.isMember(targetName)){
        Message.fail(player, target.getName() + "님은 이미 공유 되어있습니다.");
        return;
      }
      land.addMember(targetName);
      Message.success(player, target.getName() + "님에게 땅을 공유하였습니다.");
      Message.success(target, player.getName() + "님이 " + world.getCustomName() + " 월드의 " + Integer.toString(land.getNumber()) + "번 땅을 공유하셨습니다.");
    }
  }
}