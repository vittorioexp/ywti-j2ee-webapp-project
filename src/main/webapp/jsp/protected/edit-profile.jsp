<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Tourist" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.resource.User" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.servlet.SessionCheckServlet" %>
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
    <title>Edit profile</title>
</head>
<body>
<div>
    <a href="${pageContext.request.contextPath}/index">Home</a>
    <a href="${pageContext.request.contextPath}/user/profile">Profile</a>
    <a href="${pageContext.request.contextPath}/html/contacts.html">Contacts</a>
</div>

<div>
    <form method="put" action="<c:url value="/user/edit"/>">

        <label for="phonenumber">Phone Number:</label>
        <input id="phonenumber" name="phonenumber" type="text"/><br/><br/>

        <button type="submit">Edit</button><br/>

    </form>
    <br />
</div>


<div>
    <c:import url="/jsp/include/show-message.jsp"/>
</div>
</body>
</html>