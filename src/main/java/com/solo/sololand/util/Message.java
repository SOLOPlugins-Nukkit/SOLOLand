package com.solo.sololand.util;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

public abstract class Message {

  public static void usage(CommandSender sender, String msg){
    sender.sendMessage("§d[ 사용법 ] §7" + msg);
  }
  public static void usage(Player player, String msg){
    player.sendMessage("§d[ 사용법 ] §7" + msg);
  }

  public static void success(CommandSender sender, String msg){
    sender.sendMessage("§a[ 알림 ] §7" + msg);
  }
  public static void success(Player player, String msg){
    player.sendMessage("§a[ 알림 ] §7" + msg);
  }

  public static void fail(CommandSender sender, String msg){
    sender.sendMessage("§c[ 알림 ] §7" + msg);
  }
  public static void fail(Player player, String msg){
    player.sendMessage("§c[ 알림 ] §7" + msg);
  }

  public static void normal(CommandSender sender, String msg){
    sender.sendMessage("§b[ 알림 ] §7" + msg);
  }
  public static void normal(Player player, String msg){
    player.sendMessage("§b[ 알림 ] §7" + msg);
  }

  public static void help(CommandSender sender, String msg){
    sender.sendMessage("§d[ 도움말 ] §7" + msg);
  }
  public static void help(Player player, String msg){
    player.sendMessage("§d[ 도움말 ] §7" + msg);
  }

  public static void popup(Player player, String msg){
    player.sendPopup("§b" + msg);
  }

  public static void alert(Player player, String msg){
    player.sendPopup("§c" + msg);
  }

  public static void tip(Player player, String msg){
    player.sendTip("§b" + msg);
  }


}