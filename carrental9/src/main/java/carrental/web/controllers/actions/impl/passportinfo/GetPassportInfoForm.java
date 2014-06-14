package carrental.web.controllers.actions.impl.passportinfo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.LocalDate;

import carrental.domain.PassportInfo;
import carrental.domain.User;
import carrental.domain.UserStatus;
import carrental.web.controllers.actions.GenericAction;
import carrental.web.controllers.actions.annotations.Access;
import carrental.web.controllers.actions.annotations.Mapping;
import carrental.web.validators.ErrorContainer;

@Access(UserStatus.USER)
@Mapping(view = "/passportInfo.jsp")
public class GetPassportInfoForm extends GenericAction {
	protected Integer userId;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, ErrorContainer errorContainer) throws ServletException,
			IOException {
		assignUserId(request, response, errorContainer);
		
		if (errorContainer.hasErrors())
			return;
		
		PassportInfo passportInfo = passportInfoDao.find(userId);
		if (passportInfo == null)
			return;
		request.setAttribute("series", passportInfo.getSeries());
		request.setAttribute("number", passportInfo.getNumber());
		request.setAttribute("additionalInfo", passportInfo.getAdditionalInfo());

		LocalDate issuedDate = new LocalDate(passportInfo.getIssuedMillis());
		request.setAttribute("issuedDay", issuedDate.getDayOfMonth());
		request.setAttribute("issuedMonth", issuedDate.getMonthOfYear());
		request.setAttribute("issuedYear", issuedDate.getYear());
	}

	protected void assignUserId(HttpServletRequest request, HttpServletResponse response, ErrorContainer errorContainer) {
		userId = ((User) request.getSession().getAttribute("user")).getUserId();
	}

}
