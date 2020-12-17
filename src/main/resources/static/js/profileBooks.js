//will access the stored value of each element with the class userBook
function generatePath(path) {
    const getUrl = `https://www.googleapis.com/books/v1/volumes?q=isbn:${path}&key=${googleBooksAPI}`;
    return getUrl;
}

$('.userBook').each(function () {
    let isbn = $(this).attr('value')
    //now that I have access I can come up with a get api call to
    //retrieve book information and create a template to be inserted into html

    const getBooks = () => fetch(generatePath(isbn))
        .then(res => res.json())
        .then(books => {
            console.log(books)})


    // document.onreadystatechange = function () {
    //     if (document.readyState === "complete") {
    //         $('#loader').remove()
    //         getBooks()
    //     }
    // };
    getBooks()
})



