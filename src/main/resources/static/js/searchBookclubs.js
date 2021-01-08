if ($('#genreSelect').attr('value') > 0) {
    $('#defaultSelect').removeAttr('selected');
    $(`#genreSelect option[value=${$('#genreSelect').attr('value')}]`).attr({
        selected: 'selected'
    });
}