

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

    if (price.length===0) { price = "0";}
    if (numTotItem.length===0) { numTotItem = "0";}

    let messageError="";
    let errorOccurred=false;

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
        alert("Error found:"+"\n"+messageError);
        document.getElementById("editAdvertisementForm").reset();
        return;
    }else{
        let emailCompany = getUserEmail();

        let url = new URL(contextPath+"/adv/" + idAdvertisement);

        let data =
            "{\"advertisement\": {\"idAdvertisement\":\"" + idAdvertisement + "\",\"title\":\"" + title
            + "\",\"description\":\"" + description + "\",\"score\":\"" + "0" + "\",\"price\":\"" + price +
            "\",\"numTotItem\":\"" + numTotItem + "\"," +
            "\"dateStart\":\"" + dateStart + "\"," +
            "\"dateEnd\":\"" + dateEnd
            + "\",\"timeStart\":\"" + timeStart + "\",\"timeEnd\":\"" + timeEnd + "\",\"emailCompany\":\"" + emailCompany + "\",\"idType\":\"0\"}}";

        alert(data);

        sendJsonRequest(url, "PUT", data, function(req){
            window.location.replace(contextPath + "/adv-show/" + idAdvertisement);
        });
    }


}


