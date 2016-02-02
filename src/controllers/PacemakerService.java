package controllers;

import models.Activity;
import models.User;
import parsers.Parser;
import response.NotFound;
import response.Ok;
import response.Response;

public class PacemakerService
{  
  PacemakerAPI pacemaker;
  Parser       parser;
  
  public PacemakerService(PacemakerAPI pacemaker, Parser parser)
  {
    this.pacemaker = pacemaker;
    this.parser = parser;
  }
  
  public Response createUser(String firstname, String lastname, String email, String password) throws Exception 
  {
    Long id = pacemaker.createUser(firstname, lastname, email, password);
    return new Ok(parser.renderUser(pacemaker.getUser(id)));
  }
  
  public Response  getUser(Long id) throws Exception 
  {
  	User user = pacemaker.getUser(id);
  	return user == null ? new NotFound("") : new Ok(parser.renderUser(user));
  }
 
  public Response  getUser(String email) throws Exception 
  {
  	User user = pacemaker.getUser(email);
    return user == null ? new NotFound("") : new Ok(parser.renderUser(user));
  }
  
  public Response getUsers() throws Exception
  {
  	return new Ok(parser.renderUsers(pacemaker.users));
  }

  public Response deleteUser(Long id)
  {
   	User user = pacemaker.getUser(id);
   	pacemaker.deleteUser(user.id);
  	return null != user ? new Ok("") : new NotFound("");
  }
  
  public Response createActivity(Long id, String type, String location, double distance, String dateStr, String durationStr)
  {
  	if (null != pacemaker.getUser(id))
  	{
  	  pacemaker.createActivity(id, type, location, distance, dateStr, durationStr);
  	  return new Ok("");
  	 }
  	 else 
  	   return new NotFound("");
  }
 
  public Response getActivities(Long id) throws Exception
  {
  	User user = pacemaker.getUser(id);
  	return user != null? new Ok(parser.renderActivities(user.activities.values())) : new NotFound("");
  }
  
  public Response addLocation (Long id, float latitude, float longitude)
  {
  	Activity activity = pacemaker.getActivity(id);
  	if (null != activity)
  	{
      pacemaker.addLocation(id, latitude, longitude);
      return new Ok("");
  	}
  	else 
  	  return new NotFound("");
  }
}