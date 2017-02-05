package solo.sololand.command.defaults.room.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.land.Land;
import solo.sololand.land.Room;
import solo.sololand.world.World;
import solo.solobasepackage.util.Message;

import java.util.List;
import java.util.ArrayList;

public class RoomInfo extends SubCommand{

	public RoomInfo(){
		super("정보", "현재 위치에 있는 방의 정보를 확인합니다.");
		this.setPermission("sololand.command.room.info");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);
		Land land = world.getLand(player);

		if(land == null){
			Message.alert(player, "현재 위치에서 땅을 찾을 수 없습니다.");
			return true;
		}
		Room room = land.getRoom(player);
		if(room == null){
			Message.alert(player, "현재 위치에서 방을 찾을 수 없습니다.");
			return true;
		}
		
		ArrayList<String> information = new ArrayList<String>();
		information.add("땅 번호 : " + Integer.toString(land.getNumber()) + "번, 방 번호 : " + Integer.toString(room.getNumber()) + "번 (주소 : " + room.getAddress() + ")");
		if(room.isSail){
			information.add("이 방은 현재 판매중입니다.");
			information.add("방 판매자 : " + land.getOwner());
			information.add("방 가격 : " + Double.toString(room.getPrice()) + "원");
		}else if(!room.isOwner("")){
			information.add("방 주인 : " + room.getOwner());
		}
		information.add("방 크기 : " + Integer.toString(room.xLength()) + "×" + Integer.toString(room.zLength()) + ", 높이 : " + Integer.toString(room.yLength()));
		
		List<String> members = room.getMembers();
		if(members.size() > 1){
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < members.size(); i++){
				if(room.isOwner(members.get(i))){
					continue;
				}
				sb.append(members.get(i));
				if(i < members.size()-1){
					sb.append(", ");
				}
			}
			information.add("방 공유 목록 : " + sb.toString());
		}
		if(!room.getWelcomeMessage().equals("")){
			information.add("방 환영말 : " + room.getWelcomeMessage());
		}
		Message.info(player, room.getAddress() + "번 방 정보", information);
		return true;
	}
}