package carrental.web.controllers.actions.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDate;

import carrental.domain.UserStatus;
import carrental.web.controllers.actions.GenericAction;
import carrental.web.controllers.actions.annotations.Access;
import carrental.web.controllers.actions.annotations.Mapping;
import carrental.web.validators.ErrorContainer;

@Access(UserStatus.USER)
@Mapping(view = "/chooseDateIntervalForOrder.jsp")
public class GetChooseDates extends GenericAction {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, ErrorContainer errorContainer) throws ServletException,
	IOException{
		LocalDate now = LocalDate.now();
		Integer year = now.get(DateTimeFieldType.year());
		Integer month = now.get(DateTimeFieldType.monthOfYear());
		Integer day = now.get(DateTimeFieldType.dayOfMonth());
		request.setAttribute("currentYear", year);
		setAttributeIfOldValueIsNull(request, "startYear", year);
		setAttributeIfOldValueIsNull(request, "endYear", year);
		setAttributeIfOldValueIsNull(request, "startMonth", month);
		setAttributeIfOldValueIsNull(request, "endMonth", month);
		setAttributeIfOldValueIsNull(request, "startDay", day);
		setAttributeIfOldValueIsNull(request, "endDay", day);
	}

}
