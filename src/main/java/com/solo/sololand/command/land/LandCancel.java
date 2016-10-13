package com.solo.sololand.command.land;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.util.Message;
import com.solo.sololand.util.Queue;

public class LandCancel extends SubCommand{

  public LandCancel(MainCommand mCmd){
    super(mCmd, "취소", "진행중인 땅 작업을 취소합니다.");
    this.setAliases(new String[]{"작업취소", "중단", "작업중단"});
    this.setPermission("sololand.command.land.cancel");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
    String name = player.getName().toLowerCase();
    if(!Queue.containsQueue(name)){
      Message.fail(player, "진행중인 작업이 없습니다.");
      return true;
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
    return true;
  }
}