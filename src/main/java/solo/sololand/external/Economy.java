package solo.sololand.external;

import cn.nukkit.command.CommandSender;
import me.onebone.economyapi.EconomyAPI;

public class Economy {
	
	private Economy(){
		
	}

	public static double getMoney(CommandSender sender){
		return getMoney(sender.getName());
	}
	
	public static double getMoney(String name){
		name = name.toLowerCase();
		return EconomyAPI.getInstance().myMoney(name);
	}
	
	public static int reduceMoney(CommandSender sender, double money){
		return reduceMoney(sender.getName(), money);
	}
	
	public static int reduceMoney(String name, double money){
		name = name.toLowerCase();
		return EconomyAPI.getInstance().reduceMoney(name, money);
	}
	
	public static int addMoney(CommandSender sender, double money){
		return addMoney(sender.getName(), money);
	}
	
	public static int addMoney(String name, double money){
		return EconomyAPI.getInstance().addMoney(name, money);
	}
}
