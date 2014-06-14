<title>Submit Order</title>
</head>
<body>

	<%@ include file="/../WEB-INF/views/jspf/menu.jsp"%>
	<p>Please, check next information and submit the order if you are
		sure</p>
	<form method="POST" action="${appFolder }/submitOrder.do">
		<input type="hidden" name="startMillis" value="${startMillis }" />
		<input type="hidden" name="endMillis" value="${endMillis }" />
		<input type="hidden" name="vehicleId" value="${vehicle.vehicleId }" />
		<table>
			<tr>
				<th>Start date:</th>
				<td>${startDate}</td>
			</tr>
			<tr>
				<th>End date:</th>
				<td>${endDate}</td>
			</tr>
			<tr>
				<th>Vehicle model:</th>
				<td>${vehicle.model}</td>
			</tr>
			<tr>
				<th>Vehicle year:</th>
				<td>${vehicle.year}</td>
			</tr>
			<tr>
				<th>Vehicle mileage:</th>
				<td>${vehicle.mileage}</td>
			</tr>
			<tr>
				<th>Vehicle id:</th>
				<td>${vehicle.vehicleId}</td>
		</table>
		<div class="middle">
			<input type="submit" value="Submit order"  class="large"/>
		</div>
	</form>



