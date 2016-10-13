package com.solo.sololand.command.land;

import cn.nukkit.Server;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class LandShareList extends SubCommand{

  public LandShareList(MainCommand mCmd){
    super(mCmd, "공유목록", "공유받은 땅의 목록을 확인합니다.");
    this.setAliases(new String[]{"공유된땅", "공유받은땅"});
    this.setPermission("sololand.command.land.sharelist");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
    String targetName;
    if(args.length < 1){
      targetName = player.getName();
    }else{
      Player target = Server.getInstance().getPlayer(args[0]);
      if(target != null){
        targetName = target.getName();
      }else{
        targetName = args[0];
      }
    }
    Message.normal(player, "<====== " + targetName + " 님의 공유받은 땅 목록 ======>");
    StringBuilder sb;
    boolean isZero = true;
    for(World world : World.getAll().values()){
      sb = new StringBuilder();
      boolean comma = false;
      for(Land land : world.getLands(targetName, true).values()){
        if(comma){
          sb.append(", ");
        }else{
          comma = true;
        }
        sb.append(Integer.toString(land.getNumber()) + "번");
      }
      if(comma){
        Message.normal(player, world.getCustomName() + " 월드 : " + sb.toString());
        isZero = false;
      }
    }
    if(isZero){
      Message.normal(player, "공유받은 땅이 없습니다.");
    }
    return true;
  }
}