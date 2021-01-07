var emailError = document.getElementById("error").getAttribute("value");
console.log(emailError);

function checkEmail(){
    if (emailError === true){
        alert("This email is already in use");
        document.getElementById("error").setAttribute("value", "false");
    } else {
        return true;
    }
}