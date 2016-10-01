package com.solo.sololand.command.world;

import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.world.Island;
import com.solo.sololand.util.Message;

public class SettingInfo{
  public static void execute(Player player, String[] args){
    World world = World.get(player.getLevel());
    StringBuilder sb = new StringBuilder();
    String landtype = "땅";
    if(world instanceof Island){
      landtype = "섬";
    }
    Message.normal(player, world.getCustomName() + " 월드 설정 정보");
    if(world.isProtected())
      sb.append("§a(보호)  "); else
      sb.append("§7(보호)  ");
    if(world.isInvenSave())
      sb.append("§a(인벤세이브)  "); else
      sb.append("§7(인벤세이브)  "); 
    if(world.isAllowExplosion()) 
      sb.append("§a(폭발)  "); else
      sb.append("§7(폭발)  "); 
    if(world.isAllowBurn()) 
      sb.append("§a(불번짐)  "); else
      sb.append("§7(불번짐)  ");
    if(world.isAllowFight()) 
      sb.append("§a(PVP)  "); else
      sb.append("§7(PVP)  "); 
    if(world.isAllowCreateLand()) 
      sb.append("§a(땅생성허용)  "); else
      sb.append("§7(땅생성허용)  ");
    if(world.isAllowResizeLand()) 
      sb.append("§a(땅크기변경허용)  "); else
      sb.append("§7(땅크기변경허용)  ");
    /*if(world.isAllowCreateRoom()) 
      sb.append("§a(방생성허용)"); else
      sb.append("§7(방생성허용)");*/
    Message.normal(player, sb.toString()); 
    Message.normal(player, "기본 땅 가격 : " + Double.toString(world.getDefaultLandPrice()) + "원");
    Message.normal(player, landtype + " 생성 시 블럭 당 가격 : " + Double.toString(world.getPricePerBlock()) + "원");
    Message.normal(player, landtype + " 최대 소지 가능 갯수 : " + Integer.toString(world.getMaxLandCount()) + "개");
    Message.normal(player, landtype + " 생성 최소 한변 길이 : " + Integer.toString(world.getMinLandLength()) + "블럭");
    Message.normal(player, landtype + " 생성 최대 한변 길이 : " + Integer.toString(world.getMaxLandLength()) + "블럭");
  }
}