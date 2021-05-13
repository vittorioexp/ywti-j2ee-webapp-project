
document.addEventListener("DOMContentLoaded", function(event) {

    // Form validation while typing
    document.getElementById("email").addEventListener("keyup", function(event) {
        validateEmail(0)
    })
});

