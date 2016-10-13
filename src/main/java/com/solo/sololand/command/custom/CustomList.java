package com.solo.sololand.command.custom;

import cn.nukkit.Server;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class CustomList extends SubCommand{

  public CustomList(CustomWorldCommand mCmd){
    super(mCmd, "목록", "소유한 " + mCmd.getName() +" 땅을 확인합니다.");
    this.setAliases(new String[]{"리스트"});
    this.setUsage("/" + mCmd.getName() + " " + this.getName() + " 또는 " + "/" + mCmd.getName() + " " + this.getName() + " [유저]");
    this.setPermission("sololand.command.custom.list");
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
    CustomWorldCommand mCmd = (CustomWorldCommand) this.getMainCommand();
    World world = mCmd.getWorld();
    Message.normal(player, "<====== " + targetName + " 님의 " + world.getCustomName() + " 땅 목록 ======>");
    StringBuilder sb;
    boolean isZero = true;
    sb = new StringBuilder();
    boolean comma = false;
    for(Land land : world.getLands(targetName).values()){
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
    if(isZero){
      Message.normal(player, "소지하고 있는 " + world.getCustomName() + " 땅이 없습니다.");
    }
    return true;
  }
}