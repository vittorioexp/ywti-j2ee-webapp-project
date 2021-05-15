
document.addEventListener("DOMContentLoaded", function(event) {

    // Form validation while typing
    document.getElementById("email").addEventListener("focusout", function(event) {
        validateEmail(0)
    })
    document.getElementById("password").addEventListener("focusout", function(event) {
        validatePassword(0)
    })
});

