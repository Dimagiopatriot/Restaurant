<%--
  Created by IntelliJ IDEA.
  User: troll
  Date: 10.01.2018
  Time: 13:12
  To change this template use File | Settings | File Templates.
--%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<%--c:set var="language" scope="session"
       value="${empty sessionScope.locale ? 'en_US' : sessionScope.locale}" />--%>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="lang" />

<link rel="stylesheet" type="text/css" href="/resources/css/style.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title><fmt:message key="restaurant"/></title>
