

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


    let title = document.getElementById("title").value;
    let description = document.getElementById("description").value;
    let dateStart = document.getElementById("dateStart").value.toString();
    let dateEnd = document.getElementById("dateEnd").value.toString();
    let timeStart = document.getElementById("timeStart").value.toString();
    let timeEnd = document.getElementById("timeEnd").value.toString();
    let price = document.getElementById("price").value.toString();
    let numTotItem = document.getElementById("numTotItem").value.toString();

    let messageError = "";
    let errorOccured;
    if(!validateTitleOnSubmit(title)){
        messageError+="Title invalid"+"\n";
        errorOccured=true;
    }
    else if(!validateDescriptionOnSubmit(description)){
        messageError+="Description invalid"+"\n";
        errorOccured=true;
    }
    else if(!validatePriceOnSubmit(price)){
        messageError+="Price invalid"+"\n";
        errorOccured=true;
    }
    else if(!validateNumTotItemOnSubmit(numTotItem)){
        messageError+="Total number of item invalid"+"\n";
        errorOccured=true;
    }else{
        errorOccured = false;
    }
    if(errorOccured){
        alert("Error found:"+"\n"+messageError);
        document.getElementById("editAdvertisementForm").reset();
        return;
    }else{
        let emailCompany = getUserEmail();

        let url = new URL(contextPath+"/adv/" + idAdvertisement);

        let data =
            "{\"advertisement\": {\"idAdvertisement\":\"" + idAdvertisement + "\",\"title\":\"" + title
            + "\",\"description\":\"" + description + "\",\"score\":\"" + "0" + "\",\"price\":\"" + price +
            "\",\"numTotItem\":\"" + numTotItem + "\",\"dateStart\":\"" + dateStart + "\",\"dateEnd\":\"" + dateEnd
            + "\",\"timeStart\":\"" + timeStart + "\",\"timeEnd\":\"" + timeEnd + "\",\"emailCompany\":\"" + emailCompany + "\",\"idType\":\"0\"}}";

        sendJsonRequest(url, "PUT", data, function(req){
            window.location.replace(contextPath + "/adv-show/" + idAdvertisement);
        });
    }


}


