package solo.sololand.command.defaults.world;

import solo.sololand.command.defaults.world.args.*;
import solo.sololand.command.MainCommand;

public class WorldCommand extends MainCommand{

	public WorldCommand(){
		super("월드", "월드를 관리하는 명령어 입니다.");

		this.setInGameOnly(false);
		this.setPermission("sololand.command.world", false);

		this.registerSubCommand(new WorldCreate());
		this.registerSubCommand(new WorldList());
		this.registerSubCommand(new WorldInfo());
		this.registerSubCommand(new WorldSettingInfo());
		this.registerSubCommand(new WorldUserInfo());
		this.registerSubCommand(new WorldLandInfo());
		/*this.registerSubCommand(new WorldSetCustomName());*/
		this.registerSubCommand(new WorldMove());
		this.registerSubCommand(new WorldSetSpawn());
		this.registerSubCommand(new WorldProtect());
		/*this.registerSubCommand(new WorldAllowBurn());*/
		this.registerSubCommand(new WorldAllowExplosion());
		this.registerSubCommand(new WorldAllowFight());
		this.registerSubCommand(new WorldInvenSave());
		this.registerSubCommand(new WorldAllowCreateLand());
		this.registerSubCommand(new WorldAllowResizeLand());
		this.registerSubCommand(new WorldDefaultLandPrice());
		this.registerSubCommand(new WorldMaxLandCount());
		this.registerSubCommand(new WorldMaxLandLength());
		this.registerSubCommand(new WorldMinLandLength());
		this.registerSubCommand(new WorldPricePerBlock());
		this.registerSubCommand(new WorldEnableCommand());
	}

}