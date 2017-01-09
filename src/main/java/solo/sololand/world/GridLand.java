package solo.sololand.world;

import cn.nukkit.level.Level;
import solo.sololand.land.Land;
import java.util.LinkedHashMap;

public class GridLand extends World{

	public GridLand(Level level){
		super(level);
		this.lastRemember = (int) this.properties.get("nextlandnumber");
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
			
			//only for island or gridland
			put("nextlandnumber", 1);
		}};
	}

	@Override
	public Land getLand(int x, int z){
		Land land = this.getLandByRecent(x, z);
		if(land != null){
			return land;
		}
		for(Land check : this.lands.values()){
			if(check.isInside(x, z)){
				this.recentLands.add(check.getNumber());
				return check;
			}
		}

		/* Auto Land Make Code */
		if(x > 5 && z > 5){
			int gridLandX = x % 37;
			int gridLandZ = z % 37;
			if(
				(gridLandX >= 6 && gridLandZ >= 6) ||
				(gridLandX == 0 && gridLandZ >= 6) ||
				(gridLandZ == 0 && gridLandX >= 6) ||
				(gridLandX == 0 && gridLandZ == 0)
			){
				int startX = (int)(x / 37) * 37 + 6;
				int startZ = (int)(z / 37) * 37 + 6;
				Land createdLand = this.createLand(startX, startZ, startX + 31, startZ + 31);
				this.setLand(createdLand);
				return createdLand;
			}
		}
		/* End */

		return null;
	}
}