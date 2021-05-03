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
        Marco Basso
        Matteo Piva
        Francecso Giurisato
Version: 1.0
Since: 1.0
-->

<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="author" content="Basso Marco, Esposito Vittorio, Piva Matteo, Giurisato Francesco"> <!-- who wrote the page -->
    <meta name="description" content="Login page"> <!-- a textual description of it -->
    <meta name="keywords" content="login, loginpage, ywti, local, travel, italy"> <!-- some keywords to make your page more easily findable -->
    <title>register</title>
</head>
<body>
<header>
    <h1>Register</h1>
</header>
<nav>
    <a href="${pageContext.request.contextPath}/index">Home</a>

    <c:choose>
        <c:when test="${empty sessionScope.Authorization}">
            <a href="${pageContext.request.contextPath}/user/do-login">Login</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/user/profile">Profile</a>
            <a href="${pageContext.request.contextPath}/user/do-logout">Logout</a>
        </c:otherwise>
    </c:choose>

    <a href="${pageContext.request.contextPath}/html/contacts.html">Contacts</a>
</nav>
<br/>
<p>This is a mock page to register</p>

<table>
    <tr>
        <td>
            <p>Register as a tourist and start your journey!</p>
            <br/>
            <form method="POST" action="<c:url value="/user/register"/>">

                <label for="email_t">email:</label>
                <input name="email" type="text" id="email_t" required/><br/><br/>
                <label for="password_t">password:</label>
                <input name="password" type="password" id="password_t" required/><br/><br/>
                <label for="rpassword_t">repeat password:</label>
                <input name="rpassword" type="password" id="rpassword_t" required/><br/><br/>
                <label for="name_t">name:</label>
                <input name="name" type="text" id="name_t" required/><br/><br/>
                <label for="surname_t">surname:</label>
                <input name="surname" type="text" id="surname_t" required/><br/><br/>
                <label for="address_t">address:</label>
                <input name="address" type="text" id="address_t" required/><br/><br/>
                <label for="phonenumber_t">phone number:</label>
                <input name="phone" type="text" id="phonenumber_t" required/><br/><br/>
                <label for="city_t">city:</label>
                <input name="city" type="number" id="city_t" required/><br/><br/>
                <label for="birthdate_t">birth date:</label>
                <input name="birthDate" type="Date" id="birthdate_t" required/><br/><br/>

                <input type="hidden" id="userType" name="userType" value="tourist">

                <button type="submit">Register</button><br/>

            </form>
        </td>
        <td>
            <p>Register as a company and create your event!</p>
            <br/>
            <form method="POST" action="<c:url value="/user/register"/>">

                <label for="email_c">email:</label>
                <input name="email" type="text" id="email_c"/><br/><br/>
                <label for="password_c">password:</label>
                <input name="password" type="password" id="password_c"/><br/><br/>
                <label for="rpassword_c">repeat password:</label>
                <input name="rpassword" type="password" id="rpassword_c"/><br/><br/>
                <label for="name_c">name:</label>
                <input name="name" type="text" id="name_c"/><br/><br/>
                <label for="address_c">address:</label>
                <input name="address" type="text" id="address_c"/><br/><br/>
                <label for="phonenumber_c">phone number:</label>
                <input name="phone" type="text" id="phonenumber_c"/><br/><br/>
                <label for="city">city:</label>
                <input name="city" type="number" id="city"/><br/><br/>

                <input type="hidden" name="userType" value="company">

                <button type="submit">Register</button><br/>

            </form>
        </td>
    </tr>
</table>

<div>
    <c:import url="/jsp/include/show-message.jsp"/>
</div>
</body>
</html>