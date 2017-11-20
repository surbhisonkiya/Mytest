
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.glassfish.jersey.client.ClientConfig;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;





public class ClientImplementationXML {
    

	public static WebTarget config() {
    	ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);
        WebTarget service = client.target(getBaseURI());
        System.out.println("Calling " + getBaseURI() ); //Step 1.
        return service;
    }
	
	public static Document loadXMLFromString(String xml) throws Exception
	{
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    InputSource is = new InputSource(new StringReader(xml));
	    return builder.parse(is);
	}
	
	public static String format(String unformattedXml) throws Exception {
        try {
            final Document document = loadXMLFromString(unformattedXml);

            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(2);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);

            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	
	public static void main(String[] args) throws Exception {
        WebTarget service = config();
        String result;
        int count;
        int first_person_id;
        int last_person_id;
		
        
        // Step 3.1.
        System.out.println("\n**********3.1**********");
        System.out.println("****APPLICATION/XML****");
        Response resp = service.path("person").request().accept(MediaType.APPLICATION_XML).header("Content-type","application/xml").get();
        String response = resp.readEntity(String.class);
        Document doc1 = loadXMLFromString(response);
        NodeList peopleIds = doc1.getElementsByTagName("idPerson");
        count = peopleIds.getLength();
        first_person_id = Integer.parseInt(peopleIds.item(0).getTextContent());
        last_person_id =  Integer.parseInt(peopleIds.item(count-1).getTextContent());
        System.out.println("The number of people in the database is: " + count);
        System.out.println("The id of the first person: " + first_person_id);
        System.out.println("The id of the last person: " + last_person_id);
        
        if(count>4) {
        	result = "OK";
        }else {
        	result = "ERROR";
        }
        System.out.println("\nRequest #1:" + "\n"
        		+ "Header: " + "\n"
        		+ "GET /person/ Accept: APPLICATION/XML Content-Type: APPLICATION/XML" + "\n"
        		+ "=> Result: " + result +  "\n"
        		+ "=> HTTP Status: " + resp.getStatus() + " " + resp.getStatusInfo() + "\n"
        		+ "Body: "  + "\n"
        		+ format(response) + "\n");	
        
       
   
        // Step 3.2.
        System.out.println("\n**********3.2**********");
        System.out.println("****APPLICATION/XML****");
        Response resp2 = service.path("person").path(String.valueOf(first_person_id)).request().accept(MediaType.APPLICATION_XML).header("Content-type","application/xml").get();
        String response2 = resp2.readEntity(String.class);
        Document doc2 = loadXMLFromString(response2);
        NodeList origFirstNames = doc2.getElementsByTagName("firstName");
        String origFirstNameOfFirst = origFirstNames.item(0).getTextContent();
        if (resp2.getStatus()==200 || resp2.getStatus()==202) {
        	result = "OK";
        } else {
        	result ="ERROR";
        }
        System.out.println("\nRequest #2:" + "\n"
        		+ "Header: " + "\n"
        		+ "GET /person/" + first_person_id + " Accept: APPLICATION/XML Content-Type: APPLICATION/XML" + "\n"
        		+ "=> Result: " + result +  "\n"
        		+ "=> HTTP Status: " + resp2.getStatus() + " " + resp2.getStatusInfo() + "\n"
        		+ "Body: " + format(response2) + "\n");	

        // Step 3.3.
		Object entity3 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<person>\n" + 
				"    <preferences>\n" + 
				"        <activitypreference>\n" + 
				"            <description>Japanese cooking class</description>\n" + 
				"            <idActivity>3</idActivity>\n" + 
				"            <name>Cooing</name>\n" + 
				"            <place>Rovereto</place>\n" + 
				"            <startdate>2017-11-11T00:00:00+01:00</startdate>\n" + 
				"            <type>SOCIAL</type>\n" + 
				"        </activitypreference>\n" + 
				"        <activitypreference>\n" + 
				"            <description>Running on the track</description>\n" + 
				"            <idActivity>2</idActivity>\n" + 
				"            <name>Running</name>\n" + 
				"            <place>Trento</place>\n" + 
				"            <startdate>2017-11-11T00:00:00+01:00</startdate>\n" + 
				"            <type>SPORT</type>\n" + 
				"        </activitypreference>\n" + 
				"    </preferences>\n" + 
				"    <birthdate>2017-11-11T00:00:00+01:00</birthdate>\n" + 
				"    <firstName>Marika</firstName>\n" + 
				"    <idPerson>1</idPerson>\n" + 
				"    <lastname>Hermann</lastname>\n" + 
				"</person>";
		
		System.out.println("\n**********3.3**********");
        System.out.println("****APPLICATION/XML****");
        Response resp3 = service.path("person").path(String.valueOf(first_person_id)).request().accept(MediaType.APPLICATION_XML).header("Content-type","application/xml").put(Entity.xml(entity3));
        //String response3 = resp3.readEntity(String.class);
        Response resp3b = service.path("person").path(String.valueOf(first_person_id)).request().accept(MediaType.APPLICATION_XML).header("Content-type","application/xml").get();
        String response3b = resp3b.readEntity(String.class);
        Document doc3 = loadXMLFromString(response3b);
        NodeList firstnames = doc3.getElementsByTagName("firstName");
        String changedFirstName = firstnames.item(0).getTextContent();
        
        if (!origFirstNameOfFirst.equals(changedFirstName)) {
        	result = "OK";
        }else {
        	result = "ERROR";
        }
        
        System.out.println("\nRequest #3:" + "\n"
        		+ "Header: " + "\n"
        		+ "PUT /person/" + first_person_id + " Accept: APPLICATION/XML Content-Type: APPLICATION/XML" + "\n"
        		+ "=> Result: " + result +  "\n"
        		+ "=> HTTP Status: " + resp3.getStatus() + " " + resp3.getStatusInfo() + "\n"
        		+ "Updated person: " + format(response3b));	
        		
        // Step 3.4.
		Object entity4 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
				"<person>\n" + 
				"    <preferences>\n" + 
				"        <activitypreference>\n" + 
				"            <description>Going to a restaurant or a bar with friends.</description>\n" + 
				"            <name>Going out</name>\n" + 
				"            <place>London</place>\n" + 
				"            <startdate>2017-11-11T00:00:00+01:00</startdate>\n" + 
				"            <type>SOCIAL</type>\n" + 
				"        </activitypreference>\n" + 
				"    </preferences>\n" + 
				"    <birthdate>2017-11-11T00:00:00+01:00</birthdate>\n" + 
				"    <firstName>Sherlock</firstName>\n" + 
				"    <lastname>Holmes</lastname>\n" + 
				"</person>";
		
		System.out.println("\n**********3.4**********");
        System.out.println("****APPLICATION/XML****");
        Response resp4 = service.path("person").request().accept(MediaType.APPLICATION_XML).header("Content-type","application/xml").post(Entity.xml(entity4));
        String response4 = resp4.readEntity(String.class);
        Document doc4 = loadXMLFromString(response4);
        NodeList newPerson = doc4.getElementsByTagName("idPerson");
        int newPersonId = Integer.parseInt(newPerson.item(0).getTextContent());
        
        if (newPersonId>-1 && (resp4.getStatus() == 200 || resp4.getStatus() == 201 || resp4.getStatus() == 202)) {
        	result = "OK";
        }else {
        	result = "ERROR";
        }
       
        System.out.println("\nRequest #4:" + "\n"
        		+ "Header: " + "\n"
        		+ "POST /person/ Accept: APPLICATION/XML Content-Type: APPLICATION/XML" + "\n"
        		+ "=> Result: " + result +  "\n"
        		+ "=> HTTP Status: " + resp4.getStatus() + " " + resp4.getStatusInfo() + "\n"
        		+ "Body: " +format(response4));	
        System.out.println(newPersonId);
       
        
        //3.5.
        System.out.println("\n**********3.5**********");
        System.out.println("****APPLICATION/XML****");
        Response resp5 = service.path("person").path(String.valueOf(newPersonId)).request().accept(MediaType.APPLICATION_XML).header("Content-type","application/xml").delete();
        Response resp5b = service.path("person").path(String.valueOf(newPersonId)).request().accept(MediaType.APPLICATION_XML).header("Content-type","application/xml").get();
      
        if (resp5b.getStatus()==404) {
        	result = "OK";
        } else {
        	result ="ERROR";
        }
        System.out.println("\nRequest #5:" + "\n"
        		+ "Header: " + "\n"
        		+ "DELETE /person/" + newPersonId + " Accept: APPLICATION/XML Content-Type: APPLICATION/XML" + "\n"
        		+ "=> Result: " + result +  "\n"
        		+ "=> HTTP Status: " + resp5.getStatus() + " " + resp5.getStatusInfo());
        
        // Step 3.6.
        System.out.println("\n**********3.6**********");
        System.out.println("****APPLICATION/XML****");
        Response resp6 = service.path("activity_types").request().accept(MediaType.APPLICATION_XML).header("Content-type","application/xml").get();
        String response6 = resp6.readEntity(String.class);
        Document doc6 = loadXMLFromString(response6);
        NodeList activityTypes = doc6.getElementsByTagName("activity_type");
        int activityTypeCount = activityTypes.getLength();
        List<String> activityTypesList = new ArrayList<String>();
        for (int i=0;i<activityTypeCount;i++) {
        	activityTypesList.add(activityTypes.item(0).getTextContent());
        }
        if(activityTypeCount>2) {
        	result = "OK";
        }else {
        	result = "ERROR";
        }
        System.out.println("\nRequest #6:" + "\n"
        		+ "Header: " + "\n"
        		+ "GET /activity_type/ Accept: APPLICATION/XML Content-Type: APPLICATION/XML" + "\n"
        		+ "=> Result: " + result +  "\n"
        		+ "=> HTTP Status: " + resp6.getStatus() + " " + resp6.getStatusInfo() + "\n"
        		+ "Body: "  + "\n"
        		+ format(response6) + "\n");
        
        // Step 3.7.
        System.out.println("\n**********3.7**********");
        System.out.println("****APPLICATION/XML****");
        int okCount=0;
        int activityCount=0;
        int idActivity=-1;
        String type = "";
        for (int i=0;i<activityTypesList.size();i++) {
        	Response resp7 = service.path("person").path(String.valueOf(first_person_id)).path(activityTypesList.get(i)).request().accept(MediaType.APPLICATION_XML).header("Content-type","application/xml").get();
        	String response7 = resp7.readEntity(String.class);
            Document doc7 = loadXMLFromString(response7);
            NodeList activitiesWithType = doc7.getElementsByTagName("activity");
            activityCount = activitiesWithType.getLength();
            if(activityCount>0) {
            	result = "OK";
            	okCount++;
            	idActivity = Integer.parseInt(doc7.getElementsByTagName("activity").item(0).getChildNodes().item(1).getTextContent());
            	type = doc7.getElementsByTagName("activity").item(0).getLastChild().getTextContent();
            }else {
            	result = "ERROR";
            }
            System.out.println("\nRequest #7:" + "\n"
            		+ "Header: " + "\n"
            		+ "GET /person/" + first_person_id + "/" +  activityTypesList.get(i) + " Accept: APPLICATION/XML Content-Type: APPLICATION/XML" + "\n"
            		+ "=> Result: " + result +  "\n"
            		+ "=> HTTP Status: " + resp7.getStatus() + " " + resp7.getStatusInfo() + "\n"
            		+ "Body: "  + "\n"
            		+ format(response7) + "\n");
        }
        for (int i=0;i<activityTypesList.size();i++) {
        	Response resp7b = service.path("person").path(String.valueOf(last_person_id)).path(activityTypesList.get(i)).request().accept(MediaType.APPLICATION_XML).header("Content-type","application/xml").get();
        	String response7b = resp7b.readEntity(String.class);
            Document doc7b = loadXMLFromString(response7b);
            NodeList activitiesWithType = doc7b.getElementsByTagName("activity");
            activityCount = activitiesWithType.getLength();
            if(activityCount>0) {
            	result = "OK";
            	okCount++;
            }else {
            	result = "ERROR";
            }
            System.out.println("\nRequest #7b:" + "\n"
            		+ "Header: " + "\n"
            		+ "GET /person/" + last_person_id + "/" +  activityTypesList.get(i) + " Accept: APPLICATION/XML Content-Type: APPLICATION/XML" + "\n"
            		+ "=> Result: " + result +  "\n"
            		+ "=> HTTP Status: " + resp7b.getStatus() + " " + resp7b.getStatusInfo() + "\n"
            		+ "Body: "  + "\n"
            		+ format(response7b) + "\n");
        }
        if (okCount>0) {
        	System.out.println("Request#7 result is OK");
        	System.out.println("Saved activity id: " + idActivity + "\nSaved type: " + type);
        }else {
        	System.out.println("Request#7 result is ERROR");
        }
        
        //3.8.
        System.out.println("\n**********3.8**********");
        System.out.println("****APPLICATION/XML****");
        Response resp8 = service.path("person").path(String.valueOf(first_person_id)).path(type).path(String.valueOf(idActivity)).request().accept(MediaType.APPLICATION_XML).header("Content-type","application/xml").get();
        String response8 = resp8.readEntity(String.class);
        if (resp8.getStatus()==200) {
        	result = "OK";
        } else {
        	result ="ERROR";
        }
        System.out.println("\nRequest #8:" + "\n"
        		+ "Header: " + "\n"
        		+ "GET /person/" + first_person_id + "/" + type + "/" + idActivity + " Accept: APPLICATION/XML Content-Type: APPLICATION/XML" + "\n"
        		+ "=> Result: " + result +  "\n"
        		+ "=> HTTP Status: " + resp8.getStatus() + " " + resp8.getStatusInfo() + "\n"
        		+ "Body: "  + "\n"
        		+ format(response8) + "\n");
        
        int activityCountWithType = 0;
        int activityCountWithTypeChanged = 0;
        
        // Step 3.9.
        Object entity9 = 
        		"<activity>\n" + 
        		"<description>Swimming in the river</description>\n" + 
        		"<name>Swimming</name>\n" + 
        		"<place>Adige river</place>\n" + 
        		"<startdate>2017-12-28T08:50:00+01:00</startdate>\n" + 
        		"<type>SPORT</type>\n" + 
        		"</activity>";
		Response resp9a = service.path("person").path(String.valueOf(first_person_id)).path("SPORT").request().accept(MediaType.APPLICATION_XML).header("Content-type","application/xml").get();
    	String response9a = resp9a.readEntity(String.class);
        Document doc9a = loadXMLFromString(response9a);
        NodeList activitiesWithTypeOrig = doc9a.getElementsByTagName("activity");
        activityCountWithType = activitiesWithTypeOrig.getLength();
        
		System.out.println("\n**********3.9**********");
        System.out.println("****APPLICATION/XML****");
        Response resp9 = service.path("person").path(String.valueOf(first_person_id)).path("SPORT").request().accept(MediaType.APPLICATION_XML).header("Content-type","application/xml").post(Entity.xml(entity9));
        String response9 = resp9.readEntity(String.class);
        
        Response resp9b = service.path("person").path(String.valueOf(first_person_id)).path("SPORT").request().accept(MediaType.APPLICATION_XML).header("Content-type","application/xml").get();
    	String response9b = resp9b.readEntity(String.class);
        Document doc9b = loadXMLFromString(response9b);
        NodeList activitiesWithTypeChanged = doc9b.getElementsByTagName("activity");
        activityCountWithTypeChanged = activitiesWithTypeChanged.getLength();       
        
        if (activityCountWithType < activityCountWithTypeChanged) {
        	result = "OK";
        }else {
        	result = "ERROR";
        }
        int idActivityNew = Integer.parseInt(doc9b.getElementsByTagName("activity").item(1).getChildNodes().item(1).getTextContent());
        System.out.println("new activity id "+idActivityNew);
        System.out.println("\nRequest #9:" + "\n"
        		+ "Header: " + "\n"
        		+ "POST /person/" + first_person_id + "/SPORT" +" Accept: APPLICATION/XML Content-Type: APPLICATION/XML" + "\n"
        		+ "=> Result: " + result +  "\n"
        		+ "=> HTTP Status: " + resp9.getStatus() + " " + resp9.getStatusInfo() + "\n"
        		+ "Body: " + "\n" +format(response9));	
        
        // Step 3.10. EXTRA
        Object entity10 = 
        		"<activity>\n" + 
        				"<description>Swimming in the river</description>\n" + 
        				"<idActivity>"+idActivityNew + "</idActivity>\n"+
        				"<name>Swimming</name>\n" + 
        				"<place>Adige</place>\n" + 
        				"<startdate>2017-12-28T08:50:00+01:00</startdate>\n" + 
        				"<type>SPORT</type>\n" + 
        				"</activity>";
        Response resp10a = service.path("person").path(String.valueOf(first_person_id)).path("SPORT").path(String.valueOf(idActivityNew)).request().accept(MediaType.APPLICATION_XML).header("Content-type","application/xml").get();
        String response10a = resp10a.readEntity(String.class);
        Document doc10a = loadXMLFromString(response10a);
        String origPlace = doc10a.getElementsByTagName("activity").item(0).getChildNodes().item(3).getTextContent();

        System.out.println("\n**********3.10**********");
        System.out.println("****APPLICATION/XML****");
        Response resp10 = service.path("person").path(String.valueOf(first_person_id)).path("SPORT").path(String.valueOf(idActivityNew)).request().accept(MediaType.APPLICATION_XML).header("Content-type","application/xml").put(Entity.xml(entity10));
        String response10 = resp10.readEntity(String.class);

        Response resp10b = service.path("person").path(String.valueOf(first_person_id)).path("SPORT").path(String.valueOf(idActivityNew)).request().accept(MediaType.APPLICATION_XML).header("Content-type","application/xml").get();
        String response10b = resp10b.readEntity(String.class);
        Document doc10b = loadXMLFromString(response10b);
        String changedPlace = doc10b.getElementsByTagName("activity").item(0).getChildNodes().item(3).getTextContent();       
        System.out.println("changedPlace: "+ changedPlace);
        System.out.println("origPlace: "+ origPlace);
        if (!changedPlace.equals(origPlace)) {
        	result = "OK";
        }else {
        	result = "ERROR";
        }
        System.out.println("\nRequest #10:" + "\n"
        		+ "Header: " + "\n"
        		+ "PUT /person/" + first_person_id + "/SPORT" + "/" + idActivityNew +" Accept: APPLICATION/XML Content-Type: APPLICATION/XML" + "\n"
        		+ "=> Result: " + result +  "\n"
        		+ "=> HTTP Status: " + resp10.getStatus() + " " + resp10.getStatusInfo() + "\n"
        		+ "Body: " + "\n" +format(response10));	        


        //http://localhost:5900/person/1/SPORT?before=2017-12-28T08:50:00&after=2017-11-11T00:00:00
        // Step 3.11. EXTRA
        System.out.println("\n**********3.11**********");
        System.out.println("****APPLICATION/XML****");
    	Response resp11 = service.path("person").path(String.valueOf(first_person_id)).path(type).queryParam("before", "2017-12-28T08:50:00").queryParam("after", "2017-12-28T08:50:00").request().accept(MediaType.APPLICATION_XML).header("Content-type","application/xml").get();
    	String response11 = resp11.readEntity(String.class);
        Document doc11 = loadXMLFromString(response11);
        NodeList activitiesWithinRange = doc11.getElementsByTagName("activity");
        int activityWithinRangeCount = activitiesWithinRange.getLength();
        if(activityWithinRangeCount>0) {
        	result = "OK";
        }else {
        	result = "ERROR";
        }
        System.out.println("\nRequest #11:" + "\n"
        		+ "Header: " + "\n"
        		+ "GET /person/" + first_person_id + "/" +  type + "?before=2017-12-28T08:50:00&after=2017-11-11T00:00:00" + " Accept: APPLICATION/XML Content-Type: APPLICATION/XML" + "\n"
        		+ "=> Result: " + result +  "\n" 
        		+ "=> HTTP Status: " + resp11.getStatus() + " " + resp11.getStatusInfo() + "\n"
        		+ "Body: "  + "\n"
        		+ format(response11) + "\n");

	}
    private static URI getBaseURI() {
        return UriBuilder.fromUri(
                "https://activityperson.herokuapp.com").build();
    }
}