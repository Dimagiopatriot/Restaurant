<%--
  Created by IntelliJ IDEA.
  User: troll
  Date: 10.01.2018
  Time: 20:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%@include file="head.jsp" %>
</head>
<body>
<%@include file="header.jsp" %>
<form class="form-horizontal" method="post" action="/registration/signup">
    <fieldset class="mycont">

        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="name"><fmt:message key="user.name"/></label>
            <div class="col-md-4">
                <input id="name" name="name" placeholder="<fmt:message key="user.name"/>"
                       class="form-control input-md" required="required" value="${name}">
            </div>
        </div>

        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="surname"><fmt:message key="user.surname"/></label>
            <div class="col-md-4">
                <input id="surname" name="surname" placeholder="<fmt:message key="user.surname"/>"
                       class="form-control input-md" required="required" value="${surname}">
            </div>
        </div>

        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="phone"><fmt:message key="user.phone"/></label>
            <div class="col-md-4">
                <input id="phone" name="phone" type="tel" placeholder="+380_ _ _ _ _ _ _ _"
                       class="form-control input-md" required="required" value="${phone}">
            </div>
        </div>

        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="email"><fmt:message key="email"/></label>
            <div class="col-md-4">
                <input id="email" name="email" type="email" placeholder="example@email.com"
                       class="form-control input-md" required="required" value="${email}">

            </div>
        </div>

        <!-- Password input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="userpassword"><fmt:message key="password"/></label>
            <div class="col-md-4">
                <input id="userpassword" name="userpassword" type="password" placeholder="<fmt:message key="password"/>"
                       class="form-control input-md" required="required">

            </div>
        </div>

        <!-- Button -->
        <label class="col-md-4 control-label" for="registerbutton"></label>
        <div class="col-md-8">
            <button id="registerbutton" name="registerbutton" class="btn btn-success">
                <fmt:message key="sign.up"/></button>
        </div>

        <c:forEach items="${errors}" var="item">
            <p class="text-danger"><fmt:message key="${item}"/></p>
        </c:forEach>
    </fieldset>
</form>
<%@include file="footer.jsp" %>
</body>
</html>
