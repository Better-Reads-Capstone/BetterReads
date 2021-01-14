$(document).ready(() => {
    $('#showPassword').on('click', () => {
        let input = $('#password');
        if(input.attr('type') === "password") input.attr('type', 'text');
        else input.attr('type', 'password');
    })

    $('#showConfirmPassword').on('click', () => {
        let input = $('#passwordConfirm');
        if(input.attr('type') === "password") input.attr('type', 'text');
        else input.attr('type', 'password');
    })


    $('#passwordConfirm').on('keyup', () => {
        if ($('#password').val() == $('#passwordConfirm').val()) {
            $('#message').html('Passwords Match').css('color', 'green');
            $('#changePassword').prop('disabled', false);
        } else {
            $('#message').html('Passwords Do Not Match').css('color', 'red');
            $('#changePassword').prop('disabled', true);
        }
    });
})
