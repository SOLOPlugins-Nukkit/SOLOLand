package solo.sololand.command;

import cn.nukkit.Server;
import cn.nukkit.permission.Permission;
import cn.nukkit.command.CommandSender;

public abstract class BaseCommand{

	public String name;
	public boolean inGameOnly = true;
	public String description = null;
	public String permission = null;
	

	public BaseCommand(String name){
		this(name, null);
	}
	public BaseCommand(String name, String description){
		this(name, description, null);
	}
	public BaseCommand(String name, String description, String permission){
		this.name = name;
		if(description != null){
			this.description = description;
		}
		if(permission != null){
			this.setPermission(description);
		}
	}

	public boolean isInGameOnly(){
		return this.inGameOnly;
	}
	public void setInGameOnly(boolean inGameOnly){
		this.inGameOnly = inGameOnly;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getDescription(){
		return this.description;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public String getPermission(){
		if(this.permission != null){
			return this.permission;
		}
		return "default";
	}
	public void setPermission(String permission){
		this.setPermission(permission, true);
	}
	public void setPermission(String permission, boolean defualtValue){
		this.permission = permission;
		String defualt = Permission.DEFAULT_TRUE;
		if(!defualtValue){
			defualt = Permission.DEFAULT_OP;
		}
		Server.getInstance().getPluginManager().addPermission(new Permission(permission, "SOLOLand command permission", defualt));
	}

	public abstract boolean execute(CommandSender sender, String[] args);

}