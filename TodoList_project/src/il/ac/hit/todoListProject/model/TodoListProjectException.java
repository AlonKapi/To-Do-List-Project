package il.ac.hit.todoListProject.model;

public class TodoListProjectException extends Exception {
	
	/**
	 * Exception class for the project
	 * handles user's input exceptions
	 */
	private static final long serialVersionUID = 1L;

	public TodoListProjectException(String msg, Throwable rootcause) {
		super(msg, rootcause);
	}
	
	public TodoListProjectException(String msg) {
		super(msg);
	}
	
}
