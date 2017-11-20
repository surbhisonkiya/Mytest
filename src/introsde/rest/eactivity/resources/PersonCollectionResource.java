package introsde.rest.eactivity.resources;

import java.io.IOException;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import introsde.rest.eactivity.model.Person;


//Will map the resource to the URL /person
@Stateless
@LocalBean
@Path("/person")
public class PersonCollectionResource {

	// Allows to insert contextual objects into the class,
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	//Request#1
	@GET
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Person> getPersonsList() {
		System.out.println("--> PersonCollectionResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		List<Person> people =Person.getAll();
		return people;
	}
	
	//Request#4
	@POST  
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) // will be called when content-type header set to xml
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Person newPerson(Person person) throws IOException {
		System.out.println("--> PersonCollectionResource request...");
		System.out.println("--> URI = "+uriInfo.getPath());
		System.out.println("--> request = "+request.getMethod());
		System.out.println("Creating new person...");
		Person.savePerson(person);
		return person;
	}
	
	@Path("{personId}")
	public PersonResource getPerson(@PathParam("personId") Integer id) {
		System.out.println("--> PersonResource request...");
		System.out.println("--> URI = "+uriInfo);
		System.out.println("--> request = "+request);
		System.out.println("--> personId = "+id);
		return new PersonResource(uriInfo, request, id);
	}

}
