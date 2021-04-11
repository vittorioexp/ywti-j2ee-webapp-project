<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Advertisement" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.resource.City" %>
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

<a href="index.jsp">Home</a>
<a href="login.jsp">Login</a>
<a href="register.jsp">Register</a>
<a href="index.jsp">Contacts</a>


<br>
<br>
<br>

<form action="list/advertisement/" method="post">

    <select name="advertisement">

        <%
            List<Advertisement> listAdvertisement = (List<Advertisement>) request.getAttribute("advertisement_list");
        %>

        <c:forEach items="<%=listAdvertisement%>" var="advertisement">
            <option value="${advertisement.title}">${advertisement.title}</option>
        </c:forEach>
    </select>
    <input name="advertisement" type="text"/>

<br>

    <label for="city">City:</label>
    <input name="city" type="text"/>

<br>

    <fmt:formatDate value="${blah.bla}" pattern="dd/MM/yyyy" var="myDate" >
        <input name="myDate" type="date"/>

    <br>
    <br>
    <button type="submit">Start your journey</button><br/>
</form>

<br>
<br>


</body>
</html>


        <select name="city">

        <%
            List<City> listCity = (List<City>) request.getAttribute("city_list");
        %>

        <c:forEach items="<%=listCity%>" var="city">
            <option value="${city.title}">${city.title}</option>
        </c:forEach>

        </select>
        <input name="city" type="text"/>