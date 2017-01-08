package solo.sololand.task;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.BlockColor;
import cn.nukkit.level.particle.Particle;
import cn.nukkit.level.particle.RedstoneParticle;
import cn.nukkit.level.Position;
import cn.nukkit.level.particle.DustParticle;
import cn.nukkit.level.particle.GenericParticle;
import cn.nukkit.math.Vector3;
import solo.sololand.land.Land;
import solo.sololand.land.Room;
import solo.sololand.queue.Queue;

import java.util.ArrayList;

public class ShowBorderLineTask extends AsyncTask{

	public Player player;
	public ArrayList<Particle> particles;
	
	public ShowBorderLineTask(Player player){
		super();
		this.player = player;
	}

	@Override
	public void onRun(){
		this.particles = this.getQueueParticle();
	}
	
	protected ArrayList<Particle> getQueueParticle(){
		ArrayList<Particle> particles = new ArrayList<Particle>();
		Position queuePos = Queue.getPosition(this.player);
		Land land = Queue.getLand(this.player);
		Land tempLand = Queue.getTemporaryLand(this.player);
		Room room = Queue.getRoom(this.player);
		Room tempRoom = Queue.getTemporaryRoom(this.player);
		
		Vector3 vec = new Vector3();
		
		if(queuePos != null){
			particles.add(new RedstoneParticle(vec.setComponents(queuePos.getFloorX(), queuePos.getFloorY(), queuePos.getFloorZ())));
			particles.add(new RedstoneParticle(vec.setComponents(queuePos.getFloorX(), queuePos.getFloorY(), queuePos.getFloorZ() + 1)));
			particles.add(new RedstoneParticle(vec.setComponents(queuePos.getFloorX(), queuePos.getFloorY() + 1, queuePos.getFloorZ())));
			particles.add(new RedstoneParticle(vec.setComponents(queuePos.getFloorX(), queuePos.getFloorY() + 1, queuePos.getFloorZ() + 1)));
			particles.add(new RedstoneParticle(vec.setComponents(queuePos.getFloorX() + 1, queuePos.getFloorY(), queuePos.getFloorZ())));
			particles.add(new RedstoneParticle(vec.setComponents(queuePos.getFloorX() + 1, queuePos.getFloorY(), queuePos.getFloorZ() + 1)));
			particles.add(new RedstoneParticle(vec.setComponents(queuePos.getFloorX() + 1, queuePos.getFloorY() + 1, queuePos.getFloorZ())));
			particles.add(new RedstoneParticle(vec.setComponents(queuePos.getFloorX() + 1, queuePos.getFloorY() + 1, queuePos.getFloorZ() + 1)));
		}
		if(land != null){
			particles.addAll(this.getLandBorderLineParticle(land));
		}
		if(tempLand != null){
			particles.addAll(this.getLandBorderLineParticle(tempLand, BlockColor.LAVA_BLOCK_COLOR));
		}
		if(room != null){
			particles.addAll(this.getRoomBorderLineParticle(room));
		}
		if(tempRoom != null){
			particles.addAll(this.getRoomBorderLineParticle(tempRoom));
		}
		return particles;
	}
	
	protected ArrayList<Particle> getLandBorderLineParticle(Land land){
		return this.getLandBorderLineParticle(land, BlockColor.MAGENTA_BLOCK_COLOR);
	}
	
	protected ArrayList<Particle> getLandBorderLineParticle(Land land, BlockColor color){
		ArrayList<Particle> particles = new ArrayList<Particle>();
		Vector3 vec = new Vector3();
		int startX = land.start.getFloorX();
		int endX = land.end.getFloorX() + 1;
		int startZ = land.start.getFloorY();
		int endZ = land.end.getFloorY() + 1;
		for(int y = player.getFloorY() + 1; y <= player.getFloorY() + 3; y++){
			for(int x = startX; x <= endX; x++){
				particles.add(new DustParticle(vec.setComponents(x, y, startZ), color));
				particles.add(new DustParticle(vec.setComponents(x, y, endZ), color));
			}
			for(int z = startZ + 1; z < endZ; z++){
				particles.add(new DustParticle(vec.setComponents(startX, y, z), color));
				particles.add(new DustParticle(vec.setComponents(endX, y, z), color));
			}
		}
		if(land.hasRoom()){
			for(Room room : land.getRooms().values()){
				particles.addAll(this.getRoomBorderLineParticle(room, Particle.TYPE_DRIP_WATER));
			}
		}
		return particles;
	}

	protected ArrayList<Particle> getRoomBorderLineParticle(Room room){
		return this.getRoomBorderLineParticle(room, Particle.TYPE_DRIP_LAVA);
	}
	
	protected ArrayList<Particle> getRoomBorderLineParticle(Room room, int particleId){
		ArrayList<Particle> particles = new ArrayList<Particle>();
		Vector3 vec = new Vector3();
		int roomStartX = room.start.getFloorX();
		int roomEndX = room.end.getFloorX() + 1;
		int roomStartY = room.start.getFloorY();
		int roomEndY = room.end.getFloorY() + 1;
		int roomStartZ = room.start.getFloorZ();
		int roomEndZ = room.end.getFloorZ() + 1;
		for(int x = roomStartX; x <= roomEndX; x++){
			particles.add(new GenericParticle(vec.setComponents(x, roomStartY, roomStartZ), particleId));
			particles.add(new GenericParticle(vec.setComponents(x, roomStartY, roomEndZ), particleId));
			particles.add(new GenericParticle(vec.setComponents(x, roomEndY, roomStartZ), particleId));
			particles.add(new GenericParticle(vec.setComponents(x, roomEndY, roomEndZ), particleId));
		}
		for(int z = roomStartZ + 1; z < roomEndZ; z++){
			particles.add(new GenericParticle(vec.setComponents(roomStartX, roomStartY, z), particleId));
			particles.add(new GenericParticle(vec.setComponents(roomEndX, roomStartY, z), particleId));
			particles.add(new GenericParticle(vec.setComponents(roomStartX, roomEndY, z), particleId));
			particles.add(new GenericParticle(vec.setComponents(roomEndX, roomEndY, z), particleId));
		}
		for(int y = roomStartY + 1; y < roomEndY; y++){
			particles.add(new GenericParticle(vec.setComponents(roomStartX, y, roomStartZ), particleId));
			particles.add(new GenericParticle(vec.setComponents(roomEndX, y, roomStartZ), particleId));
			particles.add(new GenericParticle(vec.setComponents(roomStartX, y, roomEndZ), particleId));
			particles.add(new GenericParticle(vec.setComponents(roomEndX, y, roomEndZ), particleId));
		}
		return particles;
	}
	
	@Override
	public void onCompletion(Server server){
		if(! this.player.isOnline()){
			return;
		}
		for(Particle particle : this.particles){
			this.player.getLevel().addParticle(particle, this.player);
		}
		server.getScheduler().scheduleDelayedTask(new DelayedTask(this.player), 30);
	}
}

class DelayedTask extends Task{

	public Player player;
	
	public DelayedTask(Player player){
		super();
		this.player = player;
	}
	
	@Override
	public void onRun(int currentTick) {
		if(Queue.get(this.player) != Queue.NULL){
			Server.getInstance().getScheduler().scheduleAsyncTask(new ShowBorderLineTask(this.player));
		}
	}
	
}