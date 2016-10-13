package com.solo.sololand.command.world;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class WorldAllowExplosion extends SubCommand{

  public WorldAllowExplosion(MainCommand mCmd){
    super(mCmd, "폭발허용", "월드의 폭발 허용 여부를 설정합니다.");
    this.setAliases(new String[]{"폭발"});
    this.setPermission("sololand.command.world.allowexplosion");
  }

  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
    World world = World.get(player.getLevel());
    if(world.isAllowExplosion()){
      world.setAllowExplosion(false);
      Message.success(sender, world.getCustomName() + " 월드의 폭발허용을 껐습니다.");
    }else{
      world.setAllowExplosion(true);
      Message.success(sender, world.getCustomName() + " 월드의 폭발허용을 켰습니다.");
    }
    return true;
  }
}
