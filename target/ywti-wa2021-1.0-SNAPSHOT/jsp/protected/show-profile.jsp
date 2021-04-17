<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Tourist" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Company" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Booking" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Advertisement" %>
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
        <a href="${pageContext.request.contextPath}/index">Home</a>

        <c:choose>
            <c:when test="${empty sessionScope.Authorization}">
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/user/do-logout">Logout</a>
                <a href="${pageContext.request.contextPath}/user/do-edit">Edit profile</a>
            </c:otherwise>
        </c:choose>

        <a href="${pageContext.request.contextPath}/html/contacts.html">Contacts</a>

    </nav>


    <c:choose>
        <c:when test="${userType}">
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
                    <td><c:out value="${user.surname}"/></td>
                    <td><c:out value="${user.phoneNumber}"/></td>
                    <td><c:out value="${user.address}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.idCity}"/></td>
                    <td><c:out value="${user.birthDate}"/></td>
                </tr>
            </table>

            <table>
                <tr>
                    <td>Booking date</td>
                    <td>Item booked</td>
                    <td>Booking state</td>
                </tr>
            </table>
            <%
                List<Booking> bookingslist = (List) request.getAttribute("bookings-list");
            %>
            <table>
            <c:forEach items="<%=bookingslist%>" var="booking">
                    <tr>
                        <td>${booking.date}</td>
                        <td>${booking.numBooking}</td>
                        <td>${booking.state}</td>
                        <td>
                            <form id="delete booking-form" name="delete booking-form" action = "<c:url value="/booking-delete"/>" method="POST">
                                <input type="hidden" id="idAdvertisement" name="idAdvertisement" value="${booking.idAdvertisement}"/>
                                <button type="submit" >Delete</button><br/>
                            </form>
                        </td>
                    </tr>
            </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
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
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.phoneNumber}"/></td>
                    <td><c:out value="${user.address}"/></td>
                    <td><c:out value="${user.idCity}"/></td>
                </tr>
            </table>
            <form method="get" action="<c:url value="/advertisement-do-create"/>">
                <button type="submit">New advertisement</button>
            </form>
            <%
                // TODO : get JSON ResourceList<Advertisement>
                List<Advertisement> advertisementList = null;
            %>
            <table>
            <c:forEach items="${advertisementList}" var="adv">
                    <tr>
                        <td>${adv.title}</td>
                        <td>${adv.dateStart}</td>
                        <td>${adv.dateEnd}</td>
                        <td>${adv.numTotItem}</td>
                        <td>${adv.price}</td>
                        <!-- TODO: inserire la lista delle immagini -->
                        <td>
                            <form id="gotoEditAdvertisementForm" name="gotoEditAdvertisementForm" method="GET"
                                  action="<c:url value="/advertisement-edit"/>">
                                <input type="hidden" name="idAdvertisement" value="${adv.idAdvertisement}">
                                <button type="submit">Edit</button><br/>
                            </form>
                        </td>
                        <td><button type="submit" formaction="/advertisement/${adv.idAdvertisement}">Info</button></td>
                        <td>
                            <form id="deleteAdvertisement" name="deleteAdvertisement" method="DELETE"
                                  action="<c:url value="/advertisement/${adv.idAdvertisement}"/>">
                                <button type="submit">Delete</button>
                            </form>
                        </td>
                    </tr>
            </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>

     <div>
         <c:import url="/jsp/include/show-message.jsp"/>
     </div>

    </body>
</html>