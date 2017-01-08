package solo.sololand.command.defaults.land.args;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import solo.sololand.command.SubCommand;
import solo.sololand.world.World;
import solo.sololand.land.Land;
import solo.sololand.queue.Queue;
import solo.sololand.external.Message;

public class LandSell extends SubCommand{

	public LandSell(){
		super("판매", "땅을 매물에 등록합니다.");
		this.setPermission("sololand.command.land.sell");
		this.addCommandParameters(new CommandParameter[]{
			new CommandParameter("가격", CommandParameter.ARG_TYPE_INT, false)
		});
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
		if(!player.isOp() && !land.isOwner(player)){
			Message.alert(player, "땅 주인이 아니므로 땅을 판매할 수 없습니다.");
			return true;
		}
		if(land.isSail()){
			Message.alert(player, "땅이 이미 판매중입니다.");
			return true;
		}
		double sell;
		switch(Queue.get(player)){
			case Queue.LAND_SELL:
				Land queueLand = Queue.getLand(player);
				if(queueLand == land){
					sell = Queue.getDouble(player);
					Message.normal(player, "땅을 " + Double.toString(sell) + "원으로 매물에 등록하였습니다.");
					Message.normal(player, "땅 판매를 중단하고 싶으시다면 /땅 판매취소 명령어를 입력해주세요.");
					land.setPrice(sell);
					land.setSail(true);
					Queue.clean(player);
				}else{
					Message.alert(sender, "땅 판매중에 오류가 발생하였습니다. 다시 시도해주세요.");
					Queue.clean(player);
				}
				break;
				
			case Queue.NULL:
				if(args.length < 1){
					return false;
				}
				try{
					sell = Double.parseDouble(args[0]);
				}catch(Exception e){
					return false;
				}
				Message.normal(player, "땅을 정말로 매물에 등록하시겠습니까? 땅이 매물에 등록되어있는 동안엔 땅을 수정할 수 없습니다.");
				Message.normal(player, Double.toString(sell) + "원으로 매물에 등록하려면 /땅 판매 명령어를 다시 한번 입력해주세요.");
				Queue.set(player, Queue.LAND_SELL);
				Queue.setLand(player, land);
				Queue.setDouble(player, sell);
				break;
		}
		return true;
	}
}