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
        <title>Register - Your Way to Italy</title>
        <!-- Common libraries -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

        <!-- Common CSS -->
        <link href="/ywti_wa2021_war/css/style/ywti.css" rel="stylesheet" type="text/css">

        <!-- Custom CSS -->
        <link href="/ywti_wa2021_war/css/style/register-page.css" rel="stylesheet" type="text/css">

        <!-- Common JS -->
        <script src="/ywti_wa2021_war/js/utils.js"></script>

        <!-- Custom JS -->
        <script src="/ywti_wa2021_war/js/register-page.js"></script>
    </head>
    <body>
    <div class="mainWrapper w3-main">
        <header class="">
            <img id="small-logo" class="small-logo" src="/ywti_wa2021_war/utility/small-logo-transparent.png" >
            <div id="navbar-area" class="topnav" ></div>
        </header>
        <main class="mainContent w3-container" >
            <div id="user-email" class=""></div>
            <section class="w3-container w3-section">
                <h1>Create your own account</h1>
            </section>

            <section id ="companyornot" class="w3-container w3-panel w3-card-4 w3-section"><br/>
                <input type="checkbox" id="userTypeCheckbox" name="userTypeCheckbox" value="company" onclick="checkUserType()">
                <label for="userType"> I am a company</label><br/><br/>
            </section>
            <section id="error">
            </section>
            <section id="touristRegistration" class="w3-panel w3-card-4 w3-section">
                <p>Register as a tourist and start your journey!</p>
                <br/>
                <form id="registerFormTourist" name="registerForm" method="POST">

                    <input class="w3-input w3-section" name="email" type="email" id="email_t" placeholder="Email"required/>

                    <input class="w3-input w3-section" name="password" type="password" id="password_t" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                           title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 characters" placeholder="Password" required/>

                    <input class="w3-input w3-section" name="rpassword" type="password" id="rpassword_t" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                           title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 characters" placeholder="Repeat password" required/>

                    <input class="w3-input w3-section" name="name" type="text" id="name_t"  placeholder="Name" required/>

                    <input class="w3-input w3-section" name="surname" type="text" id="surname_t" placeholder="Surname" required/>

                    <input class="w3-input w3-section" name="address" type="text" id="address_t" placeholder="Address"  required/>

                    <input class="w3-input w3-section" name="phone" type="text" id="phonenumber_t" placeholder="Phone number" required/>

                    <select class="w3-input w3-section" form="registerFormTourist" id="idCity_t" name="idCity">
                        <option value="0" disabled selected>City</option>
                    </select>

                    <input class="w3-input w3-section" name="birthDate" type="Date" id="birthdate_t" placeholder="Birthdate" required/>

                    <input type="hidden" id="userType" name="userType" value="tourist">
                    <br/><br/>

                    <button name="register-button" class="button"type="submit">Register</button><br/><br/>

                </form>
            </section>
            <section id="companyRegistration" class="w3-panel w3-card-4 w3-section">
                <p>Register as a company and start to create your event!</p>
                <br/>
                <form id="registerFormCompany" name="registerForm" method="POST">

                    <input class="w3-input w3-section" name="email" type="email" id="email_c" placeholder="Email" required/>

                    <input class="w3-input w3-section" name="password" type="password" id="password_c" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                           title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 characters" placeholder="Password" required/>

                    <input class="w3-input w3-section" name="rpassword" type="password" id="rpassword_c" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                           title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 characters" placeholder="Repeat Password" required/>

                    <input class="w3-input w3-section" name="name" type="text" id="name_c" placeholder="Company name" required/>

                    <input class="w3-input w3-section" name="address" type="text" id="address_c" placeholder="Company address" required/>

                    <input class="w3-input w3-section" name="phone" type="text" id="phonenumber_c" placeholder="Company phone number" required/>

                    <select class="w3-input w3-section" form="companyRegistration" id="idCity_c" name="idCity">
                        <option value="0" disabled selected>City</option>
                    </select>

                    <input type="hidden" name="userType" value="company">

                    <br/><br/>

                    <button name="register-button" type="submit" class="button">Register</button><br/><br/>

                </form>
            </section>
        </main>
        <!-- footer imported with javascript -->
    </div>
    <div id="footer-area"></div>
    </body>
</html>