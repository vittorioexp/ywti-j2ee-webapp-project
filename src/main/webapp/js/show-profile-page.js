
document.addEventListener("DOMContentLoaded", function(event) {

    let deleteAdvButtons = document.getElementsByName("deleteAdvertisementButton");
    let editAdvButtons = document.getElementsByName("editAdvertisementButton");
    let showAdvButtons = document.getElementsByName("showAdvertisementButton");


    deleteAdvButtons.forEach(function(button, index) {

        // Adds an event listener (on click) on each delete adv button
        button.addEventListener(
            "click",
            function(event) {
                event.preventDefault();

                // Send an http delete in JSON to the server
                let idAdvertisement = button.value;
                let url = contextPath + "/adv/" + idAdvertisement;
                sendJsonRequest(url, "DELETE", "", function(req){});
            });
    });

    editAdvButtons.forEach(function(button, index) {

        // Adds an event listener (on click) on each edit adv button
        button.addEventListener(
            "click",
            function(event) {
                event.preventDefault();

                // Redirect to the edit advertisement html
                let idAdvertisement = button.value;
                window.location.href = contextPath + "/adv-edit/" + idAdvertisement;
            });
    });

    showAdvButtons.forEach(function(button, index) {

        // Adds an event listener (on click) on each show adv button
        button.addEventListener(
            "click",
            function(event) {
                event.preventDefault();

                // Redirect to the show advertisement html
                let idAdvertisement = button.value;
                window.location.href = contextPath + "/adv-show/" + idAdvertisement;
            });
    });



});

