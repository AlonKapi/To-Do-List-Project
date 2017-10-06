package il.ac.hit.todoListProject.model;

import java.util.*;
import java.time.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateToDoListDAO implements IToDoListDAO {
	/**
	 *  the instance which will be used by the controller to access the model
	 */
	private static HibernateToDoListDAO _instance = null;
	
	/**
	 *  manages the sessions
	 */
	private SessionFactory factory;
	
	private HibernateToDoListDAO(){
		/**
		 * Class Constructor, 
		 * only initializes the session factory
		 */
		
		factory = new Configuration().configure().buildSessionFactory();
	}
	
	public static HibernateToDoListDAO getInstance() throws TodoListProjectException{
		/**
		 * a function that implements Singleton design pattern
		 */
		
		if (_instance == null)
			_instance = new HibernateToDoListDAO();
		return _instance;
	}

	/*			 users functions				*/
	
	public void addUser(String name, String password) throws TodoListProjectException{
		/**
		 * Adds a user to the database
		 * first it checks for valid input by the user
		 * and throws exception to the controller if needed
		 * else it updates the database
		 * 
		 * Parameters:
		 * name - the user's name as string
		 * password - the user's password as string
		 * 
		 * Returns:
		 * void
		 */
		Session session = null;
		User u1 = null;
		try {
			u1 = new User(1,name,password);
			session = factory.openSession();
			session.beginTransaction();
			session.save(u1);
			session.getTransaction().commit();
			session.close();
		} catch(TodoListProjectException e) {
			throw e;
		}
	}
	
	public int getUserID(String name) {
		/**
		 * Returns a user ID as an int
		 * it gets the list of all users from the database
		 * and cycles through the list trying to find the given
		 * user name, if it finds it then returns the ID
		 * else it returns -1
		 * 
		 * Parameters:
		 * name - the user's name as string
		 * 
		 * Returns:
		 * int number for the user ID
		 */
		
		Session session = factory.openSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		// getting the list of all the users
		List<User> users = session.createQuery("from User").list();
		session.close();
		for (int i=0; i<users.size(); i++)
			if (users.get(i).getUsername().equals(name))
				return users.get(i).getId();
		
		return -1;
	}

	public boolean isUserExist(String name, String password) throws TodoListProjectException{
		/**
		 * Returns boolean value
		 * gets the users list from the database and cycles
		 * through the list trying to find the given user
		 * if the user exists in the database returns True
		 * else returns False.
		 * 
		 * Parameters:
		 * name - the user's name as a string
		 * password - the user's password as a string
		 * 
		 * Returns:
		 * boolean value depending if user was found or not
		 */
		
		Session session = factory.openSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		// getting the list of all the users
		List<User> users = session.createQuery("from User").list();
		session.close();
		for (int i=0; i<users.size(); i++){
			if (users.get(i).getUsername().equals(name)){
				if (users.get(i).getPassword().equals(password)){
					return true;
				}
				else
					throw new TodoListProjectException("Wrong Password!");
			}
		}
		return false;
	}
	
	/*			 items functions				*/
	
	public void addItem(String item, int userid, LocalDateTime enddate) throws TodoListProjectException{
		/**
		 * Adds a to-do item to the database
		 * first it checks for valid input by the user
		 * and throws exception to the controller if needed
		 * else it updates the database
		 * 
		 * Parameters:
		 * item - the todo description as a string
		 * userid - the user's ID to associate the todo with as an int
		 * enddate - the end date for the todo as a LocalDateTime
		 * 
		 * Returns:
		 * void
		 */
		
		Item newItem = null;
		try {
			newItem = new Item(1, item, userid);
			Session session = factory.openSession();
			session.beginTransaction();
			newItem.setEnddate(enddate);
			session.save(newItem);
			session.getTransaction().commit();
			session.close();
		} catch(TodoListProjectException e) {
			throw e;
		}
	}

	public List<Item> getItems(int userid) {
		/**
		 * Returns a list of items that associate with a given user ID
		 * 
		 * Parameters:
		 * userid - the user's ID as int
		 * 
		 * Returns:
		 * List<Item> - list of items
		 */
		
		Session session = factory.openSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		// getting the list of all the user's items based on the user's id
		List<Item> items = session.createQuery("from Item where userid="+userid).list();
		session.close();
		return items;
	}
	
	public void deleteItem(Item item) {
		/**
		 * Deletes an item from the database
		 * 
		 * Parameters:
		 * item - an Item object
		 * 
		 * Returns:
		 * void
		 */
		
		Session session = factory.openSession();
		session.beginTransaction();
		session.delete(item);
		session.getTransaction().commit();
		session.close();
	}
	
	public void modifyItem(Item newItem, Item oldItem) {
		/**
		 * Modifies an item's description,
		 * it deletes the old item and inserts the new to the database
		 * 
		 * Parameters:
		 * newItem - Item object that represent the item with the changes
		 * oldItem - the item before the changes to delete from the database
		 * 
		 * Returns:
		 * void
		 */
		
		Session session = factory.openSession();
		session.beginTransaction();
		session.delete(oldItem);
		session.save(newItem);
		session.getTransaction().commit();
		session.close();
	}
	
	public void modifyDate(Item newItem, Item oldItem){
		/**
		 * Modifies an item's end date,
		 * it deletes the old item and inserts the new to the database
		 * 
		 * Parameters:
		 * newItem - Item object that represent the item with the changes
		 * oldItem - the item before the changes to delete from the database
		 * 
		 * Returns:
		 * void
		 */
		
		Session session = factory.openSession();
		session.beginTransaction();
		session.delete(oldItem);
		session.save(newItem);
		session.getTransaction().commit();
		session.close();
	}
}