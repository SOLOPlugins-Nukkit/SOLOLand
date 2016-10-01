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

public class SkyBlockGenerator extends Generator{

  public static final int TYPE_SKY_BLOCK = 13;

  @Override
  public int getId() {
    return TYPE_SKY_BLOCK;
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
    return "skyblock";
  }

  public SkyBlockGenerator() {
    this(new HashMap<>());
  }

  public SkyBlockGenerator(Map<String, Object> options){
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
    for (int x = 0; x < 16; x++)
    for (int z = 0; z < 16; z++){
      chunk.setBiomeColor(x, z, 133, 188, 86);
    }

    generateIslandBlock : {
      int worldX = chunkX * 16;
      int worldZ = chunkZ * 16;
      if(chunkX < 0 || chunkZ < 0 || worldX > 40000){
        break generateIslandBlock;
      }
      int cx = worldX % 200;
      int cz = worldZ % 200;
      if(cx <= 100 && 100 <= cx + 15)
      if(cz <= 100 && 100 <= cz + 15){
        ArrayList<int[]> fill = Sphere.getElements(8, 7, 8, 7);
        int[][] highest = new int[16][16];
        for(int i = 0; i < 16; i++){
          for(int ii = 0; ii < 16; ii++){
            highest[i][ii] = 0;
          }
        }
        for(int[] el : fill){
          int x = el[0];
          int y = el[1];
          int z = el[2];
          if(y < 3){
            chunk.setBlock(x, y, z, Block.BEDROCK);
          }else if(y < 10){
            chunk.setBlock(x, y, z, Block.STONE);
          }else if(y < 12){
            chunk.setBlock(x, y, z, Block.DIRT);
          }else{
            continue;
          }
          if(highest[x][z] < y){
            highest[x][z] = y;
          }
        }

        //Wood
        chunk.setBlock(10, highest[10][10] +1, 10, 17);
        chunk.setBlock(10, highest[10][10] +2, 10, 17);
        chunk.setBlock(10, highest[10][10] +3, 10, 17);
        chunk.setBlock(10, highest[10][10] +4, 10, 17);
        chunk.setBlock(10, highest[10][10] +5, 10, 17);
        chunk.setBlock(10, highest[10][10] +6, 10, 17);

        //Leave
        chunk.setBlock(8, highest[10][10] +4, 8, 18);
        chunk.setBlock(9, highest[10][10] +4, 8, 18);
        chunk.setBlock(10, highest[10][10] +4, 8, 18);
        chunk.setBlock(11, highest[10][10] +4, 8, 18);
        chunk.setBlock(12, highest[10][10] +4, 8, 18);
        chunk.setBlock(8, highest[10][10] +4, 9, 18);
        chunk.setBlock(9, highest[10][10] +4, 9, 18);

       chunk.setBlock(10, highest[10][10] +4, 9, 18);
        chunk.setBlock(11, highest[10][10] +4, 9, 18);
        chunk.setBlock(12, highest[10][10] +4, 9, 18);
        chunk.setBlock(8, highest[10][10] +4, 10, 18);
        chunk.setBlock(9, highest[10][10] +4, 10, 18);
        chunk.setBlock(11, highest[10][10] +4, 10, 18);
        chunk.setBlock(12, highest[10][10] +4, 10, 18);
        chunk.setBlock(8, highest[10][10] +4, 11, 18);
        chunk.setBlock(9, highest[10][10] +4, 11, 18);
        chunk.setBlock(10, highest[10][10] +4, 11, 18);
        chunk.setBlock(11, highest[10][10] +4, 11, 18);
        chunk.setBlock(12, highest[10][10] +4, 11, 18);
        chunk.setBlock(8, highest[10][10] +4, 12, 18);
        chunk.setBlock(9, highest[10][10] +4, 12, 18);
        chunk.setBlock(10, highest[10][10] +4, 12, 18);
        chunk.setBlock(11, highest[10][10] +4, 12, 18);
        chunk.setBlock(12, highest[10][10] +4, 12, 18);

        chunk.setBlock(9, highest[10][10] +5, 8, 18);
        chunk.setBlock(10, highest[10][10] +5, 8, 18);
        chunk.setBlock(11, highest[10][10] +5, 8, 18);
        chunk.setBlock(12, highest[10][10] +5, 8, 18);
        chunk.setBlock(8, highest[10][10] +5, 9, 18);
        chunk.setBlock(9, highest[10][10] +5, 9, 18);
        chunk.setBlock(10, highest[10][10] +5, 9, 18);
        chunk.setBlock(11, highest[10][10] +5, 9, 18);
        chunk.setBlock(12, highest[10][10] +5, 9, 18);
        chunk.setBlock(8, highest[10][10] +5, 10, 18);
        chunk.setBlock(9, highest[10][10] +5, 10, 18);
        chunk.setBlock(11, highest[10][10] +5, 10, 18);
        chunk.setBlock(12, highest[10][10] +5, 10, 18);
        chunk.setBlock(8, highest[10][10] +5, 11, 18);
        chunk.setBlock(9, highest[10][10] +5, 11, 18);
        chunk.setBlock(10, highest[10][10] +5, 11, 18);
        chunk.setBlock(11, highest[10][10] +5, 11, 18);
        chunk.setBlock(12, highest[10][10] +5, 11, 18);
        chunk.setBlock(8, highest[10][10] +5, 12, 18);
        chunk.setBlock(9, highest[10][10] +5, 12, 18);
        chunk.setBlock(10, highest[10][10] +5, 12, 18);
        chunk.setBlock(11, highest[10][10] +5, 12, 18);

        chunk.setBlock(9, highest[10][10] +6, 9, 18);
        chunk.setBlock(10, highest[10][10] +6, 9, 18);
        chunk.setBlock(11, highest[10][10] +6, 9, 18);
        chunk.setBlock(9, highest[10][10] +6, 10, 18);
        chunk.setBlock(11, highest[10][10] +6, 10, 18);
        chunk.setBlock(9, highest[10][10] +6, 11, 18);
        chunk.setBlock(10, highest[10][10] +6, 11, 18);
        chunk.setBlock(11, highest[10][10] +6, 11, 18);

        chunk.setBlock(10, highest[10][10] +7, 9, 18);
        chunk.setBlock(9, highest[10][10] +7, 10, 18);
        chunk.setBlock(10, highest[10][10] +7, 10, 18);
        chunk.setBlock(11, highest[10][10] +7, 10, 18);
        chunk.setBlock(10, highest[10][10] +7, 11, 18);

        //Grass
        chunk.setBlock(3, highest[3][5] +1, 5, 31, 1);
        chunk.setBlock(4, highest[4][4] +1, 4, 31, 1);
        chunk.setBlock(5, highest[5][12] +1, 12, 31, 1);
        chunk.setBlock(6, highest[6][8] +1, 8, 31, 1);
        chunk.setBlock(7, highest[7][11] +1, 11, 31, 1);
        chunk.setBlock(8, highest[8][13] +1, 13, 31, 1);
        chunk.setBlock(9, highest[9][2] +1, 2, 31, 1);
        chunk.setBlock(11, highest[11][5] +1, 5, 31, 1);
        chunk.setBlock(13, highest[13][7] +1, 7, 31, 1);
 

        //Grass Block
        for(int i = 0; i < 16; i++){
          for(int ii = 0; ii < 16; ii++){
            if(highest[i][ii] < 10){
              continue;
            }
            chunk.setBlock(i, highest[i][ii], ii, Block.GRASS);
          }
        }
      }
    }
    this.level.setChunk(chunkX, chunkZ, chunk);
  }

  @Override
  public void populateChunk(int chunkX, int chunkZ){
  }

  @Override
  public Vector3 getSpawn() {
    return new Vector3(108, this.floorLevel, 108);
  }
}
