<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Advertisement" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>show advertisement</title>
</head>
<body>

<!-- < List<Advertisement> list_adv = (List<Advertisement>) request.getAttribute("advertisement-list"); %> -->
<%
    List<Advertisement> listAdvertisement = (List<Advertisement>) request.getAttribute("advertisement_list");
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
    <!-- Modificare attibute advertisement-list con advertisement_list-->
    <c:forEach items="<%=listAdvertisement%>" var="advertisment">
        <tr>
            <td>${advertisment.title()}</td>
            <td>${advertisment.description()}</td>
            <td>${advertisment.dateStart()}</td>
            <td>${advertisment.dateEnd()}</td>
            <td>${advertisment.timeStart()}</td>
            <td>${advertisment.timeEnd()}</td>
            <td>${advertisment.emailCompany()}</td>
            <td>${advertisment.numTotItem()}</td>
            <td>${advertisment.price()}</td>
            <td>${advertisment.score()}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

<c:choose>
    <c:when test="${message.error}">
        <p><c:out value="${message.message}"/></p>
    </c:when>
    <c:otherwise>

    </c:otherwise>
</c:choose>
</body>
</html>