<%@ page import="it.unipd.dei.yourwaytoitaly.utils.ErrorCode" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Message" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.resource.Advertisement" %>
<%@ page import="it.unipd.dei.yourwaytoitaly.database.AdvertisementDAO" %>
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
Marco Basso
Matteo Piva
Francecso Giurisato
Version: 1.0
Since: 1.0
-->

<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="author" content="Basso Marco, Esposito Vittorio, Piva Matteo, Giurisato Francesco">
    <meta name="description" content="upload images page">
    <meta name="keywords" content="upload, upload images, ywti, local, travel, italy">
    <!-- The viewport meta element is the key to making a responsive site work. -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Upload Images</title>

    <!-- Jquery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>

    <!-- Font awesome -->
    <link href="/ywti_wa2021_war/css/fontawesome-free-5.15.3-web/css/all.css" rel="stylesheet">

    <!-- W3 school CSS -->
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

    <!-- Common CSS -->
    <link href="/ywti_wa2021_war/css/style/ywti.css" rel="stylesheet" type="text/css">

    <!-- Custom CSS -->
    <link href="/ywti_wa2021_war/css/style/upload-images-page.css" rel="stylesheet" type="text/css">

    <!-- Common JS -->
    <script src="/ywti_wa2021_war/js/utils.js"></script>

    <!-- Custom JS -->
    <script src="/ywti_wa2021_war/js/upload-images-page.js"></script>

</head>
<body>
<div class="mainWrapper w3-main">
    <header id="header-bar" class="">
        <img id="small-logo" class="small-logo" src="/ywti_wa2021_war/utility/small-logo-transparent.png" >
        <div id="navbar-area" class="topnav" ></div>
    </header>
    <main class="mainContent w3-container" >
        <!-- Retrieve the advertisement for which we are uploading the image(s) -->
        <%
            String path = request.getRequestURI();
            String idAdvertisement = path.substring(path.lastIndexOf("image-do-upload") + 16);
            int id = Integer.parseInt(idAdvertisement);
            Advertisement a = AdvertisementDAO.searchAdvertisement(id);
        %>
        <h1 class="h1 p-3" id="advTitle">Upload images for <%= a.getTitle() %></h1>
        <div class="rowImg">
            <section id="mainPanel" class="rowImg 2 w3-panel w3-card">

                <!-- Upload image(s) -->
                <form id="uploadImagesForm" class="w3-center" name="uploadImagesForm" method="post" enctype="multipart/form-data"
                      action="<c:url value="/image-upload" />" >
                    <input type="hidden" name="idAdvertisement" value="<%= idAdvertisement %>" />
                    <label id="upload" for="image">
                        <i class="fa fa-cloud-upload"></i>
                        Choose images
                    </label>
                    <br>
                    <br>
                    <div id="previews"></div>
                    <input id="image" name="image" class="button" type="file" id="file" onChange="readURL(this);" multiple/><br/><br/>
                    <button type="submit" name="Submit" class="button" value="Submit">Upload</button><br/>
                </form>
            </section>
        </div>
    </main>
    <!-- footer imported with javascript -->
    <div id="footer-area"></div>
</div>
</body>
</html>


