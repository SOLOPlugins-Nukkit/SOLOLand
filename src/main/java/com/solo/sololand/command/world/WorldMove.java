package com.solo.sololand.command.world;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class WorldMove extends SubCommand{

  public WorldMove(MainCommand mCmd){
    super(mCmd, "이동", "해당 월드로 이동합니다.");
    this.setUsage("/" + mCmd.getName() + " " + this.getName() + " [월드 이름]");
    this.setPermission("sololand.command.world.move");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
    if(args.length < 1){
      return false;
    }
    World target;
    target = World.getByName(args[0]);
    if(target == null){
      target = World.get(args[0]);
      if(target == null){
        Message.fail(player, args[0] + "은(는) 존재하지 않는 월드입니다.");
        return true;
      }
    }
    player.teleport(target.getLevel().getSpawnLocation());
    Message.success(player, target.getCustomName() + " 월드로 이동하였습니다.");
    return true;
  }
}