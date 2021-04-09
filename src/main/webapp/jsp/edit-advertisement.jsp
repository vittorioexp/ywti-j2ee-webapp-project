

<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Edit advertisement</title>
</head>
<body>

<form method="POST" action="<c:url value="/adverisement/*"/>">         //aggiungere op in EditAdvertisementServlet

    <label for="title">title:</label>
    <input name="title" type="text"/><br/><br/>

    <label for="type_adv">type_adv:</label>
    <input name="type_adv" type="text"/><br/><br/>

    <label for="description">description:</label>
    <input name="description" type="text"/><br/><br/>

    <label for="price">price:</label>
    <input name="price" type="text"/><br/><br/>

    <label for="numTotItem">numTotItem:</label>
    <input name="numTotItem" type="text"/><br/><br/>

    <label for="dateStart">dateStart:</label>
    <input name="dateStart" type="text"/><br/><br/>

    <label for="dateEnd">dateEnd:</label>
    <input name="dateEnd" type="text"/><br/><br/>

    <label for="timeStart">timeStart:</label>
    <input name="timeStart" type="text"/><br/><br/>

    <label for="timeEnd">timeEnd:</label>
    <input name="timeEnd" type="text"/><br/><br/>

    <button type="submit">Edit</button><br/>

</form>

<c:choose>
    <c:when test="${message.error}">
        <p><c:out value="${message.message}"/></p>
    </c:when>
    <c:otherwise></c:otherwise>
</c:choose>
</body>
</html>