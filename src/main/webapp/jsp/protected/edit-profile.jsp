<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Tourist" %>
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
        <meta name="author" content="Basso Marco, Esposito Vittorio, Piva Matteo, Giurisato Francesco"> <!-- who wrote the page -->
        <meta name="description" content="Edit profile"> <!-- a textual description of it -->
        <meta name="keywords" content="edit profile, ywti, local, travel, italy"> <!-- some keywords to make your page more easily findable -->
        <!-- The viewport meta element is the key to making a responsive site work. -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Edit profile</title>
    </head>
    <body>
    <div id="page">
        <header>
            <h1>Edit profile</h1>
            <!-- TODO: import navbar.html (JS) -->
            <!-- https://stackoverflow.com/a/31837264 -->
            <div data-include="navbar"></div>
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
        </header>
        <main id="content">
            </br>
            <p>This is a mock page to update your profile</p>
            </br>
                <form name="editProfileForm" id="editProfileForm" method="POST" action="<c:url value="/user/edit"/>">

                    <label for="password">new password:</label>
                    <input id="password" name="password" type="password" required/><br/><br/>

                    <label for="phonenumber">Phone Number:</label>
                    <input id="phonenumber" name="phonenumber" type="text" required/><br/><br/>

                    <label for="address">Address:</label>
                    <input id="address" name="address" type="text" required/><br/><br/>

                    <label for="idCity">Id City:</label>
                    <input id="idCity" name="idCity" type="number" required/><br/><br/>
                    <button type="submit">Edit</button><br/>

                </form>
        </main>
        <!-- TODO: import footer.html (JS) -->
        <!-- https://stackoverflow.com/a/31837264 -->
        <div data-include="footer"></div>
    </div>
    </body>
</html>