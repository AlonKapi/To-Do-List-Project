package il.ac.hit.todoListProject.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class NameTag extends SimpleTagSupport {
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		out.print("<p class='text-dark'>Java EE server side project<br>Made By Alon Kaplanski</p>");
	}
}
