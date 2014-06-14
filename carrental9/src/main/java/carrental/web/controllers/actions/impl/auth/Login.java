package carrental.web.controllers.actions.impl.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import carrental.domain.User;
import carrental.domain.UserStatus;
import carrental.web.controllers.actions.GenericAction;
import carrental.web.controllers.actions.annotations.Access;
import carrental.web.controllers.actions.annotations.Mapping;
import carrental.web.validators.ErrorContainer;

@Access(UserStatus.ANON)
@Mapping(view = "redirect:/carrental/loginSuccess.do", errorView = "/login.jsp")
public class Login extends GenericAction {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, ErrorContainer errorContainer) throws ServletException,
			IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		validator.check("email", email, errorContainer);
		validator.check("password", password, errorContainer);
		if (errorContainer.hasErrors())
			return;

		User user = userDao.find(email, password);
		if (user == null) {
			errorContainer.addError("userNotFound");
			logger.info("Failed attempt to login using " + email);
		}

		if (errorContainer.hasErrors())
			return;
		
		request.getSession().setAttribute("user", user);
	}
}
