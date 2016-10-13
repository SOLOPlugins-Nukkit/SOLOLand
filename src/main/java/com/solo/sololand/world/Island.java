package com.solo.sololand.world;

import cn.nukkit.level.Level;

import com.solo.sololand.land.Land;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Island extends World{

  public LinkedHashMap<String, Object> properties = new LinkedHashMap<String, Object>(){{
    put("type", World.TYPE_ISLAND);
    put("customname", level.getFolderName());
    put("protect", true);
    put("invensave", true);
    put("allowexplosion", false);
    put("allowburn", false);
    put("allowfight", false);
    put("allowcreateland", false);
    put("allowcreateroom", true);
    put("allowextendland", false);
    put("allowresizeland", false);
    put("defaultlandprice", 20000.0);
    put("priceperblock", 0.0);
    put("maxlandcount", 4);
    put("minlandlength", 5);
    put("maxlandlength", 100);
  }};

  public Island(Level level){
    this(level, new LinkedHashMap<String, Object>());
  }

  public Island(Level level, LinkedHashMap<String, Object> properties){
    super(level, properties);
  }

  public Land createLand(String owner){
    owner = owner.toLowerCase();
    int num = 1;
    while(true){
      if(!(this.lands.containsKey(num))){
        break;
      }
      num++;
    }
    int divX = num % 200;
    int divZ = num / 200;
    int startX = (num % 200) * 200;
    int startZ = (num / 200) * 200;
    int endX = startX + 199;
    int endZ = startZ + 199;
    
    LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();
    data.put("landnumber", num);
    data.put("level", this.level);
    data.put("startx", startX);
    data.put("startz", startZ);
    data.put("endx", endX);
    data.put("endz", endZ);
    data.put("issail", false);
    data.put("price", 0.0);
    data.put("owner", owner);
    ArrayList<String> members = new ArrayList<String>();
    members.add(owner);
    data.put("members", members);
    int spawnY = 13;
    double spawnX = (((startX + 100) / 16) * 16) + 7.5;
    double spawnZ = (((startZ + 100) / 16) * 16) + 7.5;
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

  @Override
  public boolean removeLand(int num) {
    if(!this.lands.containsKey(num))
      return false;
    Land land = this.lands.get(num);
    land.setPrice(0.0);
    land.setOwner("");
    land.setSail(true);
    land.setMembers(new ArrayList<String>());
    land.setWelcomeMessage("");
    return true;
  }

  @Override
  public Land getLand(int x, int z){
    Land land;
    int assumeNum = this.getLandNumberByXZ(x, z);
    if(assumeNum > 0){
      land = this.getLand(assumeNum);
      if(land != null){
        if(land.startX <= x && land.startZ <= z && land.endX >= x && land.endZ >= z){
          return land;
        }
      }
    }
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

  protected int getLandNumberByXZ(int x, int z){
    if(x < 0 || z < 0 || x > 40000){
      return 0;
    }
    int divX = x / 200;
    int divZ = z / 200;
    return ((divZ * 200) + divX);
  }

}