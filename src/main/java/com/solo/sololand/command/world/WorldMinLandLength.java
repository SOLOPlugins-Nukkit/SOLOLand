package com.solo.sololand.command.world;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class WorldMinLandLength extends SubCommand{

  public WorldMinLandLength(MainCommand mCmd){
    super(mCmd, "땅최소길이", "땅 생성 최소 길이를 설정합니다.");
    this.setUsage("/" + mCmd.getName() + " " + this.getName() + " [길이(단위:블럭)]");
    this.setPermission("sololand.command.world.minlandlength");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    if(args.length < 1){
      return false;
    }
    int minLength;
    try{
      minLength = Integer.parseInt(args[0]);
    }catch (Exception e){
      return false;
    }
    Player player = (Player) sender;
    World world = World.get(player.getLevel());
    world.setMinLandLength(minLength);
    Message.success(player, world.getCustomName() + " 월드의 땅 생성 최소 길이를 " + args[0] + "블럭으로 설정하였습니다.");
    return true;
  }
}