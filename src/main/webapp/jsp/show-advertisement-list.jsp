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
    <a href="${pageContext.request.contextPath}/index">Home</a>

    <%
        User u = new SessionCheckServlet(request, response).getUser();
    %>
    <c:choose>
        <c:when test="${u}">
            <a href="${pageContext.request.contextPath}/user/profile">Profile</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/user/do-login">Login</a>
            <a href="${pageContext.request.contextPath}/user/do-register">Register</a>
        </c:otherwise>
    </c:choose>

    <a href="${pageContext.request.contextPath}/html/contacts.html">Contacts</a>

    <%
        // TODO : get JSON ResourceList<Advertisement>
        ResourceList<Advertisement> listAdvertisement = null;
    %>
        <table id="advertisement-list-table" name="advertisement-list-table"
               cellpadding="1"  cellspacing="1" border="1" bordercolor="gray">
            <tr>
                <td>Title</td>
                <td></td>
            </tr>
            <!-- Modificare attibute advertisement-list con advertisement_list-->
            <c:forEach items="<%=listAdvertisement%>" var="advertisment">
                <tr>
                    <td>${advertisment.title()}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/advertisement/" +
                            ${advertisment.idAdvertisement()}>info</a>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <c:choose>
            <c:when test="${message.error}">
                <p><c:out value="${message.message}"/></p>
            </c:when>
            <c:otherwise>

            </c:otherwise>
        </c:choose>

    </body>
</html>