package com.solo.sololand.land;

import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;

import java.util.LinkedHashMap;
import java.util.ArrayList;

public class Land {

  public int number;
  public Level level;

  public int startX, startZ, endX, endZ;
  public boolean isSail;
  public double price;
  public String owner;
  public ArrayList<String> members;

  public String spawnPoint;
  public boolean isAllowFight;
  public boolean isAllowAccess;
  public boolean isAllowPickUpItem;

  public String welcomeMessage;
  public int welcomeParticle;

  public Land(LinkedHashMap<String, Object> data){
    this.number = (int) data.get("landnumber");
    this.level = (Level) data.get("level");
    if((int) data.get("startx") < (int) data.get("endx")){
      this.startX = (int) data.get("startx");
      this.endX = (int) data.get("endx");
    }else{
      this.startX = (int) data.get("endx");
      this.endX = (int) data.get("startx");
    }
    if((int) data.get("startz") < (int) data.get("endz")){
      this.startZ = (int) data.get("startz");
      this.endZ = (int) data.get("endz");
    }else{
      this.startZ = (int) data.get("endz");
      this.endZ = (int) data.get("startz");
    }
    this.isSail = (boolean) data.get("issail");
    this.price = (double) data.get("price");
    this.owner = (String) data.get("owner");
    this.members = (ArrayList<String>) data.get("members");
    this.spawnPoint = (String) data.get("spawnpoint");
    this.isAllowFight = (boolean) data.get("isallowfight");
    this.isAllowAccess = (boolean) data.get("isallowaccess");
    this.isAllowPickUpItem = (boolean) data.get("isallowpickupitem");
    this.welcomeMessage = (String) data.get("welcomemessage");
    this.welcomeParticle = (int) data.get("welcomeparticle");
  }

  public int getNumber(){
    return this.number;
  }

  public Level getLevel() {
    return this.level;
  }

  public boolean isSail() {
    return this.isSail;
  }
  public void setSail(boolean bool){
    this.isSail = bool;
  }

  public double getPrice() {
    return this.price;
  }
  public void setPrice(double price) {
    this.price = price;
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

  public ArrayList<String> getMembers(){
    return this.members;
  }
  public void setMembers(ArrayList<String> members){
    this.members = members;
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

  public void setWelcomeParticle(int particle){
    this.welcomeParticle = particle;
  }
  public int getWelcomeParticle(){
    return this.welcomeParticle;
  }


}