﻿<title>Home</title>
</head>
<body>
	<%@ include file="/../WEB-INF/views/jspf/menu.jsp"%>
	<h2>Построить веб-систему, поддерживающую заданную
		функциональность:</h2>
	<ol>
		<li>На основе сущностей предметной области создать классы их
			описывающие.</li>
		<li>Классы и методы должны иметь отражающую их функциональность
			названия и должны быть грамотно структурированы по пакетам.</li>
		<li>Оформление кода должно соответствовать Java Code Convention.</li>
		<li>Информацию о предметной области хранить в БД, для доступа
			использовать API JDBC с использованием пула соединений, стандартного
			или разработанного самостоятельно. В качестве СУБД рекомендуется
			MySQL или Derby.</li>
		<li>Приложение должно поддерживать работу с кириллицей (быть
			многоязычной), в том числе и при хранении информации в БД.</li>
		<li>Архитектура приложения должна соответствовать шаблону
			Model-View-Controller.</li>
		<li>При реализации алгоритмов бизнес-логики использовать шаблоны
			GoF: Factory Method, Command, Builder, Strategy, State, Observer etc.</li>
		<li>Используя сервлеты и JSP, реализовать функциональности,
			предложенные в постановке конкретной задачи.</li>
		<li>В страницах JSP применять библиотеку JSTL и разработать
			собственные теги.</li>
		<li>При разработке бизнес логики использовать сессии и фильтры.</li>
		<li>Выполнить журналирование событий, то есть информацию о
			возникающих исключениях и событиях в системе обрабатывать с помощью
			Log4j.</li>
		<li>Код должен содержать комментарии.</li>
	</ol>

	<p>Система Прокат автомобилей. Клиент выбирает Автомобиль из
		списка доступных. Заполняет форму Заказа, указывая паспортные данные,
		срок аренды. Клиент оплачивает Заказ. Администратор регистрирует
		возврат автомобиля. В случае повреждения Автомобиля, Администратор
		вносит информацию и выставляет счет за ремонт. Администратор может
		отклонить Заявку, указав причины отказа.</p>