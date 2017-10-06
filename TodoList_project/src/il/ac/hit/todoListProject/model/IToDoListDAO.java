package il.ac.hit.todoListProject.model;

import java.time.LocalDateTime;
import java.util.List;

public interface IToDoListDAO {
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
	void addUser(String name, String password) throws TodoListProjectException;
	
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
	int getUserID(String name);
	
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
	boolean isUserExist(String name, String password) throws TodoListProjectException;
	
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
	void addItem(String item, int userid, LocalDateTime enddate) throws TodoListProjectException;
	
	/**
	 * Returns a list of items that associate with a given user ID
	 * 
	 * Parameters:
	 * userid - the user's ID as int
	 * 
	 * Returns:
	 * List<Item> - list of items
	 */
	List<Item> getItems(int userid);
	
	/**
	 * Deletes an item from the database
	 * 
	 * Parameters:
	 * item - an Item object
	 * 
	 * Returns:
	 * void
	 */
	void deleteItem(Item item);
	
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
	void modifyItem(Item newItem, Item oldItem);
	
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
	void modifyDate(Item newItem, Item oldItem);
}
