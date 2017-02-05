package solo.sololand.world;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector2;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.io.File;
import java.util.ArrayList;

import solo.solobasepackage.util.Debug;
import solo.sololand.Main;
import solo.sololand.command.defaults.custom.CustomWorldCommand;
import solo.sololand.land.Land;
import solo.sololand.land.Room;

public class World{

	//class
	public static final LinkedHashMap<String, World> worlds = new LinkedHashMap<String, World>();

	//data
	public final File dataPath;
	public final File landPath;
	public final Config config;
	
	//instance
	public final Level level;
	public final String name;
	
	public LinkedHashMap<String, Object> properties;

	//lands
	public HashMap<Integer, Land> lands = new HashMap<Integer, Land>();
	
	//command
	public CustomWorldCommand command;
	
	//temporary
	public HashMap<String, HashSet<Integer>> recentLands = new HashMap<String, HashSet<Integer>>();
	public int lastRemember = 1;

	@SuppressWarnings("deprecation")
	public World (Level level){
		this.name = level.getFolderName();
		this.level = level;
		
		//set data path
		this.dataPath = new File(Server.getInstance().getDataPath() + File.separator + "worlds" + File.separator + level.getFolderName());
		this.landPath = new File(this.dataPath + File.separator + "land");
		
		//mkdirs
		if(! this.dataPath.isDirectory()){
			this.dataPath.mkdirs();
		}
		if(! this.landPath.isDirectory()){
			this.landPath.mkdirs();
		}
		
		//load properties
		this.config = new Config(new File(this.dataPath, "properties.yml"), Config.YAML, this.getDefaultProperties());
		this.properties = (LinkedHashMap<String, Object>) this.config.getAll();
		
		//load lands
		Debug.normal(Main.getInstance(), this.getLevel().getFolderName() + " 월드 땅 데이터 로드중...");
		int count = 0;
		for(File file : this.landPath.listFiles()){
			try{
				if(file.getName().endsWith(".yml")){
					LinkedHashMap<String, Object> landData = (LinkedHashMap<String, Object>) (new Config(file, Config.YAML)).getAll();
					Land land = new Land(this, landData);
					this.setLand(land);
				}
			}catch(Exception e){
				Debug.critical(Main.getInstance(), "땅 데이터를 로드하던 중 에러가 발생하였습니다. : " + file.getName());
				file.renameTo(new File(file + ".error"));
				continue;
			}
			if(++count % 1000 == 0){
				Debug.normal(Main.getInstance(), this.getLevel().getFolderName() + " 월드 땅 데이터 로드중... " + Integer.toString(count) + "개");
			}
		}
		Debug.normal(Main.getInstance(), this.getLevel().getFolderName() + " 월드 땅 데이터 로드 완료. (로드된 땅 갯수 : " + Integer.toString(count) + "개)");

		//register world command
		this.command = new CustomWorldCommand(this);
		if(this.isEnableWorldCommand()){
			Main.getInstance().getCommandMap().register(this.command);
		}
	}

	/*	#class method	*/
	public static World load(Level level){
		String folderName = level.getFolderName();
		if(! World.worlds.containsKey(folderName)){
			World world;
			switch(level.getProvider().getGenerator().toLowerCase()){
				case "skyblock":
				case "island":
					world = new Island(level);
					break;
					
				case "gridland":
					world = new GridLand(level);
					break;
					
				default:
					world = new World(level);
			}
			World.registerWorld(world);
			return world;
		}
		return null;
	}
	
	public static void registerWorld(World world){
		if(World.worlds.containsKey(world.name)){
			return;
		}
		World.worlds.put(world.getName(), world);
		Debug.normal(Main.getInstance(), world.getName() + " 월드 등록 완료");
	}

	//Do not use this method !!
	//public static void unregisterWorld(World world){
	//	if(! World.worlds.containsKey(world.name)){
	//		return;
	//	}
	//	world.save(true);
	//	World.worlds.remove(world.getName(), world);
	//	Debug.normal(Main.getInstance(), world.getName() + " 월드 등록 해제 완료");
	//}

	public static World get(Position pos){
		return World.get(pos.getLevel());
	}
	
	public static World get(Level level){
		return World.get(level.getFolderName());
	}
	public static World get(String levelFolderName){
		return World.worlds.get(levelFolderName);
	}

	public static World getByName(String customname){
		for(World world : World.worlds.values()){
			if(world.getCustomName().equals(customname)){
				return world;
			}
		}
		return null;
	}

	public static LinkedHashMap<String, World> getAll() {
		return World.worlds;
	}
	
	
	
	
	

	public Level getLevel() {
		return this.level;
	}

	public String getName() {
		return this.name;
	}

	public int getType(){
		return (int) this.properties.get("type");
	}
	
	@SuppressWarnings("serial")
	public LinkedHashMap<String, Object> getDefaultProperties(){
		return new LinkedHashMap<String, Object>(){{
			//put("customname", "default");
			
			//world setting
			put("enableworldcommand", false);
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
			put("allowresizeland", true);
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

	
	
	//world setting part
	public boolean isEnableWorldCommand(){
		return (boolean) this.properties.get("enableworldcommand");
	}
	public void setEnableWorldCommand(boolean bool){
		this.properties.put("enableworldcommand", bool);
		if(bool){
			Main.getInstance().getCommandMap().register(this.command);
		}else{
			Main.getInstance().getCommandMap().unregister(this.command);
		}
	}
	
	public String getCustomName(){
		//return (String) this.properties.get("customname");
		return this.level.getFolderName();
	}
	public void setCustomName(String name){
		this.properties.put("customname", name);
	}

	public boolean isProtected() {
		return (boolean) this.properties.get("protect");
	}
	public void setProtected(boolean bool) {
		this.properties.put("protect", bool);
	}

	public boolean isInvenSave() {
		return (boolean) this.properties.get("invensave");
	}
	public void setInvenSave(boolean bool) {
		this.properties.put("invensave", bool);
	}

	public boolean isAllowExplosion() {
		return (boolean) this.properties.get("allowexplosion");
	}
	public void setAllowExplosion(boolean bool) {
		this.properties.put("allowexplosion", bool);
	}

	public boolean isAllowBurn() {
		return (boolean) this.properties.get("allowburn");
	}
	public void setAllowBurn(boolean bool) {
		this.properties.put("allowburn", bool);
	}
 
	public boolean isAllowFight() {
		return (boolean) this.properties.get("allowfight");
	}
	public void setAllowFight(boolean bool) {
		this.properties.put("allowfight", bool);
	}
	
	public boolean isAllowDoor(){
		return (boolean) this.properties.get("allowdoor");
	}
	public void setAllowDoor(boolean bool){
		this.properties.put("allowdoor", bool);
	}
	
	public boolean isAllowChest(){
		return (boolean) this.properties.get("allowdoor");
	}
	public void setAllowChest(boolean bool){
		this.properties.put("allowchest", bool);
	}
	
	public boolean isAllowCraft(){
		return (boolean) this.properties.get("allowcraft");
	}
	public void setAllowCraft(boolean bool){
		this.properties.put("allowcraft", bool);
	}

	
	
	//land setting part
	public double getDefaultLandPrice() {
		return (double) this.properties.get("defaultlandprice");
	}
	public void setDefaultLandPrice(double price) {
		this.properties.put("defaultlandprice", price);
	}

	public double getPricePerBlock() {
		return (double) this.properties.get("priceperblock");
	}
	public void setPricePerBlock(double price) {
		this.properties.put("priceperblock", price);
	}

	public boolean isAllowCreateLand() {
		return (boolean) this.properties.get("allowcreateland");
	}
	public void setAllowCreateLand(boolean bool){
		this.properties.put("allowcreateland", bool);
	}

	public boolean isAllowCombineLand() {
		return (boolean) this.properties.get("allowcombineland");
	}
	public void setAllowCombineLand(boolean bool){
		this.properties.put("allowcombineland", bool);
	}

	public boolean isAllowResizeLand() {
		return (boolean) this.properties.get("allowresizeland");
	}
	public void setAllowResizeLand(boolean bool){
		this.properties.put("allowresizeland", bool);
	}

	public int getMaxLandCount() {
		return (int) this.properties.get("maxlandcount");
	}
	public void setMaxLandCount(int count){
		this.properties.put("maxlandcount", count);
	}

	public int getMinLandLength() {
		return (int) this.properties.get("minlandlength");
	}
	public void setMinLandLength(int count){
		this.properties.put("minlandlength", count);
	}

	public int getMaxLandLength() {
		return (int) this.properties.get("maxlandlength");
	}
	public void setMaxLandLength(int count){
		this.properties.put("maxlandlength", count);
	}
	

	
	//room setting
	public boolean isAllowCreateRoom(){
		return (boolean) this.properties.get("allowcreateroom");
	}
	public void setAllowCreateRoom(boolean bool){
		this.properties.put("allowcreateroom", bool);
	}
	
	public double getRoomCreatePrice(){
		return (double) this.properties.get("roomcreateprice");
	}
	public void setRoomCreatePrice(double money){
		this.properties.put("roomcreateprice", money);
	}
	
	public int getMaxRoomCreateCount(){
		return (int) this.properties.get("maxroomcreatecount");
	}
	public void setMaxRoomCreateCount(int count){
		this.properties.put("maxroomcreatecount", count);
	}
	
	public int getMinRoomLength(){
		return (int) this.properties.get("minroomlength");
	}
	public void setMinRoomLength(int length){
		this.properties.put("minroomlength", length);
	}
	
	public int getMaxRoomLength(){
		return (int) this.properties.get("maxroomlength");
	}
	public void setMaxRoomLength(int length){
		this.properties.put("maxroomlength", length);
	}
	
	

	public int getNextLandNumber(){
		if(! this.lands.containsKey(this.lastRemember)){
			return this.lastRemember;
		}else if(! this.lands.containsKey(++this.lastRemember)){
			return this.lastRemember;
		}
		int num = 1;
		while(this.lands.containsKey(num)){
			num++;
		}
		this.lastRemember = num;
		return num;
	}
	
	public void setLand(Land land){
		this.lands.put(land.getNumber(), land);
	}
	
	public void setLand(int number, Land land){
		land.number = number;
		this.lands.put(number, land);
	}

	public Land createLand(Vector2 start, Vector2 end){
		return this.createLand(start.getFloorX(), start.getFloorY(), end.getFloorX(), end.getFloorY());
	}
	
	public Land createLand(Vector2 start, Vector2 end, String owner){
		return this.createLand(start.getFloorX(), start.getFloorY(), end.getFloorX(), end.getFloorY(), owner);
	}
	
	public Land createLand(Vector3 start, Vector3 end){
		return this.createLand(start.getFloorX(), start.getFloorZ(), end.getFloorX(), end.getFloorZ());
	}
	
	public Land createLand(Vector3 start, Vector3 end, String owner){
		return this.createLand(start.getFloorX(), start.getFloorZ(), end.getFloorX(), end.getFloorZ(), owner);
	}

	public Land createLand(int startX, int startZ, int endX, int endZ){
		return this.createLand(startX, startZ, endX, endZ, "");
	}
	
	public Land createLand(int startX, int startZ, int endX, int endZ, String owner){
		return this.createLand(startX, startZ, endX, endZ, owner, this.getDefaultLandPrice());
	}

	public Land createLand(int startX, int startZ, int endX, int endZ, String owner, double price){
		owner = owner.toLowerCase();
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("landnumber", this.getNextLandNumber());
		data.put("owner", owner);
		data.put("startx", startX);
		data.put("startz", startZ);
		data.put("endx", endX);
		data.put("endz", endZ);
		data.put("issail", owner.equals(""));
		data.put("price", price);

		return new Land(this, data);
	}

	public boolean isExistLand(Land land){
		return(
			land.getLevel().getFolderName().equals(this.getLevel().getFolderName()) &&
			this.isExistLand(land.getNumber())
		);
	}
	
	public boolean isExistLand(int num){
		return this.lands.containsKey(num);
	}

	public void setLands(HashMap<Integer, Land> lands){
		this.lands = lands;
	}

	public HashMap<Integer, Land> getLands(){
		return this.lands;
	}
	
	public HashMap<Integer, Land> getLands(Player player){
		return this.getLands(player.getName());
	}
	public HashMap<Integer, Land> getLands(String name){
		name = name.toLowerCase();
		HashMap<Integer, Land> ret = new HashMap<Integer, Land>();
		for(Land land : this.lands.values()){
			if(land.isOwner(name)){
				ret.put(land.getNumber(), land);
			}
		}
		return ret;
	}
	
	public HashMap<Integer, Land> getSharedLands(Player player){
		return this.getSharedLands(player.getName());
	}
	public HashMap<Integer, Land> getSharedLands(String name){
		name = name.toLowerCase();
		HashMap<Integer, Land> ret = new HashMap<Integer, Land>();
		for(Land land : this.lands.values()){
			if(land.isMember(name) && !land.isOwner(name)){
				ret.put(land.getNumber(), land);
			}
		}
		return ret;
	}

	public Land getLand(int num){
		if(this.lands.containsKey(num)){
			return this.lands.get(num);
		}
		return null;
	}
	
	public final Land getLand(Vector3 vec){
		return this.getLand(vec.getFloorX(), vec.getFloorZ());
	}
	
	public final Land getLand(Vector2 vec){
		return this.getLand(vec.getFloorX(), vec.getFloorY());
	}
	
	public Land getLand(int x, int z){
		Land land = this.getLandByRecent(x, z);
		if(land != null){
			return land;
		}
		for(Land check : this.lands.values()){
			if(check.isInside(x, z)){
				String chunkHash = Integer.toString(x >> 4) + ":" + Integer.toString(z >> 4);
				if(! this.recentLands.containsKey(chunkHash)){
					this.recentLands.put(chunkHash, new HashSet<Integer>());
				}
				this.recentLands.get(chunkHash).add(check.getNumber());
				return check;
			}
		}
		return null;
	}
	
	protected final Land getLandByRecent(int x, int z){
		Land land;
		String chunkHash = Integer.toString(x >> 4) + ":" + Integer.toString(z >> 4);
		if(this.recentLands.containsKey(chunkHash)){
			for(int num : this.recentLands.get(chunkHash)){
				land = this.getLand(num);
				if(land != null && land.isInside(x, z)){
					return land;
				}
			}
		}
		return null;
	}

	public boolean removeLand(int num) {
		if(!this.lands.containsKey(num)){
			return false;
		}
		Land land = this.lands.get(num);
		File landFile = new File(this.landPath, Integer.toString(land.getNumber()) + ".yml");
		if(landFile.isFile()){
			landFile.delete();
		}

		this.lands.remove(num);
		return true;
	}

	public boolean removeLand(Land land){
		return this.removeLand(land.getNumber());
	}

	public ArrayList<Land> getOverlap(Land check){
		ArrayList<Land> ret = new ArrayList<Land>();
		if(check.getLevel().getFolderName().equals(this.getLevel().getFolderName())){
			for(Land land : this.lands.values()){
				if(land.isOverlap(check)){
					if(land.getNumber() == check.getNumber()){
						continue;
					}
					ret.add(land);
				}
			}
		}
		return ret;
	}
	
	
	public void save(){
		this.save(true);
	}
	
	public void save(boolean all){
		//save properties
		this.config.setAll(this.properties);
		this.config.save();
		
		//save lands
		Debug.normal(Main.getInstance(), this.getName() + " 월드 땅 저장중...");
		HashSet<Integer> recents = new HashSet<Integer>();
		for(HashSet<Integer> set : this.recentLands.values()){
			recents.addAll(set);
		}
		int count = 0;
		for(Land land : this.lands.values()){
			if(!all && !recents.contains(land.getNumber())){
				continue;
			}
			LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();
			data.put("landnumber", land.getNumber());
			data.put("owner", land.owner);
			data.put("members", land.members);
			
			data.put("startx", land.start.getFloorX());
			data.put("endx", land.end.getFloorX());
			data.put("startz", land.start.getFloorY());
			data.put("endz", land.end.getFloorY());
			
			data.put("issail", land.sail);
			data.put("price", land.price);
			
			data.put("spawnpoint", Double.toString(land.spawnPoint.x) + ":" + Double.toString(land.spawnPoint.y) + ":" + Double.toString(land.spawnPoint.z));
			
			data.put("isallowfight", land.allowFight);
			data.put("isallowaccess", land.allowAccess);
			data.put("isallowpickupitem", land.allowPickUpItem);
			
			data.put("isallowdoor", land.allowDoor);
			data.put("isallowchest", land.allowChest);
			data.put("isallowcraft", land.allowCraft);
			
			data.put("welcomemessage", land.welcomeMessage);
			data.put("welcomeparticle", land.welcomeParticle);
			LinkedHashMap<String, Object> roomsData = new LinkedHashMap<String, Object>();
			for(Room room : land.getRooms().values()){
				LinkedHashMap<String, Object> roomData = new LinkedHashMap<String, Object>();
				roomData.put("roomnumber", room.getNumber());
				roomData.put("owner", room.getOwner());
				roomData.put("members", room.getMembers());
				
				roomData.put("startx", room.start.getFloorX());
				roomData.put("endx", room.end.getFloorX());
				roomData.put("starty", room.start.getFloorY());
				roomData.put("endy", room.end.getFloorY());
				roomData.put("startz", room.start.getFloorZ());
				roomData.put("endz", room.end.getFloorZ());
				
				roomData.put("issail", room.isSail());
				roomData.put("price", room.getPrice());
				
				roomData.put("spawnpoint", Double.toString(room.spawnPoint.x) + ":" + Double.toString(room.spawnPoint.y) + ":" + Double.toString(room.spawnPoint.z));
				
				roomData.put("welcomemessage", room.getWelcomeMessage());
				
				roomsData.put(Integer.toString(room.getNumber()), roomData);
			}
			data.put("rooms", roomsData);
			
			Config conf = new Config(new File(this.landPath, Integer.toString(land.getNumber()) + ".yml"), Config.YAML);
			conf.setAll(data);
			conf.save();
			++count;
		}
		this.recentLands.clear();
		Debug.normal(Main.getInstance(), this.getName() + " 월드 땅 저장완료 (저장된 땅 갯수 : " + Integer.toString(count) + "개)");
	}

}


