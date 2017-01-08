package solo.sololand.command.defaults.room;

import solo.sololand.command.defaults.room.args.*;
import solo.sololand.command.MainCommand;

public class RoomCommand extends MainCommand{

	public RoomCommand(){
		super("방", "방을 관리하는 명령어 입니다.");

		this.setPermission("sololand.command.room", true);

		this.registerSubCommand(new RoomCreate());
		this.registerSubCommand(new RoomInfo());
		this.registerSubCommand(new RoomMove());
		this.registerSubCommand(new RoomRemove());
		this.registerSubCommand(new RoomList());
		this.registerSubCommand(new RoomShare());
		this.registerSubCommand(new RoomLeave());
		this.registerSubCommand(new RoomWelcomeMessage());
		this.registerSubCommand(new RoomSetSpawn());
		this.registerSubCommand(new RoomBuy());
		this.registerSubCommand(new RoomSell());
		this.registerSubCommand(new RoomSellList());
		this.registerSubCommand(new RoomCancelSell());
		this.registerSubCommand(new RoomCancelShare());
		this.registerSubCommand(new RoomGive());
		this.registerSubCommand(new RoomShareList());
		this.registerSubCommand(new RoomKick());
		this.registerSubCommand(new RoomCancel());
	}

}