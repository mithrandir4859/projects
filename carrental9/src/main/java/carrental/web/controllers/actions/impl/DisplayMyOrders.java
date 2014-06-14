package carrental.web.controllers.actions.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import carrental.domain.Order;
import carrental.domain.OrderHistory;
import carrental.domain.User;
import carrental.domain.UserStatus;
import carrental.domain.Vehicle;
import carrental.web.controllers.actions.GenericAction;
import carrental.web.controllers.actions.annotations.Access;
import carrental.web.controllers.actions.annotations.Mapping;
import carrental.web.validators.ErrorContainer;

@Access(UserStatus.USER)
@Mapping(view = "/displaySelectedOrders.jsp")
public class DisplayMyOrders extends GenericAction {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, ErrorContainer errorContainer) throws ServletException,
	IOException{
		Integer userId = ((User) request.getSession().getAttribute("user")).getUserId();
		List<Order> orderList = orderDao.findByUser(userId);
		Map<Integer, Vehicle> vehicleMap = new HashMap<>();
		for (Order order: orderList){
			Integer orderId = order.getOrderId();
			List<OrderHistory> orderHistories = new LinkedList<>();
			OrderHistory orderHistory = orderHistoryDao.findLatest(orderId);
			orderHistories.add(orderHistory);
			order.setOrderHistoryList(orderHistories);
			
			Integer vehicleId = order.getVehicleId();
			if (!vehicleMap.containsKey(vehicleId))
				vehicleMap.put(vehicleId, vehicleDao.find(vehicleId));
		}
		request.setAttribute("vehicleMap", vehicleMap);
		request.setAttribute("orderList", orderList);
	}

}
