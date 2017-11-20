package introsde.rest.eactivity.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonValue;

@XmlRootElement(name="activity_type")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ActivityType implements Serializable{
	SPORT("Sport"),
	SOCIAL("Social"),
	WORK("Work"),
	TRAVEL("Travel"),
	CULTURE("Culture");
	
	private String activityType;
	
	ActivityType(String activityType){
		this.activityType = activityType;
	}
	
	@JsonValue
	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	
	 @Override
	 public String toString() {
	    return activityType;
	 }
}
