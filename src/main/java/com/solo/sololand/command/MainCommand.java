package com.solo.sololand.command;

import cn.nukkit.Server;
import cn.nukkit.command.simple.Command;
import cn.nukkit.command.simple.CommandPermission;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.util.Page;
import com.solo.sololand.util.Message;

import java.util.ArrayList;

public abstract class MainCommand extends BaseCommand{

  private CommandMap<SubCommand> subCommandMap = new CommandMap<SubCommand>();

  public MainCommand(String name){
    this(name, "description not provided");
  }
  public MainCommand(String name, String description){
    this(name, description, "/" + name);
  }
  public MainCommand(String name, String description, String usage){
    this(name, description, usage, new String[0]);
  }

  public MainCommand(String name, String description, String usage, String[] aliases){
    super(name, description, usage, aliases);
     Server.getInstance().getCommandMap().registerSimpleCommands(this);
  }

  @Override
  public boolean execute(CommandSender sender, String[] args){
    StringBuilder sb = new StringBuilder();
    boolean space = false;
    for(String arg : args){
      if(space){
        sb.append(" ");
      }else{
        space = true;
      }
      sb.append(arg);
    }
    if(!this.subCommandMap.dispatch(sender, sb.toString())){
      int page = 1;
      if(args.length > 0){
        try{
          page = Integer.parseInt(args[0]);
        }catch(Exception e){
        }
      }
      this.sendHelp(sender, page);
    }
    return true;
  }

  public void registerSubCommand(SubCommand subCommand){
    this.subCommandMap.register(subCommand);
  }

  public void unregisterSubCommand(SubCommand subCommand){
    this.subCommandMap.unregister(subCommand);
  }

  public void sendHelp(CommandSender sender){
    this.sendHelp(sender, 1);
  }
  public void sendHelp(CommandSender sender, int page){
    ArrayList<SubCommand> available = this.getAvailable(sender);
    String[] avToString = new String[available.size()];
    int c = 0;
    for(SubCommand cmd : available){
      try{
        avToString[c++] = cmd.getFullDescription();
      }catch(Exception e){
      }
    }
    for(String line : Page.make(this.getName() + " 명령어 도움말", avToString, page)){
      Message.help(sender, line);
    }
  }

  public ArrayList<SubCommand> getAvailable(CommandSender sender){
    ArrayList<SubCommand> ret = new ArrayList<SubCommand>();
    for(SubCommand cmd : this.subCommandMap.getAll().values()){
      if(!(sender instanceof Player) && cmd.isInGameOnly()){
        continue;
      }
      if(cmd.getPermission() != null){
        if(!sender.hasPermission(cmd.getPermission())){
          continue;
        }
      }
      ret.add(cmd);
    }
    return ret;
  }
}
