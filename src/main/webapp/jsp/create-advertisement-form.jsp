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
    <title>Create Employee Form</title>
</head>

<body>
<h1>Create Employee Form</h1>

<form method="POST" action="<c:url value="/create-employee"/>">
    <label for="badge">Badge:</label>
    <input name="badge" type="text"/><br/>

    <label for="surname">Surname:</label>
    <input name="surname" type="text"/><br/>

    <label for="age">Age:</label>
    <input name="age" type="text"/><br/>

    <label for="salary">Salary:</label>
    <input name="salary" type="text"/><br/><br/>

    <button type="submit">Submit</button><br/>
    <button type="reset">Reset the form</button>
</form>
</body>
</html>