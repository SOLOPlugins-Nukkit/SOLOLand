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

import com.solo.sololand.util.Sphere;

public class EmptyWorldGenerator extends Generator {

  public static final int TYPE_EMPTY_WORLD = 12;

  @Override
  public int getId() {
    return TYPE_EMPTY_WORLD;
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
    return "emptyworld";
  }

  public EmptyWorldGenerator() {
    this(new HashMap<>());
  }

  public EmptyWorldGenerator(Map<String, Object> options){
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
    if(chunkX == 0 && chunkZ == 0){
      for(int x = 0; x <= 15; x++){
        for(int z = 0; z <= 15; z++){ 
          chunk.setBlock(x, 10, z, 2, 0);
        }
      }
    }
    for(int x = 0; x <= 15; x++){
      for(int z = 0; z <= 15; z++){ 
        chunk.setBiomeColor(x, z, 133, 188, 86);
      }
    }
    this.level.setChunk(chunkX, chunkZ, chunk);
  }

  @Override
  public void populateChunk(int chunkX, int chunkZ){
  }

  @Override
  public Vector3 getSpawn() {
    return new Vector3(7, 11, 7);
  }
}
