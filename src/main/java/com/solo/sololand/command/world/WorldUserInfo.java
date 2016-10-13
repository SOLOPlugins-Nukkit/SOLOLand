package com.solo.sololand.command.world;

import cn.nukkit.Server;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Level;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class WorldUserInfo extends SubCommand{

  public WorldUserInfo(MainCommand mCmd){
    super(mCmd, "유저정보", "월드의 유저 정보를 확인합니다.");
    this.setAliases(new String[]{"플레이어정보"});
    this.setPermission("sololand.command.world.userinfo");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
    Level level = player.getLevel();
    World world = World.get(level);
    StringBuilder sb = new StringBuilder();
    Message.normal(player, world.getCustomName() + " 월드 유저 정보");
    Message.normal(player, "전체 " + Integer.toString(Server.getInstance().getOnlinePlayers().size()) + "명의 유저 중 " + Integer.toString(level.getPlayers().size()) + "명의 유저가 " + world.getCustomName() + " 월드에 있습니다.");
    boolean comma = false;
    for(Player p : level.getPlayers().values()){
      if(comma){
        sb.append(", ");
      }else{
        comma = true;
      }
      sb.append(p.getName());
    }
    return true;
  }
}