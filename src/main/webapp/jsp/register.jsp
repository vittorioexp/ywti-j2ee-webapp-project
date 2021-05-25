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

        <!-- Jquery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">

        <!-- Bootstrap Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>

        <!-- Font awesome -->
        <link href="/ywti_wa2021_war/css/fontawesome-free-5.15.3-web/css/all.css" rel="stylesheet">

        <!-- W3 school CSS -->
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

        <!-- Crypto JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.0.0/core.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.0.0/md5.js"></script>

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
        <header id="header-bar" class="">
            <img id="small-logo" class="small-logo" src="/ywti_wa2021_war/utility/small-logo-transparent.png" >
            <div id="navbar-area" class="topnav" ></div>
        </header>
        <main class="mainContent" >
            <section class="w3-container w3-section">
                <h1 class="h1 w3-center">Create your own account</h1>
            </section>
            <!-- Select if the registration is for a tourist or company -->
            <section id ="companyornot" class="w3-container w3-panel w3-card-4 w3-section w3-center"><br/>
                <input type="checkbox" id="userTypeCheckbox" name="userTypeCheckbox" value="company" onclick="checkUserType()">
                <label for="userType" class="lead" > I am a company</label><br/><br/>
            </section>
            <section id="error" class="w3-center">
            </section>
            <!-- Registration of a tourist-->
            <section id="touristRegistration" class="w3-panel w3-card-4 w3-section">
                <p class="lead p-1 w3-center" >Register as a tourist and start your journey!</p>
                <br/>
                <!-- Form to insert tourist info -->
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

                    <select class="form-select form-select-lg mb-3" form="registerFormTourist" id="idCity_t" name="idCity">
                        <option value="0" disabled selected>City</option>
                    </select>

                    <label class="label" for="birthdate_t" style="color: gray;">Birthdate</label>
                    <input class="form-select form-select-lg mb-3" name="birthDate" type="Date" id="birthdate_t" placeholder="Birthdate" required/>

                    <input type="hidden" id="userType" name="userType" value="tourist">
                    <br/><br/>

                    <div class="w3-center">
                        <button name="register-button" class="button"type="submit">Register</button><br/><br/>
                    </div>

                </form>
            </section>
            <!-- Company registration -->
            <section id="companyRegistration" class="w3-panel w3-card-4 w3-section">
                <p class="lead p-1 w3-center">Register as a company and start to create your event!</p>
                <br/>
                <!-- Form to insert company info -->
                <form id="registerFormCompany" name="registerForm" method="POST">

                    <input class="w3-input w3-section" name="email" type="email" id="email_c" placeholder="Email" required/>

                    <input class="w3-input w3-section" name="password" type="password" id="password_c" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                           title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 characters" placeholder="Password" required/>

                    <input class="w3-input w3-section" name="rpassword" type="password" id="rpassword_c" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                           title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 characters" placeholder="Repeat Password" required/>

                    <input class="w3-input w3-section" name="name" type="text" id="name_c" placeholder="Company name" required/>

                    <input class="w3-input w3-section" name="address" type="text" id="address_c" placeholder="Company address" required/>

                    <input class="w3-input w3-section" name="phone" type="text" id="phonenumber_c" placeholder="Company phone number" required/>

                    <select class="form-select form-select-lg mb-3" form="companyRegistration" id="idCity_c" name="idCity">
                        <option value="0" disabled selected>City</option>
                    </select>

                    <input type="hidden" name="userType" value="company">

                    <br/><br/>
                    <div class="w3-center">
                        <button name="register-button" type="submit" class="button">Register</button><br/><br/>
                    </div>

                </form>
            </section>
        </main>
        <!-- footer imported with javascript -->
    </div>
    <div id="footer-area"></div>
    </body>
</html>