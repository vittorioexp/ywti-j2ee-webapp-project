
document.addEventListener("DOMContentLoaded", function(event) {

    document.getElementById("idAdvFeedback").setAttribute("value", getIdAdvertisement());
    document.getElementById("idAdvBooking").setAttribute("value", getIdAdvertisement());
    fetchAdvertisement();
    fetchRate();
    fetchFeedbackList();
    fetchBookingList();
    fetchImageList();
});

function getIdAdvertisement() {
    let url = window.location.href;
    url = url.substring(
        url.lastIndexOf("adv-show/") + 9
    );
    // TODO Checks if the ID makes sense
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
    document.getElementById("advTitle").innerHTML = title;

    let info = "<p>" + description + "</p><p>" + "Only " + price + " euro!" + "</p><p>" + "There are just " + numTotItem + " items available!"
                + "</p><p>" + "The event is starting the day " + dateStart + " at " + timeStart + " until "
                + dateEnd + " at " + timeEnd + "</p><br><p>" + "For more info: " + emailCompany + "</p>";

    document.getElementById("advInfo").innerHTML = info;

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
    let info = "<p>" + "Rated " + rate + "/5" + "</p>" + document.getElementById("advInfo").innerHTML;
    document.getElementById("advInfo").innerHTML = info;
}

function fetchFeedbackList() {
    let url = new URL(contextPath+"/adv/" + getIdAdvertisement()+"/feedback");
    sendJsonRequest(url,"GET","",loadFeedbackList);
}

function loadFeedbackList(req) {
    // TODO write function body
}

function fetchBookingList() {
    let url = new URL(contextPath+"/adv/" + getIdAdvertisement()+"/booking");
    sendJsonRequest(url,"GET","",loadBookingList);
}

function loadBookingList(req) {
    // TODO write function body
}

function fetchImageList() {
    let url = new URL(contextPath+"/adv/" + getIdAdvertisement()+"/image");
    sendJsonRequest(url,"GET","",loadImageList);
}

function loadImageList(req) {
    // TODO write function body
}