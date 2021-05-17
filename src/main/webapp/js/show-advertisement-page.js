let slideIndex = 1;

document.addEventListener("DOMContentLoaded", function(event) {

    let idAdv = getIdAdvertisement();

    if (idAdv===-1) {
        window.location.replace(contextPath + "/html/error.html");
    }

    document.getElementById("idAdvFeedback").setAttribute("value", idAdv);
    document.getElementById("idAdvBooking").setAttribute("value", idAdv);
    fetchAdvertisement();
    fetchRate();
    fetchFeedbackList();
    fetchImageList();

    // if a company is visiting show-advertisement.html, hide "leave a feedback" form
    if (isLoggedIn() && getUserRole()==="company") {
        document.getElementById("createFeedback").style.display = "none";
        document.getElementById("createBooking").style.display = "none";
    }
    //images slideshow
    plusDivs(-1);
});

function getIdAdvertisement() {
    let url = window.location.href;
    url = url.substring(
        url.lastIndexOf("adv-show/") + 9
    );
    let id = parseInt(url,  10);
    if (id<=0) {
        return -1;
    }
    return url;
}

function fetchAdvertisement() {
    let url = new URL(contextPath+"/adv/" + getIdAdvertisement());
    sendJsonRequest(url,"GET","",loadAdvertisement);
}

function loadAdvertisement(req) {
    // Parses the JSON obj
    let jsonData = JSON.parse(req).advertisement;
    let title = jsonData['title'];
    let description = jsonData['description'];
    let price = jsonData['price'];
    let score = jsonData['score'];
    let numTotItem = jsonData['numTotItem'];
    let dateStart = jsonData['dateStart'];
    let dateEnd = jsonData['dateEnd'];
    let timeStart = jsonData['timeStart'];
    let timeEnd = jsonData['timeEnd'];
    let emailCompany = jsonData['emailCompany'];

    // Presents the JSON obj
    document.getElementById("advTitle").innerHTML = "<h1 id=\"advTitle\">" + title + "</h1>";

    let info =
        "<section class=\"w3-panel w3-card advInfoElement\">" +
        "<p class=\"w3-panel advInfoElement\">" + description + "" +
        "<p class=\"w3-panel advInfoElement\">" + "Only " + price + " euro!" + "</p>" +
        "<p class=\"w3-panel advInfoElement\">" + "There are just " + numTotItem + " items available!" + "</p>" +
        "<p class=\"w3-panel advInfoElement\">" + "The event is starting the day " + dateStart + " at " + timeStart + " until " + dateEnd + " at " + timeEnd + "</p><br>" +
        "<p class=\"w3-panel advInfoElement\">" + "For more info: " + emailCompany + "</p>"+
        "</section>";

    document.getElementById("advInfo").innerHTML = info;

    if (isLoggedIn() && emailCompany==getUserEmail()) {
        fetchBookingList();
    }
}

function fetchRate() {
    let url = new URL(contextPath+"/adv/" + getIdAdvertisement()+"/rate");
    sendJsonRequest(url,"GET","",loadRate);
}

function loadRate(req) {
    // Parses the JSON obj
    let jsonData = JSON.parse(req).rate;
    let rate = jsonData['rate'];

    // Presents the JSON obj
    let info = "<section class=\"w3-panel w3-card advInfoElement\">" +"<p>" + "Users rated this: " + "</p>" + "<image src=\"/ywti_wa2021_war/css/image/" + rate + "s.jpg\" >" + "</section>" + document.getElementById("advInfo").innerHTML;
    document.getElementById("advInfo").innerHTML = info;
}

function fetchFeedbackList() {
    let url = new URL(contextPath+"/adv/" + getIdAdvertisement()+"/feedback");
    sendJsonRequest(url,"GET","",loadFeedbackList);
}

function loadFeedbackList(req) {
    // Parses the JSON resourceList
    let feedbackList = JSON.parse(req).resourceList;
    let str;
    if (feedbackList.length>0) {
        str = "<h3 class=\"titled\">" + "Reviews" + "</h3>";
        feedbackList.forEach(function(resource) {
            let feedback = resource.feedback;
            let emailTourist = feedback.emailTourist;
            let idAdvertisement = feedback.idAdvertisement;
            let rate = feedback.rate;
            let text = feedback.text;
            let date = feedback.date;
            str +=
                "<div class=\"feedback w3-card\">"  +
                    "<p class=\"feedbackElement\">" + "\"" + text + "\"" + "</p>" +
                    "<p class=\"feedbackElement\">" + "Rated " + "</p>" +
                    "<image src=\"/ywti_wa2021_war/css/image/" + rate + "s.jpg\" >" +
                "</div><br>";
        });
    } else {
        str = "<p class=\"feedbackElement\">" + "No reviews found for this advertisement" + "</p>";
    }
    // Presents the JSON resourceList
    document.getElementById("feedbackList").innerHTML += str;

}

function fetchBookingList() {
    let url = new URL(contextPath+"/adv/" + getIdAdvertisement()+"/booking");
    sendJsonRequest(url,"GET","",loadBookingList);
}

function loadBookingList(req) {
    // Parses the JSON resourceList
    let bookingList = JSON.parse(req).resourceList;
    let str;
    if (bookingList.length>0) {
        str = "<h3>" + "Bookings" + "</h3>";
        bookingList.forEach(function(resource) {
            let booking = resource.booking;
            let emailTourist = booking.emailTourist;
            let idAdvertisement = booking.idAdvertisement;
            let date = booking.date;
            let time = booking.time;
            let numBooking = booking.numBooking;
            let state = booking.state;

            str +=
                "<div class=\"booking w3-container\">"  +
                    "<p class=\"w3-card bookingElement\">" + emailTourist + " booked " + numBooking + " items" + " - " + date + ", " + time + "</p>" +
                "</div><br>";
        });
    } else {
        str = "<p class=\"w3-card bookingElement\">" + "No bookings found for this advertisement" + "</p>";
    }
    // Presents the JSON resourceList
    document.getElementById("bookingList").innerHTML += str;
}

function fetchImageList() {
    let url = new URL(contextPath+"/adv/" + getIdAdvertisement()+"/image");
    sendJsonRequest(url,"GET","",loadImageList);
}

function loadImageList(req) {
    // Parses the JSON resourceList
    let imageList = JSON.parse(req).resourceList;
    let str = "";
    let str2 = "";
    if (imageList.length>0) {

        imageList.forEach(function(resource) {
            let image = resource.image;
            let idImage = image.idImage;
            let path = image.path;
            let description = image.description;
            let idAdvertisement = image.idAdvertisement;

            str += "<img class=\"mySlides w3-center\" src=\"" + "" + path + "\" width=\"320\" height=\"240\" alt=''/>";
        })
        str2 +=
            "<button class=\"w3-button\" onclick=\"plusDivs(-1)\">&#10094;</button>"
            + "<button class=\"w3-button \" onClick=\"plusDivs(+1)\">&#10095;</button>";
    }
    else{
        str +="<img class=\"mySlides w3-center\" src=\"/ywti_wa2021_war/css/image/noImage.jpg\" width=\"320\" height=\"240\" alt=''/>\n" ;
    }
    document.getElementById("advImages").innerHTML = str;
    document.getElementById("buttContainer").innerHTML = str2;

    $("img").error(function () {
        $(this).hide();
    });
}

function plusDivs(n) {
    showDivs(slideIndex += n);
}

function showDivs(n) {
    let i;
    const x = document.getElementsByClassName("mySlides");
    if (n > x.length) {slideIndex = 1}
    if (n < 1) {slideIndex = x.length;}
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    x[slideIndex-1].style.display = "block";
}