package solo.sololand.land;

import cn.nukkit.Player;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import solo.sololand.world.World;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Room{

	public int number;
	public Land land;

	public String owner;
	public Vector3 start;
	public Vector3 end;

	public List<String> members = new ArrayList<String>();
	public boolean isSail;
	public double price;
	
	//public boolean isAllowFight;
	//public boolean isAllowAccess;
	//public boolean isAllowPickUpItem;
	
	public Vector3 spawnPoint;

	public String welcomeMessage;
	//public int welcomeParticle;

	private Room(){
		
	}
	
	public Room(Land land){
		this(land, new HashMap<String, Object>());
	}
	
	public Room(Land land, Map<String, Object> data){
		this.land = land;
		this.number = (int) data.get("roomnumber");
		this.owner = ((String) data.get("owner")).toLowerCase();
		
		int x1 = (int) data.get("startx");
		int x2 = (int) data.get("endx");
		int y1 = (int) data.get("starty");
		int y2 = (int) data.get("endy");
		int z1 = (int) data.get("startz");
		int z2 = (int) data.get("endz");
			
		this.start = new Vector3(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2));
		this.end = new Vector3(Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));
		
		this.members = new ArrayList<String>();
		if(! this.owner.equals("")){
			this.members.add(this.owner);
		}
		
		this.isSail = this.owner.equals("");
		this.price = 0;
		
		//this.isAllowFight = false;
		//this.isAllowAccess = true;
		//this.isAllowPickUpItem = false;
		
		this.welcomeMessage = "";
		//this.welcomeParticle = 0;
		
		this.resetSpawnPoint();
		
		data.remove("roomnumber");
		data.remove("owner");
		data.remove("startx");
		data.remove("endx");
		data.remove("starty");
		data.remove("endy");
		data.remove("startz");
		data.remove("endz");
		
		this.apply(data);
	}
	
	@SuppressWarnings("unchecked")
	public void apply(Map<String, Object> data){
		try{
			if(data.containsKey("roomnumber")){
				this.number = (int) data.get("roomnumber");
			}
			if(data.containsKey("owner")){
				this.owner = ((String) data.get("owner")).toLowerCase();
			}
			if(
				data.containsKey("startx") && data.containsKey("endx") &&
				data.containsKey("starty") && data.containsKey("endy") &&
				data.containsKey("startz") && data.containsKey("endz")
			){
				int x1 = (int) data.get("startx");
				int x2 = (int) data.get("endx");
				int y1 = (int) data.get("starty");
				int y2 = (int) data.get("endy");
				int z1 = (int) data.get("startz");
				int z2 = (int) data.get("endz");
					
				this.start = new Vector3(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2));
				this.end = new Vector3(Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));
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
			//if(data.containsKey("isallowfight")){
			//	this.isAllowFight = (boolean) data.get("isallowfight");
			//}
			//if(data.containsKey("isallowaccess")){
			//	this.isAllowAccess = (boolean) data.get("isallowaccess");
			//}
			//if(data.containsKey("isallowpickupitem")){
			//	this.isAllowPickUpItem = (boolean) data.get("isallowpickupitem");
			//}
			if(data.containsKey("welcomemessage")){
				this.welcomeMessage = (String) data.get("welcomemessage");
			}
			//if(data.containsKey("welcomeparticle")){
			//	this.welcomeParticle = (int) data.get("welcomeparticle");
			//}
		}catch(Exception e){
		}
	}

	public int getNumber(){
		return this.number;
	}
	
	public String getAddress(){
		return Integer.toString(land.getNumber()) + "-" + Integer.toString(this.getNumber());
	}
	
	public Land getLand(){
		return this.land;
	}

	public Level getLevel(){
		return this.land.getWorld().getLevel();
	}
	
	public World getWorld(){
		return this.land.getWorld();
	}
	
	public boolean isInside(Position pos){
		if(pos.getLevel().getFolderName().equals(this.land.getWorld().getLevel().getFolderName())){
			return this.isInside(new Vector3(pos.x, pos.y, pos.z));
		}
		return false;
	}
	
	public boolean isInside(Vector3 vec){
		return this.isInside(vec.getFloorX(), vec.getFloorY(), vec.getFloorZ());
	}
	
	public boolean isInside(int x, int y, int z){
		return (
			this.start.x <= x &&
			this.start.y <= y &&
			this.start.z <= z &&
			this.end.x >= x &&
			this.end.y >= y &&
			this.end.z >= z
		);
	}
	
	public boolean isOverlap(Room room){
		return !(
			room.start.x > this.end.x ||
			room.start.y > this.end.y ||
			room.start.z > this.end.z ||
			room.end.x < this.start.x ||
			room.end.y < this.start.y ||
			room.end.z < this.start.z
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
		if(! this.owner.equals("") && ! this.isOwner(this.owner)){
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

	//public void setAllowFight(boolean bool){
	//	this.isAllowFight = bool;
	//}
	
	//public boolean isAllowFight(){
	//	return this.isAllowFight;
	//}

	//public void setAllowAccess(boolean bool){
	//	this.isAllowAccess = bool;
	//}
	
	//public boolean isAllowAccess(){
	//	return this.isAllowAccess;
	//}

	//public void setAllowPickUpItem(boolean bool){
	//	this.isAllowPickUpItem = bool;
	//}
	
	//public boolean isAllowPickUpItem(){
	//	return this.isAllowPickUpItem;
	//}

	public void setWelcomeMessage(String message){
		this.welcomeMessage = message;
	}
	
	public String getWelcomeMessage(){
		return this.welcomeMessage;
	}

	//public void setWelcomeParticle(int particle){
	//	this.welcomeParticle = particle;
	//}
	
	//public int getWelcomeParticle(){
	//	return this.welcomeParticle;
	//}
	
	//public void expand(Vector3 vec){
	//	this.expand(vec.getFloorX(), vec.getFloorZ());
	//}
	
	//public void expand(Vector2 vec){
	//	this.expand(vec.getFloorX(), vec.getFloorY());
	//}
	
	//public void expand(int x, int z){
	//	this.start = new Vector2(Math.min(this.start.x, x), Math.min(this.start.y, z));
	//	this.end = new Vector2(Math.max(this.end.x, x), Math.max(this.end.y, z));
	//}
	
	//public void expand(Land land){
	//	this.start = new Vector2(Math.min(this.start.x, land.start.x), Math.min(this.start.y, land.start.y));
	//	this.end = new Vector2(Math.min(this.end.x, land.start.x), Math.min(this.end.y, land.end.y));
	//}
	
	//public void reduce(Vector3 vec){
	//	this.reduce(vec.getFloorX(), vec.getFloorZ());
	//}
	
	//public void reduce(Vector2 vec){
	//	this.reduce(vec.getFloorX(), vec.getFloorY());
	//}
	
	//public void reduce(int x, int z){
	//	if(! this.isInside(x, z)){
	//		return;
	//	}
	//	int newStartX = (int) this.start.x;
	//	int newStartZ = (int) this.start.y;
	//	int newEndX = (int) this.end.x;
	//	int newEndZ = (int) this.end.y;
	//	
	//	if(Math.abs(this.start.x - x) > Math.abs(this.end.x - x)){
	//		newEndX = x;
	//	}else{
	//		newStartX = x;
	//	}
	//	if(Math.abs(this.start.y - z) > Math.abs(this.end.y - z)){
	//		newEndZ = z;
	//	}else{
	//		newStartZ = z;
	//	}
	//	this.start = new Vector2(newStartX, newStartZ);
	//	this.end = new Vector2(newEndX, newEndZ);
	//}
	
	public int xLength(){
		return (int) Math.abs(this.start.x - this.end.x) + 1;
	}
	
	public int yLength(){
		return (int) Math.abs(this.start.y - this.end.y) + 1;
	}
	
	public int zLength(){
		return (int) Math.abs(this.start.z - this.end.z) + 1;
	}
	
	public int size(){
		return this.xLength() * this.yLength() * this.zLength();
	}
	
	public void resetSpawnPoint(){
		this.spawnPoint = new Vector3(
			(int) ((this.start.x + this.end.x) / 2),
			this.start.y + 1,
			(int) ((this.start.z + this.end.z) / 2)
		);
	}
	
	public void clear(){
		this.clear(true);
	}
	
	public void clear(boolean all){
		this.members.clear();
		this.isSail = false;
		this.price = 0;
		this.welcomeMessage = "";
		//this.welcomeParticle = 0;
		this.resetSpawnPoint();
		if(all){
			this.owner = "";
		}else{
			this.members.add(this.owner);
		}
	}
	
	public Room clone(){
		Room room = new Room();
		room.number = this.number;
		room.land = this.land;

		room.start = this.start;
		room.end = this.end;
		room.isSail = this.isSail;
		room.price = this.price;
		room.owner = this.owner;
		room.members = this.members;

		room.spawnPoint = this.spawnPoint;
		//room.isAllowFight = this.isAllowFight;
		//room.isAllowAccess = this.isAllowAccess;
		//room.isAllowPickUpItem = this.isAllowPickUpItem;

		room.welcomeMessage = this.welcomeMessage;
		//room.welcomeParticle = this.welcomeParticle;
		return room;
	}
}