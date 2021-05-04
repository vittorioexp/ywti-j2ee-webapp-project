<%@ page import="it.unipd.dei.yourwaytoitaly.utils.ErrorCode" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Message" %>
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
    <meta name="description" content="upload images page"> <!-- a textual description of it -->
    <meta name="keywords" content="upload, upload images, ywti, local, travel, italy"> <!-- some keywords to make your page more easily findable -->
    <!-- The viewport meta element is the key to making a responsive site work. -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Create an advertisement</title>
</head>
<body>
<div id="page">
    <header>
        <h1>Upload images</h1>
        <!-- TODO: import navbar.html (JS) -->
        <!-- https://stackoverflow.com/a/31837264 -->
        <div data-include="navbar"></div>
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

    <main id="content">
        <%
            String path = request.getRequestURI();
            String idAdvertisement = path.substring(path.lastIndexOf("image-do-upload") + 16);
        %>
        <%= idAdvertisement %>
        <section>
            <form id="uploadImagesForm" name="uploadImagesForm" method="post" enctype="multipart/form-data"
                  action="<c:url value="/image-upload" />" >
                <input type="hidden" name="idAdvertisement" value="<%= idAdvertisement %>" />
                <label for="image">image:</label>
                <input id="image" name="image" type="file" id="file" multiple/><br/><br/>
                <button type="submit" name="Submit" value="Submit">Upload</button><br/>
            </form>
        </section>
    </main>
</div>
<!-- TODO: import footer.html (JS) -->
<!-- https://stackoverflow.com/a/31837264 -->
<div data-include="footer"></div>
</body>
</html>


