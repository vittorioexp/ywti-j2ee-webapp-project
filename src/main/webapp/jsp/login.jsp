<%@ page import="it.unipd.dei.yourwaytoitaly.resource.User" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.servlet.SessionCheckServlet" %>
<!--
Copyright 2021 University of Padua, Italy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Author: Vittorio Esposito
Version: 1.0
Since: 1.0
-->

<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>login</title>
</head>
<body>
<div>
    <a href="${pageContext.request.contextPath}/index">Home</a>

    <%
        User u = new SessionCheckServlet(request, response).getUser();
    %>
    <c:choose>
        <c:when test="${u}">
            <a href="${pageContext.request.contextPath}/user/profile">Profile</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/user/do-login">Login</a>
            <a href="${pageContext.request.contextPath}/user/do-register">Register</a>
        </c:otherwise>
    </c:choose>

    <a href="${pageContext.request.contextPath}/html/contacts.html">Contacts</a>
</div>

<div>
<form id="login-form" name="login-form" method="POST" action="<c:url value="/user/login"/>">
    <label for="email">email:</label>
    <input id="email" name="email" type="text"/><br/><br/>
    <label for="password">password:</label>
    <input id="password" name="password" type="password"/><br/><br/>
    <button type="submit">Submit</button><br/>
</form>
</div>

<div>
<c:choose>
    <c:when test="${message.error}">
        <p><c:out value="${message.message}"/></p>
    </c:when>
    <c:otherwise></c:otherwise>
</c:choose>
</div>
</body>
</html>


