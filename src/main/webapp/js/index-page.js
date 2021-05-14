
document.addEventListener("DOMContentLoaded", function(event) {

    document.getElementById("advertisementList").style.display = "none";

    // Fetches the list of typeAdvertisements and Cities
    $.getScript(contextPath + "/js/utils.js",function(){
        getTypeAdvList("idType");
        getCityList("idCity");
    });

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

// Loads the list of advertisements
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
            let idAdv = adv.idAdvertisement;
            let title = adv.title;
            let price = adv.price;
            let dateStart = adv.dateStart;
            let dateEnd = adv.dateEnd;

            str +=
                "<article class=\"advertisement w3-container w3-section w3-panel w3-card-4\">" +
                    "<p>" + title + " - " + price + "euro" + "</p>" +
                    "<p>" + "starting " + dateStart + " - ending " + dateEnd + "</p>" +
                    "<span>" +
                        "<form name=gotoShowAdvertisementForm method=GET />" +
                            "<button name=showAdvertisementButton class=\"w3-section showAdvertisementButton\" value=" + idAdv + ">Info</button>" +
                        "</form>" +
                    "</span>" +
                "</article> \n";
        });
    } else {
        str = "<p>" + "No advertisement found" + "</p>";
    }
    advertisementList.innerHTML = str;

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

}

function loadSlideshow() {
    let myIndex = 0;
    carousel();

    function carousel() {
        let i;
        let x = document.getElementsByClassName("mySlides");
        for (i = 0; i < x.length; i++) {
            x[i].style.display = "none";
        }
        myIndex++;
        if (myIndex > x.length) {myIndex = 1}
        x[myIndex-1].style.display = "block";
        setTimeout(carousel, 5000); // Change image every 5 seconds
    }
}