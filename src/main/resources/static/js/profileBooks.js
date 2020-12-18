//will access the stored value of each element with the class userBook
function generatePath(path) {
    const getUrl = `https://www.googleapis.com/books/v1/volumes?q=isbn:${path}&key=${googleBooksAPI}`;
    return getUrl;
}

//creates an array of isbns to be passed into fetch call
let isbns = [];
$('.userBook').each(function () {
    isbns.push($(this).attr('id'))
})

//gets and builds book objects and stores them into a books array


// async function myFetch() {
//     for (let isbn of isbns) {
//         let response = await fetch(generatePath(isbn));
//         let extractBook = response.json();
//         let drillPath = book.items[0].volumeInfo;
//         let book = {};
//
//         if (!response.ok) {
//             throw new Error(`HTTP error! status: ${response.status}`);
//         } else {
//             let book = await response;
//             console.log(book)
//         }
//     }
// }
//
// myFetch()
//     .catch(e => {
//         console.log('There has been a problem with your fetch operation: ' + e.message);
//     });


let books = [];
const getBook = () => {
    for (let isbn of isbns) {
        fetch(generatePath(isbn))
            .then(res => res.json())
            .then(book => {

                //WILL DRILL TO BASE ITEMS
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
                // return createBook;
                books.push(createBook)
            })
            .catch(error => console.error("ERROR"))
    }
}
getBook()
//
// const setBook = async () => {
//     const book = await getBook()
//     return book
// }
// setBook().then(book => {
//     console.log(book)
// })


// //will display each book after the timeout
//
setTimeout(() =>
{

for(let book of books) {

    console.log(book)
}
        // voila!

}, 1000);

// console.log(books)
// $('.userBook').each(function() {
//     console.log($(this).attr('value'));
// })



