package carrental.web.controllers.actions.impl.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
@Mapping(view = "redirect:/carrental/registerSuccess.do", errorView = "/register.jsp")
public class Register extends GenericAction {
	private static final String[] paramNames = { "email", "password", "firstname", "lastname", "phone" };

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, ErrorContainer errorContainer) throws ServletException,
			IOException {

		Map<String, String> requestParams = new HashMap<>();
		for (String paramName : paramNames) {
			String currentParamValue = request.getParameter(paramName);
			requestParams.put(paramName, currentParamValue);
			validator.check(paramName, currentParamValue, errorContainer);
		}
		
		if (!Objects.equals(requestParams.get("password"), request.getParameter("passwordConfirm")))
			errorContainer.addError("differentPasswords", "passwordConfirm");

		if (errorContainer.hasErrors())
			return;
		
		int i = 0;
		User user = new User(
				requestParams.get(paramNames[i++]),
				requestParams.get(paramNames[i++]),
				requestParams.get(paramNames[i++]),
				requestParams.get(paramNames[i++]),
				requestParams.get(paramNames[i++]),
				UserStatus.USER,
				null);
		
		userDao.create(user);
		logger.info("New user " + user.getUserId() + ", " + user.getEmail() + " is created");
	}
}
