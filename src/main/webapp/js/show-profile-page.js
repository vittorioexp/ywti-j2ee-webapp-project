
document.addEventListener("DOMContentLoaded", function(event) {

    fetchUserCity();

    document.getElementsByName("editUserProfile")[0].addEventListener(
        "click",
        function (event) {
            event.preventDefault();
            window.location.href = contextPath+"/user/do-edit"
        });

    if (getUserRole()==="company") {

        let deleteAdvButtons = document.getElementsByName("deleteAdvertisementButton");
        let editAdvButtons = document.getElementsByName("editAdvertisementButton");
        let showAdvButtons = document.getElementsByName("showAdvertisementButton");

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
                                event.preventDefault();
                                alert("Reload the page to see your information updated.");
                            },
                            error: function(res) {
                                let resMessage = res.responseJSON.message;
                                alert(resMessage.message + " " + resMessage.errorDetails);
                            }
                        });

                    }
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

        document.getElementById("createAdvertisementButton").addEventListener(
            "click",
            function (event) {
                event.preventDefault();
                window.location.href = contextPath+"/adv-do-create"
            });
    } else if (getUserRole()==="tourist") {

        let deleteBookingButtons = document.getElementsByName("deleteBookingButton");
        let showBookingButton = document.getElementsByName("showBookingButton");

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
                                event.preventDefault();
                                alert("Reload the page to see your information updated.");
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
