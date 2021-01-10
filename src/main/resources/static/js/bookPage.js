$(document).ready(function() {
    let gbreferenceId = $('#book-value').val()
    let bookCon = $('#book-container');
    let edit = $('#editReviewBtn');
    $('#editReviewBtn').click(function(event) {
        event.preventDefault();
        //modal not triggering

        //may need to incorporate a default
        let reviewId = edit.attr('data-reviewId');
        let username = edit.attr('data-username');
        let body = edit.attr('data-reviewBody');
        let rating = edit.attr('data-reviewRating');

        $('#editReview').attr('action', `/${username}/${gbreferenceId}/editReview/${reviewId}`);
        $('#editReviewBtn').attr('data-reviewId');
        $('#editBody').val(body);
        $('#currentRating').html('Current Rating: ');
        $(`#editRating option[value=${rating}]`).attr({
            selected: 'selected'
        });
        $('#reviewSubmit').html('Submit Changes');
    })

    showLoadingIcon();
    bookCon.empty();
    referenceFetch(gbreferenceId)
        .then(data => {
            console.log(data);
            let bookObj = createSingleBookObj(data);
            let bookHTML = buildLargeSingleCard(bookObj);
            bookCon.append(bookHTML)
            hideLoadingIcon()
        }).catch(err => {
        console.log(err)
    })

})
