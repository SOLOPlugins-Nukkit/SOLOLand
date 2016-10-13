package com.solo.sololand.command;

import cn.nukkit.Server;
import cn.nukkit.permission.Permission;
import cn.nukkit.command.CommandSender;

public abstract class BaseCommand{

  public String name;
  public boolean inGameOnly = true;
  public String[] aliases = new String[0];
  public String usage = "";
  public String description = "";
  public String permission = null;

  /* SimpleCommand
  @Command(
    name = this.name,
    description = this.description,
    usageMessage = this.usage,
    aliases = this.aliases
  )
  @CommandPermission(this.permission)
  public boolean onCommand(CommandSender sender, String label, String[] args){
    return this.execute(sender, args);
  }
    Server.getInstance().getCommandMap().registerSimpleCommands(new Command());
  */
  

  public BaseCommand(String name){
    this(name, "description not provided");
  }
  public BaseCommand(String name, String description){
    this(name, description, "/" + name);
  }
  public BaseCommand(String name, String description, String usage){
    this(name, description, usage, new String[0]);
  }
  public BaseCommand(String name, String description, String usage, String[] aliases){
    this.name = name;
    this.description = description;
    this.usage = usage;
    this.aliases = aliases;
  }

  public boolean isInGameOnly(){
    return this.inGameOnly;
  }
  public void setInGameOnly(boolean inGameOnly){
    this.inGameOnly = inGameOnly;
  }
  public String getName(){
    return this.name;
  }
  public void setName(String name){
    this.name = name;
  }
  public String getUsage(){
    return this.usage;
  }
  public void setUsage(String usage){
    this.usage = usage;
  }
  public String getDescription(){
    return this.description;
  }
  public void setDescription(String description){
    this.description = description;
  }
  public String getPermission(){
    return this.permission;
  }
  public void setPermission(String permission){
    this.setPermission(permission, true);
  }
  public void setPermission(String permission, boolean defualtValue){
    this.permission = permission;
    String defualt = Permission.DEFAULT_TRUE;
    if(!defualtValue){
      defualt = Permission.DEFAULT_OP;
    }
    Server.getInstance().getPluginManager().addPermission(new Permission(permission, "command permission", defualt));
  }
  public String[] getAliases(){
    return this.aliases;
  }
  public void setAliases(String[] aliases){
    this.aliases = aliases;
  }
  public String getFullDescription(){
    return this.usage + " - " + this.description;
  }

  public abstract boolean execute(CommandSender sender, String[] args);

}