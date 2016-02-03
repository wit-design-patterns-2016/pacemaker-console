package command;

import models.User;
import parsers.Parser;
import controllers.PacemakerAPI;

public class DeleteUserCommand extends Command
{
  private User user;

  public DeleteUserCommand(PacemakerAPI pacemaker, Parser parser)
  {
    super(pacemaker, parser);
  }

  public void doCommand(Object[] parameters) throws Exception
  {
    this.user = pacemaker.getUser((Long)parameters[0]);
    pacemaker.deleteUser((Long)parameters[0]);
  }
}