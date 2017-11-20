package introsde.rest.eactivity.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import introsde.rest.eactivity.DatabaseInit;
import introsde.rest.eactivity.model.Person;

@Stateless
@LocalBean
@Path("/database_init")
public class DbInitResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	//Request#1
	@GET
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Person> getPersonsList() {
		
		DatabaseInit dbInit = new DatabaseInit();
		List<Person> people = dbInit.init();
		System.out.println("Database initialialized with 5 people..");
		return people;
	}
	
	

}
