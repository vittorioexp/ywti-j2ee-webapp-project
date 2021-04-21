<%@ page import="it.unipd.dei.yourwaytoitaly.resource.User" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.servlet.SessionCheckServlet" %>

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
<p>Attention: the data must be in JSON format</p>

<form method="PUT" action="<c:url value="/adv/${idAdvertisement}"/>" >

    <label for="price">price:</label>
    <input id="price" name="price" type="text"/><br/><br/>

    <label for="numTotItem">numTotItem:</label>
    <input id="numTotItem" name="numTotItem" type="text"/><br/><br/>

    <button type="submit">Edit</button><br/>

</form>
</div>
    <div>
        <c:import url="/jsp/include/show-message.jsp"/>
    </div>
</body>
</html>