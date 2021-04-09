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
    <title>Edit profile</title>
</head>
<body>

<c:choose>
    <c:when test="${isTurist eq True}">      <!--aggiungere funzionalità per testare se l'utente loggato è un turist o una company-->
        <form method="POST" action="<c:url value="/user/editTurist/"/>">

            <label for="password">password:</label>
            <input name="password" type="password"/><br/><br/>

            <label for="phonenumber">timeEnd:</label>
            <input name="phonenumber" type="text"/><br/><br/>
            <!-- value="<c:out value="${requestScope.user.userId}"/>"-->

            <button type="submit">Edit</button><br/>
        </form>
        <br />
    </c:when>
    <c:otherwise>
        <form method="POST" action="<c:url value="/user/editCompany/"/>">

            <label for="password">password:</label>
            <input name="password" type="password"/><br/><br/>

            <label for="phonenumber">timeEnd:</label>
            <input name="phonenumber" type="text"/><br/><br/>

            <button type="submit">Edit</button><br/>

        </form>
        <br />
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${message.error}">
        <p><c:out value="${message.message}"/></p>
    </c:when>
    <c:otherwise>

    </c:otherwise>
</c:choose>
</body>
</html>