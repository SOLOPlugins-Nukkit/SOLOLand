package solo.sololand.generator;

import cn.nukkit.level.generator.Generator;
import cn.nukkit.block.Block;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.math.NukkitRandom;

import java.util.HashMap;
import java.util.Map;

public class GridLandGenerator extends Generator {

	public static final int TYPE_GRID_LAND = 11;

	@Override
	public int getId() {
		return TYPE_GRID_LAND;
	}

	private ChunkManager level;

	private final Map<String, Object> options;

	private int floorLevel;

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
		return "gridland";
	}

	public GridLandGenerator() {
		this(new HashMap<String, Object>());
	}

	public GridLandGenerator(Map<String, Object> options){
		this.options = options;
	}


	@Override
	public void init(ChunkManager level, NukkitRandom random) {
		this.level = level;
	}

	@Override
	public void generateChunk(int chunkX, int chunkZ){
		BaseFullChunk chunk = this.level.getChunk(chunkX, chunkZ);
		if(chunkX >= 0 && chunkZ >= 0){
			for(int x = 0; x <= 15; x++){
				for(int z = 0; z <= 15; z++){
					chunk.setBiomeColor(x, z, 133, 188, 86);
					chunk.setBlock(x, 0, z, 7, 0);
					chunk.setBlock(x, 1, z, 7, 0);
					chunk.setBlock(x, 2, z, 1, 0);
					chunk.setBlock(x, 3, z, 1, 0);
					chunk.setBlock(x, 4, z, 1, 0);
					chunk.setBlock(x, 5, z, 1, 0);
					chunk.setBlock(x, 6, z, 3, 0);
					chunk.setBlock(x, 7, z, 3, 0);
					int[] calcRet = this.calcGen(chunkX * 16 + x, chunkZ * 16 + z);
					chunk.setBlock(x, 8, z, calcRet[0], calcRet[1]);
				}
			}
		}
		this.level.setChunk(chunkX, chunkZ, chunk);
	}

	private int[] calcGen(int worldX, int worldZ){
		int[] ret = new int[2];
		ret[0] = 0;
		ret[1] = 0;

		if(worldX == 0 || worldZ == 0){
			ret[0] = Block.DOUBLE_SLABS;
			return ret;
		}
		int gridlandx = worldX % 37;
		int gridlandz = worldZ % 37;

		//center grass part
		if(gridlandx >= 7 && gridlandz >= 7){
			ret[0] = Block.GRASS;
			return ret;
		}

		//center grass edge (block code 43) part
		if(gridlandx >= 6 && gridlandz >= 6){
			ret[0] = Block.DOUBLE_SLABS;
			return ret;
		}

		//road (block code 1:6) part
		if(gridlandx >= 1 && gridlandz >= 1){
			ret[0] = Block.STONE;
			ret[1] = 6;
			return ret;
		}

		if(gridlandx == 0 && gridlandz >= 6){
			ret[0] = Block.DOUBLE_SLABS;
			return ret;
		}
		if(gridlandz == 0 && gridlandx >= 6){
			ret[0] = Block.DOUBLE_SLABS;
			return ret;
		}
		if(gridlandx == 0 && gridlandz == 0){
			ret[0] = Block.DOUBLE_SLABS;
			return ret;
		}
		ret[0] = 1;
		ret[1] = 6;
		return ret;		
	}

	@Override
	public void populateChunk(int chunkX, int chunkZ){
	}

	@Override
	public Vector3 getSpawn() {
		return new Vector3(128, this.floorLevel, 128);
	}
}
