package com.solo.sololand.command.world;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class WorldDefaultLandPrice extends SubCommand{

  public WorldDefaultLandPrice(MainCommand mCmd){
    super(mCmd, "땅가격", "월드의 기본 땅 가격을 설정합니다.");
    this.setUsage("/" + mCmd.getName() + " " + this.getName() + " [가격]");
    this.setAliases(new String[]{"기본땅가격"});
    this.setPermission("sololand.command.world.defaultlandprice");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
    if(args.length < 1){
      return false;
    }
    World world = World.get(player.getLevel());
    double price;
    try{
      price = Double.parseDouble(args[0]);
    }catch (Exception e){
      return false;
    }
    world.setDefaultLandPrice(price);
    Message.success(player, world.getCustomName() + " 월드의 기본 땅 가격을 " + args[0] + "원으로 설정하였습니다.");
    return true;
  }
}