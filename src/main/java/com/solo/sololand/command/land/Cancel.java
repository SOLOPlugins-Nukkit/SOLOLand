package com.solo.sololand.command.land;

import cn.nukkit.Player;

import com.solo.sololand.util.Message;
import com.solo.sololand.util.Queue;

public class Cancel{
  public static void execute(Player player, String[] args){
    String name = player.getName().toLowerCase();
    if(!Queue.containsQueue(name)){
      Message.fail(player, "진행중인 작업이 없습니다.");
      return;
    }
    switch(Queue.getQueue(name)){
      case Queue.NULL:
        Message.fail(player, "진행중인 작업이 없습니다.");
        break;
      case Queue.CREATE_LAND_FIRST:
      case Queue.CREATE_LAND_SECOND:
      case Queue.CREATE_LAND_THIRD:
        Message.success(player, "진행중이던 땅 생성 작업을 취소하였습니다.");
        break;
      case Queue.REMOVE_LAND:
        Message.success(player, "진행중이던 땅 제거 작업을 취소하였습니다.");
        break;
      default:
        Message.success(player, "진행중이던 작업을 취소하였습니다.");
        break;
    }
    Queue.clean(name);
  }
}