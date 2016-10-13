package com.solo.sololand.command.land;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class LandSellList extends SubCommand{

  public LandSellList(MainCommand mCmd){
    super(mCmd, "매물", "판매중인 땅의 목록을 확인합니다.");
    this.setAliases(new String[]{"판매목록", "매물목록"});
    this.setPermission("sololand.command.land.selllist");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
    World world = World.get(player.getLevel());
    StringBuilder sb = new StringBuilder();
    boolean comma = false;
    for(Land land : world.getLands().values()){
      if(!land.isSail()){
        continue;
      }
      if(comma){
        sb.append(", ");
      }else{
        comma = true;
      }
      sb.append(Integer.toString(land.getNumber()) + "번 (가격 : " + Double.toString(land.getPrice()) + "원");
      if(!land.isOwner("")){
        sb.append(", 판매자 : " + land.getOwner());
      }
      sb.append(")");
    }
    Message.normal(player, "판매중인 땅 목록 : " + sb.toString());
    return true;
  }
}