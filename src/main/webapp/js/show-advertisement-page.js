/*
Author: Vittorio Esposito
Author: Matteo Piva
Author: Marco Basso
Author: Francesco Giurisato

JS to manage the login page
 */

let slideIndex = 1;

document.addEventListener("DOMContentLoaded", function(event) {

    let idAdv = getIdAdvertisement();

    // If idAdvertisement is invalid, the user is redirect to the error page
    if (idAdv===-1) {
        window.location.replace(contextPath + "/html/error.html");
    }

    // if a company is visiting show-advertisement.html, hide "leave a feedback" form
    if (isLoggedIn() && getUserRole()==="company") {
        document.getElementById("createFeedback").style.display = "none";
        document.getElementById("createBooking").style.display = "none";
    }

    fetchAdvertisement();
    fetchRate();
    fetchFeedbackList();
    fetchImageList();

});

function validateRateFeedback(rateFeedback){
    return rateFeedback<=0 || rateFeedback>= 6;
}

function fetchCreateBooking(){

    let numBooking = document.getElementById("numBooking").value;
    if(validateNumBooking(numBooking)){
        // If the number of seats is invalid
        let errorObj = document.getElementById("errorCreateBooking");
        errorObj.innerHTML="Invalid total number of item.";
        errorObj.value="";
        document.getElementById("numBooking").value = document.getElementById("numBooking").defaultValue;
        return;
    }

    let url = contextPath+"/booking-create";
    let data = {
        "idAdvertisement": getIdAdvertisement(),
        "numBooking": numBooking
    }
    // Make the request tho the servlet
    $.ajax({
        url: url,
        data: data,
        method: 'POST',
        success: function(res) {
            if (res.message !== undefined) {
                // If the response is successfull
                window.location.href = contextPath + "/user/profile";
            } else {
                // The filter servlet wants the user to log in
                window.location.href = contextPath + "/user/do-login";
            }
        },
        error: function(res) {
            // If an error occured, display the error message
            let resMessage = res.responseJSON.message;
            // alert(resMessage.message + " " + resMessage.errorDetails);
            document.getElementById("errorBooking").innerHTML = resMessage.errorDetails;
        }
    });
}

function fetchCreateFeedback(){

    let rateFeedback = document.getElementById("rateFeedback").value;
    // If the rate is invalid
    if(validateRateFeedback(rateFeedback)){
        let errorObj = document.getElementById("errorCreateFeedback");
        errorObj.innerHTML="Invalid rate.";
        errorObj.value="";
        document.getElementById("rateFeedback").value = document.getElementById("rateFeedback").defaultValue;
        return;
    }

    let textFeedback = document.getElementById("textFeedback").value;

    let url = contextPath+"/feedback-create";
    let data = {
        "idAdvertisement": getIdAdvertisement(),
        "rateFeedback":rateFeedback,
        "textFeedback":textFeedback
    }
    // Make the request tho the servlet
    $.ajax({
        url: url,
        data: data,
        method: 'POST',
        success: function(res) {
            // If the response is successfull
            fetchFeedbackList();
            fetchRate();
        },
        error: function(res) {
            // If an error occured, display the error message
            let resMessage = res.responseJSON.message;
            // alert(resMessage.message);
            document.getElementById("errorFeedback").innerHTML = resMessage.errorDetails;
        }
    });
}

// Function to get the id of the advertisement
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

// Function to load the advertisement
function fetchAdvertisement() {
    let url = new URL(contextPath+"/adv/" + getIdAdvertisement());
    $.ajax({
        url: url,
        method: 'GET',
        success: function(res) {
            loadAdvertisement(res);
        },
        error: function(res) {
            window.location.href=contextPath+"/html/error.html";
        }
    });
}

function loadAdvertisement(req) {
    let adv = req.advertisement;
    let title = adv['title'];
    let description = adv['description'];
    let price = adv['price'];
    let score = adv['score'];
    let numTotItem = adv['numTotItem'];
    let dateStart = adv['dateStart'];
    let dateEnd = adv['dateEnd'];
    let timeStart = adv['timeStart'];
    let timeEnd = adv['timeEnd'];
    let emailCompany = adv['emailCompany'];

    // Presents the JSON obj
    document.getElementById("advTitle").innerHTML = "<br><h2 class=\"h1\">" + title + "</h2>";

    let info =
        "<p class=\"advInfoElement\">" + description + "</p>" +
        "<p class=\"advInfoElement\">" + "Only " + price + " euro!" + "</p>" +
        "<p class=\"advInfoElement\">" + "There are just " + numTotItem + " items available!" + "</p>" +
        "<p class=\"advInfoElement\">" + "The event is starting the day " + dateStart + " at " + timeStart + " until " + dateEnd + " at " + timeEnd + "</p><br>" +
        "<p class=\"advInfoElement\">" + "For more info: " + emailCompany + "</p>";

    document.getElementById("infoSection").innerHTML = info;

    // If the company is logged in and it is the owner of the advertisement
    if (isLoggedIn() && emailCompany==getUserEmail()) {
        fetchBookingList();
    }else if(isLoggedIn() && getUserRole()==="tourist"){
        // If the tourist is logged in
        document.getElementById("createBookingButton").addEventListener("click", function(event){
            event.preventDefault();
            fetchCreateBooking()
        });
        document.getElementById("createFeedbackButton").addEventListener("click", function(event){
            event.preventDefault();
            fetchCreateFeedback()
        });
    }else if(!isLoggedIn()){
        // If the user is not logged in
        document.getElementById("createBookingButton").addEventListener("click", function(event){
            event.preventDefault();
            window.location.href = contextPath + "/user/do-login";
        });
        document.getElementById("createFeedbackButton").addEventListener("click", function(event){
            event.preventDefault();
            window.location.href = contextPath + "/user/do-login";
        });
    }
}

function validateNumBooking(numBooking){
    return numBooking <= 0;
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
    let info = "<img src=\"/ywti_wa2021_war/css/image/" + rate + "s.jpg\" />\n";
    document.getElementById("advRate").innerHTML = info;
}

// Function to load the feedback list
function fetchFeedbackList() {
    let url = new URL(contextPath+"/adv/" + getIdAdvertisement()+"/feedback");
    sendJsonRequest(url,"GET","",loadFeedbackList);
}

function loadFeedbackList(req) {
    // Parses the JSON resourceList
    let isEmpty = true;
    let feedbackList = JSON.parse(req).resourceList;
    let str = "<p class=\"feedbackElement\">" + "No reviews found for this advertisement" + "</p>\n";
    if (feedbackList.length>0) {
        feedbackList.forEach(function(resource) {
            let feedback = resource.feedback;
            let emailTourist = feedback.emailTourist;
            let idAdvertisement = feedback.idAdvertisement;
            let rate = feedback.rate;
            let text = feedback.text;
            let date = feedback.date;

            if (text!=="" && text!==" ") {
                if (isEmpty===true) {
                    str = "<h3 class=\"sectionTitle\">" + "Reviews" + "</h3>";
                    isEmpty=false;
                }
                str +=
                    "<div class=\"feedbackElement\">"  +
                        "<p class=\"feedbackElement\">" + "\"" + text + "\"" + "</p>" +
                        "<p class=\"feedbackElement\">" + "" + "</p>" +
                        "<img src=\"/ywti_wa2021_war/css/image/" + rate + "s.jpg\"  alt=\"\" />" +
                    "</div>\n";
            }


        });
    }
    // Presents the JSON resourceList
    document.getElementById("feedbackList").innerHTML = str;

}

// Function to load the booking list
function fetchBookingList() {
    let url = new URL(contextPath+"/adv/" + getIdAdvertisement()+"/booking");
    sendJsonRequest(url,"GET","",loadBookingList);
}

function loadBookingList(req) {
    // Parses the JSON resourceList
    let bookingList = JSON.parse(req).resourceList;
    let str;
    if (bookingList.length>0) {
        str = "<h3 class=\"sectionTitle\">" + "Bookings" + "</h3>";
        bookingList.forEach(function(resource) {
            let booking = resource.booking;
            let emailTourist = booking.emailTourist;
            let idAdvertisement = booking.idAdvertisement;
            let date = booking.date;
            let time = booking.time;
            let numBooking = booking.numBooking;
            let state = booking.state;

            str +=
                "<div class=\"booking\">"  +
                    "<p class=\"bookingElement\">" + emailTourist + " booked " + numBooking + " items" + " - " + date + ", " + time + "</p>" +
                "</div><br>";
        });
    } else {
        str = "<p class=\"bookingElement\">" + "No bookings found for this advertisement" + "</p>";
    }
    // Presents the JSON resourceList
    document.getElementById("bookingList").innerHTML += str;
}

// Function to load the image list
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

            str += "<img class=\"mySlides\" style=\"display: none;\" src=\"" + "" + path + "\" alt='' />";
        })
        str2 +=
            "<button class=\"w3-button slidesButton\" onclick=\"plusDivs(-1)\">&#10094;</button>"
            + "<button class=\"w3-button slidesButton\" onClick=\"plusDivs(+1)\">&#10095;</button>";
    } else {
        str = "<img class=\"mySlides\" src=\"/ywti_wa2021_war/css/image/noImage.jpg\" alt=''/>\n" ;
        str2 = "";
    }

    document.getElementById("advImages").innerHTML = str;
    document.getElementById("buttContainer").innerHTML = str2;


    plusDivs(+1);

    // Removes broken images
    $("img").on("error", function () {
        $(this).remove();

        // If all img were removed: show default img and then remove buttons
        slides = document.getElementsByClassName("mySlides");
        if (slides.length===0) {
            str ="<img class=\"mySlides\" src=\"/ywti_wa2021_war/css/image/noImage.jpg\" alt='' />\n" ;
            str2 = "";
            document.getElementById("advImages").innerHTML = str;
            document.getElementById("buttContainer").innerHTML = str2;
            slides[0].style.display= "inline";
        }
        plusDivs(+1);
    });



}

function plusDivs(n) {
    showDivs(slideIndex += n);
}

function showDivs(n) {
    let i;
    const x = document.getElementsByClassName("mySlides");
    if (x.length===0) {return;}
    if (n > x.length) {slideIndex = 1}
    if (n < 1) {slideIndex = x.length;}
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    x[slideIndex-1].style.display = "inline";
}