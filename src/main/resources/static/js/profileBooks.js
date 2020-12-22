//will access the stored value of each element with the class userBook
function generatePath(path) {
    // const getUrl = `https://www.googleapis.com/books/v1/volumes?q=isbn:${path}&key=${googleBooksAPI}`;
    const getUrl = `https://www.googleapis.com/books/v1/volumes/${path}?&key=${googleBooksAPI}`
    return getUrl;
}

//WILL CREATE AN ARRAY OF OBJECTS THAT CAN BE PASSED INTO getBook() and accessed
let readBooks = [];
let activeBooks = [];
let wishlistBooks = [];
$('.readBook').each(function() {
    let book = {}
    book.id = $(this).attr('id');
    readBooks.push(book);
})
$('.activeBook').each(function() {
    let book = {}
    book.id = $(this).attr('id');
    activeBooks.push(book);
})
$('.wishlistBook').each(function() {
    let book = {}
    book.id = $(this).attr('id');
    wishlistBooks.push(book);
})

const getBook = (books) => {
    for (let currentBook of books) {
        console.log(generatePath(currentBook.id))
        fetch(generatePath(currentBook.id))
            .then(res => res.json())
            .then(book => {
                let drillPath = book.volumeInfo;

                    let renderBook = `
                        <div>
                                <img src="${drillPath.imageLinks.smallThumbnail}" alt="book-img">
                            <div class="d-flex">
                                <h6>${drillPath.title}</h6>
                            </div>
                        </div>

                        <div id="${'book-' + currentBook.id}"></div>
                        `
                    $(`#${currentBook.id}`).html(renderBook);
            })
            .catch(error => console.error("ERROR"))
    }
}

$('.book').click(function (event) {
    event.preventDefault();

    let id = $(this).attr("id")
    fetch(generatePath(id))
        .then(res => res.json())
        .then(book => {
            let drillPath = book.volumeInfo;
            console.log(drillPath)
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
                    <div>
                        <div class="d-flex">
                            <h6>Leave a Review</h6>
                            <button class="minimize">-</button>
                        </div>
                        <!-- PROFILE USER REVIEW FORM -->
                        <form action="" method="post">
                           <div>
                              <textarea name="" id="" cols="30" rows="10"></textarea>
                           </div>
                           <button type="submit">Submit</button>
                        </form>
                    </div>
                </div>
            </div>
            `)
        })
        .catch(error => console.log("ERROR"))
})

document.onreadystatechange = function () {
    if (document.readyState === "complete") {
        $('#loader').remove()
        getBook(readBooks)
        getBook(activeBooks)
        getBook(wishlistBooks)
    }
};