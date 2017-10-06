package il.ac.hit.todoListProject.model;

public class User {
	// serves as the user unique identifier
	private int id;
	// the user's credentials with which he logins to the system
	private String username;
	private String password;
	
	public User() {
		super();
	}
	
	public User(int id, String name, String password) throws TodoListProjectException{
		super();
		setId(id);
		setUsername(name);
		setPassword(password);
	}
	/*		getters and setters		*/
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) throws TodoListProjectException {
		/*
		 * user name must be at least 2 chars long
		 * doesn't contain spaces
		 * and doesn't start with a number
		 **/

		if (username.length() > 1 && !username.contains(" ") && !Character.isDigit(username.charAt(0)) && username != null && !username.isEmpty()) {
			this.username = username;
		}
		else
			throw new TodoListProjectException("Wrong username input!");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) throws TodoListProjectException {
		try {
			/*
			 *  the system gives id so only need to check if an actual id has been given
			 */
			this.id = id;
		}catch(Exception e) {
			throw new TodoListProjectException("Error giving ID!", e.getCause());
		}
		
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws TodoListProjectException {
		/*
		 *  password must be at least 2 chars long
		 *  and doesn't contain spaces
		 */
		if (password.length() > 1 && !password.contains(" ") && password != null && !password.isEmpty()) {
			this.password = password;
		}
		else
			throw new TodoListProjectException("Wrong password input!");
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + username + ", password=" + password + "]";
	}
}
