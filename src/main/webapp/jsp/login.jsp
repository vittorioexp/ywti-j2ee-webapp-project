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
        <title>Login - Your Way to Italy</title>
        <script src="/ywti_wa2021_war/js/utils.js"></script>
        <c:choose>
            <c:when test="${!empty sessionScope.Authorization}">
                <c:set var="context" value="${pageContext.request.contextPath}" />
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
                    localStorage.setItem("loggedIn", true);
                    localStorage.setItem("userEmail", email);
                    localStorage.setItem("userRole", role);
                    window.location.href = contextPath;
                </script>
            </c:when>
            <c:otherwise>
                <script>
                    localStorage.removeItem("loggedIn");
                    localStorage.removeItem("userEmail");
                    localStorage.removeItem("userRole");
                </script>
            </c:otherwise>
        </c:choose>
        <script src="/ywti_wa2021_war/js/login-page.js"></script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <link href="/ywti_wa2021_war/css/style/w3.css" rel="stylesheet" type="text/css">
        <link href="/ywti_wa2021_war/css/style/ywti.css" rel="stylesheet" type="text/css">
        <link href="/ywti_wa2021_war/css/style/login-page.css" rel="stylesheet" type="text/css">
    </head>
    <body>
    <div class="mainWrapper w3-main">
        <header class="">
            <img id="small-logo" class="small-logo" src="/ywti_wa2021_war/utility/small-logo-transparent.png" >
            <div id="navbar-area" class="nav-area w3-bar w33-container" ></div>
        </header>
        <main class="mainContent w3-container" >
            <div id="user-email" class=""></div>
            <h1>Login into your account</h1>
            <section id="error" class="w3-container w3-section"></section>
            <section class="w3-container w3-section">
                <form class="w3-panel w3-card-4 w3-section" id="loginForm" name="loginForm" method="POST"/>
                    <input id="email" name="email" type="email" class="w3-input w3-section" required placeholder="Email"/>
                    <span class="error"></span>
                    <input id="password" name="password" type="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                           title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 characters"
                           class="w3-input w3-section" required placeholder="Password"/><br/>
                    <button id="login-button"  type="submit" class="w3-section button">Login</button><br/>
                </form>
            </section>
            </main>
            <!-- footer imported with javascript -->
            <div id="footer-area" class="w3-container w3-section"></div>
        </div>
    </body>
</html>


