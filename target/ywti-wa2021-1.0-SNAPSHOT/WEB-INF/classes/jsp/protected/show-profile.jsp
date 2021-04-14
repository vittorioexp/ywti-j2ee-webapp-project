<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Tourist" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Company" %>
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
        <title>show profile</title>
    </head>
    <body>
    <header>
        <h1>Show profile</h1>
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
            </c:otherwise>
        </c:choose>

        <a href="${pageContext.request.contextPath}/html/contacts.html">Contacts</a>
    </nav>
        <c:choose>
            <c:when test="${isTurist eq True}">      <!--aggiungere funzionalità per testare se l'utente loggato è un turist o una company-->
                <%
                    Tourist tourist = (Tourist) request.getAttribute("tourist");
                %>
                <table cellpadding="1"  cellspacing="1" border="1" bordercolor="gray">
                    <tr>
                        <td>Name</td>
                        <td>Surname</td>
                        <td>Address</td>
                        <td>Email</td>
                        <td>Birthdate</td>
                        <td>IDCity</td>
                        <td>PhoneNumber</td>
                    </tr>
                    <tr>
                        <td><%=tourist.getName() %></td>
                        <td><%=tourist.getSurname()%></td>
                        <td><%=tourist.getAddress() %></td>
                        <td><%=tourist.getEmail() %></td>
                        <td><%=tourist.getBirthDate() %></td>
                        <td><%=tourist.getIdCity()%></td>
                        <td><%=tourist.getPhoneNumber()%></td>
                    </tr>
                </table>
            </c:when>
            <c:otherwise>
                <%
                    Company company = (Company) request.getAttribute("company");
                %>
                <table cellpadding="1"  cellspacing="1" border="1" bordercolor="gray">
                    <tr>
                        <td>Name</td>
                        <td>Address</td>
                        <td>Email</td>
                        <td>IDCity</td>
                        <td>PhoneNumber</td>
                    </tr>
                    <tr>
                        <td><%=company.getName() %></td>
                        <td><%=company.getAddress()%></td>
                        <td><%=company.getEmail() %></td>
                        <td><%=company.getIdCity()%></td>
                        <td><%=company.getPhoneNumber()%></td>
                    </tr>
                </table>
                <br />
            </c:otherwise>
        </c:choose>

 <div>
     <c:import url="/jsp/include/show-message.jsp"/>
 </div>

    </body>
</html>