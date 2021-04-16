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

<header>
    <h1>Create Advertisement</h1>
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
    int idAdvertisement = 0;
    String tmp = String.valueOf(request.getAttribute("idAdvertisement"));
    if (tmp!=null) {idAdvertisement = Integer.parseInt(tmp);}
%>

<c:choose>

    <c:when test="${idAdvertisement==0}">

        <div>
            <form id="create-advertisement-form" name="create-advertisement-form" method="POST"
                  action="<c:url value="/advertisement-create" />" >

                <label for="title">title:</label>
                <input id="title" name="title" type="text" required/><br/><br/>

                <label for="type">type:</label>
                <input id="type" name="type" type="text" required/><br/><br/>

                <label for="description">description:</label>
                <input id="description" name="description" type="text" required/><br/><br/>

                <label for="price">price:</label>
                <input id="price" name="price" type="number" required/><br/><br/>

                <label for="numTotItem">numTotItem:</label>
                <input id="numTotItem" name="numTotItem" type="number" required/><br/><br/>

                <label for="dateStart">dateStart:</label>
                <input id="dateStart" name="dateStart" type="date" required/><br/><br/>

                <label for="dateEnd">dateEnd:</label>
                <input id="dateEnd" name="dateEnd" type="date" required/><br/><br/>

                <label for="timeStart">timeStart:</label>
                <input id="timeStart" name="timeStart" type="time" required/><br/><br/>

                <label for="timeEnd">timeEnd:</label>
                <input id="timeEnd" name="timeEnd" type="time" required/><br/><br/>

                <button type="submit" name="Submit" value="Submit">Submit</button><br/>

            </form>

            <script>
                function handleSubmit(event) {
                    event.preventDefault();
                    const data = new FormData(event.target);
                    const value = Object.fromEntries(data.entries());
                    console.log({ value });
                }
                const form = document.querySelector('form');
                form.addEventListener('submit', handleSubmit);
            </script>

        </div>
    </c:when>
    <c:otherwise>
        <div>
            <form id="upload-images-form" method="post" enctype="multipart/form-data" action="/advertisement-create">
                <label for="image">image:</label>
                <input id="image" name="image" type="file" id="file" multiple/><br/><br/>
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


