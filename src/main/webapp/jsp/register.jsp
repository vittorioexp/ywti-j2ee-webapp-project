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
        <script src="/ywti_wa2021_war/js/utils.js"></script>
        <script src="/ywti_wa2021_war/js/register-page.js"></script>
        <link href="/ywti_wa2021_war/css/style/ywti.css" rel="stylesheet" type="text/css">
    </head>
    <body>
    <div class="mainWrapper">
        <header>
            <!-- navbar (which includes the small logo) imported with javascript -->
            <div id="navbar-area"></div>
            <div id="user-email"></div>
        </header>
        <main class="mainContent" >
            <br/>
            <section>
                <input type="checkbox" id="userTypeCheckbox" name="userTypeCheckbox" value="company" onclick="checkUserType()">
                <label for="userType"> I am a company</label><br>
            </section>
            <section id="error">
            </section>
            <section id="touristRegistration">
                <p>Register as a tourist and start your journey!</p>
                <br/>
                <form id="registerFormTourist" name="registerForm" method="POST" action="<c:url value="/user/register"/>">

<%--                    <label for="email_t">email:</label>--%>
                    <input name="email" type="email" id="email_t" placeholder="Email"required/><br/><br/>
<%--                    <label for="password_t">password:</label>--%>
                    <input name="password" type="password" id="password_t" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                           title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 characters" placeholder="Password" required/><br/><br/>
<%--                    <label for="rpassword_t">repeat password:</label>--%>
                    <input name="rpassword" type="password" id="rpassword_t" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                           title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 characters" placeholder="Repeat password" required/><br/><br/>
<%--                    <label for="name_t">name:</label>--%>
                    <input name="name" type="text" id="name_t"  placeholder="Insert name" required/><br/><br/>
<%--                    <label for="surname_t">surname:</label>--%>
                    <input name="surname" type="text" id="surname_t" placeholder="Insert surname" required/><br/><br/>
<%--                    <label for="address_t">address:</label>--%>
                    <input name="address" type="text" id="address_t" placeholder="Insert your address"  required/><br/><br/>
<%--                    <label for="phonenumber_t">phone number:</label>--%>
                    <input name="phone" type="text" id="phonenumber_t" placeholder="Insert your phone number" required/><br/><br/>
<%--                    <label for="city_t">city:</label>--%>
                    <select form="registerFormTourist" id="idCity" name="idCity">
                        <option value="0" disabled selected>Choose your city</option>
                    </select>
<%--                    <label for="birthdate_t">birth date:</label>--%>
                    <input name="birthDate" type="Date" id="birthdate_t" placeholder="Insert your birthdate" required/><br/><br/>

                    <input type="hidden" id="userType" name="userType" value="tourist">

                    <button type="submit">Register</button><br/>

                </form>
            </section>
            <section id="companyRegistration">
                <p>Register as a company and start to create your event!</p>
                <br/>
                <form id="registerFormCompany" name="registerForm" method="POST" action="<c:url value="/user/register"/>">

<%--                    <label for="email_c">email:</label>--%>
                    <input name="email" type="email" id="email_c" placeholder="Insert Email" required/><br/><br/>
<%--                    <label for="password_c">password:</label>--%>
                    <input name="password" type="password" id="password_c" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                           title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 characters" placeholder="Insert password" required/><br/><br/>
<%--                    <label for="rpassword_c">repeat password:</label>--%>
                    <input name="rpassword" type="password" id="rpassword_c" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                           title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 characters" placeholder="Repeat Password" required/><br/><br/>
<%--                    <label for="name_c">name:</label>--%>
                    <input name="name" type="text" id="name_c" placeholder="Insert company name" required/><br/><br/>
<%--                    <label for="address_c">address:</label>--%>
                    <input name="address" type="text" id="address_c" placeholder="Insert company address" required/><br/><br/>
<%--                    <label for="phonenumber_c">phone number:</label>--%>
                    <input name="phone" type="text" id="phonenumber_c" placeholder="Insert company phone number" required/><br/><br/>
<%--                    <label for="idCity_c">city:</label>--%>
                    <select form="companyRegistration" id="idCity_c" name="idCity">
                        <option value="0" disabled selected>Choose company location</option>
                    </select>

                    <input type="hidden" name="userType" value="company">

                    <button type="submit">Register</button><br/>

                </form>
            </section>
        </main>
        <!-- footer imported with javascript -->
        <div id="footer-area"></div>
    </div>
    </body>
</html>