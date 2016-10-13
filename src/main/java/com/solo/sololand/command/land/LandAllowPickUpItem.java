package com.solo.sololand.command.land;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class LandAllowPickUpItem extends SubCommand{

  public LandAllowPickUpItem(MainCommand mCmd){
    super(mCmd, "아이템줍기허용", "다른 유저가 아이템을 주울 수 있는지 여부를 설정합니다.");
    this.setAliases(new String[]{"아이템줍기", "아이템드랍허용", "아이템드랍", "템줍기", "드랍허용", "줍기허용"});
    this.setPermission("sololand.command.land.allowpickupitem");
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
    if(
      !player.isOp() &&
      !land.isOwner(player.getName().toLowerCase())
    ){
      Message.fail(player, "땅 주인이 아니므로 땅 아이템 드랍 여부를 수정할 수 없습니다.");
      return true;
    }
    if(land.isAllowPickUpItem()){
      land.setAllowPickUpItem(false);
      Message.success(player, "다른 유저가 아이템을 주울 수 없도록 설정하였습니다.");
    }else{
      land.setAllowPickUpItem(true);
      Message.success(player, "다른 유저가 아이템을 주울 수 있도록 설정하였습니다.");
    }
    return true;
  }
}