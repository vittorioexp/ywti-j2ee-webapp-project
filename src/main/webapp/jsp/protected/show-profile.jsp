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
        <meta name="author" content="Basso Marco, Esposito Vittorio, Piva Matteo, Giurisato Francesco, Gabriele Del Fiume"> <!-- who wrote the page -->
        <meta name="description" content="Show profile page"> <!-- a textual description of it -->
        <meta name="keywords" content="profile, page, show, ywti"> <!-- some keywords to make your page more easily findable -->
        <!-- The viewport meta element is the key to making a responsive site work. -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>My profile - Your Way to Italy</title>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="/ywti_wa2021_war/js/utils.js"></script>
        <script src="/ywti_wa2021_war/js/show-profile-page.js"></script>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link href="/ywti_wa2021_war/css/style/ywti.css" rel="stylesheet" type="text/css">
        <link href="/ywti_wa2021_war/css/style/show-profile-page.css" rel="stylesheet" type="text/css">
        <link href="/ywti_wa2021_war/css/style/w3.css" rel="stylesheet" type="text/css">
        <title>show profile</title>
    </head>
    <body>
    <div class="mainWrapper w3-main">
        w3-containerw3-container<header class="">
        <img id="small-logo" class="small-logo" src="../../utility/small-logo-transparent.png" >
        <!-- <div id="navbar-area" class="nav-area w3-bar w33-container" ></div> -->
    </header>
        <main class="mainContent w3-container">

            <c:choose>

            <c:when test="${userType}">

                <section class="userInfo">
                    <br>
                    <br>
                    <h1>${user.name} ${user.surname}</h1>
                    <h2>My info</h2>

                    <section id="userInfoSection" class="w3-panel w3-card-4 w3-section">
                        </br>

                        <p class="w3-input w3-section"> Email: ${user.email}</p>
                        <p class="w3-input w3-section">Phone number: ${user.phoneNumber}</p>
                        <p class="w3-input w3-section">Birth date: ${user.birthDate}</p>
                        <p class="w3-input w3-section">Address: ${user.address}</p>
                        <p class="w3-input w3-section" name="userCity" id="${user.idCity}"></p>
                        <p class="w3-input w3-section">Your score is ${score} </p>
                        </br>
                        <button name="editUserProfile" class="w3-section button">Edit Profile</button><br/>
                    </section>
                </section>

                <section class="userBookings">
                    <section id="listBookings" class="w3-panel w3-card-4 w3-section">
                        </br>
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
                                <h2>My bookings</h2>
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
                                                <form id="deleteBookingForm" name="deleteBookingForm" action = "<c:url value="/booking-delete"/>" method="DELETE">
                                                    <input type="hidden" name="idAdvertisement" value="${booking.idAdvertisement}"/>
                                                    <button type="submit" class="w3-section button" >Delete</button><br/>
                                                </form>
                                            </td>
                                            <%count++;%>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:otherwise>
                        </c:choose>
                    </section>

                </section>
            </c:when>
            <c:otherwise>
            <section class="userInfo">
                <br>
                <br>
                <h1>${user.name}</h1>
                <h2>My info</h2>
                <section id="userInfoSection" class="w3-panel w3-card-4 w3-section">
                    </br>

                    <p class="w3-input w3-section">Email: ${user.email}</p>
                    <p class="w3-input w3-section">Phone number: ${user.phoneNumber}</p>
                    <p class="w3-input w3-section">Address: ${user.address}</p>
                    <p class="w3-input w3-section" name="userCity" id="${user.idCity}"></p>
                    </br>
                    <button name="editUserProfile" class"w3-section button">Edit Profile</button><br/>
                </section>

                <section id="createAdvertisement" class="w3-container w3-section ">
                    <button class="w3-section button" id="createAdvertisementButton">Create new advertisement</button>
                </section>
                <section>
                    <%
                        List<Advertisement> advertisementList = (List<Advertisement>) request.getAttribute("advertisementList");
                    %>
                    </br>
                    <h2>My advertisements</h2>
                    </br>
                    <c:choose>
                        <c:when test="${empty advertisementList}">
                            <p name="emptyList">No advertisement created</p>
                        </c:when>
                        <c:otherwise>
                            <table class="w3-table w3-container w3-section ">
                                <tr>
                                    <td class="w3-input w3-section">Title Advertisement</td>
                                    <td class="w3-input w3-section">Date Start</td>
                                    <td class="w3-input w3-section">Date End</td>
                                    <td class="w3-input w3-section">Item Available</td>
                                    <td class="w3-input w3-section">Price</td>
                                    <td></td>
                                </tr>
                                <c:forEach items="${advertisementList}" var="adv">
                                    <tr>
                                        <td class="w3-input w3-section">${adv.title}   </td>
                                        <td class="w3-input w3-section">${adv.dateStart}   </td>
                                        <td class="w3-input w3-section">${adv.dateEnd}   </td>
                                        <td class="w3-input w3-section">${adv.numTotItem}   </td>
                                        <td class="w3-input w3-section">${adv.price}   </td>
                                        <td>
                                                    <span>
                                                        <form id="gotoEditAdvertisementForm" name="gotoEditAdvertisementForm" method="GET"/>
                                                        <button class="w3-section button" name="editAdvertisementButton" value="${adv.idAdvertisement}">Edit</button><br/>
                                                        </form>
                                                    </span>
                                            <span>
                                                        <form id="gotoShowAdvertisementForm" name="gotoShowAdvertisementForm" method="GET"/>
                                                        <button class="w3-section button" name="showAdvertisementButton" value="${adv.idAdvertisement}">Info</button><br/>
                                                </form>
                                                    </span>
                                            <span>
                                                        <form id="deleteAdvertisementForm" name="deleteAdvertisementForm" method="DELETE">
                                                        <button class="w3-section button" name="deleteAdvertisementButton" value="${adv.idAdvertisement}">Delete</button>
                                                        </form>
                                                    </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </section>
                </c:otherwise>
                </c:choose>
        </main>
        <div id="footer-area"></div>
    </div>
    </body>
</html>