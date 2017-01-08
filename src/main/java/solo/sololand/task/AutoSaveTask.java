package solo.sololand.task;

import cn.nukkit.scheduler.PluginTask;
import solo.sololand.Main;
import solo.sololand.world.World;

public class AutoSaveTask extends PluginTask<Main> {

	public AutoSaveTask(Main owner) {
		super(owner);
	}

	@Override
	public void onRun(int currentTick){
		for(World world : World.getAll().values()){
			world.save(false);
		}
	}
}