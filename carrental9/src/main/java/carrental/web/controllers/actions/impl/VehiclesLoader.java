package carrental.web.controllers.actions.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

import carrental.domain.UserStatus;
import carrental.domain.Vehicle;
import carrental.web.controllers.actions.annotations.Access;
import carrental.web.controllers.actions.annotations.Mapping;
import carrental.web.validators.ErrorContainer;

@Access(UserStatus.USER)
@Mapping(view = "/displaySelectedVehicles.jsp", errorView = "/getChooseDates.do")
public class VehiclesLoader extends GetSubmitOrderForm {

	private static final String[] paramNames = { "startYear", "startMonth", "startDay", "endYear", "endMonth", "endDay" };

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, ErrorContainer errorContainer) throws ServletException,
			IOException {
		// Extract query parameters from request
		Map<String, Integer> dates = new HashMap<>();
		for (String paramName : paramNames)
			dates.put(paramName, parseInt(request, paramName, errorContainer));

		if (errorContainer.hasErrors()){
			errorContainer.addError("badDataFields");
			return;
		}

		LocalDate startDate = getDate(dates.get("startYear"), dates.get("startMonth"), dates.get("startDay"), "startDate", errorContainer);
		
		if (errorContainer.hasErrors())
			return;
		
		LocalDate endDate = getDate(dates.get("endYear"), dates.get("endMonth"), dates.get("endDay"), "endDate", errorContainer);

		if (errorContainer.hasErrors())
			return;

		if (startDate.compareTo(endDate) >= 0) {
			errorContainer.addError("endBeforeStart");
			return;
		}

		long startMillis = startDate.toDateTimeAtStartOfDay().getMillis();
		request.setAttribute("startMillis", startMillis);

		long endMillis = endDate.toDateTimeAtStartOfDay().getMillis();
		request.setAttribute("endMillis", endMillis);

		Interval interval = new Interval(startMillis, endMillis);
		List<Vehicle> vehicleList = vehicleDao.findAllAvailable(interval);
		request.setAttribute("vehicleList", vehicleList);
	}

	private static LocalDate getDate(int year, int month, int day, String name, ErrorContainer errorContainer) {
		try {
			return new LocalDate(year, month, day);
		} catch (org.joda.time.IllegalFieldValueException e) {
			errorContainer.addError("badDataFields");
			return null;
		}
	}

}
