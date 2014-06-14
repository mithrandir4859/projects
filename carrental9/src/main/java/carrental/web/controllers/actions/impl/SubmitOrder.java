package carrental.web.controllers.actions.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.Interval;

import carrental.domain.Order;
import carrental.domain.OrderHistory;
import carrental.domain.OrderStatus;
import carrental.domain.User;
import carrental.domain.UserStatus;
import carrental.web.controllers.actions.GenericAction;
import carrental.web.controllers.actions.annotations.Access;
import carrental.web.controllers.actions.annotations.Mapping;
import carrental.web.validators.ErrorContainer;

import com.almende.util.IntervalsUtil;

@Access(UserStatus.USER)
@Mapping(view = "redirect:/carrental/orderCreated.do")
public class SubmitOrder extends GenericAction {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, ErrorContainer errorContainer) throws ServletException,
	IOException {
		
		
		Integer vehicleId = parseInt(request, "vehicleId", errorContainer);
		Long startMillis = parseLong(request, "startMillis", errorContainer);
		Long endMillis = parseLong(request, "endMillis", errorContainer);

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
		
		
		User user = (User) request.getSession().getAttribute("user");
		Order order = new Order(user.getUserId(), vehicleId, startMillis, endMillis,
				OrderStatus.WAITING_FOR_PHONE_CONFIRMATION, null);
		OrderHistory orderHistory = new OrderHistory();
		orderDao.create(order);
		orderHistory.setOrderId(order.getOrderId());
		orderHistory.setOrderStatus(order.getOrderStatus());
		orderHistory.setPayment(0);
		orderHistoryDao.create(orderHistory);
		logger.info("Order " + order.getOrderId() + " was created");
		
		
	}
	
}
