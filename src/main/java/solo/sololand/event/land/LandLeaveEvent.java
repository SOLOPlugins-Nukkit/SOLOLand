package solo.sololand.event.land;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import solo.sololand.external.Message;
import solo.sololand.land.Land;

public class LandLeaveEvent extends LandEvent implements Cancellable{

	private static final HandlerList handlers = new HandlerList();

	protected final Player player;
	protected String leaveMessage;
	protected int messageType;
	
	public static HandlerList getHandlers() {
		return handlers;
	}

	public LandLeaveEvent(Player player, Land land){
		this(player, land, "");
	}
	
	public LandLeaveEvent(Player player, Land land, String leaveMessage){
		this(player, land, leaveMessage, Message.TYPE_TIP);
	}
	
	public LandLeaveEvent(Player player, Land land, String leaveMessage, int messageType){
		this.player = player;
		this.land = land;
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
