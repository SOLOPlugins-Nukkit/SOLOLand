package solo.sololand.command.defaults.custom;

import solo.sololand.world.*;
import solo.sololand.command.defaults.custom.args.*;
import solo.sololand.command.MainCommand;

public class CustomWorldCommand extends MainCommand{

	public World world;

	public CustomWorldCommand(World world){
		super(world.getCustomName(), world.getCustomName() + " 월드 명령어");

		this.setPermission("sololand.command.custom", true);

		if(world instanceof Island){
			this.registerSubCommand(new CustomBuy(world));
		}
		this.registerSubCommand(new CustomList(world));
		this.registerSubCommand(new CustomMove(world));
		this.registerSubCommand(new CustomDefaultLandPrice(world));
		
		this.world = world;
	}

	public World getWorld(){
		return this.world;
	}

}