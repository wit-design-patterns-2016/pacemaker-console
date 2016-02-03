package command;

import java.util.HashMap;
import java.util.Map;

public class CommandDispatcher
{
  private Map<String, Command> commands;

  public CommandDispatcher()
  {
    commands = new HashMap<String, Command>();
  }

  public void addCommand(String commandName, Command command)
  {
    commands.put(commandName, command);
  }

  public boolean dispatchCommand(String commandName, Object [] parameters) throws Exception
  {
    boolean dispatched = false;
    Command command = commands.get(commandName);

    if (command != null)
    { 
      dispatched = true;
      command.doCommand(parameters);
    }
    return dispatched;
  }
}