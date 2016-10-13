package com.solo.sololand.command.custom;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.land.Land;
import com.solo.sololand.world.Island;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;
import com.solo.sololand.util.Economy;

public class CustomBuy extends SubCommand{

  public CustomBuy(CustomWorldCommand mCmd){
    super(mCmd, "구매", mCmd.getName() + " 땅을 구매합니다.");
    this.setPermission("sololand.command.custom.buy");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
    CustomWorldCommand mCmd = (CustomWorldCommand) this.getMainCommand();
    World world = mCmd.getWorld();

    if(world instanceof Island){
    }else{
      Message.fail(player, "해당 월드에서는 지원하지 않는 명령어입니다.");
      return true;
    }

    String name = player.getName().toLowerCase();
    double price = world.getDefaultLandPrice();

    if(!player.isOp()){
      if(Economy.getMoney(name) < price){
        Message.fail(player, "섬을 구매할 돈이 부족합니다. 비용 : " + Double.toString(price) + "원");
        return true;
      }
      if(world.getMaxLandCount() < world.getLands(name).size()){
        Message.fail(player, "해당 월드에서 소유할 수 있는 땅의 최대 갯수를 초과하였습니다. (최대 " + Integer.toString(world.getMaxLandCount()) + "개)");
        return true;
      }
      Economy.reduceMoney(name, price);
    }

    Land land;
    if(world instanceof Island){
      Island island = (Island) world;
      land = island.createLand(name);
    }else{
      Message.fail(player, "해당 월드에서는 지원하지 않는 명령어입니다.");
      return true;
    }

    Message.success(player, "성공적으로 " + world.getCustomName() +" 땅을 구매하였습니다. 땅 번호 : " + Integer.toString(land.getNumber()));
    return true;
  }
}