$('.editReview').click(function() {
    let reviewId = $(this).attr("data-reviewId");
    let username = $(this).attr("data-username");
    $('#editBody').innerText($(this).attr("data-reviewBody"));
    $('#currentRating').innerText("Current rating: "+$(this).attr("data-reviewRating"));
    $('#editReviewForm').attr('action', `/book/${username}/review/edit/id=${reviewId}`)
})

