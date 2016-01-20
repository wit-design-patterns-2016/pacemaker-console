package controllers;

import models.Activity;
import models.User;
import parsers.Parser;
import response.NotFound;
import response.Ok;
import response.Response;

class PacemakerService
{  
  PacemakerAPI pacemaker;
  Parser       parser;
  
  public PacemakerService(PacemakerAPI pacemaker, Parser parser)
  {
    this.pacemaker = pacemaker;
    this.parser = parser;
  }
  
  Response createUser(String firstname, String lastname, String email, String password) throws Exception 
  {
    Long id = pacemaker.createUser(firstname, lastname, email, password);
    return new Ok(parser.renderUser(pacemaker.getUser(id)));
  }
  
  Response  getUser(Long id) throws Exception 
  {
  	User user = pacemaker.getUser(id);
  	return user == null ? new NotFound("") : new Ok(parser.renderUser(user));
  }
 
  Response  getUser(String email) throws Exception 
  {
  	User user = pacemaker.getUser(email);
    return user == null ? new NotFound("") : new Ok(parser.renderUser(user));
  }
  
  Response getUsers() throws Exception
  {
  	return new Ok(parser.renderUsers(pacemaker.users));
  }

  Response deleteUser(Long id)
  {
   	User user = pacemaker.getUser(id);
   	pacemaker.deleteUser(user.id);
  	return null != user ? new Ok("") : new NotFound("");
  }
  
  Response createActivity(Long id, String type, String location, double distance, String dateStr, String durationStr)
  {
  	if (null != pacemaker.getUser(id))
  	{
  	  pacemaker.createActivity(id, type, location, distance, dateStr, durationStr);
  	  return new Ok("");
  	 }
  	 else 
  	   return new NotFound("");
  }
 
  Response getActivities(Long id) throws Exception
  {
  	User user = pacemaker.getUser(id);
  	return user != null? new Ok(parser.renderActivities(user.activities.values())) : new NotFound("");
  }
  
  Response addLocation (Long id, float latitude, float longitude)
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