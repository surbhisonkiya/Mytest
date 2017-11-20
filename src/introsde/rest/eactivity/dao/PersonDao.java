package introsde.rest.eactivity.dao;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


import introsde.rest.eactivity.model.Person;

public enum PersonDao {
	
	instance;
	private EntityManagerFactory emf;
	/*instance;
	private Map<Long, Person> contentProvider = new HashMap<Long, Person>();*/

	private PersonDao() {
		if (emf!=null) {
			emf.close();
		}
	
		emf = Persistence.createEntityManagerFactory("ActivityStudentServer");
	}
		
		public EntityManager createEntityManager() {
			try {
				return emf.createEntityManager();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;    
		}

		public void closeConnections(EntityManager em) {
			em.close();
		}

		public EntityTransaction getTransaction(EntityManager em) {
			return em.getTransaction();
		}
		
		public EntityManagerFactory getEntityManagerFactory() {
			return emf;
		}


	
	
		
		


	
	
	
}
