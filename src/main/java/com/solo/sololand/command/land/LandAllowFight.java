package com.solo.sololand.command.land;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class LandAllowFight extends SubCommand{

  public LandAllowFight(MainCommand mCmd){
    super(mCmd, "pvp", "땅 pvp 허용 여부를 설정합니다.");
    this.setAliases(new String[]{"전투허용", "전투", "유저간전투허용", "싸움", "싸움허용"});
    this.setPermission("sololand.command.land.allowfight");
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
      Message.fail(player, "땅 주인이 아니므로 땅 전투가능 여부를 수정할 수 없습니다.");
      return true;
    }
    if(land.isAllowFight()){
      land.setAllowFight(false);
      Message.success(player, "PVP를 허용하지 않도록 설정하였습니다.");
    }else{
      land.setAllowFight(true);
      Message.success(player, "PVP를 허용하도록 설정하였습니다.");
    }
    return true;
  }
}