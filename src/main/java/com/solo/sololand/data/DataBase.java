package com.solo.sololand.data;

import cn.nukkit.Server;
import cn.nukkit.level.Level;

import com.solo.sololand.world.World;
import com.solo.sololand.land.Land;

public class DataBase{

  public static void loadLevel(){
    DataLoader.loadLevel();
  }

  public static void loadWorld(){
    for(Level level : Server.getInstance().getLevels().values()){
      DataBase.loadWorld(level);
    }
  }

  public static void loadWorld(Level level){
    World world = DataLoader.loadWorld(level);
    if(world != null){
      World.registerWorld(world);
    }
  }

  public static void saveWorld(){
    DataBase.saveWorld(true);
  }
  public static void saveWorld(boolean saveAll){
    for(World world : World.getAll().values()){
      DataLoader.saveWorld(world);
    }
  }

  public static void removeLand(World world, Land land){
    DataLoader.removeLand(world, land);
  }

}