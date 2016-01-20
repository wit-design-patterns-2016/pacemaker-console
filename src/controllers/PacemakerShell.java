package controllers;

import utils.JSONSerializer;
import utils.Serializer;
import utils.XMLSerializer;
import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import parsers.AsciiParser;
import response.Response;

public class PacemakerShell 
{
  private PacemakerAPI       paceApi;
  private PacemakerService   pacemaker;
  private String datastore   = "testdatastore";
  
  Serializer xmlSerializer   = new XMLSerializer(datastore);
  Serializer jsonSerializer  = new JSONSerializer(datastore);
  
  public static void println(Response response)
  {
    System.out.println(response.toString());
  }
  
  public static void println(String s)
  {
    System.out.println(s);
  }
  
  public PacemakerShell() 
  {
    paceApi = new PacemakerAPI();
    pacemaker = new PacemakerService (paceApi, new AsciiParser());
  }

  @Command(description="List all users details")
  public void listUsers () throws Exception
  {
    System.out.println(pacemaker.getUsers());
  }
  
  @Command(description="Create a new User")
  public void createUser (@Param(name="first name") String firstname, @Param(name="last name") String lastname, 
                       @Param(name="email")      String email,     @Param(name="password")  String password) throws Exception 
  {
    println (pacemaker.createUser(firstname, lastname, email, password));
  }
  
  @Command(description="List a users details")
  public void listUser (@Param(name="email") String email) throws Exception
  {
  	println (pacemaker.getUser(email));
  }  
  
  @Command(description="List a users details")
  public void listUser (@Param(name="id")  Long id) throws Exception
  {
  	println (pacemaker.getUser(id));
  }
  
  @Command(description="List a users activities")
  public void listActivities (@Param(name="user id") Long id) throws Exception
  {
  	println (pacemaker.getActivities(id));
  }  

  @Command(description="Delete a User")
  public void deleteUser (@Param(name="id") Long id)
  {
    println (pacemaker.deleteUser(id));
  }
  
  @Command(description="Add an activity")
  public void addActivity (@Param(name="user-id")  Long   id,       @Param(name="type") String type, 
                        @Param(name="location") String location, @Param(name="distance") double distance,
                        @Param(name="datetime") String dateStr,  @Param(name="duration") String durationStr
                        )
  {
  	try
  	{
      println (pacemaker.createActivity(id, type, location, distance, dateStr, durationStr))	;	
  	}
  	catch (IllegalArgumentException e)
  	{
  		println ("Date or Duration format error: " + e.getMessage());
  	}
  }
  
  @Command(description="Add Location to an activity")
  public void addLocation (@Param(name="activity-id")  Long  id,   
                        @Param(name="latitude")     float latitude, @Param(name="longitude") float longitude)
  {
    println (pacemaker.addLocation(id, latitude, longitude));
  }
  
  @Command(description="Load activities persistent store")
  public void load () throws Exception
  {
    paceApi.load();
  }
  
  @Command(description="Store activities persistent store")
  public void store () throws Exception
  {
    paceApi.store();
  }
  
  @Command(description="Set file format")
  public void changeFileFormat (@Param(name="file format: xml, json") String fileFormat)
  {
    if (fileFormat.equals("xml"))
      paceApi.serializer = xmlSerializer;
    else if (fileFormat.equals("json"))
      paceApi.serializer = jsonSerializer; 
  }
  
  public static void main(String[] args) throws Exception
  {
    PacemakerShell main = new PacemakerShell();

    Shell shell = ShellFactory.createConsoleShell("pm", "Welcome to pacemaker-console - ?help for instructions", main);
    shell.commandLoop();
    
    main.paceApi.store();
  } 
}