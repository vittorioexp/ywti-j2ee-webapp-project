/*
Author: Vittorio Esposito

JS to manage the index page
 */


document.addEventListener("DOMContentLoaded", function(event) {

    document.getElementById("advertisementList").style.display = "none";

    // Loads the dropdowns fro cities and type adv
    getTypeAdvList("idType", "What");
    getCityList("idCity", "Where");

    // fetch the list of advertisements when clicking the search button
    document.getElementById("search-button").addEventListener("click", function(event) {
        event.preventDefault();
        fetchAdvertisementList();
    });

    // Load slideshow
    loadSlideshow();

    // Insert the current date inside the Date input field
    loadDatePlaceholder();
});

function loadDatePlaceholder(){
    let todayDate = new Date;
    let year = todayDate.getFullYear().toString();
    let month = (todayDate.getMonth()+1).toString();
    let day = todayDate.getDate().toString();
    if (month.length===1) {
        month = "0" + month;
    }
    if (day.length===1) {
        day = "0" + day;
    }
    let strDate = year+"-"+month+"-"+day;
    document.getElementById("dateStart").setAttribute("value",strDate);
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
            str = "<h2 class=\"h2 titleAdvList w3-center\"> Only 1 advertisement found" + "</h2>";
        } else {
            str = "<h2 class=\"h2 titleAdvList w3-center\">" + advList.length + " advertisements found" + "</h2>";
        }

        advList.forEach(function(resource) {
            let adv = resource.advertisement;
            let idAdv = adv.idAdvertisement;
            let title = adv.title;
            let description = adv.description;
            // If the description is too long, cut it
            if (description.length>300) {
                description = description.substring(
                    0,
                    300
                ) + "...";
            }
            let price = adv.price;
            let dateStart = adv.dateStart;
            let dateEnd = adv.dateEnd;

            str +=
                "<a href=\"" + contextPath + "/adv-show/" + idAdv + "\">" +  "" +
                "<article class=\"advertisement w3-section w3-container w3-panel w3-card-4\">" +
                "<span class=\"advSmallImage\">" +
                "<img id=\"img"+ idAdv +"\" class=\"small-adv-img\" src=\"/ywti_wa2021_war/utility/img-default.jpg\" alt=''/>"+
                "</span>" +
                "<span class=\"advSummary\">" +
                "<h3 class\"h3\">" + title + "</h3>" +
                "<p> Rated <span id=\"rate" + idAdv + "\">"+
                "</span>/5 - " + price + " euro" + "</p>" +
                "<p>" + description + "</p>" +
                "<p>" + "Starting " + dateStart + "</p>" +
                "<p>" + "Ending " + dateEnd + "</p>" +
                "</span>" +
                "</article>" +
                "</a> \n";

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
        str = "<p class=\"w3-center\">" + "Sorry, no ads were found for this category" + "</p>";
        advertisementList.innerHTML = str;
    }

    // smooth scroll to element
    let element = document.querySelector("#advertisementList");
    element.scrollIntoView({ behavior: 'smooth', block: 'start'});

}

// Loads the slideshow
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