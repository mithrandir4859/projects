package carrental.web.controllers.actions.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import carrental.domain.OrderHistory;
import carrental.domain.OrderStatus;
import carrental.domain.UserStatus;
import carrental.web.controllers.actions.GenericAction;
import carrental.web.controllers.actions.annotations.Access;
import carrental.web.controllers.actions.annotations.Mapping;
import carrental.web.validators.ErrorContainer;

@Access(UserStatus.USER)
@Mapping(view = "/displayMyOrders.do")
public class CancelOrder extends GenericAction {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, ErrorContainer errorContainer) throws ServletException,
			IOException{
		Integer orderId = parseInt(request, "orderId", errorContainer);
		if (errorContainer.hasErrors())	return;
		orderDao.update(orderId, OrderStatus.CANCELLED_BY_USER);
		OrderHistory orderHistory = new OrderHistory();
		orderHistory.setOrderId(orderId);
		orderHistory.setOrderStatus(OrderStatus.CANCELLED_BY_USER);
		orderHistory.setPayment(0);
		orderHistoryDao.create(orderHistory);
		logger.info("Order " + orderId + " is cancelled by user");
	}

}
