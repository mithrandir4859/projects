package carrental.web.controllers;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import carrental.domain.User;
import carrental.domain.UserStatus;
import carrental.web.PathAuthResolver;
import carrental.web.controllers.actions.ActionFactory;

@WebFilter(urlPatterns = "*.do")
public class AuthenticationFilter implements Filter {
	private static final String DEFAULT_ACCESS_DENIED_PAGE = "/accessDenied.jsp";
	private String accessDeniedPage = DEFAULT_ACCESS_DENIED_PAGE, notFoundPage;
	private ActionFactory actionFactory;
	protected PathAuthResolver pathAuthResolver;

	@Override
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String path = request.getServletPath();
		path = path.substring(1, path.length() - ".do".length());
		if (actionFactory.exists(path)) {
			HttpSession session = request.getSession(false);
			UserStatus userStatus;
			User user;

			if (session == null || (user = (User) session.getAttribute("user")) == null)
				userStatus = UserStatus.ANON;
			else
				userStatus = user.getUserStatus();
			if (actionFactory.isAuthorized(userStatus, path)){
				filterChain.doFilter(request, response);
			}
			else{
				String accessDenied = pathAuthResolver.getRealPath(accessDeniedPage);
				request.getRequestDispatcher(accessDenied).forward(request, response);
			}
		}
		else
			request.getRequestDispatcher(notFoundPage).forward(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ServletContext ctx = filterConfig.getServletContext();
		actionFactory = (ActionFactory) ctx.getAttribute("actionFactory");
		pathAuthResolver = (PathAuthResolver) ctx.getAttribute("pathAuthResolver");
	}

	@Override
	public void destroy() {
	}

}
