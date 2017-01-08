package solo.sololand.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;
import cn.nukkit.level.Position;

public class PlayerFloorMoveEvent extends PlayerEvent implements Cancellable{
	
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private Position from;
    private Position to;

    public PlayerFloorMoveEvent(Player player, Position from, Position to) {
        this.player = player;
        this.from = from;
        this.to = to;
    }

    public Position getFrom() {
        return from;
    }

    public void setFrom(Position from) {
        this.from = from;
    }

    public Position getTo() {
        return to;
    }

    public void setTo(Position to) {
        this.to = to;
    }
}
