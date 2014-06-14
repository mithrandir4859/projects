<title><my:localized name="title.accessDenied" /></title>
</head>
<body>
	<%@ include file="/../WEB-INF/views/jspf/menu.jsp"%>
	<p class="middle">
		<my:authorize access="ANON">
			<my:localized name="accessDenied.forAnon" />
		</my:authorize>
		<my:authorize access="USER ADMIN">
			<my:localized name="accessDenied.forUserAdmin" />
		</my:authorize>
	</p>