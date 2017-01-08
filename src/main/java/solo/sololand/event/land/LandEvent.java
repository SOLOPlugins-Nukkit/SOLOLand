package solo.sololand.event.land;

import cn.nukkit.event.Event;
import solo.sololand.land.Land;

public abstract class LandEvent extends Event{
	protected Land land;
	
	public Land getLand(){
		return this.land;
	}
}
