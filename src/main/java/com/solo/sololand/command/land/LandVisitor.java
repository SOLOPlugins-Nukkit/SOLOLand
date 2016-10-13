package com.solo.sololand.command.land;

import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class LandVisitor extends SubCommand{

  public LandVisitor(MainCommand mCmd){
    super(mCmd, "방문자", "현재 땅을 방문중인 유저 목록을 확인합니다.");
    this.setAliases(new String[]{"방문유저"});
    this.setPermission("sololand.command.land.visitor");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
    Level level = player.getLevel();
    World world = World.get(level);
    Land land = world.getLand(player.getFloorX(), player.getFloorZ());

    if(land == null){
      Message.fail(player, "현재 위치에서 땅을 찾을 수 없습니다.");
      return true;
    }

    StringBuilder sb = new StringBuilder();
    int count = 0;
    for(Player p : Server.getInstance().getOnlinePlayers().values()){
      if(
        p.getLevel().getFolderName().equals(world.getLevel().getFolderName()) &&
        land.startX <= player.getFloorX() &&
        land.startZ <= player.getFloorZ() &&
        land.endX >= player.getFloorX() &&
        land.endZ >= player.getFloorZ() &&
        !(p.getName().equals(player.getName())) 
      ){
        if(count++ > 0){
          sb.append(", ");
        }
        sb.append(p.getName());
      }
    }

    Message.normal(player, Integer.toString(land.getNumber()) + "번 땅 방문자 : " + sb.toString() + " ( " + Integer.toString(count) + "명 )");
    return true;
  }
}