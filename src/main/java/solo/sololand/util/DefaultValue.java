package solo.sololand.util;

import java.io.File; 
import java.util.LinkedHashMap;

import cn.nukkit.utils.Config;
import solo.sololand.Main;

public class DefaultValue{

	private DefaultValue(){
		
	}
	
	public static boolean landAllowFight;
	public static boolean landAllowAccess;
	public static boolean landAllowPickUpItem;
	public static boolean landAllowDoor;
	public static boolean landAllowChest;
	public static boolean landAllowCraft;
	
	public static void init(){
		load();
	}
	
	@SuppressWarnings("deprecation")
	public static void load(){
		LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("landAllowFight", false);
		data.put("landAllowAccess", true);
		data.put("landAllowPickUpItem", false);
		data.put("landAllowDoor", true);
		data.put("landAllowChest", false);
		data.put("landAllowCraft", true);
		Config config = new Config(new File(Main.getInstance().getDataFolder(), "defaultValue.yml"), Config.YAML, data);
		landAllowFight = config.getBoolean("landAllowFight");
		landAllowAccess = config.getBoolean("landAllowAccess");
		landAllowPickUpItem = config.getBoolean("landAllowPickUpItem");
		landAllowDoor = config.getBoolean("landAllowDoor");
		landAllowChest = config.getBoolean("landAllowChest");
		landAllowCraft = config.getBoolean("landAllowCraft");
	}
	
}