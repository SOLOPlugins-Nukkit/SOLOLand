/*
 * ISSUE
 * 
 * 1 : 갑옷 세이브가 안됨 (오류를 재현할 수 없음)
 * 2 : 평야 월드 생성 시 빈월드로 생성됨 (오류를 재현할 수 없음)
 * 3 : /땅 생성 입력 후 터치시 반응이 없음 (오류를 재현할 수 없음)
 * 4 : /땅 합치기 추가하기
 * 
 */

package solo.sololand;

import java.io.File;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.level.Level;
import cn.nukkit.level.generator.Generator;

import solo.sololand.generator.*;
import solo.sololand.task.*;
import solo.sololand.command.CommandMap;
import solo.sololand.command.MainCommand;
import solo.sololand.command.defaults.world.WorldCommand;
import solo.sololand.command.defaults.land.LandCommand;
import solo.sololand.command.defaults.room.RoomCommand;
import solo.sololand.util.DefaultValue;
import solo.sololand.util.PluginInformation;
import solo.sololand.util.Setting;
import solo.sololand.world.World;

public class Main extends PluginBase {

	private static Main main = null;
	private EventListener listener = new EventListener();
	private CommandMap<MainCommand> commandMap = new CommandMap<MainCommand>();

	public static Main getInstance(){
		return Main.main;
	}
	public EventListener getListener(){
		return this.listener;
	}
	public CommandMap<MainCommand> getCommandMap(){
		return this.commandMap;
	}

	@Override
	public void onLoad(){
		if(Main.main == null){
			Main.main = this;
		}
		this.getDataFolder().mkdirs();
		
		this.initialSetting(); //THIS IS VERY FIRST
		this.initialGenerators();
	}

	@Override
	public void onEnable(){
		this.initialWorlds();
		this.initialEvents();
		this.initialCommands();
		this.initialTasks();
	}

	@Override
	public void onDisable() {
		for(World world : World.getAll().values()){
			world.save(true);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		StringBuilder sb = new StringBuilder();
		sb.append(command.getName());
		for(String arg : args){
			sb.append(" ");
			sb.append(arg);
		}
		this.commandMap.dispatch(sender, sb.toString());
		return true;
	}





	// *************************** //
	// ********* initial ********* //
	// *************************** //
	private void initialSetting(){
		Setting.init();
		DefaultValue.init();
		PluginInformation.init();
	}
	
	private void initialGenerators(){
		Generator.addGenerator(IslandGenerator.class, "island", IslandGenerator.TYPE_ISLAND);
		Generator.addGenerator(GridLandGenerator.class, "gridland", GridLandGenerator.TYPE_GRID_LAND);
		Generator.addGenerator(EmptyWorldGenerator.class, "emptyworld", EmptyWorldGenerator.TYPE_EMPTY_WORLD);
		Generator.addGenerator(SkyBlockGenerator.class, "skyblock", SkyBlockGenerator.TYPE_SKY_BLOCK);
		Generator.addGenerator(SkyGridGenerator.class, "skygrid", SkyGridGenerator.TYPE_SKY_GRID);
	}
	
	private void initialWorlds(){
		if(Setting.loadAllWorldsOnEnable){
			File levelDirectory = new File(this.getServer().getDataPath() + File.separator + "worlds");
			for(File file : levelDirectory.listFiles()){
				if(file.isDirectory() && !file.getName().endsWith(".old")){
					this.getServer().loadLevel(file.getName());
				}
			}
		}
		for(Level level : this.getServer().getLevels().values()){
			World.load(level);
		}
	}
	
	private void initialEvents(){
		this.getServer().getPluginManager().registerEvents(this.listener, this);
	}
	
	private void initialTasks(){
		this.getServer().getScheduler().scheduleRepeatingTask(new AutoSaveTask(this), 12000);
	}
	
	private void initialCommands(){
		if(Setting.enableWorldCommand){
			this.commandMap.register(new WorldCommand());
		}
		if(Setting.enableLandCommand){
			this.commandMap.register(new LandCommand());
		}
		if(Setting.enableRoomCommand){
			this.commandMap.register(new RoomCommand());
		}
	}
}
