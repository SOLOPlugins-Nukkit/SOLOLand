package solo.sololand.world;

import cn.nukkit.Player;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import solo.sololand.land.Land;
import java.util.LinkedHashMap;

public class Island extends World{

	public Island(Level level){
		super(level);
		this.lastRemember = (int) this.properties.get("nextlandnumber");
		for(Land land : this.lands.values()){
			if(this.lastRemember < land.getNumber()){
				this.lastRemember = land.getNumber();
			}
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
			put("allowcombineland", false);
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
	public int getNextLandNumber(){
		this.properties.put("nextlandnumber", ++this.lastRemember);
		return this.lastRemember;
	}
	
	public Land createLand(Player player){
		return this.createLand(player.getName());
	}

	public Land createLand(String owner){
		owner = owner.toLowerCase();
		int num = this.getNextLandNumber();
		
		int startX = (num % 200) * 200;
		int startZ = (num / 200) * 200;
		int endX = startX + 199;
		int endZ = startZ + 199;
		
		LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("landnumber", this.getNextLandNumber());
		data.put("startx", startX);
		data.put("startz", startZ);
		data.put("endx", endX);
		data.put("endz", endZ);
		data.put("owner", owner);
		
		int spawnX = ((int) ((startX + endX) / 2) / 16) * 16 + 8;
		int spawnZ = ((int) ((startZ + endZ) / 2) / 16) * 16 + 8;

		Land land = new Land(this, data);
		land.setSpawnPoint(new Vector3(spawnX, 12.5, spawnZ));
		this.setLand(land);
		return land;
	}

	protected Land getLandByAssume(int x, int z){
		Land land;
		int assumeNum = this.getLandNumberByXZ(x, z);
		if(assumeNum > 0){
			land = this.getLand(assumeNum);
			if(land != null){
				if(land.isInside(x, z)){
					return land;
				}
			}
		}
		return null;
	}

	@Override
	public Land getLand(int x, int z){
		Land land;
		land = this.getLandByAssume(x, z);
		if(land != null){
			return land;
		}
		return super.getLand(x, z);
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