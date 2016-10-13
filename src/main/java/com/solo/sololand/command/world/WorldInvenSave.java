package com.solo.sololand.command.world;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class WorldInvenSave extends SubCommand{

  public WorldInvenSave(MainCommand mCmd){
    super(mCmd, "인벤세이브", "월드의 인벤세이브 여부를 설정합니다.");
    this.setAliases(new String[]{"인벤저장"});
    this.setPermission("sololand.command.world.invemsave");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
    World world = World.get(player.getLevel());
    if(world.isInvenSave()){
      world.setInvenSave(false);
      Message.success(sender, world.getCustomName() + " 월드의 인벤세이브를 해제하였습니다.");
    }else{
      world.setInvenSave(true);
      Message.success(sender, world.getCustomName() + " 월드의 인벤세이브를 켰습니다.");
    }
    return true;
  }
}
