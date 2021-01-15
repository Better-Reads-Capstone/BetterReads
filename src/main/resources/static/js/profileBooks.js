//will access the stored value of each element with the class userBook
function generatePath(path) {
    const getUrl = `https://www.googleapis.com/books/v1/volumes/${path}?&key=${googleBooksAPIKey}`
    return getUrl;
}

//WILL CREATE AN ARRAY OF OBJECTS THAT CAN BE PASSED INTO getBook() and accessed
let readBooks = [];
let activeBooks = [];
let wishlistBooks = [];
$('.readBook').each(function () {
    readBooks.push($(this).attr('id'));
})
$('.activeBook').each(function () {
    activeBooks.push($(this).attr('id'));
})
$('.wishlistBook').each(function () {
    wishlistBooks.push($(this).attr('id'));
})

const getBook = (books) => {
    for (let currentBook of books) {
        fetch(generatePath(currentBook))
            .then(res => res.json())
            .then(book => {
                let drillPath = book.volumeInfo;
                let renderBook = `
                        <div>
                            <img class="book-img" src="${drillPath.imageLinks.smallThumbnail}" alt="book-img">
                        </div>
                        <div id="${'book-' + currentBook}"></div>
                        `
                $(`#${currentBook}`).html(renderBook);
            })
            .catch(error => console.error("ERROR"))
    }
}

$(document).ready(function () {
    $('.book').click(function (event) {
        event.preventDefault();
        let userId = $('#userProfile').attr("data-userId");
        let username = $('#userProfile').attr("data-username");
        let id = $(this).attr("id");
        let bookId = $(this).attr("data-bookId");
        $('#createReview').attr('action', `/profile/${username}/review/${bookId}`);
        fetch(generatePath(id))
            .then(res => res.json())
            .then(book => {
                let drillPath = book.volumeInfo;
                $('.modal-body').html(
                    `
            <div>
                <div>
                    <!-- LEFT BOTH IMAGE PATHS AVAILABLE FOR STYLING PURPOSES -->
                    <img src="${drillPath.imageLinks.thumbnail}" alt="book-image">
                    <img src="${drillPath.imageLinks.smallThumbnail}" alt="book-image-small">
                </div>
                <div>
                    <h6>${drillPath.title}</h6>
                    <p>${drillPath.description}</p>
                    <div>
                        <p>Author: ${drillPath.authors}</p>
                        <p>Published Date: ${drillPath.publishedDate}</p>
                        <p>Categories${drillPath.categories}</p>
                        <div class="d-flex">
                        <p>ISBN: </p>
                        <p>(${drillPath.industryIdentifiers[0].identifier})</p>
                        <!-- NEED A CONDITIONAL TO NOT DISPLAY A VALUE IF NULL -->
                        <p>(${drillPath.industryIdentifiers[1].identifier})</p>
                        </div>
                        <!-- NEED A CONDITIONAL TO NOT DISPLAY A VALUE IF UNDEFINED -->
                        <a href="#">${book.saleInfo.buyLink}</a>
                    </div>
                </div>
            </div>
            `
                );
            })
            .catch(error => console.log("ERROR"))

        const constructDefaultReviewForm = () => {
            $('#createReview').attr('action', `/profile/${username}/review/${bookId}`);
            $('#currentRating').html('Leave A Rating: ');
            $('#createRating').html(`
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                `)
            $('#reviewId').val('');
            $('#createBody').val('');
            $('#reviewSubmit').html('Submit Review');
            $('#deleteReview').hide();
        }
        constructDefaultReviewForm();
        fetch(`/review.json`)
            .then(res => res.json())
            .then(reviews => {
                for (let review of reviews) {
                    console.log(review.owner.id);
                    let reviewId = review.id;
                    let body = review.body;
                    let rating = review.rating;
                    if (review.book.id == bookId && review.owner.id == userId) {
                        $('#createReview').attr('action', `/profile/${username}/${bookId}/editReview/${reviewId}`);
                        $('#reviewId').val(id);
                        $('#createBody').val(body);
                        $('#currentRating').html('Current Rating: ');
                        $(`#createRating option[value=${rating}]`).attr({
                            selected: 'selected'
                        });
                        $('#reviewSubmit').html('Submit Changes');
                        $('#deleteReview').show().attr('action', `/profile/${username}/deleteReview/${reviewId}`);
                    }
                }
            })
    })
})


document.onreadystatechange = function () {
    if (document.readyState === "complete") {
        $('#loader').remove()
        getBook(readBooks)
        getBook(activeBooks)
        getBook(wishlistBooks)
    }

    $(".collapse-button-read").click(function () {
        $("#read").slideToggle("slow");
    })

    $(".collapse-button-reading").click(function () {
        $("#reading").slideToggle("slow");
    })

    $(".collapse-button-wishlist").click(function () {
        $("#wishlist").slideToggle("slow");
    })
};