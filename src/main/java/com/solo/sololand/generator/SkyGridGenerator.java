package com.solo.sololand.generator;

import cn.nukkit.level.generator.Generator;

import cn.nukkit.block.Block;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.math.NukkitRandom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SkyGridGenerator extends Generator{

  public static final int TYPE_SKY_GRID = 14;

  @Override
  public int getId() {
    return TYPE_SKY_GRID;
  }

  private ChunkManager level;

  private final Map<String, Object> options;

  private int floorLevel;

  private NukkitRandom random;

  @Override
  public ChunkManager getChunkManager() {
    return level;
  }

  @Override
  public Map<String, Object> getSettings() {
    return this.options;
  }

  @Override
  public String getName() {
    return "skygrid";
  }

  public SkyGridGenerator() {
    this(new HashMap<>());
  }

  public SkyGridGenerator(Map<String, Object> options){
    this.options = options;
  }


  @Override
  public void init(ChunkManager level, NukkitRandom random) {
    this.level = level;
    this.random = random;
  }

  @Override
  public void generateChunk(int chunkX, int chunkZ){
    BaseFullChunk chunk = this.level.getChunk(chunkX, chunkZ);
    for (int x = 0; x < 16; x++){
      for(int y = 0; y < 126; y++){
        for (int z = 0; z < 16; z++){
          if(
            y % 5 == 0 &&
            (chunkX * 16 + x) % 5  == 0 &&
            (chunkZ * 16 + z) % 5 == 0
          ){
            int[] calcRet = this.calcGen();
            chunk.setBlock(x, y, z, calcRet[0], calcRet[1]);
          }
          chunk.setBiomeColor(x, z, 133, 188, 86);
        }
      }
    }  
    this.level.setChunk(chunkX, chunkZ, chunk);
  }

  protected int[] calcGen(){
    int[] ret = new int[2];
    ret[0] = 1;
    ret[1] = 0;
    return ret;
  }

  @Override
  public void populateChunk(int chunkX, int chunkZ){
  }

  @Override
  public Vector3 getSpawn() {
    return new Vector3(0, this.floorLevel, 0);
  }
}
