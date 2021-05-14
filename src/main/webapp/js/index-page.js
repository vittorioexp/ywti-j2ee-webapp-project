// TODO: fix index-page.js
// TODO Vedere tutor repo: js/search-maintenance-page.js

document.addEventListener("DOMContentLoaded", function(event) {

    document.getElementById("advertisementList").style.display = "none";

    // Fetches the list of typeAdvertisements and Cities
    //fetchTypeAdvList();
    //fetchCityList();

    // fetch the list of advertisements when clicking the search button
    document.getElementById("search-button").addEventListener("click", function(event) {
        event.preventDefault();
        fetchAdvertisementList();
    });
    //document.addEventListener("submit", fetchAdvertisementList);
});

// TODO: Fetches the list of available typeAdv
function fetchTypeAdvList() {
    let url = new URL(contextPath+"/.....");
    // put code here
}

// TODO: Loads the list of available type adv
function loadTypeAdvList(req){
    // put code here
}

// TODO: Fetches the list of available cities
function fetchCityList(){
    let url = new URL(contextPath+"/.....");
    // put code here
}

// TODO: Loads the list of available cities
function loadCityList(req){
    // TODO put code here
}

// Fetches the list of advertisements
function fetchAdvertisementList(){

    let url = new URL(contextPath+"/adv");

    let idType = document.getElementById("idType").value;
    let idCity = document.getElementById("idCity").value;
    let dateStart = document.getElementById("dateStart").value.toString();

    let data = {
        "idType": idType,
        "idCity": idCity,
        "dateStart": dateStart
    }

    $.getJSON(url, data, function (res) {
        loadAdvertisementList(res);
    });
}

// TODO: Loads the list of advertisements
function loadAdvertisementList(res){

    // make the section visible in index.html
    let advertisementList = document.getElementById("advertisementList");
    advertisementList.style.display = "block";
    
    // show the list of adv
    let advList = res.resourceList;
    let str;
    if (advList.length>0) {
        str = "<h3>" + "Advertisements" + "</h3>";
        advList.forEach(function(resource) {
            let adv = resource.advertisement;
            let title = adv.title;
            let price = adv.price;
            let dateStart = adv.dateStart;
            let dateEnd = adv.dateEnd;

            str +=
                "<article class=advertisement>" +
                    "<p>" + title + " - " + price + "euro" + "</p>" +
                    "<p>" + "starting " + dateStart + " - ending " + dateEnd + "</p>" +
                "</article> </br> \n";
            // TODO: insert INFO button for each adv (or each adv can be a link to its adv-show page)
        });
    } else {
        str = "<p>" + "No advertisement found" + "</p>";
    }
    advertisementList.innerHTML = str;

}