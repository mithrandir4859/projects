<title><my:localized name="title.login" /></title>
</head>
<body>
	<%@ include file="/../WEB-INF/views/jspf/menu.jsp"%>
	<form name="input" action="/carrental/login.do" method="post">
		<div class="middle">
			<my:localizedList name="errorList" prefix="error." />
		</div>
		<table>
			<my:advancedInput label="questionnaire.email" />
			<my:advancedInput label="questionnaire.password" />
		</table>
		<hr />

		<div class="middle">
			<input type="submit" value="<my:localized name="login.submit" />"
				class="large" />
		</div>
	</form>