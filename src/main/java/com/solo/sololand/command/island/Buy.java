package com.solo.sololand.command.island;

import cn.nukkit.Player;

import com.solo.sololand.land.Land;
import com.solo.sololand.world.Island;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;
import com.solo.sololand.util.Economy;

public class Buy{
  public static void execute(Player player, String[] args){
    if(!World.isExistIsland()){
      Message.fail(player, "섬 월드가 생성되어 있지 않습니다.");
      return;
    }
    String name = player.getName().toLowerCase();
    Island island = (Island) World.get("island");
    double price = island.getDefaultLandPrice();
    if(!player.isOp()){
      if(Economy.getMoney(name) < price){
        Message.fail(player, "섬을 구매할 돈이 부족합니다. 비용 : " + Double.toString(price) + "원");
        return;
      }
      if(island.getMaxLandCount() < island.getLands(name).size()){
        Message.fail(player, "해당 월드에서 소유할 수 있는 땅의 최대 갯수를 초과하였습니다. (최대 " + Integer.toString(island.getMaxLandCount()) + "개)");
        return;
      }
      Economy.reduceMoney(name, price);
    }
    Land land = island.createLand(name);
    Message.success(player, "성공적으로 섬을 구매하였습니다. 섬 번호 : " + Integer.toString(land.getNumber()));
  }
}