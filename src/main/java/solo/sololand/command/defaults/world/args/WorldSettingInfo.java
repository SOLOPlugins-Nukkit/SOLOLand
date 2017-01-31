package solo.sololand.command.defaults.world.args;

import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.external.Message;

public class WorldSettingInfo extends SubCommand{

	public WorldSettingInfo(){
		super("설정정보", "월드의 설정값 정보를 봅니다.");
		this.setPermission("sololand.command.world.settinginfo");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args){
		Player player = (Player) sender;
		World world = World.get(player);
		ArrayList<String> information = new ArrayList<String>();
		
		StringBuilder sb = new StringBuilder();
		sb.append(world.isProtected() ? "§a(보호)  " : "§7(보호)  ");
		sb.append(world.isInvenSave() ? "§a(인벤세이브)  " : "§7(인벤세이브)  ");
		sb.append(world.isAllowFight() ? "§a(PVP)  " : "§7(PVP)  ");
		sb.append(world.isAllowExplosion() ? "§a(TNT 블럭 파괴)  " : "§7(TNT 블럭 파괴)  ");
		sb.append(world.isAllowDoor() ? "§a(문)  " : "§7(문)  ");
		sb.append(world.isAllowChest() ? "§a(상자)  " : "§7(상자)  ");
		//sb.append(world.isAllowCraft() ? "§a(조합대)" : "§7(조합대)");
		information.add("월드 설정 : " + sb.toString());

		sb = new StringBuilder();
		sb.append(world.isAllowCreateLand() ? "§a(땅 생성 허용)  " : "§7(땅 생성 허용)  ");
		sb.append(world.isAllowResizeLand() ? "§a(땅 확장/축소 허용)  " : "§7(땅 확장/축소 허용)  ");
		sb.append(world.isAllowCombineLand() ? "§a(땅 합치기 허용)  " : "§7(땅 합치기 허용)");
		sb.append("§a(기본 땅 가격 : " + Double.toString(world.getDefaultLandPrice()) + ")  ");
		sb.append("§a(블럭당 가격 : " + Double.toString(world.getPricePerBlock()) + ")  ");
		sb.append("§a(최대 땅 갯수 : " + Integer.toString(world.getMaxLandCount()) + ")  ");
		sb.append("§a(땅 한변당 최소 길이 : " + Integer.toString(world.getMinLandLength()) + ")  ");
		sb.append("§a(땅 한변당 최대 길이 : " + Integer.toString(world.getMaxLandLength()) + ")");
		information.add("땅 설정 : " + sb.toString());

		sb = new StringBuilder();
		sb.append(world.isAllowCreateRoom() ? "§a" : "§7" + "(방 생성 허용)  ");
		sb.append("§a(방 생성 가격 : " + Double.toString(world.getRoomCreatePrice()) + ")  ");
		sb.append("§a(땅 하나당 방 최대 갯수 : " + Integer.toString(world.getMaxRoomCreateCount()) + ")  ");
		sb.append("§a(방 한변당 최소 길이 : " + Integer.toString(world.getMinRoomLength()) + ")  ");
		sb.append("§a(방 한변당 최대 길이 : " + Integer.toString(world.getMaxRoomLength()) + ")");
		information.add("방 설정 : " + sb.toString());
		
		Message.info(player, world.getCustomName() + " 월드 설정 정보", information);
		return true;
	}
}