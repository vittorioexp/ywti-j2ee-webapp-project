/*
Author: Francesco Giurisato

Js to manage the upload of imgs of an advertisement
 */

// Sets img previews while uploading them
function readURL(input) {
    let i=0;
    for (; i < input.files.length; i++) {
        if (input.files && input.files[i]) {
            let reader = new FileReader();

            reader.onload = function (e) {
                document.getElementById("previews").innerHTML += "<img class=\"preview\" src=\"" + e.target.result + "\"/>";
            }
            reader.readAsDataURL(input.files[i]);
        }
    }
}
