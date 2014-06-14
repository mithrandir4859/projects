<%@page import="carrental.domain.OrderStatus"%>
<title><my:localized name="title.selectOrders" /></title>
</head>
<body>
	<%@ include file="/../WEB-INF/views/jspf/menu.jsp"%>
	<form action="${appFolder }/ordersByStatus.do" method="get">
		<table>
			<c:forEach var="orderStatus" items="<%=OrderStatus.values()%>">
				<tr>
					<th><my:localized name="orderStatus.${orderStatus}" /></th>
					<td><input type="checkbox" name="orderStatus" value="${orderStatus}" checked></td>
				</tr>
			</c:forEach>
		</table>
		<hr />
		<div class="middle">
			<input type="submit" class="large" value="<my:localized name="selectOrders.search" />" />
		</div>
	</form>