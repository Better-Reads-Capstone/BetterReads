//will access the stored value of each element with the class userBook
function generatePath(path) {
    const getUrl = `https://www.googleapis.com/books/v1/volumes?q=isbn:${path}&key=${googleBooksAPI}`;
    return getUrl;
}

//creates an array of isbns to be passed into fetch call
let isbns = [];
$('.userBook').each(function () {
    isbns.push($(this).attr('value'))
})


let books = [];
for(let isbn of isbns) {
    const getBook = async () => fetch(generatePath(isbn))
        .then(res => res.json())
        .then(book => {
            //WILL DRILL TO BASE ITEMS
            // console.log(book.items[0])
            let drillPath = book.items[0].volumeInfo;
            // console.log(drillPath);
            console.log(book.items[0].saleInfo.buyLink);

            let createBook = {};
            createBook.img = drillPath.imageLinks.smallThumbnail;
            createBook.title = drillPath.title;
            createBook.authors = drillPath.authors;
            createBook.description = drillPath.description;
            createBook.publishedDate = drillPath.publishedDate;
            createBook.language = drillPath.language;
            createBook.categories = drillPath.categories;
            createBook.saleInfo = book.items[0].saleInfo.buyLink;
            return createBook;
        })

    const setBook = async () => {
        let extractedBook = await getBook();
        books.push(extractedBook)

    }
    setBook()
}
console.log(books);





