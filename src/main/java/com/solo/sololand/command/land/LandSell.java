package com.solo.sololand.command.land;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class LandSell extends SubCommand{

  public LandSell(MainCommand mCmd){
    super(mCmd, "판매", "땅을 매물에 등록합니다.");
    this.setUsage("/" + mCmd.getName() + " " + this.getName() + " [가격]");
    this.setPermission("sololand.command.land.sell");
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
      Message.fail(player, "땅 주인이 아니므로 땅 가격을 수정할 수 없습니다.");
      return true;
    }
    if(args.length < 1){
      return false;
    }
    double sell;
    try{
      sell = Double.parseDouble(args[0]);
    }catch(Exception e){
      return false;
    }
    land.setPrice(sell);
    land.setSail(true);
    Message.success(player, "땅을 " + args[0] + "원으로 매물에 등록하였습니다.");
    return true;
  }
}