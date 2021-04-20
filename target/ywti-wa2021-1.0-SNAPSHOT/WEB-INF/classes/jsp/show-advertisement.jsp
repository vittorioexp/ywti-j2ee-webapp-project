<%@ page import="it.unipd.dei.yourwaytoitaly.resource.*" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.sql.Time" %>
<%@ page import="java.util.ArrayList" %>
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
    <h1>Show Advertisement - mock page</h1>
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
    <br>
    <p>This is a mock page. Requests to the REST web server will be made in order to show the desired advertisement.</p>
    </br>
    <h3> Delicious dinner in the Dolomites, da Pino </h3>
    <p> This is a mock advertisement. Requests to the REST web server will be made in order to show the desired advertisement. </p>
    <p> Rated: 4.7/5</p>
    <p> At only 19 euro </p>
    <p> Starting from 19/04/2021 to 01/07/2021  </p>
    <p> Opening time: 18:30:00 - 23:30:00 </p>
    <p> Items available: 28 </p>
    </br>
    <p>Images of the advertisement will be shown here!</p>
    </br>
    <p>
        The following is a simple form to create a booking.

        <form id="createBookingForm" name="createBookingForm" method="POST" action="<c:url value="/booking-create"/>">
            <label for="numBooking">numBooking:</label>
            <input id="numBooking" name="numBooking" type="number" required/><br/><br/>
            <input type="hidden" name="idAdvertisement" value="1" />
            <button type="submit">Book your journey</button><br/>
        </form>
    </p>

    <p>
        The following is a simple form to leave a feedback.

    <form id="createFeedbackForm" name="createFeedbackForm" method="POST" action="<c:url value="/feedback-create"/>">
        <label for="rateFeedback">rate:</label>
        <input id="rateFeedback" name="rateFeedback" type="number" min="1" max="5" step="1" required/>
        <label for="textFeedback">text:</label>
        <input id="textFeedback" name="textFeedback" type="text"/>
        <input type="hidden" name="idAdvertisement" value="1" />
        <button type="submit">Leave a feedback</button><br/>
    </form>
    </p>
    </br>
    <p> Here it will be shown a list of feedback.</p>
    <p>
        Rate: 5/5, "Amazing experience!"
        Written on 31/04/2021
    </p>
    <p>
        Rate: 5/5, "This was super cool!"
        Written on 2/05/2021
    </p>
    <p>
        Rate: 4/5, "Good! I will surely come back"
        Written on 5/05/2021
    </p>

    </br>

    <p>
        Here the owner of the advertisement will see the list of bookings.
    </p>
    <p>
        pippopasticcio@gmail.com booked for 2.
        29/04/2021 - 14:30
    </p>
    <p>
        filippo@gmail.com booked for 1.
        28/04/2021 - 12:33
    </p>

</article>
</body>
</html>