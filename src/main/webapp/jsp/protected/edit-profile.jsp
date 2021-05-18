
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
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <link href="/ywti_wa2021_war/css/style/ywti.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link href="/ywti_wa2021_war/css/style/edit-profile.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="mainWrapper">
    <header>
        <div id="navbar-area"></div>
        <div id="user-email"></div>
    </header>
    <main class="mainContent w3-container" >
        <h1>Edit profile</h1>
        <section id="EditSection" class="w3-panel w3-card-4 w3-section">
        <section id="error"></section>
        <form id="edit-profile-form" name="edit-profile-form" method="POST">

            <input class="w3-input w3-section" id="password" name="password" type="password" placeholder="Insert new password"/>

            <input class="w3-input w3-section" id="phonenumber" name="phonenumber" type="text" placeholder="Insert new phone number"/>

            <input class="w3-input w3-section" id="address" name="address" type="text" placeholder="Insert new address"/>

            <select class="w3-input w3-section" form="edit-profile-form" id="idCity" name="idCity">
                <option value="0" disabled selected>Where</option>
            </select>

            <button type="submit" class="button" id="edit-profile-button">Edit</button><br/></br>
        </form>
    </main>
    <!-- footer imported with javascript -->
    <div id="footer-area"></div>
</div>
</body>
</html>

