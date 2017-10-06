package il.ac.hit.todoListProject.driver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import il.ac.hit.todoListProject.model.HibernateToDoListDAO;
import il.ac.hit.todoListProject.model.Item;

/*
 * A class to test the model without the view and the controller
 */
public class Driver {
	public static void main(String[] args) throws Exception {
		HibernateToDoListDAO instance = HibernateToDoListDAO.getInstance();
		Scanner in = new Scanner(System.in);
		String username=null, password=null;
		
		int option=0;
		System.out.println("Please pick an option:\n1. Login\n2. Create User\n3. Exit\nOption: ");
		option = in.nextInt();
		in.nextLine();
		do {
			while(option==1) {
				System.out.println("Enter username: ");
				username = in.nextLine();
				System.out.println("Enter password: ");
				password = in.nextLine();
				if (instance.isUserExist(username, password)) {
					System.out.println("Login Successful!");
					option=3;
				}
				else {
					System.out.println("User doesn't exist!");
					System.out.println("Try again?(Y/N) ");
					String cont = in.nextLine();
					if (cont.equals("N"))
						option=3;
				}
			}
			
			if(option==2){
				System.out.println("Enter username: ");
				username = in.nextLine();
				System.out.println("Enter password: ");
				password = in.nextLine();
				
				if (instance.isUserExist(username, password))
					System.out.println("User already exists!");
				else
				{
					instance.addUser(username, password);
					System.out.println("User created!");
				}
				option=3;
			}
		}while (option!=3);
		
		if(username!=null && password!=null) {
			option=0;
			int id = instance.getUserID(username);		// get user id
			List<Item> todos = instance.getItems(id);	// get user items
			do {
				System.out.println("Please pick an option:\n1. Show Items\n2. Add an Item\n3. Modify an Item\n4. Delete an Item\n5. Exit\nOption: ");
				option = in.nextInt();
				in.nextLine();
				if (option==1) {	// Show Items
					if (!todos.isEmpty())
						for (int i=0; i<todos.size(); i++) {
							System.out.println(i+1 + ". " + todos.get(i));
						}
					else
						System.out.println("There are no items!");
				}
				
				else if (option==2) {	// Add an Item
					System.out.println("Enter a To-Do: ");
					String todo = in.nextLine();
					System.out.println("Enter an end date for this To-Do:(in format dd mm yyyy hh mm) ");
					String itemdate = in.nextLine();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy HH mm");
					LocalDateTime endtime = LocalDateTime.parse(itemdate, formatter);
					instance.addItem(todo, id, endtime);
				}
				/*
				else if (option==3) {	// Modify an Item
					if (!todos.isEmpty()) {
						for (int i=0; i<todos.size(); i++) {
							System.out.println(i+1 + ". " + todos.get(i));
						}
						System.out.println("Choose which item to modify: ");
						int item_id = in.nextInt();
						in.nextLine();
						System.out.println("Enter change: ");
						String change = in.nextLine();
						instance.modifyItem(item_id, change);
					}
					else
						System.out.println("There are no items!");
				}
				else if (option==4) {	// Delete an Item
					System.out.println("Enter an item ID to delete: ");
					int itemid = in.nextInt();
					in.nextLine();
					instance.deleteItem(itemid); 
				}*/
			}while(option!=5);
		}
		
		
		in.close();
	}
}
