document.addEventListener("DOMContentLoaded", function(event) {

    //fetchTypeAdvList();

    document.getElementById("create-button").addEventListener("click", function(event) {
        event.preventDefault();
        createAdvertisement();
    });
    document.getElementsByTagName("form")[0].addEventListener("submit", validateCreation);
});

// TODO: Fetches the list of available typeAdv
function fetchTypeAdvList() {
    let url = new URL(contextPath+"/.....");
    // put code here
    sendGenericGetRequest(url, loadTypeAdvList);
}

// TODO: Loads the list of available type adv
function loadTypeAdvList(req){
    // put code here
}

function createAdvertisement() {

    let url = new URL(contextPath+"/adv-create");

    let idAdvertisement = 0;
    let title = document.getElementById("title").value;
    let description = document.getElementById("description").value;
    let price = document.getElementById("price").value.toString();
    let score = (price/3.14);
    let numTotItem = document.getElementById("numTotItem").value.toString();
    let dateStart = document.getElementById("dateStart").value.toString();
    let dateEnd = document.getElementById("dateEnd").value.toString();
    let timeStart = document.getElementById("timeStart").value.toString();
    let timeEnd = document.getElementById("timeEnd").value.toString();
    let idType = document.getElementById("idType").value.toString();
    let data = "{\"advertisement\":{\"idAvdertisement\":\"" + idAdvertisement.toString() + "\"," +
        "\"title\":\"" + title +
        "\",\"description\":\"" + description + "\"," +
        "\"price\":\"" + price + "\"," +
        "\"score\":\"" + 0 + "\"," +
        "\"numTotItem\":\"" + numTotItem + "\"," +
        "\"dateStart\":\"" + dateStart + "\"," +
        "\"dateEnd\":\"" + dateEnd + "\"," +
        "\"timeStart\":\"" + timeStart + "\"," +
        "\"timeEnd\":\"" + timeEnd + "\"," +
        "\"emailCompany\":\"" + getUserEmail() + "\"," +
        "\"idType\":\"" + idType + "\"}}";

    sendJsonRequest(url, "POST", data, function(req){
        alert("very good");
    });
}


function validateCreation()
{
    let error = document.getElementById("error");

    let title = document.getElementById("title");
    let description = document.getElementById("description");
    let price = document.getElementById("price");
    let numTotItem = document.getElementById("numTotItem");
    let idType = document.getElementById("idType");

    let test = title.value.length === 0 || description.value.length === 0 || price.value.length === 0 || numTotItem.value.length === 0 || idType.value.length === 0;

    if (test) {
        error.innerHTML = "One or more fields not filled!";
        error.className = "error active";
        event.preventDefault();
    } else {
        error.innerHTML = "";
        error.className = "error";
    }
}