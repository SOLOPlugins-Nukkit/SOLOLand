package com.solo.sololand.util;

public abstract class ProgressBar {

  public static String make(int now, int max){
    return ProgressBar.make(now, max, 30);
  }

  public static String make(int now, int max, int length){
    return ProgressBar.make(now, max, length, "§a", "§0");
  }

  public static String make(int now, int max, int length, String barColor, String nullColor){
    StringBuilder sb = new StringBuilder();
    sb.append(barColor);
    sb.append("[");
    if(now >= max){
      for(int i = 1; i <= length; i++){
        sb.append("=");
      }
    }else if(now <= 0){
      sb.append(nullColor);
      for(int i = 1; i <= length; i++){
        sb.append("=");
      }
    }else{
      boolean check = true;     
      for(int i = 1; i <= length; i++){
        if(check){
          if(
            ( now / max ) <= ( i / length )
          ){
            sb.append(nullColor);
            check = false;
          }
        }
        sb.append("=");
      }
    }
    sb.append(barColor + "] " + Integer.toString(now) + "/" + Integer.toString(max));
    return sb.toString();
  }
}