$(document).ready(function() {
    let gbreferenceId = $('#book-value').val()
    let edit = $('#editReviewBtn');
    $('#editReviewBtn').click(function(event) {
        event.preventDefault();
        //modal not triggering

        //may need to incorporate a default
        let reviewId = edit.attr('data-reviewId');
        let username = edit.attr('data-username');
        let body = edit.attr('data-reviewBody');
        let rating = edit.attr('data-reviewRating');
        console.log(reviewId)
        console.log(username)
        console.log(body)
        console.log(rating)

        $('#editReview').attr('action', `/${username}/${gbreferenceId}/editReview/${reviewId}`);
        $('#editReviewBtn').attr('data-reviewId');
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
