package carrental.web.controllers.actions.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

import carrental.domain.UserStatus;
import carrental.web.controllers.actions.GenericAction;
import carrental.web.controllers.actions.annotations.Access;
import carrental.web.controllers.actions.annotations.Mapping;
import carrental.web.validators.ErrorContainer;

import com.almende.util.IntervalsUtil;

@Access(UserStatus.USER)
@Mapping(view = "/submitOrder.jsp")
public class GetSubmitOrderForm extends GenericAction {
	protected Integer vehicleId;
	protected Long startMillis;
	protected Long endMillis;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, ErrorContainer errorContainer) throws ServletException,
			IOException {
		vehicleId = parseInt(request, "vehicleId", errorContainer);
		startMillis = parseLong(request, "startMillis", errorContainer);
		endMillis = parseLong(request, "endMillis", errorContainer);

		if (errorContainer.hasErrors())
			return;

		if (startMillis >= endMillis){
			errorContainer.addError("badMillis");
			return;
		}

		Interval currentBookingInteval = new Interval(startMillis, endMillis);
		List<Interval> alreadyBooked = vehicleDao.findWhenBooked(vehicleId);
		if (IntervalsUtil.overlaps(currentBookingInteval, alreadyBooked)) {
			errorContainer.addError("alreadyBooked");
			return;
		}

		request.setAttribute("startMillis", startMillis);
		request.setAttribute("endMillis", endMillis);
		request.setAttribute("vehicleId", vehicleId);

		request.setAttribute("vehicle", vehicleDao.find(vehicleId));
		request.setAttribute("startDate", new LocalDate(startMillis));
		request.setAttribute("endDate", new LocalDate(endMillis));
	}

}
