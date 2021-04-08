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
    <title>Create advertisement</title>
</head>
<body>

<form method="POST" action="<c:url value="/user/insertadvertisement/"/>">       //aggiungere op InsertAdvertisementServlet

    <label for="title">title:</label>
    <input name="title" type="text"/><br/><br/>

    <label for="type_adv">type_adv:</label>
    <input name="type_adv" type="text"/><br/><br/>

    <label for="description">description:</label>
    <input name="description" type="text"/><br/><br/>

    <label for="price">price:</label>
    <input name="price" type="text"/><br/><br/>

    <label for="numTotItem">numTotItem:</label>
    <input name="numTotItem" type="text"/><br/><br/>

    <label for="dateStart">dateStart:</label>
    <input name="dateStart" type="text"/><br/><br/>

    <label for="dateEnd">dateEnd:</label>
    <input name="dateEnd" type="text"/><br/><br/>

    <label for="timeStart">timeStart:</label>
    <input name="timeStart" type="text"/><br/><br/>

    <label for="timeEnd">timeEnd:</label>
    <input name="timeEnd" type="text"/><br/><br/>

    <button type="submit">Create</button><br/>

</form>

<c:choose>
    <c:when test="${message.error}">
        <p><c:out value="${message.message}"/></p>
    </c:when>
    <c:otherwise></c:otherwise>
</c:choose>
</body>
</html>


