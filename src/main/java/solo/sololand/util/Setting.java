package solo.sololand.util;

import cn.nukkit.utils.Config;
import solo.sololand.Main;

import java.util.LinkedHashMap;
import java.io.File;

public class Setting{
	
	public static boolean debugMode;
	public static boolean loadAllWorldsOnEnable;
	public static boolean enableWorldCommand;
	public static boolean enableLandCommand;
	public static boolean enableRoomCommand;
	public static boolean enableBorderLineParticle;
	
	private Setting(){
	}
	
	public static void init(){
		load();
	}
	
	@SuppressWarnings("deprecation")
	public static void load(){
		LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("debugMode", false);
		data.put("loadAllWorldsOnEnable", true);
		data.put("enableWorldCommand", true);
		data.put("enableLandCommand", true);
		data.put("enableRoomCommand", true);
		data.put("enableBorderLineParticle", true);
		//data.put("messageNormalPrefix", "§b§l[ 알림 ] §r§7");
		//data.put("messageUsagePrefix", "§d§l[ 사용법 ] §r§7");
		//data.put("messageAlertPrefix", "§c§l[ 알림 ] §r§7");
		//data.put("messageInfoFormat", "§l======< §b{TITLE} §r§l>======");
		//data.put("messagePageFormat", "§l======< {TITLE} §r§l(전체 {MAXPAGE}페이지 중 {PAGE}페이지) >======");
		Config config = new Config(new File(Main.getInstance().getDataFolder(), "setting.yml"), Config.YAML, data);
		data = (LinkedHashMap<String, Object>) config.getAll();
		debugMode = (boolean) data.get("debugMode");
		loadAllWorldsOnEnable = (boolean) data.get("loadAllWorldsOnEnable");
		enableWorldCommand = (boolean) data.get("enableWorldCommand");
		enableLandCommand = (boolean) data.get("enableLandCommand");
		enableRoomCommand = (boolean) data.get("enableRoomCommand");
		enableBorderLineParticle = (boolean) data.get("enableBorderLineParticle");
	}
}