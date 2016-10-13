package com.solo.sololand.command.world;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class WorldMaxLandLength extends SubCommand{

  public WorldMaxLandLength(MainCommand mCmd){
    super(mCmd, "땅최대길이", "땅 생성 가능 최대 길이를 설정합니다.");
    this.setUsage("/" + mCmd.getName() + " " + this.getName() + " [길이(단위:블럭)]");
    this.setPermission("sololand.command.world.maxlandlength");
  }

  public boolean execute(CommandSender sender, String[] args){
    if(args.length < 1){
      return false;
    }
    int maxLength;
    try{
      maxLength = Integer.parseInt(args[0]);
    }catch (Exception e){
      return false;
    }
    Player player = (Player) sender;
    World world = World.get(player.getLevel());
    world.setMaxLandLength(maxLength);
    Message.success(player, world.getCustomName() + " 월드의 땅 생성 가능 최대 길이를 " + args[0] + "블럭으로 설정하였습니다.");
    return true;
  }
}