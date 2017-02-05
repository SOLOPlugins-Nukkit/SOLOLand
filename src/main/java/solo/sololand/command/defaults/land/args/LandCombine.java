package solo.sololand.command.defaults.land.args;


import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import solo.sololand.command.SubCommand;
import solo.sololand.land.Land;
import solo.sololand.land.Room;
import solo.sololand.world.World;
import solo.solobasepackage.util.Message;
import solo.sololand.queue.Queue;
import solo.solobasepackage.util.Economy;

public class LandCombine extends SubCommand{

	public LandCombine(){
		super("합치기", "다른 땅과 합쳐 크기를 확장합니다.");
		this.setPermission("sololand.command.land.combine");
		this.addCommandParameters(new CommandParameter[]{
			new CommandParameter("땅 번호", CommandParameter.ARG_TYPE_INT, false)
		});
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);
		Land land;
		Land targetLand;
		Land afterLand;

		if(!player.isOp() && !world.isAllowCombineLand()){
			Message.alert(player, "해당 월드에서 땅을 합칠 수 없습니다.");
			return true;
		}
		
		StringBuilder sb;
		boolean f;

		if(Queue.get(player) == Queue.LAND_COMBINE){
			land = Queue.getLand(player);
			if(!player.isOp() && !land.isOwner(player)){
				Message.alert(player, "땅 주인이 아니므로 땅을 합칠 수 없습니다.");
				return true;
			}
			afterLand = Queue.getTemporaryLand(player);
			
		}else if(Queue.get(player) == Queue.NULL){
			land = world.getLand(player);
			if(land == null){
				Message.alert(player, "현재 위치에서 땅을 찾을 수 없습니다.");
				return true;
			}
			if(!player.isOp() && !land.isOwner(player)){
				Message.alert(player, "땅 주인이 아니므로 땅을 합칠 수 없습니다.");
				return true;
			}
			int num;
			try{
				num = Integer.parseInt(args[0]);
			}catch(Exception e){
				return false;
			}
			targetLand = world.getLand(num);
			if(targetLand == null){
				Message.alert(player, "해당 번호의 땅은 존재하지 않습니다.");
				return true;
			}
			if(!player.isOp() && !targetLand.isOwner(player)){
				Message.alert(player, Integer.toString(targetLand.getNumber()) + "번 땅 주인이 아니므로 땅을 합칠 수 없습니다.");
				return true;
			}
			if(land.getNumber() == targetLand.getNumber()){
				Message.alert(player, "같은 땅끼리 서로 합칠 수 없습니다.");
				return true;
			}
			afterLand = land.clone();
			afterLand.expand(targetLand);
			
			Message.normal(player, "합칠 시 땅 크기 : " + afterLand.xLength() + "x" + afterLand.zLength() + " (" + afterLand.size() + "블럭)");
			if(
				world.getMinLandLength() > afterLand.xLength() ||
				world.getMinLandLength() > afterLand.zLength()
			){
				Message.alert(player, "땅의 한변 길이는 최소 " + Integer.toString(world.getMinLandLength()) + "블럭 이어야 합니다. 땅 생성을 취소합니다.");
				Queue.clean(player);
				return true;
			}
			if(
				world.getMaxLandLength() < afterLand.xLength() ||
				world.getMaxLandLength() < afterLand.zLength()
			){
				Message.alert(player, "땅의 한변 길이는 " + Integer.toString(world.getMaxLandLength()) + "블럭을 넘을 수 없습니다. 땅 합치기를 취소합니다.");
				Queue.clean(player);
				return true;
			}
		}else{
			Message.alert(player, "현재 다른 작업이 진행중입니다. /땅 취소 명령어를 입력한 뒤 다시 시도해주세요.");
			return true;
		}
		
		ArrayList<Land> overlapList = world.getOverlap(afterLand);
		ArrayList<Land> cantCombine = new ArrayList<Land>();
		ArrayList<Land> notOwned = new ArrayList<Land>();
		ArrayList<Room> overlapRoomList = new ArrayList<Room>();
		sb = new StringBuilder();
		f = true;
		for(Land overlap : overlapList){
			//if overlap land is not your land
			if(! overlap.isOwner(player)){
				notOwned.add(overlap);
			}
			if(! afterLand.isInside(overlap)){
				cantCombine.add(overlap);
			}
			if(overlap.hasRoom()){
				overlapRoomList.addAll(overlap.getRooms().values());
			}
			if(f){
				f = false;
			}else{
				sb.append(", ");
			}
			sb.append(Integer.toString(overlap.getNumber()) + "번");
		}
		Message.normal(player, "총 " + Integer.toString(overlapList.size()) + "개의 겹치는 땅이 발견되었습니다 : " + sb.toString());

		boolean canCombine = true;
				
		if(! player.isOp() && notOwned.size() > 0){
			canCombine = false;
			sb = new StringBuilder();
			f = true;
			for(Land not : notOwned){
				if(f){
					f = false;
				}else{
					sb.append(", ");
				}
				sb.append(Integer.toString(not.getNumber()) + "번 (" + not.getOwner() + ")");
			}
			Message.alert(player, "겹치는 땅 중에 소유하지 않은 땅이 있어, 땅을 합칠 수 없습니다 : " + sb.toString());
		}
		
		if(cantCombine.size() > 0){
			canCombine = false;
			sb = new StringBuilder();
			f = true;
			for(Land not : cantCombine){
				if(f){
					f = false;
				}else{
					sb.append(", ");
				}
				sb.append(Integer.toString(not.getNumber()) + "번 ");
			}
			Message.alert(player, "완전히 겹쳐져 있지 않은 땅은 합칠 수 없습니다 : " + sb.toString());
		}
		
		if(! canCombine){
			return true;
		}
		
		if(Queue.get(player) == Queue.NULL && overlapRoomList.size() > 0){
			sb = new StringBuilder();
			f = true;
			for(Room room : overlapRoomList){
				if(f){
					f = false;
				}else{
					sb.append(", ");
				}
				sb.append(room.getAddress() + "번");
			}
			Message.normal(player, "땅을 합칠 시 " + sb.toString() + "방도 함께 합쳐집니다.");
		}
		
		if(! player.isOp()){
			double myMoney = Economy.getMoney(player);
			double price = world.getPricePerBlock() * (afterLand.size() - land.size());
			Message.normal(player, "현재 소지한 돈 : " + Double.toString(myMoney) + "원, 땅을 합칠 시 필요한 돈 : " + Double.toString(price) + "원");
			if(myMoney < price){
				Message.alert(player, "돈이 부족하여 땅을 합칠 수 없습니다.");
				return true;
			}
			if(Queue.get(player) == Queue.LAND_COMBINE){
				Economy.reduceMoney(player, price);
			}
		}
		if(Queue.get(player) == Queue.NULL){
			Queue.set(player, Queue.LAND_COMBINE);
			Queue.setLand(player, land);
			Queue.setTemporaryLand(player, afterLand);
			Message.normal(player, "땅 합치기를 진행하려면 /땅 합치기 명령어를 한번 더 입력해주세요.");
			return true;
		}
		
		for(Room room : overlapRoomList){
			room.land = afterLand;
			room.number = afterLand.getNextRoomNumber();
			afterLand.setRoom(room);
		}
		for(Land overlap : overlapList){
			world.removeLand(overlap);
		}
		world.setLand(land.getNumber(), afterLand);
		Message.normal(player, "성공적으로 땅를 합쳤습니다. 확장된 크기 : " + Integer.toString(afterLand.size() - land.size()) + "블럭");
		if(overlapRoomList.size() > 0){
			Message.normal(player, "합쳐진 방 갯수 : " + Integer.toString(overlapRoomList.size()) + "개");
		}
		Queue.clean(player);
		return true;
	}
}