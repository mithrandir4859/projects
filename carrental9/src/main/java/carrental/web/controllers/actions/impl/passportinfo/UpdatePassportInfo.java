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
@Mapping(view = "/getPassportInfoForm.do")
public class UpdatePassportInfo extends GenericAction {

	protected Integer userId;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, ErrorContainer errorContainer) throws ServletException,
			IOException {

		setUserId(request, response, errorContainer);
		if (errorContainer.hasErrors())
			return;

		String series = request.getParameter("series");
		validator.check("series", series, errorContainer);

		Integer number = parseInt(request, "number", errorContainer);

		request.getParameter("additionalInfo");
		String additionalInfo = request.getParameter("additionalInfo");

		Integer issuedDay = parseInt(request, "issuedDay", errorContainer);
		Integer issuedMonth = parseInt(request, "issuedMonth", errorContainer);
		Integer issuedYear = parseInt(request, "issuedYear", errorContainer);

		if (errorContainer.hasErrors())
			return;

		try {
			LocalDate issuedDate = new LocalDate(issuedYear, issuedMonth, issuedDay);
			PassportInfo passportInfo = new PassportInfo(userId, series, number, additionalInfo, issuedDate.toDateTimeAtStartOfDay().getMillis());
			if (passportInfoDao.find(userId) == null)
				passportInfoDao.create(passportInfo);
			else
				passportInfoDao.update(passportInfo);
		} catch (IllegalArgumentException e) {
			// errorContainer.
		}
	}

	protected void setUserId(HttpServletRequest request, HttpServletResponse response, ErrorContainer errorContainer) {
		userId = ((User) request.getSession().getAttribute("user")).getUserId();
	}
}
