package com.solo.sololand.command.land;

import com.solo.sololand.command.MainCommand;

public class LandCommand extends MainCommand{

  public LandCommand(){
    super("땅", "땅을 관리하는 명령어 입니다.", "/땅 [서브 커맨드]");

    this.setPermission("sololand.command.land", true);

    this.registerSubCommand(new LandCreate(this));
    this.registerSubCommand(new LandInfo(this));
    this.registerSubCommand(new LandMove(this));
    this.registerSubCommand(new LandRemove(this));
    this.registerSubCommand(new LandList(this));
    this.registerSubCommand(new LandShare(this));
    this.registerSubCommand(new LandWelcomeMessage(this));
    this.registerSubCommand(new LandWelcomeParticle(this));
    this.registerSubCommand(new LandSetSpawn(this));
    this.registerSubCommand(new LandAccess(this));
    this.registerSubCommand(new LandAllowFight(this));
    this.registerSubCommand(new LandAllowPickUpItem(this));
    this.registerSubCommand(new LandBuy(this));
    this.registerSubCommand(new LandSell(this));
    this.registerSubCommand(new LandSellList(this));
    this.registerSubCommand(new LandCancelSell(this));
    this.registerSubCommand(new LandCancelShare(this));
    this.registerSubCommand(new LandGive(this));
    /*this.registerSubCommand(new LandResize(this));*/
    this.registerSubCommand(new LandShareList(this));
    this.registerSubCommand(new LandVisitor(this));
    this.registerSubCommand(new LandCancel(this));
  }

}