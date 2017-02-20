package solo.sololand.world;

import cn.nukkit.level.Level;
import solo.sololand.generator.GridLandGenerator;
import solo.sololand.land.Land;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

public class GridLand extends World{
	
	private int landWidth;
	private int landDepth;
	private int roadWidth;
	private int roadDepth;
	
	public GridLand(Level level){
		super(level);
		
		try{
			Field f = level.getClass().getDeclaredField("generatorInstance");
			f.setAccessible(true);
			GridLandGenerator generator = (GridLandGenerator) f.get(level);
			
			this.landWidth = generator.getLandWidth();
			this.landDepth = generator.getLandDepth();
			this.roadWidth = generator.getRoadWidth();
			this.roadDepth = generator.getRoadDepth();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("serial")
	@Override
	public LinkedHashMap<String, Object> getDefaultProperties(){
		return new LinkedHashMap<String, Object>(){{
			//world setting
			put("enableworldcommand", true);
			put("protect", true);
			put("invensave", true);
			put("allowexplosion", false);
			put("allowburn", false);
			put("allowfight", false);
			put("allowdoor", true);
			put("allowchest", false);
			put("allowcraft", true);
			
			//land setting
			put("allowcreateland", false);
			put("allowcombineland", true);
			put("allowresizeland", false);
			put("defaultlandprice", 20000.0);
			put("priceperblock", 20.0);
			put("maxlandcount", 4);
			put("minlandlength", 5);
			put("maxlandlength", 100);
			
			//room setting
			put("allowcreateroom", true);
			put("roomcreateprice", 5000.0);
			put("maxroomcreatecount", 50);
			put("minroomlength", 3);
			put("maxroomlength", 50);
		}};
	}

	@Override
	public Land getLand(int x, int z){
		Land land = super.getLand(x, z);
		if(land != null){
			return land;
		}

		/* Auto Land Make Code */
		if(x > 5 && z > 5){
			int startX = (int)( (x - 1) / (this.landWidth + this.roadWidth) ) * (this.landWidth + this.roadWidth) + this.roadWidth + 1;
			int startZ = (int)( (z - 1) / (this.landDepth + this.roadDepth) ) * (this.landDepth + this.roadDepth) + this.roadDepth + 1;
			if(
					x >= startX &&
					z >= startZ &&
					x <= startX + this.landWidth - 1 &&
					z <= startZ + this.landDepth - 1
					){
				land = this.createLand(startX, startZ, startX + this.landWidth - 1, startZ + this.landDepth - 1);
				if(this.getOverlap(land).size() == 0){
					this.setLand(land);
					return land;
				}
			}
		}
		/* End */

		return null;
	}
}