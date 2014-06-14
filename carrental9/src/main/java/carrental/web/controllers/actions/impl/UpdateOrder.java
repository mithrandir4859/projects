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

@Access(UserStatus.ADMIN)
@Mapping(view = "/displayMessages.jsp")
public class UpdateOrder extends GenericAction {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response, ErrorContainer errorContainer) throws ServletException,
			IOException {
		OrderHistory orderHistory = new OrderHistory();

		OrderStatus orderStatus = null;
		try {
			String candidateOrderStatus = request.getParameter("orderStatus");
			orderStatus = OrderStatus.valueOf(candidateOrderStatus);
			orderHistory.setOrderStatus(orderStatus);
		} catch (IllegalArgumentException | NullPointerException ex) {
			errorContainer.addError("badOrderStatus");
		}
		
		{
			Integer payment = parseInt(request, "payment", errorContainer);
			orderHistory.setPayment(payment);
		}
		
		Integer orderId;
		{
			orderId = parseInt(request, "orderId", errorContainer);
			orderHistory.setOrderId(orderId);
		}
		
		{
			boolean hasReason = false;
			checkIfHasReason: {
				String[] hasReasonList = request.getParameterValues("hasReason");
				if (hasReasonList == null)
					break checkIfHasReason;
				for (String str : hasReasonList)
					if (str.equals("true")) {
						hasReason = true;
						break checkIfHasReason;
					}
			}

			if (hasReason){
				String reason = request.getParameter("reason");
				validator.errorEmpty("reason", reason, errorContainer);
				orderHistory.setReason(reason);
			}
		}
		
		if (errorContainer.hasErrors())
			return;
		
		request.setAttribute("message", "success");
		
		orderHistoryDao.create(orderHistory);
		orderDao.update(orderId, orderStatus);
	}

}
