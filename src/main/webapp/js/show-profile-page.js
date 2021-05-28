
document.addEventListener("DOMContentLoaded", function(event) {

    // Replaces the idCity integer with the name of the city everywhere on the page
    fetchUserCity();

    // edit user event listener
    document.getElementsByName("editUserProfile")[0].addEventListener(
        "click",
        function (event) {
            event.preventDefault();
            window.location.href = contextPath+"/user/do-edit"
        });

    //enabling company features on profile
    if (getUserRole()==="company") {

        let deleteAdvButtons = document.getElementsByName("deleteAdvertisementButton");
        let editAdvButtons = document.getElementsByName("editAdvertisementButton");
        let showAdvButtons = document.getElementsByName("showAdvertisementButton");

        // delete adv event listener
        deleteAdvButtons.forEach(function(button, index) {
            // Adds an event listener (on click) on each delete adv button
            button.addEventListener(
                "click",
                function(event) {
                    event.preventDefault();
                    if(confirm("Are you sure you want to cancel the advertisement?")){
                        // Send an http delete in JSON to the server
                        let idAdvertisement = button.value;
                        let url = contextPath + "/adv/" + idAdvertisement;
                        $.ajax({
                            url: url,
                            method: 'DELETE',
                            success: function(res) {
                                alert(res.message.message + " Reload the page to see your information updated.");
                            },
                            error: function(res) {
                                let resMessage = res.responseJSON.message;
                                alert(resMessage.message + " " + resMessage.errorDetails);
                            }
                        });

                    }
                });
        });

        // edit adv event listener
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

        // show adv event listener
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
        // create adv event listener
        document.getElementById("createAdvertisementButton").addEventListener(
            "click",
            function (event) {
                event.preventDefault();
                window.location.href = contextPath+"/adv-do-create"
            });
    }
    // enabling only the tourist functionalities
    else if (getUserRole()==="tourist") {

        let deleteBookingButtons = document.getElementsByName("deleteBookingButton");
        let showBookingButton = document.getElementsByName("showBookingButton");

        // show adv event listener
        showBookingButton.forEach(function(button, index) {

            // Adds an event listener (on click) on each delete booking button
            button.addEventListener(
                "click",
                function(event) {
                    event.preventDefault();
                    // Redirect to the show advertisement html
                    let idAdvertisement = button.value;
                    window.location.href = contextPath + "/adv-show/" + idAdvertisement;
                });
        });

        // delete booking event listener
        deleteBookingButtons.forEach(function(button, index) {

            // Adds an event listener (on click) on each delete booking button
            button.addEventListener(
                "click",
                function(event) {
                    event.preventDefault();
                    if(confirm("Are you sure you want to cancel the reservation?")){
                        // Send an http delete to the server
                        let idAdvertisement = button.value;
                        let url = contextPath + "/booking-delete";
                        let data = {
                            "idAdvertisement": idAdvertisement
                        }

                        $.ajax({
                            url: url,
                            data: data,
                            method: 'GET',
                            success: function(res) {
                                alert(res.message.message + " Reload the page to see your information updated.");
                            },
                            error: function(res) {
                                let resMessage = res.responseJSON.message;
                                alert(resMessage.message + " " + resMessage.errorDetails);
                            }
                        });

                    }else{}
                });
        });
    } else {
        //
        window.location.href = contextPath + "/user/do-login"
    }
});

// Replaces the idCity integer with the name of the city everywhere on the page
function fetchUserCity() {
    let url = contextPath + "/city";
    let parList = document.getElementsByName("userCity");
    let data = {"key": parList[0].id};
    $.getJSON(url, data, function (res) {
        let cityName = res.city.idCityName;
        parList.forEach(function(par) {
            par.innerText =  cityName;
        });
    });
}
