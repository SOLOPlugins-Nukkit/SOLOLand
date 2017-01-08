package solo.sololand.command.defaults.land.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.land.Land;
import solo.sololand.land.Room;
import solo.sololand.world.World;
import solo.sololand.external.Message;

import java.util.List;
import java.util.ArrayList;

public class LandInfo extends SubCommand{

	public LandInfo(){
		super("정보", "현재 위치에 있는 땅의 정보를 확인합니다.");
		this.setPermission("sololand.command.land.info");
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
		
		ArrayList<String> information = new ArrayList<String>();
		if(land.isSail()){
			information.add("이 땅은 현재 판매중입니다.");
			if(!land.isOwner("")){
				information.add("땅 판매자 : " + land.getOwner());
			}
			information.add("땅 가격 : " + Double.toString(land.getPrice()) + "원");
		}else if(!land.isOwner("")){
			information.add("땅 주인 : " + land.getOwner());
		}
		information.add("땅 크기 : " + Integer.toString(land.xLength()) + "×" + Integer.toString(land.zLength()));
		
		List<String> members = land.getMembers();
		if(members.size() > 1){
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < members.size(); i++){
				if(land.isOwner(members.get(i))){
					continue;
				}
				sb.append(members.get(i));
				if(i < members.size()-1){
					sb.append(", ");
				}
			}
			information.add("땅 공유 목록 : " + sb.toString());
		}
		if(!land.getWelcomeMessage().equals("")){
			information.add("땅 환영말 : " + land.getWelcomeMessage());
		}
		if(land.hasRoom()){
			StringBuilder sb = new StringBuilder();
			boolean f = true;
			for(Room room : land.getRooms().values()){
				if(f){
					f = false;
				}else{
					sb.append(", ");
				}
				sb.append(Integer.toString(room.getNumber()) + "번");
			}
			information.add("방 목록 : " + sb.toString());
		}
		Message.info(player, Integer.toString(land.getNumber()) + "번 땅 정보", information);
		return true;
	}
}