<title><my:localized name="title.editOrder" /></title>
</head>
<body>
	<%@ include file="/../WEB-INF/views/jspf/menu.jsp"%>
	<p>
		<my:localized name="editOrder.editOrderWithNum" />${order.orderId }
		(<my:localized name="orderStatus.${order.orderStatus }" />)
	</p>
	<table class="withBorders">
		<tr>
			<th><my:localized name="user" /></th>
			<th><my:localized name="vehicle" /></th>
			<th><my:localized name="date.dates" /></th>
		</tr>
		<tr>
			<td>${user.firstname } ${user.lastname },
			<my:localized name="user.phone" />: ${user.phone }
			</td>
			<td>${vehicle.model }</td>
			<td><my:localized name="date.start" /> ${order.startDate }</td>
		</tr>
		<tr>
			<td>(<my:localized name="questionnaire.email" />: ${user.email },
				<my:localized name="user.userId" />: ${user.userId })
			</td>
			<td>(<my:localized name="vehicle.mileage" />: ${vehicle.mileage },
				<my:localized name="vehicle.year" />: ${vehicle.year } (<my:localized
					name="vehicle.vehicleId" />: ${vehicle.vehicleId })
			</td>
			<td><my:localized name="date.end" /> ${order.endDate }</td>
		</tr>
	</table>
	
	<br />

	<c:forEach var="orderHistory" items="${order.orderHistoryList }">
		<my:orderHistory value="${orderHistory }" />
		<br />
	</c:forEach>

	<form method="POST" class="middle"
		action="${appFolder }/updateOrder.do">
		<input type="hidden" name="orderId" value="${order.orderId }" />
		<table class="withBorders">
			<tr>
				<th><my:localized name="order.orderStatus" /></th>
				<td><select name="orderStatus">
						<c:forEach var="orderStatus" items="${orderStatuses }">
							<option value="${orderStatus }">
								<my:localized name="orderStatus.${orderStatus }" />
							</option>
						</c:forEach>
				</select></td>
			</tr>


			<my:advancedInput label="editOrder.payment" />
			<tr>
				<th colspan="2"><my:localized name="editOrder.sendReason" /> <input
					type="checkbox" name="hasReason" value="true"></th>
			</tr>

			<tr>
				<td colspan="2"><textarea rows="4" cols="60" name="reason"><my:localized
							name="editOrder.reason" /></textarea></td>
			</tr>

		</table>
		<hr />
				<input type="submit"
					value="<my:localized name="editOrder.updateOrder" />" class="large" />
	</form>