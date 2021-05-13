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
        <!-- The viewport meta element is the key to making a responsive site work. -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>login</title>
        <script src="/ywti_wa2021_war/js/utils.js"></script>
        <script src="/ywti_wa2021_war/js/login-page.js"></script>
        <link href="/ywti_wa2021_war/css/style/ywti.css" rel="stylesheet" type="text/css">
        <c:choose>
            <c:when test="${!empty sessionScope.Authorization}">
                <script>
                    let auth = "${sessionScope.Authorization}";
                    let authHeader = auth.substring(
                        auth.lastIndexOf(" ") + 1,
                    );
                    authHeader = atob(authHeader);
                    let email = authHeader.substring(
                        0,
                        authHeader.lastIndexOf(":")
                    );
                    let role = authHeader.substring(
                        authHeader.lastIndexOf(":")+1
                    );
                    sessionStorage.setItem("loggedIn", true);
                    sessionStorage.setItem("userEmail", email);
                    sessionStorage.setItem("userRole", role);
                    window.location.href = contextPath;
                </script>
            </c:when>
            <c:otherwise>
                <script>
                    sessionStorage.removeItem("loggedIn");
                    sessionStorage.removeItem("userEmail");
                    sessionStorage.removeItem("userRole");
                </script>
            </c:otherwise>
        </c:choose>
    </head>
    <body>
        <div class="mainWrapper">
            <header>
                <!-- navbar (which includes the logo) imported with javascript -->
                <div id="navbar-area"></div>
            </header>
            <main class="mainContent" >
                <form id="login-form" name="login-form" method="POST" action="<c:url value="/user/login"/>">
                    <label for="email">email:</label>
                    <input id="email" name="email" type="email" required/>
                    <span class="error"></span>
                    <br/><br/>
                    <label for="password">password:</label>
                    <input id="password" name="password" type="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                           title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 characters" required/><br/><br/>
                    <button id="login-button" type="submit">Submit</button><br/>
                </form>
            </main>
            <div id="footer-area"></div>
        </div>
    </body>
</html>


