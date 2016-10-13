package com.solo.sololand.command.land;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class LandCancelSell extends SubCommand{
 
  public LandCancelSell(MainCommand mCmd){
    super(mCmd, "판매취소", "판매중인 땅의 판매를 취소합니다.");
    this.setPermission("sololand.command.land.cancelsell");
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
    if(!player.isOp() && !land.isOwner(player.getName().toLowerCase())){
      Message.fail(player, "땅 주인이 아니므로 땅 판매 여부를 수정할 수 없습니다.");
      return true;
    }
    if(!land.isSail()){
      Message.fail(player, "이 땅은 현재 판매중이 아닙니다.");
      return true;
    }
    land.setSail(false);
    Message.success(player, "땅 판매를 취소하였습니다.");
    return true;
  }
}