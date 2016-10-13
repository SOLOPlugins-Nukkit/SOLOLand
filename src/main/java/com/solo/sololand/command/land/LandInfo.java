package com.solo.sololand.command.land;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.land.Land;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

import java.util.List;

public class LandInfo extends SubCommand{

  public LandInfo(MainCommand mCmd){
    super(mCmd, "정보", "현재 위치에 있는 땅의 정보를 확인합니다.");
    this.setAliases(new String[]{"보기"});
    this.setPermission("sololand.command.land.info");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
    World world = World.get(player.getLevel());
    Land land = world.getLand(player.getFloorX(), player.getFloorZ());

    if(land == null){
      Message.fail(player, "현재 위치에서 땅을 찾을 수 없습니다.");
      return true;
    }
    if(land.isSail){
      Message.normal(player, "이 땅은 현재 판매중입니다.");
      if(!land.isOwner("")){
        Message.normal(player, "땅 판매자 : " + land.getOwner());
      }
      Message.normal(player, "땅 가격 : " + Double.toString(land.getPrice()) + "원");
    }else{
      if(!land.isOwner("")){
        Message.normal(player, "땅 주인 : " + land.getOwner());
      }
    }
    Message.normal(player, "땅 번호 : " + Integer.toString(land.getNumber()) + "번");
    int xSize = Math.abs(land.startX - land.endX) + 1;
    int zSize = Math.abs(land.startZ - land.endZ) + 1;
    Message.normal(player, "땅 크기 : " + Integer.toString(xSize) + "×" + Integer.toString(zSize));
    List members = land.getMembers();
    if(members.size() > 0){
      StringBuilder sb = new StringBuilder();
      for(int i = 0; i < members.size(); i++){
        if(members.get(i).equals(land.getOwner())){
          continue;
        }
        sb.append(members.get(i));
        if(i < members.size()-1){
          sb.append(", ");
        }
      }
      Message.normal(player, "땅 공유 목록 : " + sb.toString());
    }
    if(!land.getWelcomeMessage().equals("")){
      Message.normal(player, "땅 환영말 : " + land.getWelcomeMessage());
    }
    return true;
  }
}