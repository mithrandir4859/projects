<title><my:localized name="title.passportInfo" /></title>
</head>
<body>
	<%@ include file="/../WEB-INF/views/jspf/menu.jsp"%>
	<my:authorize access="USER"><c:set var="action" value="/carrental/updatePassportInfo.do"/></my:authorize>
	<my:authorize access="ADMIN"><c:set var="action" value="/carrental/updatePassportInfoAdmin.do"/></my:authorize>
	
	<form method="post" action="${action }">

		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib prefix="my" uri="/WEB-INF/custom.tld"%>

		<div class="middle">
			<my:localizedList name="errorList" />
		</div>
		<table>
			<my:authorize access="ADMIN">
			
			<tr><th><my:localized name="user.userId" /></th>
			<td>${user.userId }</td></tr>
			
			<tr><th><my:localized name="user.email" /></th>
			<td>${user.email }</td></tr>
			
			<tr><th><my:localized name="user.firstname" /></th>
			<td>${user.firstname }</td></tr>
			
			<tr><th><my:localized name="user.lastname" /></th>
			<td>${user.lastname }</td></tr>
			
			<tr><th><my:localized name="user.phone" /></th>
			<td>${user.phone }</td></tr>
			
			</my:authorize>
			<c:set var="prefix" value="passportInfo" />
			<my:advancedInput label="${prefix }.series" />
			<my:advancedInput label="${prefix }.number" />
			<tr>
				<td colspan="2">
					<textarea rows="4" cols="60" name="additionalInfo">
							${additionalInfo }
						</textarea>
					</td>
			</tr>
			<tr>
			<th><my:localized name="date.issued" /></th>
			<td><my:dropdownList name="issuedDay" start="1" end="32" />
			<my:dropdownList name="issuedMonth" start="1" end="13" />
			<input type="text" width="4" name="issuedYear" value="${issuedYear }" />
			</td>
			</tr>
		</table>
		
		<my:authorize access="ADMIN">
		<input type="hidden" value="${user.userId }" name="userId" /></my:authorize>

		<hr />
		<div class="middle">
			<input type="submit" value="<my:localized name="${prefix }.submit" />"
				class="large" />
		</div>
	</form>