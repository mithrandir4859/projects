<%@ page import="carrental.domain.*"%>
<title><my:localized name="title.displaySelectedOrders" /></title>
</head>
<body>

	<%@ include file="/../WEB-INF/views/jspf/menu.jsp"%>

	<table class="withBorders">
		<tr>
			<th width="140"><my:localized name="order.orderId" /></th>
	
			<my:authorize access="ADMIN">
				<th><my:localized name="user.userId" /></th>
			</my:authorize>

			<my:authorize access="USER">
				<th><my:localized name="vehicle.model" /></th>
			</my:authorize>

			<th width="170"><my:localized name="date.dates" /></th>
			<th><my:localized name="order.orderStatus" /></th>
			<my:authorize access="USER">
				<th><my:localized name="orderHistory.payment"/></th>
				<th><my:localized name="orderHistory.reason"/></th>
				<td>
					<!-- empty cell for "cancel" option -->
				</td>
			</my:authorize>
		</tr>

		<c:forEach var="order" items="${orderList}">
			<tr>
				<c:set var="vehicleId" value="${order.vehicleId }" />
				<c:set var="orderId" value="${order.orderId}" />

				<td>
				<my:authorize access="USER">
					<my:localized name="order.orderId" />: ${orderId }
					<br />
					<my:localized name="vehicle.vehicleId" />: ${vehicleId }
				</my:authorize>
					
				<my:authorize access="ADMIN">
					<form action="${appFolder }/getEditOrderForm.do">
						<input type="hidden" name="orderId" value="${orderId }" />
						<my:localized name="order.orderId" />:
						<input type="submit" value="${orderId }" />
					</form>
					<my:localized name="vehicle.vehicleId" />: ${vehicleId }
				</my:authorize>
				</td>

				<my:authorize access="ADMIN">
					<td><form action="${appFolder }/getPassportInfoFormForAdmin.do" method="get">
					<input type="hidden" name="userId" value="${order.userId }" />
					<input type="submit" value="${order.userId }" />
					</form></td>
				</my:authorize>

				<my:authorize access="USER">
					<td>${vehicleMap[vehicleId].model }</td>
				</my:authorize>

				<td>
				<my:localized name="date.start" /> ${order.startDate}
				<br />
				<my:localized name="date.end" /> ${order.endDate }
				</td>
				
				<td><my:localized name="orderStatus.${order.orderStatus}" /></td>

				<my:authorize access="USER">
					<c:forEach var="orderHistory" items="${order.orderHistoryList }">
						<td>${orderHistory.payment }</td>
						<td>${orderHistory.reason }</td>
					</c:forEach>
					<td>
						<form action="${appFolder}/cancelOrder.do" method="POST">
							<input type="hidden" value="${orderId }" name="orderId" /> <input
								type="submit" value="<my:localized name="cancel"/>" />
						</form>
					</td>
					
				</my:authorize>
				
				
			</tr>

		</c:forEach>
	</table>