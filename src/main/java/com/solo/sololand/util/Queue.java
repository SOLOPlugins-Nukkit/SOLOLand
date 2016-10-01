package com.solo.sololand.util;

import java.util.Map;
import java.util.HashMap;

public abstract class Queue {

  public static Map player = new HashMap<String, Integer>();
  public static Map playerData = new HashMap<String, Object>();

  public static final int NULL = 0;

  public static final int CREATE_LAND_FIRST = 1;
  public static final int CREATE_LAND_SECOND = 2;
  public static final int CREATE_LAND_THIRD = 3;

  public static final int REMOVE_LAND = 4;

  public static final int EXTEND_LAND_FIRST = 5;
  public static final int EXTEND_LAND_SECOND = 6;

  public static final int RESIZE_LAND_FIRST = 7;
  public static final int RESIZE_LAND_SECOND = 8;

  public static void putQueue(String name, int queue){
    Queue.player.put(name.toLowerCase(), queue);
  }

  public static void removeQueue(String name){
    Queue.player.remove(name.toLowerCase());
  }
  public static boolean containsQueue(String name){
    return Queue.player.containsKey(name.toLowerCase());
  }
  public static int getQueue(String name){
    if(Queue.containsQueue(name.toLowerCase())){
      return (int) Queue.player.get(name.toLowerCase());
    }
    return Queue.NULL;
  }

  public static void putData(String name, Object obj){
    Queue.playerData.put(name.toLowerCase(), obj);
  }
  public static void removeData(String name){
    Queue.playerData.remove(name.toLowerCase());
  }
  public static boolean containsData(String name){
    return Queue.playerData.containsKey(name.toLowerCase());
  }
  public static Object getData(String name){
    return Queue.playerData.get(name.toLowerCase());
  }

  public static void clean(String name){
    Queue.removeQueue(name);
    Queue.removeData(name);
  }
  

}