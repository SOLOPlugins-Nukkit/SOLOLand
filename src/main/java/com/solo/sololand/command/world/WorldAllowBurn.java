package com.solo.sololand.command.world;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class WorldAllowBurn extends SubCommand{

  public WorldAllowBurn(MainCommand mCmd){
    super(mCmd, "불번짐허용", "월드의 불 번짐 여부를 설정합니다.");
    this.setAliases(new String[]{"불퍼짐", "불번짐"});
    this.setPermission("sololand.command.world.allowburn");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
    World world = World.get(player.getLevel());
    if(world.isAllowBurn()){
      world.setAllowBurn(false);
      Message.success(sender, world.getCustomName() + " 월드의 불 번짐을 껐습니다.");
    }else{
      world.setAllowBurn(true);
      Message.success(sender, world.getCustomName() + " 월드의 불 번짐을 켰습니다.");
    }
    return true;
  }
}
