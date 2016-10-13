package com.solo.sololand.command.world;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class WorldAllowFight extends SubCommand{

  public WorldAllowFight(MainCommand mCmd){
    super(mCmd, "전투허용", "월드의 전투 허용 여부를 설정합니다.");
    this.setAliases(new String[]{"싸움허용", "pvp허용", "유저간전투허용", "유저싸움하용", "싸움", "pvp", "전투"});
    this.setPermission("sololand.command.world.allowfight");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
    World world = World.get(player.getLevel());
    if(world.isAllowFight()){
      world.setAllowFight(false);
      Message.success(sender, world.getCustomName() + " 월드의 pvp를 해제하였습니다.");
    }else{
      world.setAllowFight(true);
      Message.success(sender, world.getCustomName() + " 월드의 pvp를 켰습니다.");
    }
    return true;
  }
}