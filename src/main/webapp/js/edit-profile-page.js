

document.addEventListener("DOMContentLoaded", function(event) {

    fetchProfile();
    document.getElementById("button").addEventListener("click", updateProfile);
    // document.getElementById("password").addEventListener("change", checkValue);
    // document.getElementById("address").addEventListener("change", checkValue);
    // document.getElementById("idCity").addEventListener("change", checkValue);
    // document.getElementById("phonenumber").addEventListener("change", checkValue);

});







function checkValue(e) {
    if (e.target.value ===""){
        alert ( e.target.name + " field is empty!");
    }
    else{
        if (e.target.id === "password") {}
            // if (e.target.value === MD5(document.getElementById( e.target.id + "Old").value) )
            //     alert ( e.target.name + " field has not been modified!");
        if (e.target.value === document.getElementById( e.target.id + "Old").value)
            alert ( e.target.name + " field has not been modified!");
    }
}

function fetchProfile() {
    let url = new URL(contextPath+"/user/profile");
    sendGenericGetRequest(url ,loadPreviousProfile);
}

function loadPreviousProfile(req){

    // let userObj = req.getAttribute("user");
    // document.getElementById("passwordOld").value = userObj.password;
    // document.getElementById("idCityOld").value = userObj.idCity;
    // document.getElementById("addressOld").value = userObj.address;
    // document.getElementById("phonenumberOld").value = userObj.phonenumber;

}

function updateProfile() {

    alert ("clicking");
    let url = new URL(contextPath + "/user/edit");
    let m = "post";

    // The rest of this code assumes you are not using a library.
    // It can be made less verbose if you use one.
    const form = document.createElement('form');
    form.method = m;
    form.action = url;

    let p = document.getElementById("password");
    let a = document.getElementById("address");
    let pn = document.getElementById("phonenumber");
    let id = document.getElementById("idCity");

    let f = [ p , a , pn , id];

    for ( let i=0; i<4; i++) {

        const hiddenField = document.createElement('input');
        hiddenField.type = 'hidden';
        hiddenField.name = f[i].name;
        hiddenField.value = f[i].value;
        form.appendChild(hiddenField);

    }
    document.body.appendChild(form);
    form.submit();


}

