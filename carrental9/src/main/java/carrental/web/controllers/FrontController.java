package carrental.web.controllers;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import carrental.web.PathAuthResolver;
import carrental.web.controllers.actions.Action;
import carrental.web.controllers.actions.ActionFactory;

@WebServlet(urlPatterns="*.do")
public class FrontController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	public static final String REDIRECT_PREFIX = "redirect:";

	protected ActionFactory actionFactory;
	protected PathAuthResolver pathAuthResolver;

	@Override
	public void init() {
		ServletContext ctx = getServletContext();
		actionFactory = (ActionFactory) ctx.getAttribute("actionFactory");
		pathAuthResolver = (PathAuthResolver) ctx.getAttribute("pathAuthResolver");
	}

	protected void forward(String path, HttpServletRequest request, HttpServletResponse response) throws IllegalArgumentException,
			NullPointerException, ServletException, IOException {
		if (path.startsWith(REDIRECT_PREFIX)) {
			path = path.substring(REDIRECT_PREFIX.length());
			response.sendRedirect(path);
		} else {
			if (path.endsWith(".jsp"))
				path = pathAuthResolver.getRealPath(path);
			request.getRequestDispatcher(path).forward(request, response);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String path = request.getServletPath();
		path = path.substring(1, path.length() - ".do".length());
		Action action = actionFactory.getAction(path);
		String viewPath = action.process(request, response);
		forward(viewPath, request, response);
	}

}
