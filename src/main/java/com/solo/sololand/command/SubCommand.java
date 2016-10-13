package com.solo.sololand.command;

import cn.nukkit.command.CommandSender;

public abstract class SubCommand extends BaseCommand{

  private MainCommand mainCommand;
  public String name;
  public boolean inGameOnly;
  public String[] aliases = new String[0];
  public String usage = "";
  public String description = "";
  public String permission = null;

  public SubCommand(MainCommand mCmd, String name){
    this(mCmd, name, "description not provided");
  }
  public SubCommand(MainCommand mCmd, String name, String description){
    this(mCmd, name, description, "/" + mCmd.getName() + " " + name);
  }
  public SubCommand(MainCommand mCmd, String name, String description, String usage){
    this(mCmd, name, description, usage, new String[0]);
  }
  public SubCommand(MainCommand mCmd, String name, String description, String usage, String[] aliases){
    super(name, description, usage, aliases);
    this.mainCommand = mCmd;
  }

  /*  recommended
  public SubCommand(MainCommand mCmd){
    super(mCmd, "테스트", "테스트 서브명령어 입니다.", "/" + mCmd.getName() + " " + "[args...]", new String[]{"텥스트", "테슷트"});
    this.setPermission("sololand.command.world.test");
  }
  */

  public MainCommand getMainCommand(){
    return this.mainCommand;
  }

}