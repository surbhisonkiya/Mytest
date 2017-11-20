package introsde.rest.eactivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import introsde.rest.eactivity.model.Activity;
import introsde.rest.eactivity.model.ActivityType;
import introsde.rest.eactivity.model.Person;



public class DatabaseInit {
	//Request#0
	public static List<Activity> createActivities(){
		List<Activity> activities = new ArrayList<Activity>();
		Activity a1 = new Activity("Squash", "Squash Court in Sanbapolis", "Trento", ActivityType.SPORT, new Date());
		activities.add(a1);
		
		Activity a2 = new Activity("Jogging", "Jogging on Golden Gate Bridge", "San Francisco", ActivityType.SPORT, new Date());
		activities.add(a2);
		
		Activity a3 = new Activity("Dance", "Salsa dance classes", "Madrid", ActivityType.CULTURE, new Date());
		activities.add(a3);
		
		Activity a4 = new Activity("Kho Kho", "Running outdoor games", "Jaipur", ActivityType.SPORT, new Date());
		activities.add(a4);
		
		Activity a5 = new Activity("Culture class", "Learn italian culture in CLA", "Trento", ActivityType.CULTURE, new Date());
		activities.add(a5);
		
		Activity a6 = new Activity("Yoga", "Yoga Teacher Training Program", "Mysore", ActivityType.SPORT, new Date());
		activities.add(a6);
		
		Activity a7 = new Activity("Trekking", "Mountain trekking and rock climbing", "Shimla", ActivityType.SPORT, new Date());
		activities.add(a7);
		
		Activity a8 = new Activity("Travelling", "Visit historical places in the country", "Italy", ActivityType.TRAVEL, new Date());
		activities.add(a8);
		
		Activity a9 = new Activity("Party", "Friends get-togther", "Sanbapolis", ActivityType.SOCIAL, new Date());
		activities.add(a9);
		
		Activity a10 = new Activity("Football", "Play football in women's team", "Mysore", ActivityType.SPORT, new Date());
		activities.add(a10);
		
		return activities;
	}
	
	public static List<Person> createFivePeople(){
		List<Person> people = new ArrayList<>();
		List<Activity> activityList = createActivities();
		Person p1 = new Person("Surbhi", "Sonkiya", new Date(), activityList.subList(0, 2));
		people.add(p1);
		activityList.get(0).setPerson(p1);
		activityList.get(1).setPerson(p1);
		
		Person p2 = new Person("Rohit", "Prakash", new Date(), activityList.subList(2, 4));
		people.add(p2);
		activityList.get(2).setPerson(p2);
		activityList.get(3).setPerson(p2);
		
		Person p3 = new Person("Parul", "Jain", new Date(), activityList.subList(4, 6));
		people.add(p3);
		activityList.get(4).setPerson(p3);
		activityList.get(5).setPerson(p3);

		Person p4 = new Person("Hari", "Jhawar", new Date(), activityList.subList(8, 10));
		people.add(p4);
		activityList.get(8).setPerson(p4);
		activityList.get(9).setPerson(p4);

		Person p5 = new Person("Renu", "Jhalani", new Date(), activityList.subList(6, 8));
		people.add(p5);
		activityList.get(6).setPerson(p5);
		activityList.get(7).setPerson(p5);
		
		return people;
	}
	
	
	
	public static List<Person> init() {
		List<Person> people = new ArrayList<>();
		Person person;
		for (int i=0; i<5; i++) {
			person = Person.savePerson(createFivePeople().get(i));
			people.add(person);
		}
		return people;

		
	}
	
	public static void main(String[] args) {
		init();
	}
	
}
