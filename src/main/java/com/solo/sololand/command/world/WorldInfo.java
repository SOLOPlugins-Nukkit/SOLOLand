package com.solo.sololand.command.world;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class WorldInfo extends SubCommand{

  public WorldInfo(MainCommand mCmd){
    super(mCmd, "정보", "월드의 정보를 확인합니다.");
    this.setAliases(new String[]{"정보보기"});
    this.setPermission("sololand.command.world.info");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
    World world = World.get(player.getLevel());
    StringBuilder sb = new StringBuilder();
    Message.normal(player, "월드 이름 : " + world.getCustomName());
    Message.normal(player, "월드 폴더 이름 : " + world.getName());
    Message.normal(player, "설정 정보 보기 > /월드 설정정보");
    Message.normal(player, "유저 정보 보기 > /월드 유저정보");
    Message.normal(player, "땅 정보 보기 > /월드 땅정보");
    return true;
  }
}