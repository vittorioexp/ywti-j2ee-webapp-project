<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Advertisement" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Resource" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.resource.User" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.servlet.SessionCheckServlet" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.resource.ResourceList" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>show advertisement list</title>
</head>
    <body>
    <header>
        <h1>Show Advertisement List</h1>
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
    <%
        // TODO : get JSON ResourceList<Advertisement>
        ResourceList<Advertisement> listAdvertisement = null;
    %>
        <table id="advertisement-list-table" name="advertisement-list-table"
               cellpadding="1"  cellspacing="1" border="1" bordercolor="gray">
            <c:forEach items="<%=listAdvertisement%>" var="adv">
                <tr>
                    <td>${adv.title}</td>
                    <td>${adv.dateStart}</td>
                    <td>${adv.dateEnd}</td>
                    <td>${adv.numTotItem}</td>
                    <td>${adv.price}</td>
                    <td>
                        <!-- TODO: Insert the idAdvertisement -->
                        <form id="showAdvertisement" name="showAdvertisement" method="GET" action="<c:url value=""/advertisement/" + ${advertisment.idAdvertisement/>">
                            <button type="submit">Info</button><br/>
                        </form>


                    </td>
                </tr>
            </c:forEach>
        </table>
<div>
    <c:import url="/jsp/include/show-message.jsp"/>
</div>

    </body>
</html>