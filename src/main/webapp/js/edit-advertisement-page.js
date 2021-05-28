/*
Author: Marco Basso

JS that manages the modification of the advertisement. Checks are made on the input fields.

 */

document.addEventListener("DOMContentLoaded", function(event) {

    let currentUrl = window.location.href;

    document.getElementById("edit-button").addEventListener(
        "click",
        function(event) {
            event.preventDefault();
            fetchEditAdvertisement(currentUrl);
    });

});

function fetchEditAdvertisement(currentUrl){

    let idAdvertisement =  currentUrl.substring(
        currentUrl.lastIndexOf("adv-edit/")+9
    );

    // Retrieve the values of the input fields
    let title = document.getElementById("title").value;
    let description = document.getElementById("description").value;
    let dateStart = document.getElementById("dateStart").value.toString();
    let dateEnd = document.getElementById("dateEnd").value.toString();
    let timeStart = document.getElementById("timeStart").value.toString();
    let timeEnd = document.getElementById("timeEnd").value.toString();
    let price = document.getElementById("price").value.toString();
    let numTotItem = document.getElementById("numTotItem").value.toString();

    if (price.length===0) { price = "0";}
    if (numTotItem.length===0) { numTotItem = "0";}

    let messageError="";
    let errorOccurred=false;

    // Validate the various input fields
    if(!validateStringOnSubmit(title)){
        messageError+="Title invalid"+"\n";
        document.getElementById("title").value = document.getElementById("title").defaultValue;
        errorOccurred=true;
    }
    if(!validateStringOnSubmit(description)){
        messageError+="Description invalid"+"\n";
        document.getElementById("description").value = document.getElementById("description").defaultValue;
        errorOccurred=true;
    }
    if(!validateIntegerOnSubmit(price)){
        messageError+="Price invalid"+"\n";
        document.getElementById("price").value = document.getElementById("price").defaultValue;
        errorOccurred=true;
    }
    if(!validateIntegerOnSubmit(numTotItem)){
        messageError+="Total number of item invalid"+"\n";
        document.getElementById("numTotItem").value = document.getElementById("numTotItem").defaultValue;
        errorOccurred=true;
    }

    if(errorOccurred){
        // Display an error message
        alert("Error found:"+"\n"+messageError);
        return;
    }else{
        // If no error occured
        let emailCompany = getUserEmail();

        let url = new URL(contextPath+"/adv/" + idAdvertisement);

        // Sanitize inputs
        title = sanitizeString(title);
        description = sanitizeString(description);

        let data =
            "{\"advertisement\": {\"idAdvertisement\":\"" + idAdvertisement + "\",\"title\":\"" + title
            + "\",\"description\":\"" + description + "\",\"score\":\"" + "0" + "\",\"price\":\"" + price +
            "\",\"numTotItem\":\"" + numTotItem + "\"," +
            "\"dateStart\":\"" + dateStart + "\"," +
            "\"dateEnd\":\"" + dateEnd
            + "\",\"timeStart\":\"" + timeStart + "\",\"timeEnd\":\"" + timeEnd + "\",\"emailCompany\":\"" + emailCompany + "\",\"idType\":\"0\"}}";

        $.ajax({
            type: "PUT",
            url: url,
            data: data,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(data){
                alert(data.message.message);
                window.location.replace(contextPath + "/adv-show/" + idAdvertisement);
            },
            error: function(res) {
                let resMessage = res.responseJSON.message;
                alert(resMessage.message + " " + resMessage.errorDetails);
            }
        });

        // Send JSON object to the url
        /*
        sendJsonRequest(url, "PUT", data, function(req){
            window.location.replace(contextPath + "/adv-show/" + idAdvertisement);
        });*/
    }


}


