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
    <title>register</title>
</head>
<body>

<form method="POST" action="<c:url value="/user/register/"/>">

    <label for="email">email:</label>
    <input name="email" type="text"/><br/><br/>
    <label for="name">name:</label>
    <input name="name" type="text"/><br/><br/>
    <label for="surname">surname:</label>
    <input name="surname" type="text"/><br/><br/>
    <label for="password">password:</label>
    <input name="password" type="password"/><br/><br/>
    <label for="rpassword">repeat password:</label>
    <input name="rpassword" type="password"/><br/><br/>
    <label for="address">address:</label>
    <input name="address" type="text"/><br/><br/>
    <label for="birth_date">birth_date:</label>
    <input name="birth_date" type="text"/><br/><br/>
    <label for="phone_number">phone_number:</label>
    <input name="phone_number" type="text"/><br/><br/>
    <button type="submit">Submit</button><br/>

</form>

<c:choose>
    <c:when test="${message.error}">
        <p><c:out value="${message.message}"/></p>
    </c:when>
    <c:otherwise></c:otherwise>
</c:choose>
</body>
</html>