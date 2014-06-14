<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title><my:localized name="title.displayVehicles" /></title>
</head>
<body>
	<%@ include file="/../WEB-INF/views/jspf/menu.jsp"%>
	<p class="middle"><my:localized name="displayVehicles.chooseCar" /></p>

	<table class="withBorders">
		<tr>
			<th><my:localized name="vehicle.model" /></th>
			<th><my:localized name="vehicle.mileage" /></th>
			<th><my:localized name="vehicle.year" /></th>
			<th></th>
		</tr>
		<c:forEach var="vehicle" items="${vehicleList}">
			<tr>
				<form method="GET" action="${appFolder }/getSubmitOrderForm.do">
					<input type="hidden" value="${startMillis }" name="startMillis" />
					<input type="hidden" value="${endMillis }" name="endMillis" />
					<input type="hidden" name="vehicleId" value="${vehicle.vehicleId }" />
					<td>${vehicle.model }</td>
					<td>${vehicle.mileage }</td>
					<td>${vehicle.year }</td>
					<td><input type="submit" value="<my:localized name="displayVehicles.go" />" /></td>
				</form>
			</tr>
		</c:forEach>
	</table>

