package com.solo.sololand.command.world;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class WorldPricePerBlock extends SubCommand{

  public WorldPricePerBlock(MainCommand mCmd){
    super(mCmd, "블럭당가격", "땅 생성시 1블럭당 받을 가격을 설정합니다.");
    this.setUsage("/" + mCmd.getName() + " " + this.getName() + " [가격]");
    this.setAliases(new String[]{"블럭개당가격"});
    this.setPermission("sololand.command.world.priceperblock");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
    if(args.length < 1){
      return false;
    }
    double pricePerBlock;
    try{
      pricePerBlock = Double.parseDouble(args[0]);
    }catch (Exception e){
      return false;
    }
    World world = World.get(player.getLevel());
    world.setPricePerBlock(pricePerBlock);
    Message.success(player, world.getCustomName() + " 월드의 블럭 당 가격을 " + args[0] + "원으로 설정하였습니다.");
    return true;
  }
}