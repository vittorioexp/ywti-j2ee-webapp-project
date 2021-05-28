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
        <!-- Tourist information -->
            <c:choose>
                <c:when test="${userType}">
                    <h1 class="h1 usernameTitle w3-center p-2">${user.name} ${user.surname}</h1>
                        <section class="userInfoSection w3-section w3-container w3-panel w3-card-4 w3-center">
                            <h2 class="h2">My info</h2>
                            <table  class="infoTable">
                                <tr><td>Email:</td> <td>${user.email}</td></tr>
                                <tr ><td>Phone number: </td><td>${user.phoneNumber}</td></tr>
                                <tr><td>Birth date: </td><td>${user.birthDate}</td></tr>
                                <tr ><td>Address:</td> <td>${user.address}</td></tr>
                                <tr ><td>City:</td><td name="userCity" id="${user.idCity}"></td></tr>
                                <tr><td>Your score is </td><td>${score} </td></tr>
                            </table>
                            </br>
                            <button name="editUserProfile" class="button w3-section w3-container w3-center">Edit Profile</button>
                            <br/>
                        </section>

                    <!-- List of bookings of the tourist, shown below the user info -->

                        <div class=" touristBookingList w3-center">
                            <section class="rowProfile w3-section w3-container w3-panel w3-card-4">
                                <%
                                    List<Booking> bookingList = (List) request.getAttribute("bookingList");
                                    List<Advertisement> advertisementList = (List) request.getAttribute("advertisementList");
                                    int count = 0;
                                %>
                                <c:choose>
                                    <c:when test="${empty bookingList}">
                                        <p name="emptyList" class="">Reservations not yet made!</p>
                                    </c:when>
                                    <c:otherwise>
                                        <h2 class="h2 w3-center sectionTitle ">My bookings</h2>

                                            <c:forEach items="<%=bookingList%>" var="booking">
                                            <table class="table table-borderless">
                                                <thead>
                                                    <tr>
                                                        <th scope="col">Advertisement</th>
                                                        <th scope="col" class="show-desktop">Starting</th>
                                                        <th scope="col" class="show-desktop">Items</th>
                                                        <th scope="col" ></th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr >
                                                        <td class="">
                                                            <%=advertisementList.get(count).getTitle()%>
                                                        </td>
                                                        <td class="show-desktop">
                                                            <%=advertisementList.get(count).getDateStart()%>
                                                        </td>
                                                        <td class="show-desktop"> ${booking.numBooking} items  </td>
                                                        <td class="">

                                                            <!-- The tourist can view or delete the booking -->
                                                                <button class="small-btn" name="showBookingButton" value="${booking.idAdvertisement}"><i class="fas fa-info-circle"></i></button>
                                                                <button class="small-btn" name="deleteBookingButton" value="${booking.idAdvertisement}"><i class="far fa-trash-alt"></i></button>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                                <%count++;%>
                                            </c:forEach>
                                        </table>
                                    </c:otherwise>
                                </c:choose>
                            </section>
                        </div>
                </c:when>

                <c:otherwise>
                    <!-- Company information -->
                    <h1 class="h1 usernameTitle w3-center p-2">${user.name}</h1>
                    <section class="w3-section w3-container w3-panel w3-card-4 w3-center userInfoSection">
                        <h2 class="h2 sectionTitle">My info</h2>

                        <table  class="infoTable">
                            <tr><td>Email:</td> <td>${user.email}</td></tr>
                            <tr ><td>Phone number: </td><td>${user.phoneNumber}</td></tr>
                            <tr ><td>Address:</td> <td>${user.address}</td></tr>
                            <tr ><td>City:</td><td name="userCity" id="${user.idCity}"></td></tr>
                        </table>

                        <section class="w3-center">
                            <button name="editUserProfile" class="button infoButtons w3-section w3-container">Edit Profile</button>
                        </section>

                    </section>


                    <!-- List of advertisement of the company -->
                    <section class="bookings w3-section w3-container w3-panel w3-card-4 w3-center touristAdvList">
                        <%
                            List<Advertisement> advertisementList = (List<Advertisement>) request.getAttribute("advertisementList");
                        %>
                        <h2 class="h2 sectionTitle">My advertisements</h2>
                        <form action="/ywti_wa2021_war/adv-do-create" >
                            <button type="submit" name="createAdvertisementButton" id="createAdvertisementButton" class="button infoButtons w3-section w3-container">New advertisement</button>
                        </form>
                        <c:choose>
                            <c:when test="${empty advertisementList}">
                                <p name="emptyList">No advertisement created</p>
                            </c:when>
                            <c:otherwise>
                                <table class="table table-borderless">
                                    <thead>
                                        <tr>
                                            <th scope="col">Advertisement</th>
                                            <th scope="col" class="show-desktop">Starting</th>
                                            <th scope="col" class="show-desktop">Ending</th>
                                            <th scope="col" class="show-desktop">Items</th>
                                            <th scope="col" class="show-desktop">Price</th>
                                            <th scope="col" ></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                <c:forEach items="${advertisementList}" var="adv">
                                        <tr>
                                            <td class="">${adv.title}   </td>
                                            <td class="show-desktop">${adv.dateStart}   </td>
                                            <td class="show-desktop">${adv.dateEnd}   </td>
                                            <td class="show-desktop">${adv.numTotItem}   </td>
                                            <td class="show-desktop">${adv.price}   </td>

                                            <td>
                                                <!-- Show, edit or delete the selected adv -->
                                                <button class="small-btn" name="showAdvertisementButton" value="${adv.idAdvertisement}"><i class="fas fa-info-circle"></i></button>
                                                <button class="small-btn" name="editAdvertisementButton" value="${adv.idAdvertisement}"><i class="fas fa-edit"></i></button>
                                                <button class="small-btn" name="deleteAdvertisementButton" value="${adv.idAdvertisement}"><i class="far fa-trash-alt"></i></button>
                                            </td>
                                        </tr>
                                </c:forEach>
                                    </tbody>
                                </table>
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