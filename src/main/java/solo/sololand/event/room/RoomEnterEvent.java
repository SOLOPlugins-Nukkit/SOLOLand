package solo.sololand.event.room;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import solo.sololand.external.Message;
import solo.sololand.land.Room;

public class RoomEnterEvent extends RoomEvent implements Cancellable{
	
	private static final HandlerList handlers = new HandlerList();

	protected final Player player;
	protected String welcomeMessage;
	protected int welcomeParticle;
	protected int messageType;
	
	public static HandlerList getHandlers() {
		return handlers;
	}

	public RoomEnterEvent(Player player, Room room){
		this(player, room, "");
	}
	
	public RoomEnterEvent(Player player, Room room, String welcomeMessage){
		this(player, room, welcomeMessage, Message.TYPE_TIP);
	}
	
	public RoomEnterEvent(Player player, Room room, String welcomeMessage, int messageType){
		this.player = player;
		this.room = room;
		this.welcomeMessage = welcomeMessage;
		this.messageType = messageType;
	}
	
	public String getWelcomeMessage(){
		return this.welcomeMessage;
	}
	
	public int getMessageType(){
		return this.messageType;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void setWelcomeMessage(String welcomeMessage){
		this.setWelcomeMessage(welcomeMessage, this.messageType);
	}
	
	public void setWelcomeMessage(String welcomeMessage, int messageType){
		this.welcomeMessage = welcomeMessage;
		this.messageType = messageType;
	}
}
