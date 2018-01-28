<%--
  Created by IntelliJ IDEA.
  User: troll
  Date: 10.01.2018
  Time: 18:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%@include file="head.jsp" %>
</head>
<body>
<%@include file="header.jsp" %>
<fieldset class="mycont">
    <div class="form-group">
        <h3 class="col-sm-6 control-label"><fmt:message key="menu.entrees"/>
    </div>
    <c:forEach items="${entrees}" var="entree">
        <p>${entree.toString()}</p>
        <br>
    </c:forEach>

    <div class="form-group">
        <h3 class="col-sm-6 control-label"><fmt:message key="menu.seconds"/>
    </div>
    <c:forEach items="${seconds}" var="second">
        <p>${second.toString()}</p>
        <br>
    </c:forEach>

    <div class="form-group">
        <h3 class="col-sm-6 control-label"><fmt:message key="menu.desserts"/>
    </div>
    <c:forEach items="${desserts}" var="dessert">
        <p>${dessert.toString()}</p>
        <br>
    </c:forEach>

    <div class="form-group">
        <h3 class="col-sm-6 control-label"><fmt:message key="menu.drinks"/>
    </div>
    <c:forEach items="${drinks}" var="drink">
        <p>${drink.toString()}</p>
        <br>
    </c:forEach>
</fieldset>
<%@include file="footer.jsp" %>
</body>
</html>
