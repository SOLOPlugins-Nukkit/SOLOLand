package solo.sololand.external;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import solo.sololand.util.Setting;

public class Debug {

	private Debug(){
		
	}
	
	public static void normal(PluginBase plugin, String message){
		if(Setting.debugMode){
			Server.getInstance().getLogger().info("§b[" + plugin.getName() + " Debug] " + message);
		}
	}
	
	public static void alert(PluginBase plugin, String message){
		if(Setting.debugMode){
			Server.getInstance().getLogger().info("§c[" + plugin.getName() + " Alert] " + message);
		}
	}
	
	public static void critical(PluginBase plugin, String message){
		if(Setting.debugMode){
			Server.getInstance().getLogger().info("§4[" + plugin.getName() + " Critical] " + message);
		}
	}
}
