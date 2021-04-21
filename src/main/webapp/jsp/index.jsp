<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Advertisement" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.resource.City" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.resource.User" %>
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
    <title>Home page</title>
</head>
    <body>
    <header>
        <h1>Your Way To Italy</h1>
    </header>
    <nav>
        <a href="${pageContext.request.contextPath}/index">Home</a>

        <c:choose>
            <c:when test="${empty sessionScope.Authorization}">
                <a href="${pageContext.request.contextPath}/user/do-login">Login</a>
                <a href="${pageContext.request.contextPath}/user/do-register">Register</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/user/profile">Profile</a>
                <a href="${pageContext.request.contextPath}/user/do-logout">Logout</a>
            </c:otherwise>
        </c:choose>

        <a href="${pageContext.request.contextPath}/html/contacts.html">Contacts</a>
    </nav>
    <%
        // TODO: write contacts.html
    %>
        <br>
        <br>
        <br>
    <div>
        <form id="advertisementListForm" name="advertisementListForm" method="get" action="<c:url value="/adv"/>">

            <label for="typeAdvertisement">typeAdvertisement:</label>
            <input type="number" id="typeAdvertisement" name="typeAdvertisement" required
                   size="10">

            <label for="city">city:</label>
            <input type="number" id="city" name="city" required
                   size="10">

            <label for="date">Start date:</label>
            <input type="date" id="date" name="date" required>

            <button type="submit">Start your journey</button><br/>

        </form>
    </div>
        <br>
        <br>
    <div>
        <c:import url="/jsp/include/show-message.jsp"/>
    </div>
    </body>
</html>