package solo.sololand.event.land;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import solo.solobasepackage.util.Message;
import solo.sololand.land.Land;

public class LandEnterEvent extends LandEvent implements Cancellable{
	
	private static final HandlerList handlers = new HandlerList();

	protected final Player player;
	protected String welcomeMessage;
	protected int welcomeParticle;
	protected int messageType;
	
	public static HandlerList getHandlers() {
		return handlers;
	}

	public LandEnterEvent(Player player, Land land){
		this(player, land, "");
	}
	
	public LandEnterEvent(Player player, Land land, String welcomeMessage){
		this(player, land, welcomeMessage, 0);
	}
	
	public LandEnterEvent(Player player, Land land, String welcomeMessage, int welcomeParticle){
		this(player, land, welcomeMessage, 0, Message.TYPE_TIP);
	}
	
	public LandEnterEvent(Player player, Land land, String welcomeMessage, int welcomeParticle, int messageType){
		this.player = player;
		this.land = land;
		this.welcomeMessage = welcomeMessage;
		this.welcomeParticle = welcomeParticle;
		this.messageType = messageType;
	}
	
	public String getWelcomeMessage(){
		return this.welcomeMessage;
	}
	
	public int getWelcomeParticle(){
		return this.welcomeParticle;
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
	
	public void setWelcomeParticle(int particle){
		this.welcomeParticle = particle;
	}
}
