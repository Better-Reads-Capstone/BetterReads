//will access the stored value of each element with the class userBook
function generatePath(path) {
    const getUrl = `https://www.googleapis.com/books/v1/volumes?q=isbn:${path}&key=${googleBooksAPI}`;
    return getUrl;
}

//CREATES ARRAYS OF ISBNS FROM THE VIEW
let readIsbns = [];
$('.readBook').each(function () {
    readIsbns.push($(this).attr('id'))
})
let activeIsbns = [];
$('.activeBook').each(function () {
    activeIsbns.push($(this).attr('id'))
})
let wishlistIsbns = [];
$('.wishList').each(function () {
    wishlistIsbns.push($(this).attr('id'))
})

const getBook = (isbns) => {
    for (let isbn of isbns) {
        fetch(generatePath(isbn))
            .then(res => res.json())
            .then(book => {
                let drillPath = book.items[0].volumeInfo;

                let createBook = {};
                createBook.img = drillPath.imageLinks.smallThumbnail;
                createBook.title = drillPath.title;
                createBook.authors = drillPath.authors;
                createBook.description = drillPath.description;
                createBook.publishedDate = drillPath.publishedDate;
                createBook.language = drillPath.language;
                createBook.categories = drillPath.categories;
                createBook.isbn = drillPath.industryIdentifiers;
                createBook.saleInfo = book.items[0].saleInfo.buyLink;
                if (createBook.isbn[1].identifier === isbn || createBook.isbn[0].identifier === isbn) {
                    let renderBook = `
                        <div>
                                <img src="${createBook.img}" alt="book-img">
                            <div class="d-flex">
                                <h6>${createBook.title}</h6>
                                <div class="dropdown show">
                                    <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        currentStatus goes here
                                    </a>
                                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                        <!-- POST METHOD TO CHANGE A BOOKS BOOKSHELF STATUS -->
                                        <form action="#" method="post">
                                            <button type="submit" class="dropdown-item" th:value="READ" th:href="#">Read</button>
                                            <button type="submit" class="dropdown-item" th:value="READING" th:href="#">Reading</button>
                                            <button type="submit" class="dropdown-item" th:value="WISHLIST" th:href="#">Wishlist</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div id="${'book-' + isbn}"></div>
                        `
                    $(`#${isbn}`).html(renderBook);
                }
            })
            .catch(error => console.error("ERROR"))
    }
}

$('.book').click(function (event) {
    event.preventDefault();

    let isbn = $(this).attr("id")
    fetch(generatePath(isbn))
        .then(res => res.json())
        .then(book => {
            let drillPath = book.items[0].volumeInfo;
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
                        <a href="#">${book.items[0].saleInfo.buyLink}</a>
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
        getBook(readIsbns)
        getBook(activeIsbns)
        getBook(wishlistIsbns)
    }
};