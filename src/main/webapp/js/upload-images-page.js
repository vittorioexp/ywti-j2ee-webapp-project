

// document.addEventListener("DOMContentLoaded", function(event) {
//
//     $("#image").change(function () {
//         readURL(this);
//     });
// });
//
function readURL(input) {
    let i=0;
    for (; i < input.files.length; i++) {
        if (input.files && input.files[i]) {
            let reader = new FileReader();

            reader.onload = function (e) {
                //alert(e.target.result);
                document.getElementById("previews").innerHTML += "<img class=\"preview\" src=\"" + e.target.result + "\"/>";
            }

            reader.readAsDataURL(input.files[i]);

        }
    }
}

// function img_pathUrl(input){
//     let i=0;
//     for (; i < input.files.length; i++) {
//         document.getElementById("previews").innerHTML += "<img id=\"img" + i + "\"class=\"preview\" src=\"\"/>";
//         let name = "img"+i;
//         let url= (window.URL ? URL : webkitURL).createObjectURL(input.files[i]);
//         alert(url);
//         $(name).src = url;
//     }
//
// }