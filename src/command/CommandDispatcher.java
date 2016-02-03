package command;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class CommandDispatcher
{
  private Map<String, Command> commands;
  private Stack<Command> undoBuffer;
  private Stack<Command> redoBuffer;

  public CommandDispatcher()
  {
    undoBuffer = new Stack<Command>();
    redoBuffer = new Stack<Command>();
    commands = new HashMap<String, Command>();

    commands.put("undo", new UndoCommand(undoBuffer, redoBuffer));
    commands.put("redo", new RedoCommand(undoBuffer, redoBuffer));
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
      if ((command instanceof CreateUserCommand) || (command instanceof DeleteUserCommand))
      {
        undoBuffer.push(command);
      }
    }
    return dispatched;
  }
}