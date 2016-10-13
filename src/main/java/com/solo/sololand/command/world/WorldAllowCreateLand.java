package com.solo.sololand.command.world;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class WorldAllowCreateLand extends SubCommand{

  public WorldAllowCreateLand(MainCommand mCmd){
    super(mCmd, "땅생성허용", "월드의 땅 생성 허용 여부를 설정합니다.");
    this.setAliases(new String[]{"수동생성허용", "땅만들기허용"});
    this.setPermission("sololand.command.world.allowcreateland");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
    World world = World.get(player.getLevel());
    if(world.isAllowCreateLand()){
      world.setAllowCreateLand(false);
      Message.success(sender, world.getCustomName() + " 월드의 땅 생성을 금지하였습니다.");
    }else{
      world.setAllowCreateLand(true);
      Message.success(sender, world.getCustomName() + " 월드의 땅 생성을 허용하였습니다.");
    }
    return true;
  }
}