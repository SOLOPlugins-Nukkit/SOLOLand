package solo.sololand.event.room;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import solo.sololand.external.Message;
import solo.sololand.land.Room;

public class RoomLeaveEvent extends RoomEvent implements Cancellable{

	private static final HandlerList handlers = new HandlerList();

	protected final Player player;
	protected String leaveMessage;
	protected int messageType;
	
	public static HandlerList getHandlers() {
		return handlers;
	}

	public RoomLeaveEvent(Player player, Room room){
		this(player, room, "");
	}
	
	public RoomLeaveEvent(Player player, Room room, String leaveMessage){
		this(player, room, leaveMessage, Message.TYPE_TIP);
	}
	
	public RoomLeaveEvent(Player player, Room room, String leaveMessage, int messageType){
		this.player = player;
		this.room = room;
		this.leaveMessage = leaveMessage;
		this.messageType = messageType;
	}
	
	public String getLeaveMessage(){
		return this.leaveMessage;
	}
	
	public int getMessageType(){
		return this.messageType;
	}
	
	public Player getPlayer(Player player) {
		return this.player;
	}
	
	public void setLeaveMessage(String leaveMessage){
		this.setLeaveMessage(leaveMessage, Message.TYPE_TIP);
	}
	
	public void setLeaveMessage(String leaveMessage, int messageType){
		this.leaveMessage = leaveMessage;
		this.messageType = messageType;
	}
}
