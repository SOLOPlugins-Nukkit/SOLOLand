package solo.sololand.external;

import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

public class Message {
	
	public static final int TYPE_TEXT = 0;
	public static final int TYPE_POPUP = 1;
	public static final int TYPE_TIP = 2;
	
	private Message(){
		
	}
	
	public static void raw(CommandSender sender, String message){
		raw(sender, message, TYPE_TEXT);
	}
	
	public static void raw(CommandSender sender, String message, int type){
		if(type == TYPE_TEXT){
			sender.sendMessage(message);
		}else{
			if(! (sender instanceof Player)){
				sender.sendMessage("§7[팝업] " + message);
			}else{
				Player player = (Player) sender;
				if(type == TYPE_POPUP){
					player.sendPopup(message);
				}else if(type == TYPE_TIP){
					player.sendTip(message);
				}
			}
		}
	}
	
	public static void normal(CommandSender sender, String message){
		raw(sender, "§b§l[ 알림 ] §r§7" + message, TYPE_TEXT);
	}
	
	public static void normal(CommandSender sender, String message, int type){
		String prefix = "§b§l[ 알림 ] §r§7";
		if(type == TYPE_POPUP || type == TYPE_TIP){
			prefix = "§b";
		}
		raw(sender, prefix + message, type);
	}
	
	public static void success(CommandSender sender, String message){
		normal(sender, message);
	}
	
	public static void alert(CommandSender sender, String message){
		raw(sender, "§c§l[ 알림 ] §r§7" + message, TYPE_TEXT);
	}
	
	public static void alert(CommandSender sender, String message, int type){
		String prefix = "§c§l[ 알림 ] §r§7";
		if(type == TYPE_POPUP || type == TYPE_TIP){
			prefix = "§c";
		}
		raw(sender, prefix + message, type);
	}
	
	public static void usage(CommandSender sender, String usage){
		raw(sender, "§d§l[ 사용법 ] §r§7" + usage, TYPE_TEXT);
	}
	
	public static void usage(CommandSender sender, String usage, int type){
		String prefix = "§d§l[ 사용법 ] §r§7";
		if(type == TYPE_POPUP || type == TYPE_TIP){
			prefix = "§d사용법 : ";
		}
		raw(sender, prefix + usage, type);
	}
	
	public static void info(CommandSender sender, String title, ArrayList<String> information){
		info(sender, title, information.toArray(new String[0]));
	}
	
	public static void info(CommandSender sender, String title, String[] information){
		raw(sender, "§l======< §b" + title + " §r§l>======");
		for(String inf : information){
			raw(sender, inf);
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i <= title.length(); i++){
			sb.append("=");
		}
	}

	public static void page(CommandSender sender, String title, String[] lines){
		page(sender, title, lines, 1);
	}
	
	public static void page(CommandSender sender, String title, String[] lines, int page){
		ArrayList<String> newLines = new ArrayList<String>();
		for(String line : lines){
			newLines.add(line);
		}
		page(sender, title, newLines, page);
	}

	public static void page(CommandSender sender, String title, ArrayList<String> lines){
		page(sender, title, lines, 1);
	}

	public static void page(CommandSender sender, String title, ArrayList<String> lines, int page){
		int linesPerPage = 5;
		int maxPage = 0;
		int currentPage = 0;
		
		if(lines.size() > 0){
			maxPage = (lines.size() / linesPerPage) + 1;
			if(page < 1){
				currentPage = 1;
			}else if(page > maxPage){
				currentPage = maxPage;
			}else{
				currentPage = page;
			}
		}

		raw(sender, "§l======< " + title + " §r§l(전체 " + Integer.toString(maxPage) + "페이지 중 " + Integer.toString(currentPage) + "페이지)" + " >======");
		
		int startIndex = ((currentPage - 1) * linesPerPage);
		int endIndex = currentPage * linesPerPage - 1;
		for(int i = startIndex; i <= endIndex; i++){
			try{
				raw(sender, lines.get(i));
			}catch(Exception e){
				break;
			}
		}
	}
}
