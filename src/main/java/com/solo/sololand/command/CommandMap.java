package com.solo.sololand.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.ArrayList;
import com.solo.sololand.util.Debug;

public class CommandMap<T extends BaseCommand>{

  private LinkedHashMap<String, T> commands = new LinkedHashMap<String, T>();
  private HashMap<String, String> linkAliases = new HashMap<String, String>();

  public void register(T cmd){
    if(this.commands.containsKey(cmd.getName())){
      Debug.fail(cmd.getName() + " 명령어 등록 실패 : 이미 존재하는 명령어 입니다.");
      return;
    }
    this.commands.put(cmd.getName(), cmd);
    if(cmd.getAliases().length > 0){
      for(String aliase : cmd.getAliases()){
        if(linkAliases.containsKey(aliase)){
          Debug.fail(cmd.getName() + " 명령어의 aliase(" + aliase + ") 등록 실패 : 이미 존재하는 aliase입니다.");
          continue;
        }
        this.linkAliases.put(aliase, cmd.getName());
      }
    }
  }

  public boolean unregister(T cmd){
    return this.unregister(cmd.getName());
  }
  public boolean unregister(String name){
    if(!this.commands.containsKey(name)){
      return false;
    }
    this.commands.remove(name);
    ArrayList<String> toRemove = new ArrayList<String>();
    for(String aliase : this.linkAliases.keySet()){
      if(this.linkAliases.get(aliase).equals(name)){
        toRemove.add(aliase);
      }
    }
    for(String rm : toRemove){
      this.linkAliases.remove(rm);
    }
    return true;
  }

  public LinkedHashMap<String, T> getAll(){
    return this.commands;
  }

  public T get(String name){
    if(this.linkAliases.containsKey(name)){
      name = this.linkAliases.get(name);
    }
    if(!this.commands.containsKey(name)){
      return null;
    }
    return this.commands.get(name);
  }


  public boolean dispatch(CommandSender sender, String cmdLine){
    String[] args = cmdLine.split(" ");
    if(args.length == 0){
      return false;
    }
    String firstArgs = args[0];
    T cmd = this.get(firstArgs);
    if(cmd == null){
      return false;
    }
    if(!sender.hasPermission(cmd.getPermission())){
      sender.sendMessage("§c권한이 없습니다.");
      return true;
    }
    if(!(sender instanceof Player) && cmd.isInGameOnly()){
      sender.sendMessage("§c인게임에서만 가능합니다.");
      return true;
    }
    String[] newArgs = new String[args.length - 1];
    for(int i = 0; i<= args.length - 1; i++){
      try{
        newArgs[i] = args[i+1];
      }catch(Exception e){
        break;
      }
    }
    if(!cmd.execute(sender, newArgs)){
      sender.sendMessage(cmd.getUsage());
    }
    return true;
  }

}