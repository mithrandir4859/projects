package carrental.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class OrderHistory extends SimpleTagSupport {

	private JspContext jspContext;
	private TagUtils utils;
	private carrental.domain.OrderHistory orderHistory;

	public void setValue(carrental.domain.OrderHistory orderHistory) {
		this.orderHistory = orderHistory;
	}

	@Override
	public void doTag() throws JspException, IOException {
		jspContext = getJspContext();
		utils = new TagUtils(jspContext);
		utils.print("<table class=\"withBorders\">");

		utils.print("<tr>");
		for (String str : new String[] { "orderHistory.date", "order.orderStatus", "orderHistory.payment" }) {
			utils.print("<th>");
			utils.localizedPrint(str);
			utils.print("</th>");
		}
		utils.print("</tr>");

		utils.print("<tr>");
		utils.print("<td>");
		utils.print(orderHistory.getChangeTime().toString());
		utils.print("</td>");
		utils.print("<td>");
		utils.localizedPrint("orderStatus." + orderHistory.getOrderStatus());
		utils.print("</td>");
		utils.print("<td>");
		utils.print(orderHistory.getPayment().toString());
		utils.print("</td>");
		utils.print("</tr>");
		
		String reason = orderHistory.getReason();
		if (reason != null && !reason.isEmpty()){
			utils.print("<tr><th colspan = \"3\">");
			utils.localizedPrint("orderHistory.reason");
			utils.print("</th></tr>");
			
			utils.print("<tr><td colspan = \"3\">");
			utils.print(reason);
			utils.print("</td></tr>");
		}

		utils.print("</table>");
	}

}
