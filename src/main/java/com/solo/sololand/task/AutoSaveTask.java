package com.solo.sololand.task;

import cn.nukkit.scheduler.PluginTask;
import com.solo.sololand.Main;
import com.solo.sololand.data.DataBase;

public class AutoSaveTask extends PluginTask<Main> {

  public AutoSaveTask(Main owner) {
    super(owner);
  }

  @Override
  public void onRun(int currentTick){
    DataBase.saveWorld(false);
  }
}