package solo.sololand.event.room;

import cn.nukkit.event.Event;
import solo.sololand.land.Room;

public class RoomEvent extends Event{
	protected Room room;
	
	public Room getRoom(){
		return this.room;
	}
}
