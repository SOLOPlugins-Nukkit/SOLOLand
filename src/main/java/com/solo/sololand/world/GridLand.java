package com.solo.sololand.world;

import cn.nukkit.level.Level;

import com.solo.sololand.land.Land;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class GridLand extends World{


  public LinkedHashMap<String, Object> properties = new LinkedHashMap<String, Object>(){{
    put("type", World.TYPE_GRID_LAND);
    put("customname", level.getFolderName());
    put("protect", true);
    put("invensave", true);
    put("allowexplosion", false);
    put("allowburn", false);
    put("allowfight", false);
    put("allowcreateland", false);
    put("allowcreateroom", true);
    put("allowextendland", true);
    put("allowresizeland", false);
    put("defaultlandprice", 20000.0);
    put("priceperblock", 0.0);
    put("maxlandcount", 4);
    put("minlandlength", 5);
    put("maxlandlength", 100);
  }};

  public GridLand(Level level){
    this(level, new LinkedHashMap<String, Object>());
  }

  public GridLand(Level level, LinkedHashMap<String, Object> properties){
    super(level, properties);
  }

  @Override
  public Land getLand(int x, int z){
    Land land = this.getLandByRecent(x, z);
    if(land != null){
      return land;
    }
    for(Land land2 : this.lands.values()){
      if(this.isInsideLand(land2, x, z)){
        this.recentLands.add(land2.getNumber());
        return land2;
      }
    }

    /* Auto Land Make Code */
    int gridLandX = x % 36;
    int gridLandZ = z % 36;
    if(gridLandX < 0){
      gridLandX += 36;
    }
    if(gridLandZ < 0){
      gridLandZ += 36;
    }
    if(gridLandX >= 6 && gridLandZ >= 6){
      int startX = (int)(x / 36) * 36 + 6;
      int startZ = (int)(z / 36) * 36 + 6;
      return this.createLand(startX, startZ, startX + 31, startZ + 31);
    }
    /* End */

    return null;
  }


}