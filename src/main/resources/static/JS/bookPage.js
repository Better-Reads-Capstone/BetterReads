$(document).ready(function() {
    let gbreferenceId = $('#book-value').val()
    let edit = $('#editReview');
    $(edit).click(function(event) {
        event.preventDefault();
        //may need to incorporate a default
        let reviewId = edit.attr('data-reviewId');
        let username = edit.attr('data-username');
        let body = edit.attr('data-reviewBody');
        let rating = edit.attr('data-reviewRating');

        let book = $('#book').attr('data-book');
        $('#editReview').attr('action', `/${username}/${book}/editReview/${reviewId}`);
        $('#reviewId').val(id);
        $('#editBody').val(body);
        $('#currentRating').html('Current Rating: ');
        $(`#editRating option[value=${rating}]`).attr({
            selected: 'selected',
            disabled: 'disabled'
        });
        $('#reviewSubmit').html('Submit Changes');
    })
    // Fetch the book data and populate the "book-container" HTML element with the book card
    referenceQuery(gbreferenceId, "book-container", displaySingleBookCard);
})
