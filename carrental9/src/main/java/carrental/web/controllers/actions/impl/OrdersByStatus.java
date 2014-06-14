package carrental.web.controllers.actions.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import carrental.domain.Order;
import carrental.domain.OrderStatus;
import carrental.domain.UserStatus;
import carrental.web.controllers.actions.GenericAction;
import carrental.web.controllers.actions.annotations.Access;
import carrental.web.controllers.actions.annotations.Mapping;
import carrental.web.validators.ErrorContainer;

@Access(UserStatus.ADMIN)
@Mapping(view = "/displaySelectedOrders.jsp", errorView = "/selectOrders.do")
public class OrdersByStatus extends GenericAction {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, ErrorContainer errorContainer) throws ServletException,
			IOException {

		EnumSet<OrderStatus> statuses = EnumSet.noneOf(OrderStatus.class);
		String[] orderStatuses = request.getParameterValues("orderStatus");
		
		if (orderStatuses == null || orderStatuses.length == 0) {
			errorContainer.addError("emptyOrderStatuses");
			return;
		}
		
		for (String strStatus : orderStatuses)
			try {
				statuses.add(OrderStatus.valueOf(strStatus));
			} catch (IllegalArgumentException ex) {
				// just skip bad parameter
			}

		List<Order> orderList = orderDao.list();
		List<Order> filteredOrderList = new ArrayList<>();
		for (Order order : orderList)
			if (statuses.contains(order.getOrderStatus()))
				filteredOrderList.add(order);
		
		if (filteredOrderList.isEmpty()) {
			errorContainer.addError("noOrdersFound");
			return;
		}
		
		request.setAttribute("orderList", filteredOrderList);
	}

}
