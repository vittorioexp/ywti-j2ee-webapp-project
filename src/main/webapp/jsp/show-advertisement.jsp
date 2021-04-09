<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Advertisement" %>
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

<%
    Advertisement adv = (Advertisement) request.getAttribute("advertisement");
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