package com.solo.sololand.command.land;

import cn.nukkit.Server;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class LandCancelShare extends SubCommand{

  public LandCancelShare(MainCommand mCmd){
    super(mCmd, "공유취소", "공유중이던 유저의 공유를 취소합니다.");
    this.setUsage("/" + mCmd.getName() + " " + this.getName() + " [유저] <유저> <유저>...");
    this.setAliases(new String[]{"추방"});
    this.setPermission("sololand.command.land.cancelshare");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
    World world = World.get(player.getLevel());
    Land land = world.getLand(player.getFloorX(), player.getFloorZ());
    if(land == null){
      Message.fail(player, "현재 위치에서 땅을 찾을 수 없습니다.");
      return true;
    }
    String name = player.getName().toLowerCase();
    if(!player.isOp() && !land.isOwner(name)){
      Message.fail(player, "땅 주인이 아니므로 땅 공유목록을 수정할 수 없습니다.");
      return true;
    }
    if(args.length < 1){
      return false;
    }
    for(int i = 0; i < (args.length - 1); i++){
      Player target = Server.getInstance().getPlayer(args[i]);
      String targetName;
      if(target == null){
        targetName = args[i].toLowerCase();
      }else{
        targetName = target.getName().toLowerCase();
      }
      if(land.isOwner(targetName)){
        Message.fail(player, "땅 주인을 공유 취소할 수 없습니다.");
        return true;
      }
      if (!land.isMember(targetName)){
        Message.fail(player, targetName + "님은 공유 목록에 없습니다.");
        return true;
      }
      land.removeMember(targetName);
      Message.success(player, targetName + "님을 공유 취소 하였습니다.");
      if(target != null){
        Message.fail(target, world.getCustomName() + " 월드의 " + Integer.toString(land.getNumber()) + "번 땅 공유가 취소되었습니다.");
      }
    }
    return true;
  }
}