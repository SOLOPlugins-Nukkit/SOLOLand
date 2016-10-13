package com.solo.sololand.command.land;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.SubCommand;
import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;
import com.solo.sololand.util.Message;
import com.solo.sololand.util.Queue;

public class LandRemove extends SubCommand{

  public LandRemove(MainCommand mCmd){
    super(mCmd, "삭제", "땅을 삭제합니다.");
    this.setAliases(new String[]{"제거"});
    this.setPermission("sololand.command.land.remove");
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    Player player = (Player) sender;
    World world = World.get(player.getLevel());
    Land land = world.getLand(player.getFloorX(), player.getFloorZ());
    if(land == null){
      Message.usage(player, "현재 위치에서 땅을 찾을 수 없습니다.");
      return true;
    }
    int num = land.getNumber();
    String name = player.getName().toLowerCase();
    if(!player.isOp() && !land.isOwner(name)){
      Message.fail(player, "땅 주인이 아니므로 땅을 삭제할 수 없습니다.");
      return true;
    }
    switch(Queue.getQueue(name)){
      case Queue.NULL:
        Queue.putQueue(name, Queue.REMOVE_LAND);
        Queue.putData(name, num);
        Message.normal(player, "정말로 땅을 제거하시겠습니까? 제거하시려면 /땅 삭제 명령어를 한번 더 입력해주세요. 취소하려면 /땅 취소 명령어를 입력해주세요.");
        break;

      case Queue.REMOVE_LAND:
        int prevNum = (int) Queue.getData(name);
        if(prevNum == num){
          world.removeLand(num);
          Message.success(player, "성공적으로 " + Integer.toString(num) + "번 땅을 제거하였습니다.");
          Queue.clean(name);
        }
        break;

      default:
        Message.fail(player, "다른 작업이 진행중입니다. /땅 취소 명령어 입력 후 다시 시도해주세요.");
    }
    return true;
  }
}
