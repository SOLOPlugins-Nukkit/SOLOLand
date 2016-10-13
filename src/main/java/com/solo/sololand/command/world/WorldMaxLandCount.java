package com.solo.sololand.command.world;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class WorldMaxLandCount extends SubCommand{

  public WorldMaxLandCount(MainCommand mCmd){
    super(mCmd, "땅최대갯수", "소지 가능한 땅 최대 갯수를 설정합니다.");
    this.setUsage("/" + mCmd.getName() + " " + this.getName() + " [숫자(단위:갯수)]");
    this.setAliases(new String[]{"땅최대개수", "땅최대수"});
    this.setPermission("sololand.command.world.maxlandcount");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    if(args.length < 1){
      return false;
    }
    int maxCount;
    try{
      maxCount = Integer.parseInt(args[0]);
    }catch (Exception e){
      return false;
    }
    Player player = (Player) sender;
    World world = World.get(player.getLevel());
    world.setMaxLandCount(maxCount);
    Message.success(player, world.getCustomName() + " 월드의 땅 소지 가능 최대 갯수를 " + args[0] + "개로 설정하였습니다.");
    return true;
  }
}