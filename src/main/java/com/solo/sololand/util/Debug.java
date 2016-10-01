package com.solo.sololand.util;

import cn.nukkit.Server;

public abstract class Debug{

  public static void normal(String msg){
    Server.getInstance().getLogger().info("§b[SOLOLand Debug] "+ msg);
  }
  public static void fail(String msg){
    Server.getInstance().getLogger().info("§c[SOLOLand Debug] "+ msg);
  }
  public static void bench(String msg){
    Server.getInstance().getLogger().info("§d[SOLOLand Bench] "+ msg);
  }
}