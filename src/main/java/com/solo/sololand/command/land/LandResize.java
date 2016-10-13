package com.solo.sololand.command.land;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.land.Land;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;
import com.solo.sololand.util.Queue;
import com.solo.sololand.util.Economy;

import java.util.Map;
import java.util.LinkedHashMap;

public class LandResize extends SubCommand{

  public LandResize(MainCommand mCmd){
    super(mCmd, "크기변경", "땅의 크기를 변경합니다.");
    this.setPermission("sololand.command.land.resize");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
    World world = World.get(player.getLevel());
    Land land = world.getLand(player.getFloorX(), player.getFloorZ());
    String name = player.getName().toLowerCase();
    LinkedHashMap<String, Object> map;

    if(!player.isOp() && !world.isAllowResizeLand()){
      Message.fail(player, "해당 월드에서 땅 크기변경을 할 수 없습니다.");
      return true;
    }

    switch(Queue.getQueue(name)){
      case Queue.NULL:
        break;

      case Queue.RESIZE_LAND_FIRST:
        Message.normal(player, "이미 땅 크기변경이 진행중입니다. 취소하려면 /땅 취소 명령어를 입력하세요.");
        return true;

      case Queue.RESIZE_LAND_SECOND:
        map = (LinkedHashMap<String, Object>) Queue.getData(name);
        land = (Land) map.get("land");
        int[] xz = (int[]) map.get("xz");
        if(!player.isOp() && !land.isOwner(name)){
          Message.fail(player, "땅 주인이 아니므로 땅 크기변경을 할 수 없습니다.");
          return true;
        }
        int newStartX; int newEndX;
        int newStartZ; int newEndZ;
        int startXDist = Math.abs(land.startX - xz[0]);
        int endXDist = Math.abs(land.endX - xz[0]);
        int startZDist = Math.abs(land.startZ - xz[1]);
        int endZDist = Math.abs(land.endZ - xz[1]);
        if(startXDist < endXDist){
          newStartX = xz[0];
          newEndX = land.endX;
        }else{
          newStartX = land.startX;
          newEndX = xz[0];
        }
        if(startZDist < endZDist){
          newStartZ = xz[1];
          newEndZ = land.endZ;
        }else{
          newStartZ = land.startZ;
          newEndZ = xz[1];
        }
        int oldSize = (Math.abs(land.startX - land.endX) + 1) * (Math.abs(land.startZ - land.endZ) + 1);
        int newSize = (Math.abs(newStartX - newEndX) + 1) * (Math.abs(newStartZ - newEndZ) + 1);
        if(!player.isOp() && newSize > oldSize){
          double myMoney = Economy.getMoney(name);
          double price = world.getPricePerBlock() * (newSize - oldSize);
          if(myMoney < price){
            Message.fail(player, "돈이 부족합니다. 현재 소지한 돈 : " + Double.toString(myMoney) + "원, 크기변경 시 필요한 돈 : " + Double.toString(price) + "원");
            Queue.clean(name);
            return true;
          }
          Economy.reduceMoney(name, price);
        }
        land.startX = newStartX;
        land.startZ = newStartZ;
        land.endX = newEndX;
        land.endZ = newEndZ;
        Message.success(player, "성공적으로 땅 크기를 변경하였습니다. 변경된 크기 : " + Integer.toString(newSize - oldSize) + "블럭");
        Queue.clean(name);
        return true;

      default:
        Message.fail(player, "현재 다른 작업이 진행중입니다. /땅 취소 명령어를 입력한 뒤 시도해주세요.");
        return true;
    }
    if(land == null){
      Message.fail(player, "현재 위치에서 땅을 찾을 수 없습니다.");
      return true;
    }
    if(!player.isOp() && !land.isOwner(name)){
      Message.fail(player, "땅 주인이 아니므로 땅 크기변경을 할 수 없습니다.");
      return true;
    }
    map = new LinkedHashMap<String, Object>();
    map.put("land", land);
    Queue.putQueue(name, Queue.RESIZE_LAND_FIRST);
    Queue.putData(name, map);
    Message.normal(player, "땅 크기변경을 시작합니다. 크기를 변경할 지점을 터치해주세요.");
    return true;
  }
}