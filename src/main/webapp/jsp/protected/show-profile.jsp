<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Tourist" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Company" %>
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
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>show profile</title>
    </head>
    <body>
    <header>
        <h1>Show profile</h1>
    </header>
    <nav>
        <a href="${pageContext.request.contextPath}/protected/edit-profile.jsp">Home</a>

        <%!
        %><c:choose>
            <c:when test="${empty sessionScope.Authorization}">
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/user/do-logout">Logout</a>
            </c:otherwise>
        </c:choose>

        <a href="${pageContext.request.contextPath}/html/contacts.html">Contacts</a>

    </nav>


    <c:choose>
        <c:when test="${userType}">
            // User = Tourist
            <table>
                <tr>
                    <td>Name</td>
                    <td>Surname</td>
                    <td>Phone number</td>
                    <td>Address</td>
                    <td>Email</td>
                    <td>City</td>
                    <td>Birthdate</td>
                </tr>
                <tr>
                    <td><c:out value="${user.name}"/></td>
                    <td><c:out value="${user.surname}"/><</td>
                    <td><c:out value="${user.phoneNumber}"/><</td>
                    <td><c:out value="${user.address}"/><</td>
                    <td><c:out value="${user.email}"/><</td>
                    <td><c:out value="${user.idCity}"/><</td>
                    <td><c:out value="${user.birthDate}"/><</td>
                </tr>
            </table>

            <button type="submit" formaction="/user/do-edit" method="PUT">Delete</button>

            <table>
                <tr>
                    <td>Booking date</td>
                    <td>Item booked</td>
                    <td>Booking state</td>
                </tr>
            </table>

            <c:forEach items="${bookings-list}" var="booking">
                <table>
                    <tr>
                        <td>${booking.date}</td>
                        <td>${booking.numBooking}</td>
                        <td>${booking.state}</td>
                        <input type="hidden" id="idAdvertisement" name="idAdvertisement" value="${booking.idAdvertisement}">
                        <td><button type="submit" formaction="/booking-delete" method="PUT">Delete</button><br/></td>
                    </tr>
                </table>
            </c:forEach>
        </c:when>
        <c:otherwise>
            // User = Company
            <table>
                <tr>
                    <td>Name</td>
                    <td>Email</td>
                    <td>Phone number</td>
                    <td>Address</td>
                    <td>City</td>
                </tr>
                <tr>
                    <td><c:out value="${user.name}"/></td>
                    <td><c:out value="${user.email}"/><</td>
                    <td><c:out value="${user.phoneNumber}"/><</td>
                    <td><c:out value="${user.address}"/><</td>
                    <td><c:out value="${user.idCity}"/><</td>
                </tr>
            </table>
            <!-- TODO: Create the advertisement-create.jsp -->
            <button type="submit" formaction="/advertisement-create">Inset new advertisement</button>

            <table>
                <tr>
                    <td>Title</td>
                    <!-- TODO:insert the ield for the image -->
                    <td>Edit</td>
                    <td>Info</td>
                </tr>
            </table>

            <c:forEach items="${advertisement-list}" var="advertisement">
                <table>
                    <tr>
                        <td>${advertisement.title}</td>
                        <!-- TODO: inserire la lista delle immagini -->
                        <td><button type="submit" formaction="/advertisement-edit">Edit</button><br /></td>
                        <td><button type="submit" formaction="/advertisement${advertisement.idAdvertisement}">Info</button><br/></td>
                    </tr>
                </table>
            </c:forEach>
        </c:otherwise>
    </c:choose>

     <div>
         <c:import url="/jsp/include/show-message.jsp"/>
     </div>

    </body>
</html>