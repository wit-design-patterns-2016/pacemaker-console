package command;

import models.User;
import parsers.Parser;
import controllers.PacemakerAPI;

public class CreateUserCommand extends Command
{
  User user;

  public CreateUserCommand(PacemakerAPI pacemaker, Parser parser)
  {
    super(pacemaker, parser);
  }

  public void doCommand(Object[] parameters) throws Exception
  {
    Long id = pacemaker.createUser((String)parameters[0], (String)parameters[1], (String)parameters[2], (String)parameters[3]);
    System.out.println(parser.renderUser(pacemaker.getUser(id)));
    this.user = pacemaker.getUser(id);
  }
}