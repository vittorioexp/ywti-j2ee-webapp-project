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
    <title>Create Booking</title>
</head>

<body>
<h1>Create Booking</h1>
<hr/>

<!-- display the message -->
<c:import url="/jsp/include/show-message.jsp"/>

<!-- display the just created employee, if any and no errors -->
<c:if test='${not empty booking && !message.error}'>
    <ul>
        <li>badge: <c:out value="${employee.badge}"/></li>
        <li>surname: <c:out value="${employee.surname}"/></li>
        <li>age: <c:out value="${employee.age}"/></li>
        <li>salary: <c:out value="${employee.salary}"/></li>
    </ul>
</c:if>
</body>
</html>