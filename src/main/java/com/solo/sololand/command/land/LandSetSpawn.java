package com.solo.sololand.command.land;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class LandSetSpawn extends SubCommand{

  public LandSetSpawn(MainCommand mCmd){
    super(mCmd, "스폰", "땅 이동시 텔레포트될 지점을 설정합니다.");
    this.setAliases(new String[]{"스폰설정", "입구"});
    this.setPermission("sololand.command.land.setspawn");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
    World world = World.get(player.getLevel());
    Land land = world.getLand(player.getFloorX(), player.getFloorZ());
    if(land == null){
      Message.fail(player, "현재 위치에서 땅을 찾을 수 없습니다.");
      return true;
    }
    if(!player.isOp() && !land.isOwner(player.getName().toLowerCase())){
      Message.fail(player, "땅 주인이 아니므로 땅 스폰을 수정할 수 없습니다.");
      return true;
    }
    land.setSpawnPoint(player.getFloorX() + 0.5, player.getFloorY() + 0.05, player.getFloorZ() +0.5);
    Message.success(player, "땅 스폰 위치를 변경하였습니다.");
    return true;
  }
}
