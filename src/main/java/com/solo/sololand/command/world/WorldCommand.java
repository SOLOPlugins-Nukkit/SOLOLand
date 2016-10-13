package com.solo.sololand.command.world;

import com.solo.sololand.command.MainCommand;

public class WorldCommand extends MainCommand{

  public WorldCommand(){
    super("월드", "월드를 관리하는 명령어 입니다.", "/월드 [서브 커맨드]");

    this.setInGameOnly(false);
    this.setPermission("sololand.command.world", false);

    this.registerSubCommand(new WorldCreate(this));
    this.registerSubCommand(new WorldList(this));
    this.registerSubCommand(new WorldInfo(this));
    this.registerSubCommand(new WorldSettingInfo(this));
    this.registerSubCommand(new WorldUserInfo(this));
    this.registerSubCommand(new WorldLandInfo(this));
    /*this.registerSubCommand(new WorldSetCustomName(this));*/
    this.registerSubCommand(new WorldMove(this));
    this.registerSubCommand(new WorldProtect(this));
    /*this.registerSubCommand(new WorldAllowBurn(this));*/
    this.registerSubCommand(new WorldAllowCreateLand(this));
    this.registerSubCommand(new WorldAllowExplosion(this));
    this.registerSubCommand(new WorldAllowFight(this));
    this.registerSubCommand(new WorldDefaultLandPrice(this));
    this.registerSubCommand(new WorldInvenSave(this));
    this.registerSubCommand(new WorldMaxLandCount(this));
    this.registerSubCommand(new WorldMaxLandLength(this));
    this.registerSubCommand(new WorldMinLandLength(this));
    this.registerSubCommand(new WorldPricePerBlock(this));
  }

}