
document.addEventListener("DOMContentLoaded", function(event) {

    document.getElementById("advertisementList").style.display = "none";

    getTypeAdvList("idType", "What");
    getCityList("idCity", "Where");

    // fetch the list of advertisements when clicking the search button
    document.getElementById("search-button").addEventListener("click", function(event) {
        event.preventDefault();
        fetchAdvertisementList();
    });

    loadSlideshow();

});

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
    /*
    //URL encoded
    var xhr = new XMLHttpRequest();
    var url = "url?data=" + encodeURIComponent(data);
    xhr.open("GET", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert(JSON.parse(xhr.responseText););
            loadAdvertisementList(xhr)
        }
    };
    xhr.send();
     */

    $.getJSON(url, data, function (res) {
        loadAdvertisementList(res);
    });
}

// TODO: fix the following (style, img and info to display)
// Loads the list of advertisements
function loadAdvertisementList(res){

    // make the section visible in index.html
    let advertisementList = document.getElementById("advertisementList");
    advertisementList.style.display = "block";
    
    // show the list of adv
    let advList = res.resourceList;
    let str;
    if (advList.length>0) {
        if (advList.length===1) {
            str = "<h2 class=\"titleAdvList w3-center\"> Only 1 advertisement found" + "</h2>";
        } else {
            str = "<h2 class=\"titleAdvList w3-center\">" + advList.length + " advertisements found" + "</h2>";
        }

        advList.forEach(function(resource) {
            let adv = resource.advertisement;
            let idAdv = adv.idAdvertisement;
            let title = adv.title;
            let description = adv.description;
            let price = adv.price;
            let dateStart = adv.dateStart;
            let dateEnd = adv.dateEnd;

            let path = "";

            str +=
                "<article class=\"advertisement w3-section w3-container w3-panel w3-card-4\">" +
                    "<span class=\"advSmallImage\">" +
                        "<img class=\"\" src=\"https://cf.bstatic.com/xdata/images/hotel/square600/226809345.webp?k=f78d142a274e06bf0a6a7356c7d6d98e41eeeb8a9e9e616c014ff2154d220d61&o=\" alt=''/>"+
                    "</span>" +
                    "<span class=\"gotoShowAdvertisement\" >" +
                        "<form name=gotoShowAdvertisementForm class=\"gotoShowAdvertisementForm\" method=GET />" +
                        "<button name=showAdvertisementButton class=\"button showAdvertisementButton\" value=" + idAdv + ">Info</button>" +
                        "</form>" +
                    "</span>" +
                    "<span class=\"advSummary\">" +
                        "<h3>" + title + "</h3>" +
                        "<p>" + "Rated 5/5 - " + price + " euro" + "</p>" +
                        "<p>" + description + "</p>" +
                        "<p>" + "Starting " + dateStart + "</p>" +
                        "<p>" + "Ending " + dateEnd + "</p>" +
                    "</span>" +
                "</article> \n";

        });

        advertisementList.innerHTML = str;

        // TODO For each adv, load the rate

        // TODO For each adv, load an image


    } else {
        str = "<p>" + "No advertisement found" + "</p>";
        advertisementList.innerHTML = str;
    }


    let showAdvButtons = document.getElementsByName("showAdvertisementButton");

    showAdvButtons.forEach(function(button, index) {

        // Adds an event listener (on click) on each show adv button
        button.addEventListener(
            "click",
            function(event) {
                event.preventDefault();

                // Redirect to the show advertisement html
                window.location.href = contextPath + "/adv-show/" + button.value;
            });
    });

    // smooth scroll to element
    let element = document.querySelector("#advertisementList");
    element.scrollIntoView({ behavior: 'smooth', block: 'start'});

}

function loadSlideshow() {
    let myIndex = 0;
    carousel();

    function carousel() {
        let i;
        let x = document.getElementsByClassName("mySlides");
        if (x.length===0) {
            return;
        }
        for (i = 0; i < x.length; i++) {
            x[i].style.display = "none";
        }
        myIndex++;
        if (myIndex > x.length) {myIndex = 1}
        x[myIndex-1].style.display = "block";
        setTimeout(carousel, 5000); // Change image every 5 seconds
    }
}