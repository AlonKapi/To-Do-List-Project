package il.ac.hit.todoListProject.model;

import java.time.*;

public class Item {
	// item unique identifier in the system
	private int id;
	// the actual item description
	private String todo;
	// the user id with which the item associates with
	private int userid;
	// start date is the date when the item first created, end date is given by the user
	private LocalDateTime startdate;
	private LocalDateTime enddate;
	
	public Item() {
		super();
	}
	
	public Item(int id, String todo, int userid) throws TodoListProjectException{
		super();
		setId(id);
		setTodo(todo);
		setUserid(userid);
		setStartdate(LocalDateTime.now());
	}
	
	/*		getters and setters		*/
	public int getId() {
		return id;
	}

	public void setId(int id) throws TodoListProjectException{
		/*
		 * id is set automatically by the system
		 */
		try {
			this.id = id;
		}catch(Exception e) {
			throw new TodoListProjectException("Error Assigning Item ID", e.getCause());
		}

	}

	public String getTodo() {
		return todo;
	}

	public void setTodo(String todo) throws TodoListProjectException {
		  /*
		   * to do must be at least 3 chars long
		   * doesn't start with space
		   * and doesn't start with a number
		   **/

		if (todo.length() > 2 && !todo.startsWith(" ") && !Character.isDigit(todo.charAt(0)) && todo != null && !todo.isEmpty()) {
			this.todo = todo;
		}
		else
			throw new TodoListProjectException("Wrong Description Input!");
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) throws TodoListProjectException{
		/*
		 * user ID is set automatically according to the given user
		 */
		try {
			this.userid = userid;
		}catch(Exception e) {
			throw new TodoListProjectException("Error Assigning User ID to the item!", e.getCause());
		}
	}

	public LocalDateTime getStartdate() {
		return startdate;
	}

	public void setStartdate(LocalDateTime startdate) throws TodoListProjectException{
	    /*
		*  start date is given automatically as the date the item was created at
		*/
		try {
			this.startdate = startdate;
		}catch(Exception e) {
			throw new TodoListProjectException("Error Assigning Start Date to Item!", e.getCause());
		}
	}

	public LocalDateTime getEnddate() {
		return enddate;
	}

	public void setEnddate(LocalDateTime enddate) throws TodoListProjectException {
		/*
		 *  need to make sure user enters a date that is after the start date
		 */
		if (enddate.isAfter(startdate) && enddate != null) {
			this.enddate = enddate;
		}
		else
			throw new TodoListProjectException("End Date Must Be After Start Date!");
	}
	
	@Override
	public String toString() {
		return "Item [id=" + id + ", todo=" + todo + ", userid=" + userid + ", startdate=" + startdate + ", enddate="
				+ enddate + "]";
	}
}
