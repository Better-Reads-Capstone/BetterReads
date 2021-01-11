$(document).ready(() => {
    //refactor to apply to reset-password form
    $('#showPassword').on('click', () => {
        const hideShowNewPassword = () => {
            let password = $('#password');
            if(password.attr('type') === "password") password.attr('type', 'text');
            else password.attr('type', 'password');
        }
        hideShowNewPassword();
    })
    $('#showConfirmPassword').on('click', () => {
        const hideShowOldPassword = () => {
            let confirmPassword = $('#confirmPassword');
            if(confirmPassword.attr('type') === "password") confirmPassword.attr('type', 'text');
            else confirmPassword.attr('type', 'password');
        }
        hideShowOldPassword();
    })


    const checkPasswordMatch = fieldConfirmPassword => {
        if (fieldConfirmPassword.value != $("#password").val()) {
            fieldConfirmPassword.setCustomValidity("Passwords do not match!");
        } else {
            fieldConfirmPassword.setCustomValidity("");
        }
    }

    $('#checkPassword').on('input', () => {
        checkPasswordMatch();
    });
})
