package com.solo.sololand.command.land;

import cn.nukkit.Server;
import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;
import com.solo.sololand.util.Economy;

import java.util.ArrayList;

public class Buy {
  public static void execute(Player player, String[] args) {
    World world = World.get(player.getLevel());
    Land land = world.getLand(player.getFloorX(), player.getFloorZ());
    if(land == null){
      Message.fail(player, "현재 위치에서 땅을 찾을 수 없습니다.");
      return;
    }
    if(!land.isSail()){
      Message.fail(player, "이 땅은 현재 판매중이 아닙니다.");
      return;
    }
    String name = player.getName().toLowerCase();
    if(land.isOwner(name)){
      Message.fail(player, "땅 주인이 자신의 땅을 구매할 수 없습니다. 만일 판매를 취소하시길 원하시면, /땅 판매취소 명령어를 입력해주세요.");
      return;
    }
    double myMoney = Economy.getMoney(name);
    double landPrice = land.getPrice();
    if(myMoney < landPrice){ 
      Message.fail(player, "돈이 부족합니다. 내 돈 : " + Double.toString(myMoney) + "원");
      return;
    }
    if(!player.isOp() && world.getMaxLandCount() < world.getLands(name).size()){
      Message.fail(player, "해당 월드에서 소유할 수 있는 땅의 최대 갯수를 초과하였습니다. (최대 " + Integer.toString(world.getMaxLandCount()) + "개)");
      return;
    }
    Economy.reduceMoney(name, landPrice);
    Economy.addMoney(land.getOwner(), landPrice);
    Player prevOwner = Server.getInstance().getPlayerExact(land.getOwner());
    if(prevOwner != null){
      Message.success(prevOwner, player.getName() + "님이 " + world.getCustomName() + " 월드의 " + Integer.toString(land.getNumber()) + "번 땅을 구매하셨습니다.");
    }

    land.setPrice(0.0);
    land.setOwner(name);
    land.setSail(false);
    ArrayList<String> members = new ArrayList<String>();
    members.add(name);
    land.setMembers(members);
    land.setWelcomeMessage("");
    Message.success(player, Integer.toString(land.getNumber()) + "번 땅을 구매하였습니다.");
  }
}