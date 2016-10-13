package com.solo.sololand.command.custom;

import com.solo.sololand.world.*;
import com.solo.sololand.command.MainCommand;

public class CustomWorldCommand extends MainCommand{

  public World world;

  public CustomWorldCommand(World world){
    super(world.getCustomName(), world.getCustomName() + " 월드 명령어", "/" + world.getCustomName() + " [서브 커맨드]");
    this.world = world;

    this.setPermission("sololand.command.custom", true);

    if(world instanceof Island){
      this.registerSubCommand(new CustomBuy(this));
    }
    this.registerSubCommand(new CustomList(this));
    this.registerSubCommand(new CustomMove(this));
    this.registerSubCommand(new CustomDefaultLandPrice(this));
  }

  public void update(){
    this.name = world.getCustomName();
    this.description = world.getCustomName() + " 월드 명령어";
    this.usage = "/" + world.getCustomName() + " [서브 커맨드]";
  }

  public World getWorld(){
    return this.world;
  }

}