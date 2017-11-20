package introsde.rest.eactivity.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;


import introsde.rest.eactivity.model.Activity;
import introsde.rest.eactivity.model.ActivityType;
import introsde.rest.eactivity.model.Person;





@Stateless
@LocalBean
@Path("/activity_types")
public class ActivityResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	//Request#6
	@GET
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public ActivityType[] getActivityTypeList() {
		System.out.println("--> ActivityResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		ActivityType[] activities = ActivityType.values();
		
		return activities;
	}
	
	
}