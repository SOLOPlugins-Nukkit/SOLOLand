package com.solo.sololand.room;

import cn.nukkit.math.Vector3;
import com.solo.sololand.land.Land;

class Room {

  public Room(){
  }

  public int number;
  public Land land;

  public int startX, startY, startZ, endX, endY, endZ;
  public String owner;
  public String spawnPoint;
  public String welcomeMessage;

  public Room(LinkedHashMap<String, Object> data){
    this.number = (int) data.get("roomnumber");
    this.land = (Land) data.get("land");
    if((int) data.get("startx") < (int) data.get("endx")){
      this.startX = (int) data.get("startx");
      this.endX = (int) data.get("endx");
    }else{
      this.startX = (int) data.get("endx");
      this.endX = (int) data.get("startx");
    }
    if((int) data.get("starty") < (int) data.get("endy")){
      this.startY = (int) data.get("starty");
      this.endY = (int) data.get("endy");
    }else{
      this.startY = (int) data.get("endy");
      this.endY = (int) data.get("starty");
    }
    if((int) data.get("startz") < (int) data.get("endz")){
      this.startZ = (int) data.get("startz");
      this.endZ = (int) data.get("endz");
    }else{
      this.startZ = (int) data.get("endz");
      this.endZ = (int) data.get("startz");
    }
    this.owner = (String) data.get("owner");
    this.spawnPoint = (String) data.get("spawnpoint");
    this.welcomeMessage = (String) data.get("welcomemessage");
  }

  public int getNumber(){
    return this.number;
  }

  public Level getLand() {
    return this.land;
  }

  public String getOwner(){
    return this.owner;
  }
  public void setOwner(String owner){
    this.owner = owner;
  }
  public boolean isOwner(String name){
    return (this.owner.equals(name.toLowerCase()));
  }

  public boolean isMember(String name){
    return (this.members.contains(name.toLowerCase()));
  }
  public void addMember(String name){
    this.members.add(name.toLowerCase());
  }
  public void removeMember(String name){
    this.members.remove(name.toLowerCase());
  }

  public Vector3 getSpawnPoint(){
    String[] xyz = this.spawnPoint.split(":");
    double x = Double.valueOf(xyz[0]).doubleValue();
    double y = Double.valueOf(xyz[1]).doubleValue();
    double z = Double.valueOf(xyz[2]).doubleValue();
    return new Vector3(x, y, z);
  }
  public void setSpawnPoint(Vector3 vec){
    this.spawnPoint = Double.toString(vec.x) + ":" + Double.toString(vec.y) + ":" + Double.toString(vec.z);
  }
  public void setSpawnPoint(double x, double y, double z){
    this.spawnPoint = Double.toString(x) + ":" + Double.toString(y) + ":" + Double.toString(z);
  }

  public void setAllowFight(boolean bool){
    this.isAllowFight = bool;
  }
  public boolean isAllowFight(){
    return this.isAllowFight;
  }

  public void setAllowAccess(boolean bool){
    this.isAllowAccess = bool;
  }
  public boolean isAllowAccess(){
    return this.isAllowAccess;
  }

  public void setAllowPickUpItem(boolean bool){
    this.isAllowPickUpItem = bool;
  }
  public boolean isAllowPickUpItem(){
    return this.isAllowPickUpItem;
  }

  public void setWelcomeMessage(String message){
    this.welcomeMessage = message;
  }
  public String getWelcomeMessage(){
    return this.welcomeMessage;
  }
}