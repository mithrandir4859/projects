package carrental.web.controllers.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action extends Cloneable{
	String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	Action clone();
}