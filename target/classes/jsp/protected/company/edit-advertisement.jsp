<%@ page import="it.unipd.dei.yourwaytoitaly.resource.User" %>

<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Edit advertisement</title>
</head>
<body>

<header>
    <h1>Edit Advertisement</h1>
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

<div>

    <%
        int idAdvertisement = 0;
        String url = "/adv/" + String.valueOf(idAdvertisement);
    %>
<p>This is a mock page. Please, use the proper curl command to update an advertisement</p>

<form method="PUT" action="<c:url value="/adv/${idAdvertisement}"/>" >

    <label for="title">title:</label>
    <input id="title" name="title" type="text"/><br/><br/>

    <label for="idType">type:</label>
    <input id="idType" name="idType" type="number"/><br/><br/>

    <label for="description">description:</label>
    <input id="description" name="description" type="text"/><br/><br/>

    <label for="price">price:</label>
    <input id="price" name="price" type="number" /><br/><br/>

    <label for="numTotItem">numTotItem:</label>
    <input id="numTotItem" name="numTotItem" type="number" /><br/><br/>

    <label for="dateStart">dateStart:</label>
    <input id="dateStart" name="dateStart" type="date" /><br/><br/>

    <label for="dateEnd">dateEnd:</label>
    <input id="dateEnd" name="dateEnd" type="date" /><br/><br/>

    <label for="timeStart">timeStart:</label>
    <input id="timeStart" name="timeStart" type="time" value="05:00:00"/><br/><br/>

    <label for="timeEnd">timeEnd:</label>
    <input id="timeEnd" name="timeEnd" type="time" value="23:30:00"/><br/><br/>

    <button type="submit">Edit</button><br/>

</form>
</div>
    <div>
        <c:import url="/jsp/include/show-message.jsp"/>
    </div>
</body>
</html>