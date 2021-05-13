
document.addEventListener("DOMContentLoaded", function(event) {

    let deleteAdvButtons = document.getElementsByName("deleteAdvertisementButton");


    deleteAdvButtons.forEach(function(button, index) {

        // Adds an event listener (on click) on each delete adv button
        button.addEventListener(
            "click",
            function() {

                // Send an http delete in JSON to the server
                let idAdvertisement = button.value;
                alert(idAdvertisement);
                let url = contextPath + "/adv/" + idAdvertisement;
                sendJsonRequest(url, "DELETE", "", function(req){});
            });
    });

});




