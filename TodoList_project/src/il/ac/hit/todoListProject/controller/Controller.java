package il.ac.hit.todoListProject.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import il.ac.hit.todoListProject.model.HibernateToDoListDAO;
import il.ac.hit.todoListProject.model.Item;
import il.ac.hit.todoListProject.model.TodoListProjectException;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller/*")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String str = request.getRequestURL().toString();
		
		// forward to items view
		if (str.endsWith("items")) {
			getServletContext().getRequestDispatcher("/items.jsp").forward(request, response);
		}
		
		// forward to add view
		else if (str.endsWith("add")) {
			getServletContext().getRequestDispatcher("/additem.jsp").forward(request, response);
		}
		
		// invalidate the session and forward to home page view
		else if (str.endsWith("logout")) {
			request.getSession().invalidate();
			getServletContext().getRequestDispatcher("/homepage.jsp").forward(request, response);
		}
		
		// forward to menu view
		else if (str.endsWith("menu")) {
			getServletContext().getRequestDispatcher("/menu.jsp").forward(request, response);
		}
		
		/*
		 *  gets the item the user selected to delete and deletes it using the model,
		 *  updates the user items attribute and forwards to items view
		 */
		else if (str.endsWith("delete")) {
			Item toDelete = (Item) request.getSession().getAttribute("itemSelected");
			int userid = toDelete.getUserid();
			try {
				HibernateToDoListDAO _instance = HibernateToDoListDAO.getInstance();
				_instance.deleteItem(toDelete);
				request.getSession().setAttribute("items", _instance.getItems(userid));
				getServletContext().getRequestDispatcher("/items.jsp").forward(request, response);
			} catch (TodoListProjectException e) {
				e.printStackTrace();
			}
		}
		
		// request for change description, forward to the view
		else if(str.endsWith("changeDesc")) {
			getServletContext().getRequestDispatcher("/changeDesc.jsp").forward(request, response);
		}
		
		// request for change time, forward to the view
		else if(str.endsWith("changeTime")) {
			getServletContext().getRequestDispatcher("/changeTime.jsp").forward(request, response);
		}
		
		/*
		 * tries to get user name using session attribute that was attached from the cookies,
		 * if fails then forward to users view to login/register else forward to post
		 */
		else if (str.endsWith("users")) {
			String name = (String)request.getSession().getAttribute("name");
			
			if (name == null) {
				getServletContext().getRequestDispatcher("/users.jsp").forward(request, response);
			}
			
			else
				this.doPost(request, response);
			
		} 
		
		/*
		 * default is forwarding to home page view, tries to get the user's cookies
		 * if there are cookies then updates the session attribute
		 */
		else {
			Cookie[] cookies = request.getCookies();
			String name = null;
			String password = null;
			
			if (cookies != null){
				for (Cookie c : cookies){
					if (c.getName().equals("username"))
						name = c.getValue();
					if (c.getName().equals("password"))
						password = c.getValue();
				}
				request.getSession().setAttribute("name", name);
				request.getSession().setAttribute("password", password);
			}
			request.getSession().setAttribute("Exception", null);
			getServletContext().getRequestDispatcher("/homepage.jsp").forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String str = request.getRequestURL().toString();
		// boolean variable used to skip user check if the name and password came from cookies
		boolean cookiesData = false;
		
		/*
		 *  request coming from users view
		 *  first tries to get user name and password either by parameter or by session attribute,
		 *  after that checks if the user exists for precaution
		 *  get and set his items list as session attribute, set cookies and forwards to items view
		 */
		if (str.endsWith("users")) {
			try {
				// either gets the name and password from the form or from cookies which were stored as session attributes
				String name = (String)request.getParameter("name");
				String password = (String)request.getParameter("password");

				if (name == null) {
					name = (String)request.getSession().getAttribute("name");
					password = (String)request.getSession().getAttribute("password");
					cookiesData = true;
				}
				
				HibernateToDoListDAO _instance = HibernateToDoListDAO.getInstance();
				// login / register
				if (cookiesData == false) {
					if (!_instance.isUserExist(name, password)) {
						_instance.addUser(name, password);
					}
					request.getSession().setAttribute("name", name);
					request.getSession().setAttribute("password", password);
				}
				int userid = _instance.getUserID(name);
				request.getSession().setAttribute("items", _instance.getItems(userid));
				Cookie c1 = new Cookie("username", name);
				Cookie c2 = new Cookie("password", password);
				c1.setMaxAge(6000);
				c2.setMaxAge(6000);
				response.addCookie(c1);
				response.addCookie(c2);
				request.getSession().setAttribute("Exception", null);
				getServletContext().getRequestDispatcher("/items.jsp").forward(request, response);
				
			} catch (TodoListProjectException e) {
				request.getSession().setAttribute("Exception", e.getMessage());
				getServletContext().getRequestDispatcher("/users.jsp").forward(request, response);
			}
		}
		
		/*
		 *  request coming from add item view
		 *  gets the relevant info and process it
		 *  calls the additem function from the model to add the item to the database
		 *  updates the user items list and forwards to items view
		 */
		else if (str.endsWith("add")) {
			try {
				String description = (String)request.getParameter("description");
				String date = (String)request.getParameter("date");
				if (date.isEmpty())
					throw new TodoListProjectException("Error Empty Date!");
				String[] date_split = date.split("/");
				String time = (String)request.getParameter("time");
				if (time.isEmpty())
					throw new TodoListProjectException("Error Empty Time!");
				String[] time_split = time.split(":");
				String item_date = date_split[0] + " " + date_split[1] + " " + date_split[2] + " " + time_split[0] + " " + time_split[1];
				
				HibernateToDoListDAO _instance = HibernateToDoListDAO.getInstance();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy HH mm");
				LocalDateTime endtime = LocalDateTime.parse(item_date, formatter);
				String name = (String)request.getSession().getAttribute("name");
				int userid = _instance.getUserID(name);
				_instance.addItem(description, userid, endtime);
				request.getSession().setAttribute("items", _instance.getItems(userid));
				request.getSession().setAttribute("Exception", null);
				getServletContext().getRequestDispatcher("/items.jsp").forward(request, response);
				
			} catch(TodoListProjectException e) {
				request.getSession().setAttribute("Exception", e.getMessage());
				getServletContext().getRequestDispatcher("/additem.jsp").forward(request, response);
			}
		}
		
		/*
		 *  request coming from items view
		 *  gets the index the user selected using the radio buttons
		 *  sets a session attribute as the selected item and forwards to menu view
		 */
		else if (str.endsWith("menu")) {
			String radio = (String)request.getParameter("options");
			System.out.println(radio);
			int itemindex = Integer.parseInt(radio);
			@SuppressWarnings("unchecked")
			java.util.List<Item> items = (java.util.List<Item>)request.getSession().getAttribute("items");
			Item selected = items.get(itemindex);
			request.getSession().setAttribute("itemSelected", selected);
			getServletContext().getRequestDispatcher("/menu.jsp").forward(request, response);
		}
		
		/*
		 *  request coming from menu view
		 *  gets the description the user entered
		 *  copies the old item and change the description
		 *  sends both to the model so the old can be deleted and the new inserted
		 */
		else if(str.endsWith("changeDesc")) {
			try {
				Item toDel = (Item) request.getSession().getAttribute("itemSelected");
				Item toUpdate = toDel;
				String newDesc = (String)request.getParameter("description");
				toUpdate.setTodo(newDesc);
				
				HibernateToDoListDAO _instance = HibernateToDoListDAO.getInstance();
				_instance.modifyItem(toUpdate, toDel);
				int userid = toUpdate.getUserid();
				request.getSession().setAttribute("items", _instance.getItems(userid));
				request.getSession().setAttribute("Exception", null);
				getServletContext().getRequestDispatcher("/items.jsp").forward(request, response);
				
			} catch (TodoListProjectException e) {
				request.getSession().setAttribute("Exception", e.getMessage());
				getServletContext().getRequestDispatcher("/changeDesc.jsp").forward(request, response);
			}
		}
		
		/*
		 *  request coming from menu view
		 *  gets the date the user entered
		 *  copies the old item and change the date
		 *  sends both to the model so the old can be deleted and the new inserted
		 */
		else if(str.endsWith("changeTime")) {
			try {
				Item toDel = (Item) request.getSession().getAttribute("itemSelected");
				Item toUpdate = toDel;
				String date = (String)request.getParameter("date");
				if (date.isEmpty())
					throw new TodoListProjectException("Error Empty Date!");
				String[] date_split = date.split("/");
				String time = (String)request.getParameter("time");
				if (time.isEmpty())
					throw new TodoListProjectException("Error Empty Time!");
				String[] time_split = time.split(":");
				String item_date = date_split[0] + " " + date_split[1] + " " + date_split[2] + " " + time_split[0] + " " + time_split[1];
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy HH mm");
				LocalDateTime newDate = LocalDateTime.parse(item_date, formatter);
				toUpdate.setEnddate(newDate);
				
				HibernateToDoListDAO _instance = HibernateToDoListDAO.getInstance();
				_instance.modifyDate(toUpdate, toDel);
				int userid = toUpdate.getUserid();
				request.getSession().setAttribute("items", _instance.getItems(userid));
				request.getSession().setAttribute("Exception", null);
				getServletContext().getRequestDispatcher("/items.jsp").forward(request, response);
				
			} catch (TodoListProjectException e) {
				request.getSession().setAttribute("Exception", e.getMessage());
				getServletContext().getRequestDispatcher("/changeTime.jsp").forward(request, response);
			}
		}
	}
}
