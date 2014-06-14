<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title><my:localized name="title.chooseInterval" /></title>
</head>
<body>
	<%@ include file="/../WEB-INF/views/jspf/menu.jsp"%>
	<my:localizedMap prefix="error." name="errorMap" />
	<p class="middle"><my:localized name="date.chooseDate" /></p>
	<form name="input" method="get" action="${appFolder }/vehiclesLoader.do">
		<table>
			<tr>
				<th></th>
				<th><my:localized name="date.day" /></th>
				<th><my:localized name="date.month" /></th>
				<th><my:localized name="date.year" /></th>
			</tr>
			<tr>
				<th><my:localized name="date.start" /></th>
				<td><my:dropdownList name="startDay" start="1" end="32" /></td>
				<td><my:dropdownList name="startMonth" start="1" end="13" /></td>
				<td><my:dropdownList name="startYear" start="${currentYear }" end="${currentYear + 5}" /></td>
			</tr>
			<tr>
				<th><my:localized name="date.end" /></th>
				<td><my:dropdownList name="endDay" start="1" end="32" /></td>
				<td><my:dropdownList name="endMonth" start="1" end="13" /></td>
				<td><my:dropdownList name="endYear" start="${currentYear }" end="${currentYear + 5}" /></td>
			</tr>
		</table>
		<hr />

		
		<div class="middle">
			<input type="submit" value="<my:localized name="date.searchCar" />" class="large" />
		</div>
	</form>

