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
    <title>Create an advertisement</title>
</head>
<body>

<div>
    <a href="${pageContext.request.contextPath}/index">Home</a>

    <c:choose>
        <c:when test="${empty sessionScope.user}">
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/user/profile">Profile</a>
        </c:otherwise>
    </c:choose>

    <a href="${pageContext.request.contextPath}/html/contacts.html">Contacts</a>
</div>

<%
    int idAdvertisement = (int) request.getAttribute("idAdvertisement");
%>

<c:choose>

    <c:when test="${idAdvertisement!='0'}">

        <div>
            <form method="post" action="<c:url value="/advertisement-do-create>"/>">

                <label for="title">title:</label>
                <input name="title" type="text"/><br/><br/>

                <label for="type">type:</label>
                <input name="type" type="text"/><br/><br/>

                <label for="description">description:</label>
                <input name="description" type="text"/><br/><br/>

                <label for="price">price:</label>
                <input name="price" type="number"/><br/><br/>

                <label for="numTotItem">numTotItem:</label>
                <input name="numTotItem" type="number"/><br/><br/>

                <label for="dateStart">dateStart:</label>
                <input name="dateStart" type="date"/><br/><br/>

                <label for="dateEnd">dateEnd:</label>
                <input name="dateEnd" type="date"/><br/><br/>

                <label for="timeStart">timeStart:</label>
                <input name="timeStart" type="time"/><br/><br/>

                <label for="timeEnd">timeEnd:</label>
                <input name="timeEnd" type="time"/><br/><br/>

                <button type="submit" name="Submit" value="Submit">Submit</button><br/>

            </form>
        </div>
    </c:when>
    <c:otherwise>
        <div>
            <form method="post" enctype="multipart/form-data" action="<c:url value="/advertisement-create>"/>">
                <label for="image">image:</label>
                <input name="image" type="file" id="file" multiple/><br/><br/>
                <button type="submit" name="Submit" value="Submit">Upload</button><br/>

            </form>
        </div>
    </c:otherwise>
</c:choose>
<div>
    <c:import url="/jsp/include/show-message.jsp"/>
</div>
</body>
</html>


