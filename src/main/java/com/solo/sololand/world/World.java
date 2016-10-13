package com.solo.sololand.world;

import cn.nukkit.Server;
import cn.nukkit.Player;
import cn.nukkit.level.Level;
import cn.nukkit.level.generator.biome.Biome;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Config;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;

import java.io.File;

import com.solo.sololand.Main;
import com.solo.sololand.command.custom.CustomWorldCommand;
import com.solo.sololand.land.Land;
import com.solo.sololand.data.DataBase;
import com.solo.sololand.util.Debug;

public class World{

  //class
  public static LinkedHashMap<String, World> worlds = new LinkedHashMap<String, World>();
  public static final int TYPE_NORMAL = 0;
  public static final int TYPE_ISLAND = 1;
  public static final int TYPE_GRID_LAND = 2;

  //instance
  public final Level level;
  public final String name;
  public LinkedHashMap<String, Object> properties = new LinkedHashMap<String, Object>(){{
    put("type", World.TYPE_NORMAL);
    put("customname", "default");
    put("protect", true);
    put("invensave", true);
    put("allowexplosion", false);
    put("allowburn", false);
    put("allowfight", false);
    put("allowcreateland", false);
    put("allowcreateroom", true);
    put("allowextendland", true);
    put("allowresizeland", true);
    put("defaultlandprice", 20000.0);
    put("priceperblock", 20.0);
    put("maxlandcount", 4);
    put("minlandlength", 5);
    put("maxlandlength", 100);
  }};

  //lands
  public HashMap<Integer, Land> lands = new HashMap<Integer, Land>();
  public ArrayList<Integer> recentGetLandByNum = new ArrayList<Integer>();
  public HashMap<String, Integer> recentGetLandByName = new HashMap<String, Integer>();

  public World(Level level){
    this(level, new LinkedHashMap<String, Object>());
  }

  public World (Level level, LinkedHashMap<String, Object> properties){
    this.name = level.getFolderName();
    this.level = level;
    this.properties.put("customname", level.getFolderName());
    this.properties.putAll(properties);

    //Add new Command !!
    Main.getInstance().getCommandMap().register(new CustomWorldCommand(this));
  }

  /*  #class method  */
  public static void registerWorld(World world){
    if(World.worlds.containsKey(world.name)){
      return;
    }
    World.worlds.put(world.getName(), world);
    Debug.normal(world.getName() + " 월드 등록 완료");
  }

  //Do not use this method !!
  public static void unregisterWorld(World world){
    if(!World.worlds.containsKey(world.name)){
      return;
    }
    World.worlds.remove(world.getName(), world);
    Debug.normal(world.getName() + " 월드 등록 해제 완료");
  }

  public static World get(Level level){
    return World.worlds.get(level.getFolderName());
  }
  public static World get(String levelFolderName){
    return World.worlds.get(levelFolderName);
  }

  public static World getByName(String customname){
    for(World world : World.worlds.values()){
      if(world.getCustomName().equals(customname)){
        return world;
      }
    }
    return null;
  }

  public static LinkedHashMap<String, World> getAll() {
    return World.worlds;
  }

  public static boolean isExistIsland() {
    World world = World.get("island");
    if(world == null || !(world instanceof Island)){
      return false;
    }
    return true;
  }





  public Level getLevel() {
    return this.level;
  }

  public String getName() {
    return this.name;
  }

  public String getCustomName(){
    return (String) this.properties.get("customname");
  }
  public void setCustomName(String name){
    this.properties.put("customname", name);
  }

  public boolean isProtected() {
    return (boolean) this.properties.get("protect");
  }
  public void setProtected(boolean bool) {
    this.properties.put("protect", bool);
  }

  public boolean isInvenSave() {
    return (boolean) this.properties.get("invensave");
  }
  public void setInvenSave(boolean bool) {
    this.properties.put("invensave", bool);
  }

  public boolean isAllowExplosion() {
    return (boolean) this.properties.get("allowexplosion");
  }
  public void setAllowExplosion(boolean bool) {
    this.properties.put("allowexplosion", bool);
  }

  public boolean isAllowBurn() {
    return (boolean) this.properties.get("allowburn");
  }
  public void setAllowBurn(boolean bool) {
    this.properties.put("allowburn", bool);
  }
 

  public boolean isAllowFight() {
    return (boolean) this.properties.get("allowfight");
  }
  public void setAllowFight(boolean bool) {
    this.properties.put("allowfight", bool);
  }

  public double getDefaultLandPrice() {
    return (double) this.properties.get("defaultlandprice");
  }
  public void setDefaultLandPrice(double price) {
    this.properties.put("defaultlandprice", price);
  }

  public double getPricePerBlock() {
    return (double) this.properties.get("priceperblock");
  }
  public void setPricePerBlock(double price) {
    this.properties.put("priceperblock", price);
  }

  public boolean isAllowCreateLand() {
    return (boolean) this.properties.get("allowcreateland");
  }
  public void setAllowCreateLand(boolean bool){
    this.properties.put("allowcreateland", bool);
  }

  public boolean isAllowExtendLand() {
    return (boolean) this.properties.get("allowextendland");
  }
  public void setAllowExtendLand(boolean bool){
    this.properties.put("allowextendland", bool);
  }

  public boolean isAllowResizeLand() {
    return (boolean) this.properties.get("allowresizeland");
  }
  public void setAllowResizeLand(boolean bool){
    this.properties.put("allowresizeland", bool);
  }

  public int getMaxLandCount() {
    return (int) this.properties.get("maxlandcount");
  }
  public void setMaxLandCount(int count){
    this.properties.put("maxlandcount", count);
  }

  public int getMinLandLength() {
    return (int) this.properties.get("minlandlength");
  }
  public void setMinLandLength(int count){
    this.properties.put("minlandlength", count);
  }

  public int getMaxLandLength() {
    return (int) this.properties.get("maxlandlength");
  }
  public void setMaxLandLength(int count){
    this.properties.put("maxlandlength", count);
  }





  public Land createLand(int startX, int startZ, int endX, int endZ) {
    ArrayList<String> members = new ArrayList<String>();
    return this.createLand(startX, startZ, endX, endZ, true, this.getDefaultLandPrice(), "", members);
  }

  public Land createLand(int startX, int startZ, int endX, int endZ, String owner){
    owner = owner.toLowerCase();
    ArrayList<String> members = new ArrayList<String>();
    members.add(owner);
    return this.createLand(startX, startZ, endX, endZ, false, this.getDefaultLandPrice(), owner, members);
  }

  public Land createLand(int startX, int startZ, int endX, int endZ, boolean isSail, double price, String owner, ArrayList<String> members){
    owner = owner.toLowerCase();
    int num = 1;
    while(true){
      if(!(this.lands.containsKey(num))){
        break;
      }
      num++;
    }
    /*
    # set glass color, but this is not work yet
    for(int x = startX; x <= endX; x++)
    for(int z = startZ; z <= endZ; z++){
      this.level.setBiomeColor(x, z, 255, 255, 255);
    }
    */
    LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();
    data.put("landnumber", num);
    data.put("level", this.level);
    data.put("startx", startX);
    data.put("startz", startZ);
    data.put("endx", endX);
    data.put("endz", endZ);
    data.put("issail", isSail);
    data.put("price", price);
    data.put("owner", owner);
    data.put("members", members);
    int spawnY = 128;
    for(int y = 127; y > 0; y--){
      if(this.level.getBlockIdAt(startX, y, startZ) == 0){
        continue;
      }
      spawnY = y;
      break;
    }
    double spawnX = startX + 0.5;
    double spawnZ = startZ + 0.5;
    String spawnPoint = Double.toString(spawnX) + ":" + Integer.toString(spawnY + 1) + ":" + Double.toString(spawnZ);
    data.put("spawnpoint", spawnPoint);
    data.put("isallowfight", false);
    data.put("isallowaccess", true);
    data.put("isallowpickupitem", true);
    data.put("welcomemessage", "");
    data.put("welcomeparticle", 0);

    this.lands.put(num, new Land(data));
    return this.lands.get(num);
  }

  public boolean isExistLand(int num){
    return this.lands.containsKey(num);
  }

  public void setLands(HashMap<Integer, Land> lands){
    this.lands = lands;
  }

  public HashMap<Integer, Land> getLands(){
    return this.lands;
  }
  public HashMap<Integer, Land> getLands(Player player){
    return this.getLands(player.getName());
  }
  public HashMap<Integer, Land> getLands(String name){
    return this.getLands(name, false);
  }
  public HashMap<Integer, Land> getLands(Player player, boolean getShareLands){
    return this.getLands(player.getName(), getShareLands);
  }
  public HashMap<Integer, Land> getLands(String name, boolean getShareLands){
    name = name.toLowerCase();
    HashMap<Integer, Land> ret = new HashMap<Integer, Land>();
    if(getShareLands){
      for(Land land : this.getLands().values()){
        if(land.isMember(name) && !land.isOwner(name)){
          ret.put(land.getNumber(), land);
        }
      }
    }else{
      for(Land land : this.getLands().values()){
        if(land.isOwner(name)){
          ret.put(land.getNumber(), land);
        }
      }
    }
    return ret;
  }



  public Land getLand(int num){
    return this.lands.get(num);
  }
  public Land getLand(Vector3 vec){
    return this.getLand((int)vec.x, (int)vec.z);
  }
  public Land getLand(int x, int z){
    Land land;
    for(int num : this.recentGetLandByNum){
      land = this.getLand(num);
      if(land != null){
        if(land.startX <= x && land.startZ <= z && land.endX >= x && land.endZ >= z){
          return land;
        }
      }
    }
    for(Land land2 : this.lands.values()){
      if(land2.startX <= x && land2.startZ <= z && land2.endX >= x && land2.endZ >= z){
        this.recentGetLandByNum.add(land2.getNumber());
        return land2;
      }
    }
    return null;
  }
  public Land getLand(int x, int z, String who){
    Land land;
    if(this.recentGetLandByName.containsKey(who)){
      land = this.getLand(this.recentGetLandByName.get(who));
      if(land != null){
        if(land.startX <= x && land.startZ <= z && land.endX >= x && land.endZ >= z){
          return land;
        }
      }
    }
    land = this.getLand(x, z);
    if(land != null){
      this.recentGetLandByName.put(name, land.getNumber());
      return land;
    }
    return null;
  }

  public boolean removeLand(int num) {
    if(!this.lands.containsKey(num)){
      return false;
    }
    Land land = this.lands.get(num);
    DataBase.removeLand(this, land);

    /* Sucks! why this code is remain until now? */
    /*for(int x = land.startX; x <= land.endX; x++)
    for(int z = land.startZ; z <= land.endZ; z++){
      int c = Biome.getBiome(this.level.getBiomeId(x, z)).getColor();
      int r = c >> 16;
      int g = (c >> 8) & 0xff;
      int b = c & 0xff;
      this.level.setBiomeColor(x, z, r, g, b);
      Debug.normal("biome color");
    }*/

    this.lands.remove(num);
    return true;
  }
  public boolean removeLand(Land land){
    return this.removeLand(land.getNumber());
  }

  public Land checkOverlap(int startX, int startZ, int endX, int endZ){
    for(Land land : this.lands.values()){
      if(land.endX < startX || land.startX > endX){
        continue;
      }
      if(land.endZ < startZ || land.startZ > endZ){
        continue;
      }
      return land;
    }
    return null;
  }

}


