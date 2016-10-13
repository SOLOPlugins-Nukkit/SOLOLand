package com.solo.sololand.command.world;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.util.Message;

public class WorldSetCustomName extends SubCommand{

  public WorldSetCustomName(MainCommand mCmd){
    super(mCmd, "이름변경", "플러그인에서 사용될 월드 이름을 변경합니다.");
    this.setAliases(new String[]{"이름", "커스텀이름"});
    this.setUsage("/" + mCmd.getName() + " " + this.getName() + " [바꿀 이름]");
    this.setPermission("sololand.command.world.setcustomname");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
    if(args.length < 1){
      return false;
    }
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < (args.length - 1); i++){
      sb.append(args[i]);
      if(i < args.length - 1){
        sb.append(" ");
      }
    }
    String customName = sb.toString();
    switch(customName){
      case "월드":
      case "땅":
        Message.fail(player, "해당 이름으로 월드 이름을 지을 수 없습니다.");
        return true;
    }
    World world = World.get(player.getLevel());
    world.setCustomName(customName);
    Message.success(player, world.getName() + " 월드의 이름을 " + customName + " 으로 변경하였습니다.");
    return true;
  }
}