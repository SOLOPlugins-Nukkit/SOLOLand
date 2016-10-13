package com.solo.sololand.command.land;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;

public class LandWelcomeMessage extends SubCommand{

  public LandWelcomeMessage(MainCommand mCmd){
    super(mCmd, "환영말", "다른 유저가 땅 방문시 보낼 메세지를 설정합니다.");
    this.setAliases(new String[]{"환영메세지", "방문메세지"});
    this.setUsage("/" + mCmd.getName() + " " + this.getName() + " [환영말...]");
    this.setPermission("sololand.command.land.welcomemessage");
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
      Message.fail(player, "땅 주인이 아니므로 땅 환영말을 수정할 수 없습니다.");
      return true;
    }
    if(args.length < 1){
      return false;
    }
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < (args.length - 1); i++){
      sb.append(args[i]);
      if(i < (args.length - 1)){
        sb.append(" ");
      }
    }
    String welcomeMsg = sb.toString();
    land.setWelcomeMessage(welcomeMsg);
    Message.success(player, "성공적으로 환영말을 설정하였습니다 : " + welcomeMsg);
    return true;
  }
}