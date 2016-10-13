package com.solo.sololand.command.world;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class WorldProtect extends SubCommand{

  public WorldProtect(MainCommand mCmd){
    super(mCmd, "보호", "월드의 보호 여부를 설정합니다.");
    this.setAliases(new String[]{"블럭파괴", "블럭설치", "블럭허용"});
    this.setPermission("sololand.command.world.protect");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
    World world = World.get(player.getLevel());
    if(world.isProtected()){
      world.setProtected(false);
      Message.success(player, world.getName() + " 월드의 보호를 해제하였습니다.");
    }else{
      world.setProtected(true);
      Message.success(player, world.getCustomName() + " 월드의 보호를 켰습니다.");
    }
    return true;
  }
}