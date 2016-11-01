package com.solo.sololand.util;

import me.onebone.economyapi.EconomyAPI;

public abstract class Economy {

  public static double getMoney(String name){
    return EconomyAPI.getInstance().myMoney(name.toLowerCase());
  }
  public static void reduceMoney(String name, double money){
    EconomyAPI.getInstance().reduceMoney(name, money);
  }
  public static void addMoney(String name, double money){
    EconomyAPI.getInstance().addMoney(name, money);
  }
}




/* USE AS FAKE ECONOMY */
/*
package com.solo.sololand.util;

public abstract class Economy {

  public static double getMoney(String name){
    return 100000.0;
  }
  public static void reduceMoney(String name, double money){
  }
  public static void addMoney(String name, double money){
  }
}*/
