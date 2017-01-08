package solo.sololand.land;

import cn.nukkit.Player;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector2;
import cn.nukkit.math.Vector3;
import solo.sololand.world.World;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class Land{

	public int number;
	public World world;

	public String owner;
	public Vector2 start;
	public Vector2 end;

	public List<String> members = new ArrayList<String>();
	public boolean isSail;
	public double price;

	public Vector3 spawnPoint;
	public boolean isAllowFight;
	public boolean isAllowAccess;
	public boolean isAllowPickUpItem;

	public String welcomeMessage;
	public int welcomeParticle;
	
	public LinkedHashMap<Integer, Room> rooms = new LinkedHashMap<Integer, Room>();

	private Land(){
		
	}
	
	public Land(World world){
		this(world, new HashMap<String, Object>());
	}
	
	public Land(World world, Map<String, Object> data){
		this.world = world;
		this.number = (int) data.get("landnumber");
		this.owner = ((String) data.get("owner")).toLowerCase();
		
		int x1 = (int) data.get("startx");
		int x2 = (int) data.get("endx");
		int z1 = (int) data.get("startz");
		int z2 = (int) data.get("endz");
			
		this.start = new Vector2(Math.min(x1, x2), Math.min(z1, z2));
		this.end = new Vector2(Math.max(x1, x2), Math.max(z1, z2));
		
		this.members = new ArrayList<String>();
		if(! this.owner.equals("")){
			this.members.add(this.owner);
		}
		
		this.isSail = this.owner.equals("");
		this.price = this.world.getDefaultLandPrice();
		
		this.isAllowFight = false;
		this.isAllowAccess = true;
		this.isAllowPickUpItem = false;
		
		this.welcomeMessage = "";
		this.welcomeParticle = 0;
		
		this.resetSpawnPoint();
		
		data.remove("landnumber");
		data.remove("owner");
		data.remove("startx");
		data.remove("endx");
		data.remove("startz");
		data.remove("endz");
		
		this.apply(data);
	}
	
	@SuppressWarnings("unchecked")
	public void apply(Map<String, Object> data){
		try{
			if(data.containsKey("landnumber")){
				this.number = (int) data.get("landnumber");
			}
			if(data.containsKey("owner")){
				this.owner = ((String) data.get("owner")).toLowerCase();
			}
			if(data.containsKey("startx") && data.containsKey("endx") && data.containsKey("startz") && data.containsKey("endz")){
				int x1 = (int) data.get("startx");
				int x2 = (int) data.get("endx");
				int z1 = (int) data.get("startz");
				int z2 = (int) data.get("endz");
					
				this.start = new Vector2(Math.min(x1, x2), Math.min(z1, z2));
				this.end = new Vector2(Math.max(x1, x2), Math.max(z1, z2));
			}
			
			if(data.containsKey("issail")){
				this.isSail = (boolean) data.get("issail");
			}
			if(data.containsKey("price")){
				this.price = (double) data.get("price");
			}
			if(data.containsKey("members")){
				this.members = (ArrayList<String>) data.get("members");
			}
			if(data.containsKey("spawnpoint")){
				String[] spawnCoord = ((String) data.get("spawnpoint")).split(":");
				this.spawnPoint = new Vector3(
					Double.parseDouble(spawnCoord[0]),
					Double.parseDouble(spawnCoord[1]),
					Double.parseDouble(spawnCoord[2])
				);
			}
			if(data.containsKey("isallowfight")){
				this.isAllowFight = (boolean) data.get("isallowfight");
			}
			if(data.containsKey("isallowaccess")){
				this.isAllowAccess = (boolean) data.get("isallowaccess");
			}
			if(data.containsKey("isallowpickupitem")){
				this.isAllowPickUpItem = (boolean) data.get("isallowpickupitem");
			}
			if(data.containsKey("welcomemessage")){
				this.welcomeMessage = (String) data.get("welcomemessage");
			}
			if(data.containsKey("welcomeparticle")){
				this.welcomeParticle = (int) data.get("welcomeparticle");
			}
			if(data.containsKey("rooms")){
				LinkedHashMap<String, Object> roomsData = (LinkedHashMap<String, Object>) data.get("rooms");
				for(Object obj : roomsData.values()){
					this.setRoom(new Room(this, (LinkedHashMap<String, Object>) obj));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public int getNumber(){
		return this.number;
	}

	public Level getLevel() {
		return this.world.getLevel();
	}
	
	public World getWorld(){
		return this.world;
	}
	
	public boolean isInside(Position pos){
		if(pos.getLevel().getFolderName().equals(this.world.getLevel().getFolderName())){
			return this.isInside(new Vector3(pos.x, pos.y, pos.z));
		}
		return false;
	}
	
	public boolean isInside(Vector3 vec){
		return this.isInside(vec.getFloorX(), vec.getFloorZ());
	}
	
	public boolean isInside(Vector2 vec){
		return this.isInside(vec.getFloorX(), vec.getFloorY());
	}
	
	public boolean isInside(int x, int z){
		return (
			this.start.x <= x &&
			this.start.y <= z &&
			this.end.x >= x &&
			this.end.y >= z
		);
	}
	
	public boolean isOverlap(Land land){
		return !(
			land.start.x > this.end.x ||
			land.start.y > this.end.y ||
			land.end.x < this.start.x ||
			land.end.y < this.start.y
		);
	}

	public boolean isSail() {
		return this.isSail;
	}
	
	public void setSail(boolean bool){
		this.isSail = bool;
	}

	public double getPrice() {
		return this.price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}

	public String getOwner(){
		return this.owner;
	}
	
	public void setOwner(Player player){
		this.setOwner(player.getName());
	}
	
	public void setOwner(String owner){
		owner = owner.toLowerCase();
		if(! this.owner.equals(owner)){
			this.members.remove(this.owner);
			this.members.add(owner);
		}
		this.owner = owner;
		if(! this.owner.equals("") && ! this.isMember(this.owner)){
			this.addMember(this.owner);
		}
	}
	
	public boolean isOwner(Player player){
		return this.isOwner(player.getName());
	}
	
	public boolean isOwner(String name){
		return (this.owner.equals(name.toLowerCase()));
	}

	public List<String> getMembers(){
		return this.members;
	}
	
	public void setMembers(List<String> members){
		if(! this.owner.equals("") && ! members.contains(this.owner)){
			members.add(this.owner);
		}
		this.members = members;
	}
	
	public void setMembers(Set<String> members){
		List<String> newMembers = new ArrayList<String>();
		for(String member : members){
			newMembers.add(member);
		}
		this.members = newMembers;
	}
	
	public boolean isMember(Player player){
		return this.isMember(player.getName());
	}
	
	public boolean isMember(String name){
		name = name.toLowerCase();
		return (this.isOwner(name) || this.members.contains(name));
	}
	
	public boolean addMember(Player player){
		return this.addMember(player.getName());
	}
	
	public boolean addMember(String name){
		name = name.toLowerCase();
		if(this.isMember(name)){
			return false;
		}
		this.members.add(name);
		return true;
	}
	
	public boolean removeMember(Player player){
		return this.removeMember(player.getName());
	}
	
	public boolean removeMember(String name){
		name = name.toLowerCase();
		if(this.isMember(name)){
			this.members.remove(name.toLowerCase());
			return true;
		}
		return false;
	}

	public Vector3 getSpawnPoint(){
		return this.spawnPoint;
	}
	
	public void setSpawnPoint(Vector3 vec){
		this.spawnPoint = new Vector3(vec.x, vec.y, vec.z);
	}
	
	public void setSpawnPoint(double x, double y, double z){
		this.spawnPoint = new Vector3(x, y, z);
	}
	
	public void setSpawnPoint(int x, int y, int z){
		this.spawnPoint = new Vector3(x, y, z);
	}

	public void setAllowFight(boolean bool){
		this.isAllowFight = bool;
	}
	
	public boolean isAllowFight(){
		return this.isAllowFight;
	}

	public void setAllowAccess(boolean bool){
		this.isAllowAccess = bool;
	}
	
	public boolean isAllowAccess(){
		return this.isAllowAccess;
	}

	public void setAllowPickUpItem(boolean bool){
		this.isAllowPickUpItem = bool;
	}
	
	public boolean isAllowPickUpItem(){
		return this.isAllowPickUpItem;
	}

	public void setWelcomeMessage(String message){
		this.welcomeMessage = message;
	}
	
	public String getWelcomeMessage(){
		return this.welcomeMessage;
	}

	public void setWelcomeParticle(int particle){
		this.welcomeParticle = particle;
	}
	
	public int getWelcomeParticle(){
		return this.welcomeParticle;
	}
	
	public void expand(Vector3 vec){
		this.expand(vec.getFloorX(), vec.getFloorZ());
	}
	
	public void expand(Vector2 vec){
		this.expand(vec.getFloorX(), vec.getFloorY());
	}
	
	public void expand(int x, int z){
		this.start = new Vector2(Math.min(this.start.x, x), Math.min(this.start.y, z));
		this.end = new Vector2(Math.max(this.end.x, x), Math.max(this.end.y, z));
	}
	
	public void expand(Land land){
		this.start = new Vector2(Math.min(this.start.x, land.start.x), Math.min(this.start.y, land.start.y));
		this.end = new Vector2(Math.min(this.end.x, land.start.x), Math.min(this.end.y, land.end.y));
	}
	
	public void reduce(Vector3 vec){
		this.reduce(vec.getFloorX(), vec.getFloorZ());
	}
	
	public void reduce(Vector2 vec){
		this.reduce(vec.getFloorX(), vec.getFloorY());
	}
	
	public void reduce(int x, int z){
		if(! this.isInside(x, z)){
			return;
		}
		int newStartX = (int) this.start.x;
		int newStartZ = (int) this.start.y;
		int newEndX = (int) this.end.x;
		int newEndZ = (int) this.end.y;
		
		if(Math.abs(this.start.x - x) > Math.abs(this.end.x - x)){
			newEndX = x;
		}else{
			newStartX = x;
		}
		if(Math.abs(this.start.y - z) > Math.abs(this.end.y - z)){
			newEndZ = z;
		}else{
			newStartZ = z;
		}
		this.start = new Vector2(newStartX, newStartZ);
		this.end = new Vector2(newEndX, newEndZ);
	}
	
	public int xLength(){
		return (int) Math.abs(this.start.x - this.end.x) + 1;
	}
	
	public int zLength(){
		return (int) Math.abs(this.start.y - this.end.y) + 1;
	}
	
	public int size(){
		return this.xLength() * this.zLength();
	}

	
	
	
	//Room part
	public int getNextRoomNumber(){
		int c = 0;
		while(this.rooms.containsKey(++c)){
		}
		return c;
	}
	public Room createRoom(Vector3 start, Vector3 end){
		return this.createRoom(start.getFloorX(), start.getFloorY(), start.getFloorZ(), end.getFloorX(), end.getFloorY(), end.getFloorZ());
	}
	public Room createRoom(int startX, int startY, int startZ, int endX, int endY, int endZ){
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("roomnumber", this.getNextRoomNumber());
		data.put("owner", "");
		data.put("startx", startX);
		data.put("starty", startY);
		data.put("startz", startZ);
		data.put("endx", endX);
		data.put("endy", endY);
		data.put("endz", endZ);
		data.put("issail", false);
		data.put("price", price);
		Room room = new Room(this, data);
		return room;
	}
	
	public void setRoom(Room room){
		this.rooms.put(room.getNumber(), room);
	}
	
	public void setRoom(int num, Room room){
		this.rooms.put(num, room);
		room.number = num;
	}
	
	public boolean hasRoom(){
		return this.rooms.size() != 0;
	}
	
	public boolean isExistRoom(Room room){
		return this.isExistRoom(room.getNumber());
	}
	public boolean isExistRoom(int roomNumber){
		return this.rooms.containsKey(roomNumber);
	}
	
	public Room getRoom(int num){
		if(this.rooms.containsKey(num)){
			return this.rooms.get(num);
		}
		return null;
	}
	
	public Room getRoom(Vector3 vec){
		return this.getRoom(vec.getFloorX(), vec.getFloorY(), vec.getFloorZ());
	}
	public Room getRoom(int x, int y, int z){
		for(Room room : this.rooms.values()){
			if(room.isInside(x, y, z)){
				return room;
			}
		}
		return null;
	}
	
	public LinkedHashMap<Integer, Room> getRooms(){
		return this.rooms;
	}
	
	public LinkedHashMap<Integer, Room> getRooms(Player player){
		return this.getRooms(player.getName());
	}
	public LinkedHashMap<Integer, Room> getRooms(String name){
		name = name.toLowerCase();
		LinkedHashMap<Integer, Room> ret = new LinkedHashMap<Integer, Room>();
		for(Room room : this.rooms.values()){
			if(room.isOwner(name)){
				ret.put(room.getNumber(), room);
			}
		}
		return ret;
	}
	
	public LinkedHashMap<Integer, Room> getSharedRooms(Player player){
		return this.getSharedRooms(player.getName());
	}
	public LinkedHashMap<Integer, Room> getSharedRooms(String name){
		name = name.toLowerCase();
		LinkedHashMap<Integer, Room> ret = new LinkedHashMap<Integer, Room>();
		for(Room room : this.rooms.values()){
			if(room.isMember(name) && ! room.isOwner(name)){
				ret.put(room.getNumber(), room);
			}
		}
		return ret;
	}
	
	public ArrayList<Room> getOverlap(Room check){
		ArrayList<Room> ret = new ArrayList<Room>();
		if(check.getLevel().getFolderName().equals(this.getLevel().getFolderName())){
			for(Room room : this.rooms.values()){
				if(room.isOverlap(check)){
					if(room.getNumber() == check.getNumber()){
						continue;
					}
					ret.add(room);
				}
			}
		}
		return ret;
	}
	
	public boolean removeRoom(Room room){
		return this.removeRoom(room.getNumber());
	}
	
	public boolean removeRoom(int num){
		if(this.rooms.containsKey(num)){
			this.rooms.remove(num);
			return true;
		}
		return false;
	}
	
	public void clearRooms(){
		this.rooms.clear();
	}
	
	public void resetSpawnPoint(){
		int centerX = (int) ((this.start.x + this.end.x) / 2);
		int centerZ = (int) ((this.start.y + this.end.y) / 2);
		this.spawnPoint = new Vector3(
			centerX,
			this.world.getLevel().getHighestBlockAt(centerX, centerZ) + 1,
			centerZ
		);
	}
	
	public void clear(){
		this.clear(true);
	}
	
	public void clear(boolean all){
		this.members.clear();
		this.isSail = false;
		this.price = world.getDefaultLandPrice();
		this.welcomeMessage = "";
		this.welcomeParticle = 0;
		this.resetSpawnPoint();
		if(all){
			this.owner = "";
		}else{
			this.members.add(this.owner);
		}
		this.clearRooms();
	}
	
	public Land clone(){
		Land land = new Land();
		land.number = this.number;
		land.world = this.world;

		land.start = this.start;
		land.end = this.end;
		land.isSail = this.isSail;
		land.price = this.price;
		land.owner = this.owner;
		land.members = this.members;

		land.spawnPoint = this.spawnPoint;
		land.isAllowFight = this.isAllowFight;
		land.isAllowAccess = this.isAllowAccess;
		land.isAllowPickUpItem = this.isAllowPickUpItem;

		land.welcomeMessage = this.welcomeMessage;
		land.welcomeParticle = this.welcomeParticle;
		return land;
	}
}