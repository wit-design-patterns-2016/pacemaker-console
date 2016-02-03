package command;

import parsers.Parser;
import controllers.PacemakerAPI;

public abstract class Command
{
  protected PacemakerAPI pacemaker;
  protected Parser       parser;

  public Command()
  {}

  public Command(PacemakerAPI pacemaker, Parser parser)
  {
    this.pacemaker = pacemaker;
    this.parser    = parser;
  }

  public abstract void doCommand(Object[] parameters)  throws Exception;
}
 