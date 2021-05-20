
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

            str +=
                "<article class=\"advertisement w3-section w3-container w3-panel w3-card-4\">" +
                    "<span class=\"advSmallImage\">" +
                        "<img id=\"img"+ idAdv +"\" class=\"small-adv-img\" src=\"/ywti_wa2021_war/utility/img-default.jpg\" alt=''/>"+
                    "</span>" +
                    "<span class=\"gotoShowAdvertisement\" >" +
                        "<form name=gotoShowAdvertisementForm class=\"gotoShowAdvertisementForm\" method=GET />" +
                        "<button name=showAdvertisementButton class=\"button showAdvertisementButton\" value=" + idAdv + ">Info</button>" +
                        "</form>" +
                    "</span>" +
                    "<span class=\"advSummary\">" +
                        "<h3>" + title + "</h3>" +
                        "<p> Rated <span id=\"rate" + idAdv + "\"></span>/5 - " + price + " euro" + "</p>" +
                        "<p>" + description + "</p>" +
                        "<p>" + "Starting " + dateStart + "</p>" +
                        "<p>" + "Ending " + dateEnd + "</p>" +
                    "</span>" +
                "</article> \n";

        });

        // Display textual ads
        advertisementList.innerHTML = str;

        //For each adv, load the rate
        advList.forEach(function(resource) {
            let adv = resource.advertisement;
            let idAdv = adv.idAdvertisement;
            // Fetch the rate
            sendJsonRequest(
                contextPath+"/adv/" + idAdv +"/rate",
                "GET",
                "",
                function(req) {
                    // Parses the JSON obj
                    let jsonData = JSON.parse(req).rate;
                    let rate = jsonData['rate'];
                    // Insert the rate inside the HTML
                    document.getElementById("rate" + idAdv).innerText = rate;
                });
        });

        // fix broken images
        $(".small-adv-img").on("error", function () {
            $(this).attr("src", "/ywti_wa2021_war/utility/img-default.jpg");
        });

        //For each adv, load an image
        advList.forEach(function(resource, index) {
            let adv = resource.advertisement;
            let idAdv = adv.idAdvertisement;
            // Fetch the rate
            sendJsonRequest(
                contextPath+"/adv/" + idAdv +"/image",
                "GET",
                "",
                function(req) {
                    // Parses the JSON obj
                    let imageList = JSON.parse(req).resourceList;
                    if (imageList.length>0) {
                        // Insert the last image inside the HTML
                        let image = imageList[imageList.length-1].image;
                        let path = image.path;
                        document.getElementById("img" + idAdv).src = path;
                    }
                });
        });


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