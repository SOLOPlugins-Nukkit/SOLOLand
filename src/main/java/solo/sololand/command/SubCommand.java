package solo.sololand.command;

import java.util.ArrayList;

import cn.nukkit.command.data.CommandParameter;

public abstract class SubCommand extends BaseCommand{
	
	public ArrayList<CommandParameter[]> commandParameters = new ArrayList<CommandParameter[]>();
	
	public SubCommand(String name){
		this(name, null);
	}
	public SubCommand(String name, String description){
		this(name, description, null);
	}
	public SubCommand(String name, String description, String permission){
		super(name, description, permission);
	}
	
	public void addCommandParameters(CommandParameter[] commandParams){
		this.commandParameters.add(commandParams);
	}
	
	public ArrayList<CommandParameter[]> getCommandParameters(){
		return this.commandParameters;
	}
	
	public void clearCommandParameters(){
		this.commandParameters.clear();
	}
}