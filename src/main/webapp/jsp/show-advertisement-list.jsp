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

<%
    List<Advertisement> list_adv = (List<Advertisement>) request.getAttribute("advertisement-list");
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
    <c:forEach items="${<%=list_adv%>}" var="advertisment">
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