package com.solo.sololand;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.level.Level;
import cn.nukkit.level.generator.Generator;

import com.solo.sololand.world.*;
import com.solo.sololand.generator.*;
import com.solo.sololand.task.*;
import com.solo.sololand.data.DataBase;
import com.solo.sololand.command.CommandMap;
import com.solo.sololand.command.MainCommand;
import com.solo.sololand.command.world.WorldCommand;
import com.solo.sololand.command.land.LandCommand;

import java.io.File;

public class Main extends PluginBase {

  private static Main main = null;
  private EventListener listener = new EventListener(this);
  private CommandMap<MainCommand> commandMap = new CommandMap<MainCommand>();

  public static Main getInstance(){
    return Main.main;
  }
  public EventListener getListener(){
    return this.listener;
  }
  public CommandMap getCommandMap(){
    return this.commandMap;
  }

  @Override
  public void onLoad(){
    if(Main.main == null){
      Main.main = this;
    }
    this.registerGenerators();
  }

  @Override
  public void onEnable(){
    this.registerWorlds();
    this.registerCommands();
    this.registerEvents();
    this.registerTasks();
  }

  @Override
  public void onDisable() {
    DataBase.saveWorld();
  }






  // *************************** //
  // ********** initial ********** //
  // *************************** //
  private void registerGenerators(){
    Generator.addGenerator(IslandGenerator.class, "island", IslandGenerator.TYPE_ISLAND);
    Generator.addGenerator(GridLandGenerator.class, "gridland", GridLandGenerator.TYPE_GRID_LAND);
    Generator.addGenerator(EmptyWorldGenerator.class, "emptyworld", EmptyWorldGenerator.TYPE_EMPTY_WORLD);
    Generator.addGenerator(SkyBlockGenerator.class, "skyblock", SkyBlockGenerator.TYPE_SKY_BLOCK);
    Generator.addGenerator(SkyGridGenerator.class, "skygrid", SkyGridGenerator.TYPE_SKY_GRID);
  }
  private void registerWorlds(){
    DataBase.loadLevel();
    DataBase.loadWorld();
  }
  private void registerEvents(){
    
this.getServer().getPluginManager().registerEvents(this.listener, this);
  }
  private void registerTasks(){
    this.getServer().getScheduler().scheduleRepeatingTask(new AutoSaveTask(this), 12000);
  }
  private void registerCommands(){
    this.commandMap.register(new WorldCommand());
    this.commandMap.register(new LandCommand());
  }
}
