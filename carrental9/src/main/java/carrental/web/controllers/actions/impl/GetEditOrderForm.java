package carrental.web.controllers.actions.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import carrental.domain.Order;
import carrental.domain.OrderStatus;
import carrental.domain.User;
import carrental.domain.UserStatus;
import carrental.domain.Vehicle;
import carrental.web.controllers.actions.GenericAction;
import carrental.web.controllers.actions.annotations.Access;
import carrental.web.controllers.actions.annotations.Mapping;
import carrental.web.validators.ErrorContainer;

@Access(UserStatus.ADMIN)
@Mapping(view = "/editOrder.jsp")
public class GetEditOrderForm extends GenericAction {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, ErrorContainer errorContainer) throws ServletException,
			IOException {
		Integer orderId = parseInt(request, "orderId", errorContainer);
		if (errorContainer.hasErrors()){
			errorContainer.addError("orderId.empty");
			return;
		}
		
		Order order = orderDao.find(orderId);
		if (order == null){
			errorContainer.addError("orderNotFound");
			return;
		}
		
		Vehicle vehicle = vehicleDao.find(order.getVehicleId());
		User user = userDao.find(order.getUserId());
		order.setOrderHistoryList(orderHistoryDao.find(order.getOrderId()));
		
		request.setAttribute("order", order);
		request.setAttribute("vehicle", vehicle);
		request.setAttribute("user", user);
		request.setAttribute("orderStatuses", OrderStatus.values());
	}

}
