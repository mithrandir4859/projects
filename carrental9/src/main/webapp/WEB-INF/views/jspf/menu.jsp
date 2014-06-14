<c:set var="appFolder" value="<%=request.getContextPath()%>" />

<div class="middle">
	<img src="/carrental/resources/logo.png" alt="Car Rental">
</div>
<br />
<div class="middle">

	<ul class="menu">
		<li><a href="${appFolder}"><my:localized name="menu.home" /></a></li>

		<my:authorize access="USER">
			<li><a href="${appFolder}/getChooseDates.do"><my:localized
						name="menu.makeOrder" /></a></li>
			<li><a href="${appFolder}/displayMyOrders.do"><my:localized
						name="menu.myOrders" /></a></li>
			<li><a href="${appFolder}/getPassportInfoForm.do"><my:localized
						name="menu.passportInfo" /></a></li>
		</my:authorize>

		<my:authorize access="ANON">
			<li><a href="${appFolder}/getLoginForm.do"><my:localized
						name="menu.login" /></a></li>
			<li><a href="${appFolder}/getRegisterForm.do"><my:localized
						name="menu.register" /></a></li>
		</my:authorize>

		<my:authorize access="ADMIN">
			<li><a href="${appFolder}/selectOrders.do"><my:localized
						name="menu.selectOrders" /></a></li>
		</my:authorize>

		<my:authorize access="ADMIN or USER">
			<li><a href="${appFolder}/logout.do"><my:localized
						name="menu.logout" /></a></li>
		</my:authorize>

	</ul>
</div>


<my:authorize access="ADMIN or USER">
	<p align="right"><my:localized name="menu.welcome" />, ${sessionScope.user.firstname} ${sessionScope.user.lastname }</p>
</my:authorize>
<hr />

<my:localizedList name="errorList" prefix="error."/>