package introsde.rest.eactivity.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import introsde.rest.eactivity.dao.PersonDao;


/**
 * The persistent class for the "Person" database table.
 * 
 */
@Entity
@Table(name="\"Person\"")
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
@XmlRootElement(name="person")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int idPerson;
	
	@Column(name="firstname")
	private String firstname;

	@Column(name="lastname")
	private String lastname;

	@Temporal(TemporalType.DATE)
	@Column(name="birthdate")
	private Date birthdate;
	
	
	@OneToMany(mappedBy="person",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@XmlElementWrapper(name="preferences")
	private List<Activity> activitypreference;
	
	public Person() {
	}
	

	public Person(String firstname, String lastname, Date birthdate, List<Activity> activitypreference) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.activitypreference = activitypreference;
	}


	public Date getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public int getIdPerson() {
		return this.idPerson;
	}

	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstName() {
		return this.firstname;
	}

	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}

	@XmlTransient
	public List<Activity> getActivityPreferences() {
	    return activitypreference;
	}
	

	public List<Activity> getActivitiesWithType(ActivityType type) {
	    List<Activity> activities = getActivityPreferences();
	    List<Activity> activitiesWithType = new ArrayList<>();
	    
	    for (Activity activity : activities) {
	    	if (activity.getType().equals(type)) {
	    		activitiesWithType.add(activity);
	    	}
	    }
	    return activitiesWithType;
	}
	
	public void setActivityPreferences(List<Activity> activities) {
	    this.activitypreference = activities;
	}
	
	public static Person getPersonById(int personId) {
		EntityManager em = PersonDao.instance.createEntityManager();
		Person p = em.find(Person.class, personId);
		PersonDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<Person> getAll() {
		System.out.println("--> Initializing Entity manager...");
		EntityManager em = PersonDao.instance.createEntityManager();
		System.out.println("--> Querying the database for all the people...");
	    List<Person> list = em.createNamedQuery("Person.findAll", Person.class).getResultList();
		System.out.println("--> Closing connections of entity manager...");
		PersonDao.instance.closeConnections(em);
	    return list;
	}
	
	public static Person savePerson(Person p) {
		EntityManager em = PersonDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
		PersonDao.instance.closeConnections(em);
	    return p;
	}
	
	public static Person updatePerson(Person p) {
		EntityManager em = PersonDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
		PersonDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removePerson(Person p) {
		EntityManager em = PersonDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    PersonDao.instance.closeConnections(em);
	}


	public List<Activity> getActivitiesWithTypeAndId(ActivityType type, int activityId) {
		List<Activity> activities = getActivityPreferences();
	    List<Activity> activitiesWithTypeAndId = new ArrayList<>();
	    
	    for (Activity activity : activities) {
	    	if (activity.getType().equals(type) && activity.getIdActivity()==activityId) {
	    		activitiesWithTypeAndId.add(activity);
	    	}
	    }
	    return activitiesWithTypeAndId;
	}

	
	public Activity addActivityWithType(ActivityType type, Activity activity) {
		EntityManager em = PersonDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		List<Activity> origActivities = this.getActivityPreferences();
		activity.setType(type);
		origActivities.add(activity);
		this.setActivityPreferences(origActivities);
		em.merge(this);
		tx.commit();
		PersonDao.instance.closeConnections(em);
		return activity;
		
	}


	public List<Activity> getActivitiesWithWithinRange(ActivityType type, Date beforeDate, Date afterDate) {
		List<Activity> activitiesWithType = this.getActivitiesWithType(type);
		System.out.println(activitiesWithType);
		
		for (Activity activity : activitiesWithType) {
			System.out.println(activity.getStartdate().before(beforeDate));
			if (activity.getStartdate().before(afterDate) || activity.getStartdate().after(beforeDate)) {
				activitiesWithType.remove(activity);
				System.out.println("Dropped from list:" + activity);
			} else{
				System.out.println("Kept in list:" + activity);
			}
		}
		return activitiesWithType; 	
		
	}
}

