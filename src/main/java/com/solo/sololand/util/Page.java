package com.solo.sololand.util;

public abstract class Page{

  public static final int linesPerPage = 5;

  public static String[] make(String title, String[] lines){
    return Page.make(title, lines, 1);
  }

  public static String[] make(String title, String[] lines, int page){
    if(lines.length < 1){
      return new String[0];
    }
    String[] ret = new String[Page.linesPerPage + 1];
    int maxPage = lines.length / Page.linesPerPage + 1;
    if(page >= maxPage){
      page = maxPage;
      if(lines.length % Page.linesPerPage != 0){
        ret = new String[lines.length % Page.linesPerPage + 1];
      }
    }
    if(page < 1){
      page = 1;
    }
    int c = 0;
    ret[c++] = "====== " + title + " ( " + Integer.toString(page) + " / " + Integer.toString(maxPage) + " 페이지 )======";
    for(int i = (Page.linesPerPage * (page - 1)) + 1; i <= Page.linesPerPage * page; i++){
      try{
        ret[c++] = lines[i-1];
      }catch(Exception e){
        break;
      }
    }
    return ret;
  }
}