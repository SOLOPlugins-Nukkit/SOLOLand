package solo.sololand.util;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

import cn.nukkit.Server;
import cn.nukkit.utils.Config;
import solo.sololand.Main;

public class PluginInformation{
	
	private PluginInformation(){
		
	}
	
	public static String VERSION;
	
	public static void init(){
		VERSION = Main.getInstance().getDescription().getVersion();
		load();
	}
	
	@SuppressWarnings({ "deprecation", "serial" })
	public static void load(){
		Config config = new Config(new File(Main.getInstance().getDataFolder(), "pluginInformation.yml"), Config.YAML, new LinkedHashMap<String, Object>(){{
			put("gridlandDuplicateIssue", false);
		}});
		for(String key : config.getAll().keySet()){
			if(! config.getBoolean(key)){
				solveIssue(key);
				config.set(key, true);
			}
		}
		config.save();
	}
	
	public static void log(String message){
		Server.getInstance().getLogger().info("§a[SOLOLand Info] " + message);
	}
	
	public static void solveIssue(String issue){
		switch(issue){
			case "gridlandDuplicateIssue":
				log("검사하지 않은 문제점을 발견하였습니다 : 평야(gridland) 월드에서 땅이 겹쳐지게 생성되는 문제점");
				int total = 0;
				int check = 0;
				for(File file : (new File(Server.getInstance().getDataPath() + File.separator + "worlds")).listFiles()){
					if(file.isDirectory() && ! file.getName().endsWith(".old")){
						
						HashMap<String, File> landList = new HashMap<>();
						HashSet<File> toRemove = new HashSet<File>();
						
						File landPath = new File(file + File.separator + "land");
						if(landPath.isDirectory()){
							for(File landFile : landPath.listFiles()){
								try{
									if(landFile.getName().endsWith(".yml")){
										check++;
										Config conf = new Config(landFile, Config.YAML);
										String hash = Integer.toString(conf.getInt("startx")) + ":" + Integer.toString(conf.getInt("startz")) + ":" + Integer.toString(conf.getInt("endx")) + ":" + Integer.toString(conf.getInt("endz"));
										//if duplicate
										if(landList.containsKey(hash)){
											
											//owner exist?
											if(conf.getString("owner").equals("")){
												toRemove.add(landFile);
												continue;
											}
											//if owner is not exist, remove origin dupe
											toRemove.add(landList.get(hash));
											landList.put(hash, landFile);
											continue;
										}
										landList.put(hash, landFile);
									}
								}catch(Exception e){
									
								}
							}
						}
						int count = 0;
						for(File rm : toRemove){
							count++;
							total++;
							rm.renameTo(new File(rm + ".duplicate"));
						}
						if(count != 0){
							log(file.getName() + " 월드에서 겹쳐진 땅 " + Integer.toString(count) + "개를 발견하였습니다.");
						}
					}
				}
				log("검사한 땅 갯수 : " + Integer.toString(check) + "개");
				if(total == 0){
					log("문제점이 발견되지 않았습니다.");
				}else{
					log("총 " + Integer.toString(total) + "개의 겹치는 땅이 발견되었습니다.");
					log("문제점 해결을 완료하였습니다.");
				}
				break;
				
		}
	}
	
}