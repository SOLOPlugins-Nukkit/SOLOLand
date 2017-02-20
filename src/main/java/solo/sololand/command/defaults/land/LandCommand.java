package solo.sololand.command.defaults.land;

import solo.sololand.command.defaults.land.args.*;
import solo.sololand.command.MainCommand;

public class LandCommand extends MainCommand{

	public LandCommand(){
		super("땅", "땅을 관리하는 명령어 입니다.");

		this.setPermission("sololand.command.land", true);

		//test code
		this.registerSubCommand(new LandTest());
		
		this.registerSubCommand(new LandCreate());
		this.registerSubCommand(new LandInfo());
		this.registerSubCommand(new LandMove());
		this.registerSubCommand(new LandRemove());
		this.registerSubCommand(new LandList());
		this.registerSubCommand(new LandShare());
		this.registerSubCommand(new LandLeave());
		this.registerSubCommand(new LandWelcomeMessage());
		this.registerSubCommand(new LandWelcomeParticle());
		this.registerSubCommand(new LandSetSpawn());
		this.registerSubCommand(new LandAccess());
		this.registerSubCommand(new LandAllowFight());
		this.registerSubCommand(new LandAllowPickUpItem());
		this.registerSubCommand(new LandAllowDoor());
		this.registerSubCommand(new LandAllowChest());
		//this.registerSubCommand(new LandAllowCraft());
		this.registerSubCommand(new LandBuy());
		this.registerSubCommand(new LandSell());
		this.registerSubCommand(new LandSellList());
		this.registerSubCommand(new LandCancelSell());
		this.registerSubCommand(new LandCancelShare());
		this.registerSubCommand(new LandGive());
		this.registerSubCommand(new LandExpand());
		this.registerSubCommand(new LandReduce());
		this.registerSubCommand(new LandCombine());
		this.registerSubCommand(new LandShareList());
		this.registerSubCommand(new LandVisitor());
		this.registerSubCommand(new LandCancel());
	}

}