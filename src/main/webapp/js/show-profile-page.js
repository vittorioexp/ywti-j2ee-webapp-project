
document.addEventListener("DOMContentLoaded", function(event) {

    fetchPage();

    document.getElementsByName("deleteAdvertisementButton").addEventListener("click", fetchDeleteAdvertisement());

});



function fetchPage(){
    let navbarUrl = new URL(contextPath + "/html/reusable-snippets/navbar.html");
    let footerUrl = new URL(contextPath + "/html/reusable-snippets/footer.html");
    sendGenericGetRequest(navbarUrl, loadNavbar);
    sendGenericGetRequest(footerUrl, loadFooter);
}

function fetchDeleteAdvertisement(){
    let url = contextPath + "/adv/${adv.idAdvertisement}"
    sendJsonRequest(url, "DELETE", "", function(req){});
}





