package com.solo.sololand.generator;

import cn.nukkit.level.generator.Generator;

import cn.nukkit.block.Block;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.math.NukkitRandom;

import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SkyGridGenerator extends Generator{

  public static final int TYPE_SKY_GRID = 14;

  private static final int BLOCK_INTERVAL = 3;

  @Override
  public int getId() {
    return TYPE_SKY_GRID;
  }

  private ChunkManager level;
  private final Map<String, Object> options;
  private int floorLevel;
  private NukkitRandom random;
  private Random javaRandom = new Random();

  protected final ArrayList<int[]> genBlocks = new ArrayList<int[]>(){{
    add(new int[]{1, 0});
    add(new int[]{1, 1});
    add(new int[]{1, 3});
    add(new int[]{1, 5});
    add(new int[]{2, 0});
    add(new int[]{3, 0});
    add(new int[]{4, 0});
    add(new int[]{5, 0});
    add(new int[]{5, 1});
    add(new int[]{5, 2});
    add(new int[]{5, 3});
    add(new int[]{5, 4});
    add(new int[]{5, 5});
    add(new int[]{6, 0});
    add(new int[]{6, 1});
    add(new int[]{6, 2});
    add(new int[]{6, 3});
    add(new int[]{6, 4});
    add(new int[]{6, 5});
    add(new int[]{9, 0});
    add(new int[]{11, 0});
    add(new int[]{12, 0});
    add(new int[]{12, 1});
    add(new int[]{13, 0});
    add(new int[]{14, 0});
    add(new int[]{15, 0});
    add(new int[]{16, 0});
    add(new int[]{17, 0});
    add(new int[]{17, 1});
    add(new int[]{17, 2});
    add(new int[]{17, 3});
    add(new int[]{18, 0});
    add(new int[]{18, 1});
    add(new int[]{18, 2});
    add(new int[]{18, 3});
    add(new int[]{19, 0});
    add(new int[]{20, 0});
    add(new int[]{21, 0});
    add(new int[]{24, 0});
    add(new int[]{24, 1});
    add(new int[]{24, 2});
    add(new int[]{30, 0});
    add(new int[]{35, 0});
    add(new int[]{46, 0});
    add(new int[]{47, 0});
    add(new int[]{48, 0});
    add(new int[]{49, 0});
    add(new int[]{54, 0});
    add(new int[]{56, 0});
    add(new int[]{58, 0});
    add(new int[]{61, 0});
    add(new int[]{73, 0});
    add(new int[]{79, 0});
    add(new int[]{80, 0});
    add(new int[]{81, 0});
    add(new int[]{82, 0});
    add(new int[]{83, 0});
    add(new int[]{86, 0});
    add(new int[]{87, 0});
    add(new int[]{88, 0});
    add(new int[]{89, 0});
    add(new int[]{98, 0});
    add(new int[]{98, 1});
    add(new int[]{98, 2});
    add(new int[]{103, 0});
    add(new int[]{110, 0});
    add(new int[]{112, 0});
    add(new int[]{121, 0});
    add(new int[]{153, 0});
    add(new int[]{165, 0});
    add(new int[]{246, 0});
  }};

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
            y % (BLOCK_INTERVAL + 1) == 0 &&
            (chunkX * 16 + x) % (BLOCK_INTERVAL + 1)  == 0 &&
            (chunkZ * 16 + z) % (BLOCK_INTERVAL + 1) == 0
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
    return this.genBlocks.get(this.javaRandom.nextInt(this.genBlocks.size() - 1));
  }

  @Override
  public void populateChunk(int chunkX, int chunkZ){
  }

  @Override
  public Vector3 getSpawn() {
    return new Vector3(0, this.floorLevel, 0);
  }
}
