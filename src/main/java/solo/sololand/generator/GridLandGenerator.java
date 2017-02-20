package solo.sololand.generator;

import cn.nukkit.level.generator.Generator;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockStone;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.math.NukkitRandom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GridLandGenerator extends Generator{

	public static final int TYPE_GRID_LAND = 11;

	@Override
	public int getId(){
		return TYPE_GRID_LAND;
	}

	private ChunkManager level;
	private final Map<String, Object> options;
	private int floorLevel;
	
	private String preset = "1;7,4x1,3x3;3;road(block=1:6 width=5 depth=5),land(width=32 depth=32 border=43 block=2)";
	
	// preset example = 1;7,4x1,3x3;3;road(block=17 width=3 depth=3),land(width=32 depth=20 border=43 block=2)
	
	@SuppressWarnings("unused")
	private int version = 1;
	
	private int[] flatBlocksId = new int[]{Block.BEDROCK, Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.DIRT, Block.DIRT, Block.DIRT};
	private int[] flatBlocksDamage = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
	private int roadBlockId = Block.STONE;
	private int roadBlockDamage = BlockStone.POLISHED_ANDESITE;
	private int roadWidth = 5;
	private int roadDepth = 5;
	
	private int landBlockId = 2;
	private int landBlockDamage = 0;
	private int landWidth = 32;
	private int landDepth = 32;
	private int landBorderBlockId = Block.DOUBLE_SLAB;
	private int landBorderBlockDamage = 0;

	@Override
	public ChunkManager getChunkManager(){
		return level;
	}

	@Override
	public Map<String, Object> getSettings(){
		return this.options;
	}

	@Override
	public String getName(){
		return "gridland";
	}

	public GridLandGenerator(){
		this(new HashMap<String, Object>());
	}

	public GridLandGenerator(Map<String, Object> options){
		this.options = options;
		if(this.options.containsKey("preset")){
			this.preset = (String) this.options.get("preset");
		}
		this.parsePreset();
	}
	
	public int getLandWidth(){
		return this.landWidth;
	}
	
	public int getLandDepth(){
		return this.landDepth;
	}
	
	public int getRoadWidth(){
		return this.roadWidth;
	}
	
	public int getRoadDepth(){
		return this.roadDepth;
	}
	
	protected void parsePreset(){
		if(this.preset.isEmpty()){
			return;
		}
		
		String[] p = this.preset.toLowerCase().split(";");
		
		try{
			this.version = Integer.parseInt(p[0]);
		}catch(Exception e){
			System.out.println("Causes at first block");
			e.printStackTrace();
		}
		
		try{
			ArrayList<Integer> flatBlocksId = new ArrayList<Integer>();
			ArrayList<Integer> flatBlocksDamage = new ArrayList<Integer>();
			for(String b : p[1].split(",")){
				String[] c = b.split("x");
				int loop = c.length == 1 ? 1 : Integer.parseInt(c[0]);
				String blockString = c[c.length - 1];
				for(int i = 0; i < loop; i++){
					int blockId = Integer.parseInt(blockString.split(":")[0]);
					int blockDamage = 0;
					if(blockString.split(":").length == 2){
						blockDamage = Integer.parseInt(blockString.split(":")[1]);
					}
					flatBlocksId.add(blockId);
					flatBlocksDamage.add(blockDamage);
				}
			}
			int total = flatBlocksId.size();
			this.flatBlocksId = new int[total];
			this.flatBlocksDamage = new int[total];
			for(int i = 0; i < total; i++){
				this.flatBlocksId[i] = flatBlocksId.get(i);
				this.flatBlocksDamage[i] = flatBlocksDamage.get(i);
			}
		}catch(Exception e){
			System.out.println("Causes at second block");
			e.printStackTrace();
		}
		
		try{
			@SuppressWarnings("unused")
			int biomeId = Integer.parseInt(p[2]);
		}catch(Exception e){
			System.out.println("Causes at third block");
			e.printStackTrace();
		}
		
		try{
			for(String b : p[3].split(",")){
				String param = b.substring(0, b.indexOf("("));
				String[] options = b.substring(b.indexOf("(") + 1, b.indexOf(")")).split(" ");
				
				if(options.length != 0){
					switch(param){
						case "":
							break;
							
						case "road":
							for(String option : options){
								String[] t = option.split("=");
								if(t[0].equals("block")){
									String[] block = t[1].split(":");
									this.roadBlockId = Integer.parseInt(block[0]);
									try{
										this.roadBlockDamage = Integer.parseInt(block[1]);
									}catch(Exception e){
										this.roadBlockDamage = 0;
									}
								}else if(t[0].equals("width")){
									this.roadWidth = Integer.parseInt(t[1]);
								}else if(t[0].equals("depth")){
									this.roadDepth = Integer.parseInt(t[1]);
								}
							}
							
						case "land":
							for(String option : options){
								String[] t = option.split("=");
								if(t[0].equals("block")){
									String[] block = t[1].split(":");
									this.landBlockId = Integer.parseInt(block[0]);
									try{
										this.landBlockDamage = Integer.parseInt(block[1]);
									}catch(Exception e){
										this.landBlockDamage = 0;
									}
								}else if(t[0].equals("width")){
									this.landWidth = Integer.parseInt(t[1]);
								}else if(t[0].equals("depth")){
									this.landDepth = Integer.parseInt(t[1]);
								}else if(t[0].equals("border")){
									String[] block = t[1].split(":");
									this.landBorderBlockId = Integer.parseInt(block[0]);
									try{
										this.landBorderBlockDamage = Integer.parseInt(block[1]);
									}catch(Exception e){
										this.landBorderBlockDamage = 0;
									}
								}
							}
					}
				}
			}
		}catch(Exception e){
			System.out.println("Causes at fourth block");
			e.printStackTrace();
		}
		
	}

	@Override
	public void init(ChunkManager level, NukkitRandom random){
		this.level = level;
	}

	@Override
	public void generateChunk(int chunkX, int chunkZ){
		BaseFullChunk chunk = this.level.getChunk(chunkX, chunkZ);
		if(chunkX >= 0 && chunkZ >= 0){
			for(int x = 0; x <= 15; x++){
				for(int z = 0; z <= 15; z++){
					chunk.setBiomeColor(x, z, 133, 188, 86);
					for(int i = 0; i < this.flatBlocksId.length; i++){
						chunk.setBlock(x, i, z, this.flatBlocksId[i], this.flatBlocksDamage[i]);
					}
					int[] calcRet = this.calcGen(chunkX * 16 + x, chunkZ * 16 + z);
					chunk.setBlock(x, this.flatBlocksId.length, z, calcRet[0], calcRet[1]);
				}
			}
		}
		this.level.setChunk(chunkX, chunkZ, chunk);
	}

	private int[] calcGen(int worldX, int worldZ){
		int[] landBlock = new int[]{this.landBlockId, this.landBlockDamage};
		int[] roadBlock = new int[]{this.roadBlockId, this.roadBlockDamage};
		int[] landBorder = new int[]{this.landBorderBlockId, this.landBorderBlockDamage};

		if(worldX == 0 || worldZ == 0){
			return landBorder;
		}
		int gridlandx = worldX % (this.landWidth + this.roadWidth);
		int gridlandz = worldZ % (this.landDepth + this.roadDepth);

		/*
		 * +00000+@@@@@@@@@@@@@@@@@@@@...
		 * +00000+@@@@@@@@@@@@@@@@@@@@...
		 * +00000+@@@@@@@@@@@@@@@@@@@@...
		 * +00000+@@@@@@@@@@@@@@@@@@@@...
		 * +00000+@@@@@@@@@@@@@@@@@@@@...
		 * +00000+++++++++++++++++++++...
		 * 000000000000000000000000000...
		 * 000000000000000000000000000...
		 * 000000000000000000000000000...
		 * 000000000000000000000000000...
		 * 000000000000000000000000000...
		 * +00000+++++++++++++++++++++...
		 */
		
		//center grass part
		if(gridlandx >= (this.roadWidth + 2) && gridlandz >= (this.roadDepth + 2)){
			return landBlock;
		}

		//center grass edge (block code 43) part
		if(gridlandx >= (this.roadWidth + 1) && gridlandz >= (this.roadDepth + 1)){
			return landBorder;
		}

		//road (block code 1:6) part
		if(gridlandx >= 1 && gridlandz >= 1){
			return roadBlock;
		}

		if(gridlandx == 0 && gridlandz >= (this.roadDepth + 1)){
			return landBorder;
		}
		if(gridlandz == 0 && gridlandx >= (this.roadWidth + 1)){
			return landBorder;
		}
		if(gridlandx == 0 && gridlandz == 0){
			return landBorder;
		}
		return roadBlock;	
	}

	@Override
	public void populateChunk(int chunkX, int chunkZ){
	}

	@Override
	public Vector3 getSpawn() {
		return new Vector3(128, this.floorLevel, 128);
	}
}
