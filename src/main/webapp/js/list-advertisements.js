// TODO: fix list-advertisements.js
// TODO Vedere tutor repo: js/search-maintenance-page.js

// self executing function here
document.addEventListener('DOMContentLoaded', function(event) {
    // your page initialization code here
    // the DOM will be available here

    getTypeAdvertisements();
    getCities();

    // calls searchAdvertisements() when clicking the search button
    document.getElementById("search-button").addEventListener("click", searchAdvertisements);
});

// TODO: Request the list of available typeAdvertisement
function getTypeAdvertisements() {
}

// TODO: Gets and shows the list of available typeAdvertisement
function showTypeAdvertisements(req){
}

// TODO: Request the list of available cities
function getCities(){
}

// TODO: Gets and shows the list of available cities
function showCities(req){
}

// TODO: Converts the form in JSON format and sends a GET request to /adv
function searchAdvertisements(){
    var url = new URL(contextPath+'/adv');
    //url.searchParams.set("idType", document.getElementById("idType").value);
    //url.searchParams.set("idCity", document.getElementById("idCity").value);
    //url.searchParams.set("dateStart", document.getElementById("dateStart").value);
    genericGETRequest(url, listAdvertisements);
}

// TODO: Gets and shows the list of advertisements
function listAdvertisements(req){
}