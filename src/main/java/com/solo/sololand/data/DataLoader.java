package com.solo.sololand.data;

import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.utils.Config;

import com.solo.sololand.land.Land;
import com.solo.sololand.world.*;
import com.solo.sololand.util.Debug;

import java.util.LinkedHashMap;
import java.util.HashMap;
import java.io.File;

public class DataLoader{

  public static File getWorldDir(String levelFolderName){
    return new File(Server.getInstance().getDataPath() + File.separator + "worlds" + File.separator + levelFolderName);
  }
  public static File getWorldPropertiesFile(String levelFolderName){
    return new File(DataLoader.getWorldDir(levelFolderName) + File.separator + "properties.yml");
  }
  public static File getLandDir(String levelFolderName){
    return new File(DataLoader.getWorldDir(levelFolderName) + File.separator + "land");
  }


  public static void loadLevel(){
    File levelDir = new File(Server.getInstance().getDataPath() + File.separator + "worlds");
    for(File levelFile : levelDir.listFiles()) {
      if(!levelFile.isDirectory()){
        continue;
      }
      Server.getInstance().loadLevel(levelFile.getName());
    }
  }

  public static World loadWorld(Level level){
    Debug.normal(level.getFolderName() + " 월드 로딩중...");
    String levelName = level.getFolderName();
    File worldDir = DataLoader.getWorldDir(levelName);
    if(!worldDir.isDirectory()){
      Debug.fail(levelName + " 월드 로드에 실패하였습니다 : 디렉터리가 존재하지 않습니다");
      return null;
    }
    if(World.get(levelName) != null){
      Debug.fail(levelName + " 월드 로드에 실패하였습니다 : 이미 존재하는 월드입니다");
      return null;
    }
    LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) (new Config(DataLoader.getWorldPropertiesFile(levelName), Config.YAML)).getAll();
    World world;
    if(
      properties.containsKey("type") &&
      (int) properties.get("type") == World.TYPE_ISLAND
    ){
      world = new Island(level, properties);
      Debug.normal(levelName + " 월드 로드 성공. 월드 타입 : 1");
    }else{
      world = new World(level, properties);
      Debug.normal(levelName + " 월드 로드 성공. 월드 타입 : 0");
    }
    world.setLands(DataLoader.loadLand(level));
    Debug.normal(levelName + " 월드 땅 로드 성공.");
    return world;
  }

  public static void saveWorld(World world){
    DataLoader.saveWorld(world, true);
  }
  public static void saveWorld(World world, boolean saveAll){
    String levelName = world.getLevel().getFolderName();
    Debug.normal(levelName + " 월드 저장중...");
    Config conf = new Config(DataLoader.getWorldPropertiesFile(levelName), Config.YAML);
    conf.setAll(world.properties);
    conf.save();
    Debug.normal(levelName + " 월드 properties 저장 성공.");
    DataLoader.saveLand(world, saveAll);
    Debug.normal(levelName + " 월드 땅 저장 성공.");
  }



  //You may not to use below method ?
  //maybe ?
  public static HashMap<Integer, Land> loadLand(Level level){
    Debug.normal("땅 로드중...");
    String levelName = level.getFolderName();
    File landDir = DataLoader.getLandDir(levelName);
    landDir.mkdir();

    HashMap<Integer, Land> lands = new HashMap<Integer, Land>();
    int count = 0;
    for(File landFile : landDir.listFiles()) {
      if(!landFile.isFile()){
        continue;
      }
      Land land = new Land((LinkedHashMap<String, Object>) (new Config(landFile, Config.YAML).getAll()));
      lands.put(land.getNumber(), land);
      ++count;
    }
    Debug.normal("로드된 땅 갯수 : " + Integer.toString(count) + "개");
    return lands;
  }

  public static void saveLand(World world){
    DataLoader.saveLand(world, true);
  }
  public static void saveLand(World world, boolean saveAll){
    Debug.normal("땅 저장중...");
    int count = 0;
    for(Land land : world.getLands().values()){
      Config conf = new Config(new File(DataLoader.getLandDir(world.getLevel().getFolderName()) + File.separator + Integer.toString(land.getNumber()) + ".yml"), Config.YAML);
      conf.setAll(DataLoader.landToMap(land));
      conf.save();
      count++;
    }
    Debug.normal("저장된 땅 갯수 : " + Integer.toString(count) + "개");
  }

  private static LinkedHashMap<String, Object> landToMap(Land land){
    LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>(); 
    data.put("landnumber", land.getNumber());
    data.put("startx", land.startX);
    data.put("endx", land.endX);
    data.put("startz", land.startZ);
    data.put("endz", land.endZ);
    data.put("issail", land.isSail);
    data.put("price", land.price);
    data.put("owner", land.owner);
    data.put("members", land.members);
    data.put("spawnpoint", land.spawnPoint);
    data.put("isallowfight", land.isAllowFight);
    data.put("isallowaccess", land.isAllowAccess);
    data.put("isallowpickupitem", land.isAllowPickUpItem);
    data.put("welcomemessage", land.welcomeMessage);
    data.put("welcomeparticle", land.welcomeParticle);
    return data;
  }

  public static boolean removeLand(World world, Land land){
    File target = new File(DataLoader.getLandDir(world.getLevel().getFolderName()) + File.separator + Integer.toString(land.getNumber()) + ".yml");
    return target.delete();
  }


}