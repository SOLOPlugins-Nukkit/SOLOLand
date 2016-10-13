package com.solo.sololand.command.land;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.land.Land;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class LandMove extends SubCommand{

  public LandMove(MainCommand mCmd){
    super(mCmd, "이동", "해당 번호의 땅으로 이동합니다.");
    this.setUsage("/" + mCmd.getName() + " " + this.getName() + " [땅 번호]");
    this.setPermission("sololand.command.land.move");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    if(args.length < 1){
      return false;
    }
    int num;
    try{
      num = Integer.parseInt(args[0]);
    } catch (Exception e){
      return false;
    }
    if(num < 1){
      return false;
    }
    Player player = (Player) sender;
    World world = World.get(player.getLevel());
    Land land = world.getLand(num);
    if(land == null){
      Message.fail(player, args[0] + "번 땅은 존재하지 않습니다.");
      return true;
    }
    if(
      !player.isOp() &&
      !land.isOwner(player.getName().toLowerCase()) &&
      !land.isAllowAccess()
    ){
      Message.fail(player, args[0] + "번 땅은 현재 출입이 제한되어 있습니다.");
      return true;
    }
    player.teleport(land.getSpawnPoint());
    Message.success(player, args[0] + "번 땅으로 이동하였습니다.");
    return true;
  }
}