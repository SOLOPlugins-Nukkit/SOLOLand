package com.solo.sololand.command.world;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class WorldList extends SubCommand{

  public WorldList(MainCommand mCmd){
    super(mCmd, "목록", "월드 목록을 확인합니다.");
    this.setInGameOnly(false);
    this.setAliases(new String[]{"리스트"});
    this.setPermission("sololand.command.world.list");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Message.normal(sender, "====== 월드 목록 ======");
    for(World world : World.getAll().values()){
      Message.normal(sender, world.getCustomName() + " (폴더 이름 : " + world.getName() + "), 제너레이터 : " + world.getLevel().getProvider().getGenerator());
    }
    return true;
  }
}