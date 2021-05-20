<%@ page import="it.unipd.dei.yourwaytoitaly.database.CityDAO" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Advertisement" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Booking" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.resource.User" %>
<%@ page import="java.util.List" %>
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

Author: Marco Basso
Author: Vittorio Esposito
Author: Francesco Giurisato
Author: Matteo Piva
Version: 1.0
Since: 1.0
-->


<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="author" content="Basso Marco, Esposito Vittorio, Piva Matteo, Giurisato Francesco">
    <meta name="description" content="Show profile page">
    <meta name="keywords" content="profile, page, show, ywti">
    <!-- The viewport meta element is the key to making a responsive site work. -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>My profile - Your Way to Italy</title>
    <!-- Common libraries -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

    <!-- Common CSS -->
    <link href="/ywti_wa2021_war/css/style/ywti.css" rel="stylesheet" type="text/css">

    <!-- Custom CSS -->
    <link href="/ywti_wa2021_war/css/style/show-profile-page.css" rel="stylesheet" type="text/css">

    <!-- Common JS -->
    <script src="/ywti_wa2021_war/js/utils.js"></script>

    <!-- Custom JS -->
    <script src="/ywti_wa2021_war/js/show-profile-page.js"></script>

</head>
<body>
<div class="mainWrapper w3-main">
    <header id="header-bar" class="">
        <img id="small-logo" class="small-logo" src="/ywti_wa2021_war/utility/small-logo-transparent.png" >
        <div id="navbar-area" class="topnav" ></div>
    </header>
    <main class="mainContent w3-container" >
        <div id="user-email" class=""></div>
            <c:choose>
                <c:when test="${userType}">
                    <h1 class="usernameTitle w3-center">${user.name} ${user.surname}</h1>
                    <section class="userInfoSection w3-section w3-container w3-panel w3-card-4">
                        <h2>My info</h2>
                        <p>Email: ${user.email}</p>
                        <p>Phone number: ${user.phoneNumber}</p>
                        <p>Birth date: ${user.birthDate}</p>
                        <p>Address: ${user.address}</p>
                        <p name="userCity" id="${user.idCity}"></p>
                        <p>Your score is ${score} </p>
                        </br>
                        <button name="editUserProfile" class="button w3-section w3-container w3-center">Edit Profile</button>
                        <br/>
                    </section>
                    <section class="bookings w3-section w3-container w3-panel w3-card-4">
                        <%
                            List<Booking> bookingList = (List) request.getAttribute("bookingList");
                            List<Advertisement> advertisementList = (List) request.getAttribute("advertisementList");
                            int count = 0;
                        %>
                        <c:choose>
                            <c:when test="${empty bookingList}">
                                <p name="emptyList">Reservations not yet made!</p>
                            </c:when>
                            <c:otherwise>
                                <h2 class="sectionTitle">My bookings</h2>
                                <table class="w3-table w3-container w3-section ">
                                    <tr>
                                        <td>Advertisement</td>
                                        <td>Date</td>
                                        <td>Items</td>
                                        <td></td>
                                    </tr>
                                    <c:forEach items="<%=bookingList%>" var="booking">
                                        <tr>
                                            <td>
                                                <%=advertisementList.get(count).getTitle()%>
                                            </td>
                                            <td>
                                                <%=advertisementList.get(count).getDateStart()%>
                                            </td>
                                            <td> ${booking.numBooking} items  </td>
                                            <td>
                                                <form id="deleteBookingForm" name="deleteBookingForm" method="DELETE">
                                                    <button name="deleteBookingButton" value="${adv.idAdvertisement}" class="button">Delete</button>
                                                </form>
                                            </td>
                                            <%count++;%>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:otherwise>
                        </c:choose>
                    </section>

                </c:when>
                <c:otherwise>
                    <h1 class="usernameTitle w3-center">${user.name}</h1>
                    <section class="w3-section w3-container w3-panel w3-card-4 userInfoSection">
                        <h2 class="sectionTitle">My info</h2>
                        <p>Email: ${user.email}</p>
                        <p>Phone number: ${user.phoneNumber}</p>
                        <p>Address: ${user.address}</p>
                        <p name="userCity" id="${user.idCity}"></p>

                        <section class="w3-center">
                            <button name="editUserProfile" class="button infoButtons w3-section w3-container">Edit Profile</button>
                        </section>

                    </section>



                    <section class="bookings w3-section w3-container w3-panel w3-card-4">
                        <%
                            List<Advertisement> advertisementList = (List<Advertisement>) request.getAttribute("advertisementList");
                        %>
                        <h2 class="sectionTitle">My advertisements</h2>
                        <form action="/ywti_wa2021_war/adv-do-create" >
                            <button type="submit" name="createAdvertisementButton" id="createAdvertisementButton" class="button infoButtons w3-section w3-container">New advertisement</button>
                        </form>
                        <c:choose>
                            <c:when test="${empty advertisementList}">
                                <p name="emptyList">No advertisement created</p>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${advertisementList}" var="adv">
                                    <table class="w3-table w3-container w3-section w3-panel w3-card">
                                        <tr>
                                            <td class="titleAdv"><p class="w3-small w3-light-blue">Title : </p>${adv.title}   </td>
                                            <td class="smallColumn"><p class="w3-small w3-light-blue">Starting : </p>${adv.dateStart}   </td>
                                            <td class="smallColumn"><p class="w3-small w3-light-blue"> Ending : </p>${adv.dateEnd}   </td>
                                            <td class="smallColumn"><p class="w3-small w3-light-blue"> # of Items : </p>${adv.numTotItem}   </td>
                                            <td class="smallColumn"><p class="w3-small w3-light-blue"> Pricing € : </p> ${adv.price}   </td>
                                        </tr>
                                        <tr>
                                            <td colspan="5">
                                                <form id="gotoEditAdvertisementForm" class="buttonSection" name="gotoEditAdvertisementForm" method="GET"/>
                                                <button name="editAdvertisementButton" value="${adv.idAdvertisement}" class="button listButtons">Edit</button><br/>
                                                </form>
                                                <form id="gotoShowAdvertisementForm" class="buttonSection" name="gotoShowAdvertisementForm" method="GET"/>
                                                <button name="showAdvertisementButton" value="${adv.idAdvertisement}" class="button listButtons">Info</button><br/>
                                                </form>
                                                <form id="deleteAdvertisementForm" class="buttonSection" name="deleteAdvertisementForm" method="DELETE">
                                                    <button name="deleteAdvertisementButton" value="${adv.idAdvertisement}" class="button listButtons">Delete</button>
                                                </form>
                                            </td>
                                        </tr>
                                    </table>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </section>
                </c:otherwise>
            </c:choose>
        </main>
        <!-- footer imported with javascript -->
    </div>
    <div id="footer-area"></div>
</body>
</html>