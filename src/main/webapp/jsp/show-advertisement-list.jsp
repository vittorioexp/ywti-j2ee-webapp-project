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
    <c:forEach items="${advertisement_list}" var="advertisment">
        <tr>
            <td>${advertisment.getTitle()}</td>
            <td>${advertisment.getDescription()}</td>
            <td>${advertisment.getDateStart()}</td>
            <td>${advertisment.getDateEnd()}</td>
            <td>${advertisment.getTimeStart()}</td>
            <td>${advertisment.getTimeEnd()}</td>
            <td>${advertisment.getEmailCompany()}</td>
            <td>${advertisment.getNumTotItem()}</td>
            <td>${advertisment.getPrice()}</td>
            <td>${advertisment.getScore()}</td>
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