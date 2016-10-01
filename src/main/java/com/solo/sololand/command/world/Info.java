package com.solo.sololand.command.world;

import cn.nukkit.Player;

import com.solo.sololand.world.World;
import com.solo.sololand.world.Island;
import com.solo.sololand.util.Message;

public class Info{
  public static void execute(Player player, String[] args){
    World world = World.get(player.getLevel());
    StringBuilder sb = new StringBuilder();
    Message.normal(player, "월드 이름 : " + world.getCustomName());
    Message.normal(player, "월드 폴더 이름 : " + world.getName());
    Message.normal(player, "설정 정보 보기 > /월드 설정정보");
    Message.normal(player, "유저 정보 보기 > /월드 유저정보");
    Message.normal(player, "땅 정보 보기 > /월드 땅정보");
  }
}