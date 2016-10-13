package com.solo.sololand;

/* test */

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.level.Level;
import cn.nukkit.level.generator.Generator;

import com.solo.sololand.world.World;
import com.solo.sololand.world.Island;
import com.solo.sololand.generator.IslandGenerator;
import com.solo.sololand.generator.GridLandGenerator;
import com.solo.sololand.generator.EmptyWorldGenerator;
import com.solo.sololand.generator.SkyBlockGenerator;
 
import com.solo.sololand.task.AutoSaveTask;

public class Main extends PluginBase {

  private EventListener listener = new EventListener(this);
  private MainCommand command = new MainCommand(this);

  @Override
  public void onLoad(){
    Generator.addGenerator(IslandGenerator.class, "island", IslandGenerator.TYPE_ISLAND);
    Generator.addGenerator(GridLandGenerator.class, "gridland", GridLandGenerator.TYPE_GRID_LAND);
    Generator.addGenerator(EmptyWorldGenerator.class, "emptyworld", EmptyWorldGenerator.TYPE_EMPTY_WORLD);
    Generator.addGenerator(SkyBlockGenerator.class, "skyblock", SkyBlockGenerator.TYPE_SKY_BLOCK);
  }

  @Override
  public void onEnable(){
    File islandFolder = new File(Server.getInstance().getDataPath() + File.separator + "island");
    File gridlandFolder = new File(Server.getInstance().getDataPath() + File.separator + "gridland");
    File skyblockFolder = new File(Server.getInstance().getDataPath() + File.separator + "skyblock");
    if(islandFolder.isDirectory()){
      this.getServer().getInstance().loadLevel("island");
    }
    if(gridlandFolder.isDirectory()){
      this.getServer().getInstance().loadLevel("gridland");
    }
    if(skyblockFolder.isDirectory()){
      this.getServer().getInstance().loadLevel("skyblock");
    }
    for(Level level : this.getServer().getLevels().values()){
      if(level.getFolderName().equals("island")){
        World.registerWorld(new Island(level));
      }else{
        World.registerWorld(new World(level));
      }
    }

this.getServer().getPluginManager().registerEvents(this.listener, this);
    this.getServer().getScheduler().scheduleRepeatingTask(new AutoSaveTask(this), 12000);
  }

  @Override
  public void onDisable() {
    for(World world : World.getAll().values()){
      world.save();
    }
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    return this.command.onCommand(sender, command, args);
  }
}
