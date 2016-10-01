package com.solo.sololand.command.land;

import cn.nukkit.Player;
import cn.nukkit.level.particle.Particle;

import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class WelcomeParticle{
  public static void execute(Player player, String[] args){
    World world = World.get(player.getLevel());
    Land land = world.getLand(player.getFloorX(), player.getFloorZ());
    if(land == null){
      Message.fail(player, "현재 위치에서 땅을 찾을 수 없습니다.");
      return;
    }
    if(!player.isOp() && !land.isOwner(player.getName().toLowerCase())){
      Message.fail(player, "땅 주인이 아니므로 땅 환영 파티클을 수정할 수 없습니다.");
      return;
    }
    if(args.length < 2){
      Message.usage(player, "/땅 환영효과 [물방울/반짝/연기/하트/제거]");
      return;
    }
    int particle;
    switch(args[1]){
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
        Message.usage(player, "/땅 환영효과 [물방울/반짝/연기/하트/용암/불/제거]");
        return;
    }
    land.setWelcomeParticle(particle);
    Message.success(player, "성공적으로 환영 효과를 설정하였습니다 : " + args[1]);
  }
}