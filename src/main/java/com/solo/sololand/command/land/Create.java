package com.solo.sololand.command.land;

import cn.nukkit.Player;

import com.solo.sololand.land.Land;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;
import com.solo.sololand.util.Queue;
import com.solo.sololand.util.Economy;

import java.util.Map;
import java.util.HashMap;

public class Create {
  public static void execute(Player player, String[] args){

    World world = World.get(player.getLevel());
    String name = player.getName().toLowerCase();

    if(!player.isOp()){
      if(!world.isAllowCreateLand()){
        Message.fail(player, "해당 월드에서 땅을 생성할 수 없습니다.");
        return;
      }
      if(world.getMaxLandCount() < world.getLands(name).size()){
        Message.fail(player, "해당 월드에서 소유할 수 있는 땅의 최대 갯수를 초과하였습니다. (최대 " + Integer.toString(world.getMaxLandCount()) + "개)");
        return;
      }
    }
    switch(Queue.getQueue(name)){
      case Queue.NULL:
        break;

      case Queue.CREATE_LAND_FIRST:
      case Queue.CREATE_LAND_SECOND:
        Message.normal(player, "이미 땅을 생성중입니다. 취소하려면 /땅 취소 명령어를 입력하세요.");
        return;

      case Queue.CREATE_LAND_THIRD:
        Map map = (HashMap<String, Object>) Queue.getData(name);
        int[] first = (int[]) map.get("first");
        int[] second = (int[]) map.get("second");
        int Xlength = Math.abs(first[0] - second[0])+1;
        int Zlength = Math.abs(first[1] - second[1])+1;
        if(!player.isOp()){
          double myMoney = Economy.getMoney(name);
          double price = world.getPricePerBlock() * Xlength * Zlength; 
          if(myMoney < price){
            Message.fail(player, "돈이 부족합니다. 현재 소지한 돈 : " + Double.toString(myMoney) + "원, 생성 시 필요한 돈 : " + Double.toString(price) + "원");
            Queue.clean(name);
            return;
          }
          Economy.reduceMoney(name, price);
        }
        Land land =world.createLand(first[0], first[1], second[0], second[1], name);
        Message.success(player, "성공적으로 땅을 생성하였습니다. 땅 번호는 " + Integer.toString(land.getNumber()) + "번 입니다.");
        Queue.clean(name);
        return;

      default:
       Message.fail(player, "현재 다른 작업이 진행중입니다. /땅 취소 명령어를 입력한 뒤 시도해주세요.");
       return;
    }
    Queue.putQueue(name, Queue.CREATE_LAND_FIRST);
    Message.normal(player, "땅 생성을 시작합니다. 시작 지점을 터치해주세요.");
  }
}