
<!--

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
    <meta name="description" content="Edit profile"> <!-- a textual description of it -->
    <meta name="keywords" content="edit profile, ywti, local, travel, italy"> <!-- some keywords to make your page more easily findable -->
    <!-- The viewport meta element is the key to making a responsive site work. -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Edit profile</title>
    <script src="/ywti_wa2021_war/js/utils.js"></script>
    <script src="/ywti_wa2021_war/js/edit-profile-page.js"></script>
    <link href="/ywti_wa2021_war/css/style/ywti.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="mainWrapper">
    <header>
        <div id="navbar-area"></div>
        <div id="user-email"></div>
    </header>
    <main id="mainContent">
        <section id="error"></section>

        <form id="edit-profile-form" name="edit-profile-form"method="PUT">
            <label for="password">New password:</label>
            <input id="password" name="password" type="password" /><br/><br/>

            <label for="phonenumber">Phone Number:</label>
            <input id="phonenumber" name="phonenumber" type="text" /><br/><br/>

            <label for="address">Address:</label>
            <input id="address" name="address" type="text" /><br/><br/>

            <label for="idCity">Id City:</label>
            <input id="idCity" name="idCity" type="number" /><br/><br/>
            <button type="submit" id="edit-profile-button">Edit</button><br/>
        </form>
    </main>
    <div id="footer-area"></div>
</div>
</body>
</html>

