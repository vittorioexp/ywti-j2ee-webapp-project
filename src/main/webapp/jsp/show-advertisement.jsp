<%@ page import="org.json.JSONObject" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.sql.Time" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.resource.*" %>
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
            <a href="${pageContext.request.contextPath}/user/do-logout">Logout</a>
        </c:otherwise>
    </c:choose>

    <a href="${pageContext.request.contextPath}/html/contacts.html">Contacts</a>
</nav>
</br>

<%
    // For debug only
    Advertisement adv = (Advertisement) request.getAttribute("advertisement");
    int idAdvertisement = adv.getIdAdvertisement();
    // TODO: don't show the score of an advertisement
%>
<table cellpadding="1"  cellspacing="1" border="1" bordercolor="gray">
    <tr>
        <td>Title</td>
        <td>Description</td>
        <td>Date Start</td>
        <td>Date End</td>
        <td>Time Start</td>
        <td>Time End</td>
        <td>Items available</td>
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
        <td><%=adv.getNumTotItem() %></td>
        <td><%=adv.getPrice() %></td>
        <td><%=adv.getScore() %></td>
    </tr>
</table>
</br>
<div>
    <%
        List<Image> imageList = (List<Image>) request.getAttribute("imageList");
    %>
    <table id="imageTable" name="imageTable"
           cellpadding="1"  cellspacing="1" border="1" bordercolor="gray">
        <tr>
        <c:forEach items="<%=imageList%>" var="image">
            <td><img src="${image.path}" width="320" height="240"/></td>
        </c:forEach>
        </tr>
    </table>
</div>
<p>
    Rate is: <%=((Rate) request.getAttribute("rate")).getRate() %>
</p>
</br>
    <%
        List<Booking> bookingList = (List) request.getAttribute("bookingList");
    %>
    <table id="booking-list-table" name="booking-list-table"
           cellpadding="1"  cellspacing="1" border="1" bordercolor="gray">
        <c:forEach items="<%=bookingList%>" var="booking">
            <tr>
                <td>${booking.getEmailTourist()}</td>
                <td>${booking.getDate()}</td>
                <td>${booking.getTime()}</td>
                <td>${booking.getNumBooking()}</td>
                <td>${booking.getState()}</td>
            </tr>
        </c:forEach>
    </table>
</br>
<%
    // TODO: show the following form NOT to companies
%>
    <div>
        <form id="booking-form" name="booking-form" method="POST" action="<c:url value="/booking-create"/>">
            <label for="numBooking">numBooking:</label>
            <input id="numBooking" name="numBooking" type="number" required/><br/><br/>
            <input type="hidden" id="idAdvertisement" name="idAdvertisement" value="<%=idAdvertisement %>" />
            <button type="submit">Book your journey</button><br/>
        </form>
    </div>
</br>
    <%

    List<Feedback> feedbackList = (List) request.getAttribute("feedbackList");
    %>
    <table id="feedback-list-table" name="feedback-list-table"
        cellpadding="1"  cellspacing="1" border="1" bordercolor="gray">
        <c:forEach items="<%=feedbackList%>" var="feedback">
            <tr>
            <td>${feedback.getDate()}</td>
            <td>${feedback.getRate()}</td>
            <td>${feedback.getText()}</td>
            </tr>
        </c:forEach>
    </table>
    </br>
<%
    // TODO: show the following form NOT to companies
%>
    <div>
    <form id="feedback-form" name="feedback-form" method="POST" action="<c:url value="/feedback-create"/>">
        <label for="rate">rate:</label>
        <input id="rate" name="rate" type="number" min="1" max="5" step="1" required/>
        <label for="text_f">text::</label>
        <input id="text_f" name="text_f" type="text"/>
        <input type="hidden" name="idAdvertisement" value="<%=idAdvertisement %>" />
        <button type="submit">Leave a feedback</button><br/>
    </form>
    </div>
</br>
    <div>
        <c:import url="/jsp/include/show-message.jsp"/>
    </div>
</body>
</html>