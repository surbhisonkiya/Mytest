
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
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;





public class ClientImplementationJSON {
    

	public static WebTarget config() {
    	ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);
        WebTarget service = client.target(getBaseURI());
        System.out.println("Calling " + getBaseURI() ); //Step 1.
        return service;
    }
	
	
	public static void main(String[] args) throws Exception {
        WebTarget service = config();
        String result;
        int count;
        int first_person_id;
        int last_person_id;
		
        
        // Step 3.1.
        System.out.println("\n**********3.1**********");
        System.out.println("****APPLICATION/JSON****");
        Response resp = service.path("person").request().accept(MediaType.APPLICATION_JSON).header("Content-type","application/json").get();
        String response = resp.readEntity(String.class);
      
        JSONArray array_response = new JSONArray(response);
        count = array_response.length();
        first_person_id = (Integer) array_response.getJSONObject(0).get("idPerson");
        last_person_id =  (Integer) array_response.getJSONObject(count-1).get("idPerson");
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
        		+ "GET /person/ Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON" + "\n"
        		+ "=> Result: " + result +  "\n"
        		+ "=> HTTP Status: " + resp.getStatus() + " " + resp.getStatusInfo() + "\n"
        		+ "Body: "  + "\n"
        		+ response + "\n");	
        
       
   
        // Step 3.2.
        System.out.println("\n**********3.2**********");
        System.out.println("****APPLICATION/JSON****");
        Response resp2 = service.path("person").path(String.valueOf(first_person_id)).request().accept(MediaType.APPLICATION_JSON).header("Content-type","application/json").get();
        String response2 = resp2.readEntity(String.class);
        
        JSONObject obj2 = new JSONObject(response2);
        String origFirstNameOfFirst = (String) obj2.get("firstName");
        
        if (resp2.getStatus()==200 || resp2.getStatus()==202) {
        	result = "OK";
        } else {
        	result ="ERROR";
        }
        System.out.println("\nRequest #2:" + "\n"
        		+ "Header: " + "\n"
        		+ "GET /person/" + first_person_id + " Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON" + "\n"
        		+ "=> Result: " + result +  "\n"
        		+ "=> HTTP Status: " + resp2.getStatus() + " " + resp2.getStatusInfo() + "\n"
        		+ "Body: " + response2 + "\n");	
       
        // Step 3.3.
		Object entity3 = "{\"idPerson\":1,\"lastname\":\"Hermann\",\"birthdate\":1510780525483,\"activitypreference\":[{\"idActivity\":2,\"name\":\"Running\",\"description\":\"Running on the track\",\"place\":\"Trento\",\"type\":\"Sport\",\"startdate\":1510780525483},{\"idActivity\":3,\"name\":\"Cooking\",\"description\":\"Japanese cooking class\",\"place\":\"Rovereto\",\"type\":\"Social\",\"startdate\":1510780525483}],\"firstName\":\"Marika\"}";
		
		System.out.println("\n**********3.3**********");
        System.out.println("****APPLICATION/JSON****");
        Response resp3 = service.path("person").path(String.valueOf(first_person_id)).request().accept(MediaType.APPLICATION_JSON).header("Content-type","application/json").put(Entity.json(entity3));
        //String response3 = resp3.readEntity(String.class);
        Response resp3b = service.path("person").path(String.valueOf(first_person_id)).request().accept(MediaType.APPLICATION_JSON).header("Content-type","application/json").get();
        String response3b = resp3b.readEntity(String.class);
        
        JSONObject obj3b = new JSONObject(response3b);
        String changedFirstName = (String) obj3b.get("firstName");
        
        if (!origFirstNameOfFirst.equals(changedFirstName)) {
        	result = "OK";
        }else {
        	result = "ERROR";
        }
        
        System.out.println("\nRequest #3:" + "\n"
        		+ "Header: " + "\n"
        		+ "PUT /person/" + first_person_id + " Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON" + "\n"
        		+ "=> Result: " + result +  "\n"
        		+ "=> HTTP Status: " + resp3.getStatus() + " " + resp3.getStatusInfo() + "\n"
        		+ "Updated person: " + response3b);
       	
        // Step 3.4.
        Object entity4 = "{\"lastname\":\"Holmes\",\"birthdate\":1510780525483,\"activitypreference\":[{\"name\":\"Going out\",\"description\":\"Going to a restaurant or a bar with friends.\",\"place\":\"London\",\"type\":\"Social\",\"startdate\":1510780525483}],\"firstName\":\"Sherlock\"}";
		
		System.out.println("\n**********3.4**********");
        System.out.println("****APPLICATION/JSON****");
        Response resp4 = service.path("person").request().accept(MediaType.APPLICATION_JSON).header("Content-type","application/json").post(Entity.json(entity4));
        String response4 = resp4.readEntity(String.class);
        
        JSONObject obj4 = new JSONObject(response4);
        int newPersonId = (Integer) obj4.get("idPerson");
        
        if (newPersonId>-1 && (resp4.getStatus() == 200 || resp4.getStatus() == 201 || resp4.getStatus() == 202)) {
        	result = "OK";
        }else {
        	result = "ERROR";
        }
       
        System.out.println("\nRequest #4:" + "\n"
        		+ "Header: " + "\n"
        		+ "POST /person/ Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON" + "\n"
        		+ "=> Result: " + result +  "\n"
        		+ "=> HTTP Status: " + resp4.getStatus() + " " + resp4.getStatusInfo() + "\n"
        		+ "Body: " +response4);	
        System.out.println(newPersonId);
       
      
        //3.5.
        System.out.println("\n**********3.5**********");
        System.out.println("****APPLICATION/JSON****");
        Response resp5 = service.path("person").path(String.valueOf(newPersonId)).request().accept(MediaType.APPLICATION_JSON).header("Content-type","application/json").delete();
        Response resp5b = service.path("person").path(String.valueOf(newPersonId)).request().accept(MediaType.APPLICATION_JSON).header("Content-type","application/json").get();
      
        if (resp5b.getStatus()==404) {
        	result = "OK";
        } else {
        	result ="ERROR";
        }
        System.out.println("\nRequest #5:" + "\n"
        		+ "Header: " + "\n"
        		+ "DELETE /person/" + newPersonId + " Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON" + "\n"
        		+ "=> Result: " + result +  "\n"
        		+ "=> HTTP Status: " + resp5.getStatus() + " " + resp5.getStatusInfo());
    
        // Step 3.6.
        System.out.println("\n**********3.6**********");
        System.out.println("****APPLICATION/JSON****");
        Response resp6 = service.path("activity_types").request().accept(MediaType.APPLICATION_JSON).header("Content-type","application/json").get();
        String response6 = resp6.readEntity(String.class);

        JSONArray array6 = new JSONArray(response6);
        int activityTypeCount = array6.length();
      
        List<String> activityTypesList = new ArrayList<String>();
        for (int i=0;i<activityTypeCount;i++) {
        	activityTypesList.add(array6.get(i).toString());
        }
        if(activityTypeCount>2) {
        	result = "OK";
        }else {
        	result = "ERROR";
        }
        System.out.println("\nRequest #6:" + "\n"
        		+ "Header: " + "\n"
        		+ "GET /activity_type/ Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON" + "\n"
        		+ "=> Result: " + result +  "\n"
        		+ "=> HTTP Status: " + resp6.getStatus() + " " + resp6.getStatusInfo() + "\n"
        		+ "Body: "  + "\n"
        		+ response6 + "\n");
      
        // Step 3.7.
        System.out.println("\n**********3.7**********");
        System.out.println("****APPLICATION/JSON****");
        int okCount=0;
        int activityCount=0;
        int idActivity=-1;
        String type = "";
        for (int i=0;i<activityTypesList.size();i++) {
        	Response resp7 = service.path("person").path(String.valueOf(first_person_id)).path(activityTypesList.get(i).toUpperCase()).request().accept(MediaType.APPLICATION_JSON).header("Content-type","application/json").get();
        	String response7 = resp7.readEntity(String.class);
        	JSONArray array7 = new JSONArray(response7);
            activityCount = array7.length();          
            if(activityCount>0) {
            	result = "OK";
            	okCount++;
            	idActivity = (Integer) array7.getJSONObject(0).get("idActivity");
            	type = (String) array7.getJSONObject(0).get("type");
            }else {
            	result = "ERROR";
            }
            
            System.out.println("\nRequest #7:" + "\n"
            		+ "Header: " + "\n"
            		+ "GET /person/" + first_person_id + "/" +  activityTypesList.get(i) + " Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON" + "\n"
            		+ "=> Result: " + result +  "\n"
            		+ "=> HTTP Status: " + resp7.getStatus() + " " + resp7.getStatusInfo() + "\n"
            		+ "Body: "  + "\n"
            		+ response7 + "\n");
        }
        for (int i=0;i<activityTypesList.size();i++) {
        	Response resp7b = service.path("person").path(String.valueOf(last_person_id)).path(activityTypesList.get(i).toUpperCase()).request().accept(MediaType.APPLICATION_JSON).header("Content-type","application/json").get();
        	String response7b = resp7b.readEntity(String.class);
        	JSONArray array7b = new JSONArray(response7b);
            activityCount = array7b.length();    
            if(activityCount>0) {
            	result = "OK";
            	okCount++;
            }else {
            	result = "ERROR";
            }
            System.out.println("\nRequest #7b:" + "\n"
            		+ "Header: " + "\n"
            		+ "GET /person/" + last_person_id + "/" +  activityTypesList.get(i) + " Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON" + "\n"
            		+ "=> Result: " + result +  "\n"
            		+ "=> HTTP Status: " + resp7b.getStatus() + " " + resp7b.getStatusInfo() + "\n"
            		+ "Body: "  + "\n"
            		+ response7b + "\n");
        }
        if (okCount>0) {
        	System.out.println("Request#7 result is OK");
        	System.out.println("Saved activity id: " + idActivity + "\nSaved type: " + type);
        }else {
        	System.out.println("Request#7 result is ERROR");
        }
        	
        //3.8.
        System.out.println("\n**********3.8**********");
        System.out.println("****APPLICATION/JSON****");
        Response resp8 = service.path("person").path(String.valueOf(first_person_id)).path(type.toUpperCase()).path(String.valueOf(idActivity)).request().accept(MediaType.APPLICATION_JSON).header("Content-type","application/json").get();
        String response8 = resp8.readEntity(String.class);
        if (resp8.getStatus()==200) {
        	result = "OK";
        } else {
        	result ="ERROR";
        }
        System.out.println("\nRequest #8:" + "\n"
        		+ "Header: " + "\n"
        		+ "GET /person/" + first_person_id + "/" + type + "/" + idActivity + " Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON" + "\n"
        		+ "=> Result: " + result +  "\n"
        		+ "=> HTTP Status: " + resp8.getStatus() + " " + resp8.getStatusInfo() + "\n"
        		+ "Body: "  + "\n"
        		+ response8 + "\n");
        
        int activityCountWithType = 0;
        int activityCountWithTypeChanged = 0;
       
        // Step 3.9.
        Object entity9 = "{\"name\":\"Swimming\",\"description\":\"Swimming in the river\",\"place\":\"Adige river\",\"type\":\"Sport\",\"startdate\":1514447400000}";
		System.out.println(Entity.json(entity9));
        Response resp9a = service.path("person").path(String.valueOf(first_person_id)).path("SPORT").request().accept(MediaType.APPLICATION_JSON).header("Content-type","application/json").get();
    	String response9a = resp9a.readEntity(String.class);
    	JSONArray array9a = new JSONArray(response9a);
    	activityCountWithType = array9a.length();  
 
        
		System.out.println("\n**********3.9**********");
        System.out.println("****APPLICATION/JSON****");
        Response resp9 = service.path("person").path(String.valueOf(first_person_id)).path("SPORT").request().accept(MediaType.APPLICATION_JSON).header("Content-type","application/json").post(Entity.json(entity9));
        String response9 = resp9.readEntity(String.class);
      
        Response resp9b = service.path("person").path(String.valueOf(first_person_id)).path("SPORT").request().accept(MediaType.APPLICATION_JSON).header("Content-type","application/json").get();
    	String response9b = resp9b.readEntity(String.class);
    	JSONArray array9b = new JSONArray(response9b);
    	activityCountWithTypeChanged = array9b.length();  
 
        
        if (activityCountWithType < activityCountWithTypeChanged) {
        	result = "OK";
        }else {
        	result = "ERROR";
        }
        int idActivityNew = array9b.getJSONObject(activityCountWithTypeChanged-1).getInt("idActivity");
        System.out.println("new activity id "+idActivityNew);
        System.out.println("\nRequest #9:" + "\n"
        		+ "Header: " + "\n"
        		+ "POST /person/" + first_person_id + "/SPORT" +" Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON" + "\n"
        		+ "=> Result: " + result +  "\n"
        		+ "=> HTTP Status: " + resp9.getStatus() + " " + resp9.getStatusInfo() + "\n"
        		+ "Body: " + "\n" +response9);	
        
        // Step 3.10. EXTRA
        Object entity10 = "{\"idActivity\":"+idActivityNew+", \"name\":\"Swimming\",\"description\":\"Swimming in the river\",\"place\":\"Adige\",\"type\":\"Sport\",\"startdate\":1514447400000}";		
        Response resp10a = service.path("person").path(String.valueOf(first_person_id)).path("SPORT").path(String.valueOf(idActivityNew)).request().accept(MediaType.APPLICATION_JSON).header("Content-type","application/json").get();
        String response10a = resp10a.readEntity(String.class);
        JSONArray array10a = new JSONArray(response10a);
        String origPlace = array10a.getJSONObject(0).getString("place");
      
        System.out.println("\n**********3.10**********");
        System.out.println("****APPLICATION/JSON****");
        Response resp10 = service.path("person").path(String.valueOf(first_person_id)).path("SPORT").path(String.valueOf(idActivityNew)).request().accept(MediaType.APPLICATION_JSON).header("Content-type","application/json").put(Entity.json(entity10));
        String response10 = resp10.readEntity(String.class);

        Response resp10b = service.path("person").path(String.valueOf(first_person_id)).path("SPORT").path(String.valueOf(idActivityNew)).request().accept(MediaType.APPLICATION_JSON).header("Content-type","application/json").get();
        String response10b = resp10b.readEntity(String.class);
        JSONArray array10b = new JSONArray(response10b);
        String changedPlace = array10b.getJSONObject(0).getString("place");
           
        System.out.println("changedPlace: "+ changedPlace);
        System.out.println("origPlace: "+ origPlace);
        if (!changedPlace.equals(origPlace)) {
        	result = "OK";
        }else {
        	result = "ERROR";
        }
        System.out.println("\nRequest #10:" + "\n"
        		+ "Header: " + "\n"
        		+ "PUT /person/" + first_person_id + "/SPORT" + "/" + idActivityNew +" Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON" + "\n"
        		+ "=> Result: " + result +  "\n"
        		+ "=> HTTP Status: " + resp10.getStatus() + " " + resp10.getStatusInfo() + "\n"
        		+ "Body: " + "\n" + response10);	        


        //http://localhost:5900/person/1/SPORT?before=2017-12-28T08:50:00&after=2017-11-11T00:00:00
       
        // Step 3.11. EXTRA
        System.out.println("\n**********3.11**********");
        System.out.println("****APPLICATION/JSON****");
    	Response resp11 = service.path("person").path(String.valueOf(first_person_id)).path(type.toUpperCase()).queryParam("before", "2017-12-28T08:50:00").queryParam("after", "2017-11-11T00:00:00").request().accept(MediaType.APPLICATION_JSON).header("Content-type","application/json").get();
    	String response11 = resp11.readEntity(String.class);
    	System.out.println("Response"+response11);
    	JSONArray array11 = new JSONArray(response11);
        int activityWithinRangeCount = array11.length();
        
        if(activityWithinRangeCount>0) {
        	result = "OK";
        }else {
        	result = "ERROR";
        }
      
        System.out.println("\nRequest #11:" + "\n"
        		+ "Header: " + "\n"
        		+ "GET /person/" + first_person_id + "/" +  type + "?before=2017-12-28T08:50:00&after=2017-11-11T00:00:00" + " Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON" + "\n"
        		+ "=> Result: " + result +  "\n" 
        		+ "=> HTTP Status: " + resp11.getStatus() + " " + resp11.getStatusInfo() + "\n"
        		+ "Body: "  + "\n"
        		+ response11 + "\n");
	}
    private static URI getBaseURI() {
        return UriBuilder.fromUri("https://activityperson.herokuapp.com").build();
                //"http://localhost:5900").build();
    }
}