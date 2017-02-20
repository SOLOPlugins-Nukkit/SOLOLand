package solo.sololand.queue;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Position;
import solo.sololand.land.Land;
import solo.sololand.land.Room;
import solo.sololand.task.ShowBorderLineTask;
import solo.sololand.util.Setting;
import solo.sololand.world.World;

import java.util.HashMap;

public class Queue{
	
	public static final int NULL = 0;
	
	public static final int LAND_CREATE_FIRST = 1;
	public static final int LAND_CREATE_SECOND = 2;
	public static final int LAND_CREATE_THIRD = 3;
	
	public static final int LAND_REMOVE = 4;
	
	public static final int LAND_EXPAND_FIRST = 5;
	public static final int LAND_EXPAND_SECOND = 6;
	
	public static final int LAND_REDUCE_FIRST = 7;
	public static final int LAND_REDUCE_SECOND = 8;
	
	public static final int LAND_SELL = 9;
	
	public static final int LAND_COMBINE = 10;
	
	public static final int ROOM_CREATE_FIRST = 20;
	public static final int ROOM_CREATE_SECOND = 21;
	public static final int ROOM_CREATE_THIRD = 22;
	
	public static final int ROOM_REMOVE = 23;
	
	public static final int ROOM_SELL = 24;
	
	
	public static HashMap<String, Integer> queueList = new HashMap<String, Integer>();
	
	public static HashMap<String, Position> positionList = new HashMap<String, Position>();
	
	public static HashMap<String, Integer> landList = new HashMap<String, Integer>();
	public static HashMap<String, Land> temporaryLandList = new HashMap<String, Land>();
	
	public static HashMap<String, String> roomList = new HashMap<String, String>();
	public static HashMap<String, Room> temporaryRoomList = new HashMap<String, Room>();
	
	public static HashMap<String, Integer> intList = new HashMap<String, Integer>();
	public static HashMap<String, Double> doubleList = new HashMap<String, Double>();
	
	private Queue(){
		
	}
	
	@SuppressWarnings("deprecation")
	public static void set(Player player, int queueType){
		if(Setting.enableBorderLineParticle && get(player) == NULL){
			Server.getInstance().getScheduler().scheduleAsyncTask(new ShowBorderLineTask(player));
		}
		queueList.put(player.getName().toLowerCase(), queueType);
	}
	
	public static int get(Player player){
		String name = player.getName().toLowerCase();
		if(queueList.containsKey(name)){
			return queueList.get(name);
		}
		return NULL;
	}
	
	public static void setPosition(Player player){
		setPosition(player, new Position(player.x, player.y, player.z, player.level));
	}
	
	public static void setPosition(Player player, Position pos){
		positionList.put(player.getName().toLowerCase(), pos);
	}
	
	public static Position getPosition(Player player){
		String name = player.getName().toLowerCase();
		if(positionList.containsKey(name)){
			return positionList.get(name);
		}
		return null;
	}
	
	public static void setLand(Player player, Land land){
		landList.put(player.getName().toLowerCase(), land.getNumber());
	}
	
	public static Land getLand(Player player){
		String name = player.getName().toLowerCase();
		if(landList.containsKey(name)){
			return World.get(player.getLevel()).getLand(landList.get(name));
		}
		return null;
	}
	
	public static void setTemporaryLand(Player player, Land land){
		temporaryLandList.put(player.getName().toLowerCase(), land);
	}
	
	public static Land getTemporaryLand(Player player){
		String name = player.getName().toLowerCase();
		if(temporaryLandList.containsKey(name)){
			return temporaryLandList.get(name);
		}
		return null;
	}
	
	public static void setRoom(Player player, String address){
		roomList.put(player.getName().toLowerCase(), address);
	}
	
	public static Room getRoom(Player player){
		String name = player.getName().toLowerCase();
		if(roomList.containsKey(name)){
			String address = roomList.get(name);
			int landNum = Integer.parseInt(address.split("-")[0]);
			Land land = World.get(player.getLevel()).getLand(landNum);
			if(land != null){
				int roomNum = Integer.parseInt(address.split("-")[1]);
				Room room = land.getRoom(roomNum);
				return room;
			}
		}
		return null;
	}
	
	public static void setTemporaryRoom(Player player, Room room){
		temporaryRoomList.put(player.getName().toLowerCase(), room);
	}
	
	public static Room getTemporaryRoom(Player player){
		String name = player.getName().toLowerCase();
		if(temporaryRoomList.containsKey(name)){
			return temporaryRoomList.get(name);
		}
		return null;
	}
	
	public static void setInt(Player player, int value){
		intList.put(player.getName().toLowerCase(), value);
	}
	
	public static Integer getInt(Player player){
		String name = player.getName().toLowerCase();
		if(intList.containsKey(name)){
			return intList.get(name);
		}
		return null;
	}
	
	public static void setDouble(Player player, double value){
		doubleList.put(player.getName().toLowerCase(), value);
	}
	
	public static Double getDouble(Player player){
		String name = player.getName().toLowerCase();
		if(doubleList.containsKey(name)){
			return doubleList.get(name);
		}
		return null;
	}
	
	public static void clean(Player player){
		String name = player.getName().toLowerCase();
		queueList.remove(name);
		positionList.remove(name);
		landList.remove(name);
		temporaryLandList.remove(name);
		roomList.remove(name);
		temporaryRoomList.remove(name);
		intList.remove(name);
		doubleList.remove(name);
	}
	
}