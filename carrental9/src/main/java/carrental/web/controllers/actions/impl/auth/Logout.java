package carrental.web.controllers.actions.impl.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import carrental.domain.UserStatus;
import carrental.web.controllers.actions.GenericAction;
import carrental.web.controllers.actions.annotations.Access;
import carrental.web.controllers.actions.annotations.Mapping;
import carrental.web.validators.ErrorContainer;

@Access({ UserStatus.USER, UserStatus.ADMIN })
@Mapping(view = "redirect:/carrental/getLoginForm.do")
public class Logout extends GenericAction {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, ErrorContainer errorContainer) throws ServletException,
			IOException {
		request.getSession().removeAttribute("user");
	}

}
