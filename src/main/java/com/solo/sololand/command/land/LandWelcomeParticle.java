package com.solo.sololand.command.land;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.particle.Particle;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class LandWelcomeParticle extends SubCommand{

  public LandWelcomeParticle(MainCommand mCmd){
    super(mCmd, "환영효과", "다른 유저가 땅 방문시 나타낼 효과를 설정합니다.");
    this.setAliases(new String[]{"환영파티클"});
    this.setUsage("/" + mCmd.getName() + " " + this.getName() + " [물방울/반짝/연기/하트/불/용암/제거]");
    this.setPermission("sololand.command.land.welcomeparticle");
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
      Message.fail(player, "땅 주인이 아니므로 땅 환영 효과를 수정할 수 없습니다.");
      return true;
    }
    if(args.length < 1){
      return false;
    }
    int particle;
    switch(args[0]){
      case "물방울":
        particle = Particle.TYPE_BUBBLE;
        break;
      case "반짝":
        particle = Particle.TYPE_CRITICAL;
        break;
      case "연기":
        particle = Particle.TYPE_SMOKE;
        break;
      case "하트":
        particle = Particle.TYPE_HEART;
        break;
      case "용암":
        particle = Particle.TYPE_LAVA;
        break;
      case "불":
        particle = Particle.TYPE_FLAME;
        break;
      case "제거":
        particle = 0;
        break; 
      default:
        return false;
    }
    land.setWelcomeParticle(particle);
    Message.success(player, "성공적으로 환영 효과를 설정하였습니다 : " + args[0]);
    return true;
  }
}