<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Advertisement" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Booking" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Feedback" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.resource.User" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.servlet.SessionCheckServlet" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.database.AdvertisementDAO" %>
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
Version: 1.0
Since: 1.0
-->

<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>show advertisement</title>
</head>
<body>
<header>
    <h1>Show Advertisement</h1>
</header>
<nav>
    <a href="${pageContext.request.contextPath}/index">Home</a>

    <c:choose>
        <c:when test="${empty sessionScope.Authorization}">
            <a href="${pageContext.request.contextPath}/user/do-login">Login</a>
            <a href="${pageContext.request.contextPath}/user/do-register">Register</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/user/profile">Profile</a>
        </c:otherwise>
    </c:choose>

    <a href="${pageContext.request.contextPath}/html/contacts.html">Contacts</a>
</nav>

<%
    Advertisement adv = Advertisement.fromJSON(request.getInputStream());
%>
<table cellpadding="1"  cellspacing="1" border="1" bordercolor="gray">
    <tr>
        <td>Title</td>
        <td>Description</td>
        <td>Date Start</td>
        <td>Date End</td>
        <td>Time Start</td>
        <td>Time End</td>
        <td>Email company</td>
        <td>Num Tot Item</td>
        <td>Price</td>
        <td>Score</td>
    </tr>
    <tr>
        <td><%=adv.getTitle() %></td>
        <td><%=adv.getDescription() %></td>
        <td><%=adv.getDateStart() %></td>
        <td><%=adv.getDateEnd() %></td>
        <td><%=adv.getTimeStart() %></td>
        <td><%=adv.getTimeEnd() %></td>
        <td><%=adv.getEmailCompany()%></td>
        <td><%=adv.getNumTotItem() %></td>
        <td><%=adv.getPrice() %></td>
        <td><%=adv.getScore() %></td>
    </tr>
</table>

<%
    int rate = (int) request.getAttribute("rate");
%>
<c:choose>
    <c:when test="${rate}">
        <p><c:out value="${rate}"/></p>
    </c:when>
    <c:otherwise>
        Not available
    </c:otherwise>
</c:choose>

    <%
        List<String> filepathList = (List) request.getAttribute("filepath-list");
    %>
    <c:choose>
        <c:when test="${filepathList}">
            <table id="image-list-table" name="image-list-table"
                   cellpadding="1"  cellspacing="1" border="1" bordercolor="gray">
            <c:forEach items="<%=filepathList%>" var="filepath">
                <tr>
                    <td>
                        <img src="${pageContext.request.contextPath}" + filepath>
                    </td>
                </tr>
                </table>
            </c:forEach>
        </c:when>
        <c:otherwise>

        </c:otherwise>
    </c:choose>

    <%
        List<Booking> bookingList = (List) request.getAttribute("booking-list");
        // TODO: insert form to do a booking
    %>
    <c:choose>
        <c:when test="${bookingList}">
        <table id="booking-list-table" name="booking-list-table"
               cellpadding="1"  cellspacing="1" border="1" bordercolor="gray">
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <c:forEach items="<%=bookingList%>" var="booking">
                <tr>
                    <td>${booking.emailTourist()}</td>
                    <td>${booking.date()}</td>
                    <td>${booking.time()}</td>
                    <td>${booking.numBooking()}</td>
                    <td>${booking.state()}</td>
                </tr>
            </c:forEach>
        </table>
        </c:when>
        <c:otherwise>

        </c:otherwise>
    </c:choose>

    <%
    List<Feedback> feedbackList = (List) request.getAttribute("feedback-list");
    // TODO: insert form to leave a feedback
    %>
    <c:choose>
    <c:when test="${feedbackList}">
        <table id="feedback-list-table" name="feedback-list-table"
            cellpadding="1"  cellspacing="1" border="1" bordercolor="gray">
            <tr>
            <td></td>
            <td></td>
            <td></td>
            </tr>
            <c:forEach items="<%=feedbackList%>" var="feedback">
                <tr>
                <td>${feedback.date()}</td>
                <td>${feedback.rate()}</td>
                <td>${feedback.text()}</td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>

    </c:otherwise>
    </c:choose>

        <div>
            <c:import url="/jsp/include/show-message.jsp"/>
        </div>
</body>
</html>
</body>
</html>