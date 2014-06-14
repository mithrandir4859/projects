package carrental.web.controllers.actions.impl.passportinfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import carrental.domain.User;
import carrental.domain.UserStatus;
import carrental.web.controllers.actions.annotations.Access;
import carrental.web.controllers.actions.annotations.Mapping;
import carrental.web.validators.ErrorContainer;

@Access(UserStatus.ADMIN)
@Mapping(view = "/passportInfo.jsp")
public class GetPassportInfoFormForAdmin extends GetPassportInfoForm {

	@Override
	protected void assignUserId(HttpServletRequest request, HttpServletResponse response, ErrorContainer errorContainer) {
		userId = parseInt(request, "userId", errorContainer);
		User user;
		if ((user = userDao.find(userId)) == null)
			errorContainer.addError("error.userNotFound");
		else
			request.setAttribute("user", user);
	}

}
