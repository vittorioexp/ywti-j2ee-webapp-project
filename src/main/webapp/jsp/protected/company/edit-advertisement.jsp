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
<div>
    <a href="${pageContext.request.contextPath}/index">Home</a>
    <a href="${pageContext.request.contextPath}/user/profile">Profile</a>
    <a href="${pageContext.request.contextPath}/html/contacts.html">Contacts</a>
</div>
<div>

    <%
        int idAdvertisement = (int) request.getAttribute("idAdvertisement");
        String url = "/advertisement/" + String.valueOf(idAdvertisement);
    %>

<form method="put" action="<c:url value="${url}"/>">

    <label for="price">price:</label>
    <input id="price" name="price" type="text"/><br/><br/>

    <label for="numTotItem">numTotItem:</label>
    <input id="numTotItem" name="numTotItem" type="text"/><br/><br/>

    <button type="submit">Edit</button><br/>

</form>
</div>
    <div>
        <c:import url="/jsp/include/show-message.jsp"/>
    <div>
</body>
</html>