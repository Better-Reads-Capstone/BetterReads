const baseUrl = new URL('https://www.googleapis.com/books/v1/volumes');
const apiKey = googleBooksAPIKey;

// Fetch functions
const simpleQuery = (searchTerm, elementId, callback) => {
    document.getElementById(elementId).innerHTML = "";
    fetch(baseUrl + '?' + new URLSearchParams({
        q: searchTerm,
        printType: 'books',
        key: apiKey
    }))
        .then(response => response.json())
        .then(result => {
            let finalhtml = callback(result);
            document.getElementById(elementId).innerHTML += finalhtml;
        })
        .catch(e => {
            console.log(e);
        })
}

const fetchQuery = (searchTerm) => {
    return new Promise((resolve) => {
        fetch(baseUrl + '?' + new URLSearchParams({
            q: searchTerm,
            printType: 'books',
            key: apiKey
        }))
            .then(response => response.json())
            .then(data => {resolve(data)})
            .catch(error => {
                console.error(error);
            })
    })
}

const isbnQuery = (isbn, elementId, callback) => {
    document.getElementById(elementId).innerHTML = "";
    fetch(baseUrl + '?' + new URLSearchParams({
        q: 'isbn:' + isbn,
        printType: 'books',
        key: apiKey
    }))
        .then(response => response.json())
        .then(result => {
            let finalhtml = callback(result);
            document.getElementById(elementId).innerHTML += finalhtml;
        })
        .catch(e => {
            console.log(e);
        })
}

const isbnFetch = (isbn) => {
    return new Promise((resolve) => {
        fetch(baseUrl + '?' + new URLSearchParams({
            q: 'isbn:' + isbn,
            printType: 'books',
            key: apiKey
        }))
            .then(response => response.json())
            .then(data => {resolve(data)});
    })
}

const titleAuthorFetch = (title, author) => {
    return fetch(baseUrl + '?' + new URLSearchParams({
        q: title + '+inauthor' + author,
        printType: 'books',
        key: apiKey
    }))
        .then(response => response.json())

}

const referenceQuery = (gbid, elementId, callback) => {
    document.getElementById(elementId).innerHTML = "";
    fetch(baseUrl + '/' + gbid)
        .then(response => response.json())
        .then(result => {
            let finalhtml = callback(result);
            document.getElementById(elementId).innerHTML += finalhtml;
        })
        .catch(e => {
            console.log(e);
        })
}

// Callback functions to manipulate the data

const displayMultiBookCards = (data) => {
    let finalHTML = '';
    for(let i = 0; i < data.items.length; i++) {
        let book = data.items[i];
        let thumbnail = 'https://via.placeholder.com/75';
        let isbn = "Unable to search for book"
        try {
            thumbnail = book.volumeInfo.imageLinks.thumbnail;
        } catch (error) {
            console.error("No thumbnail exists for this book.")
        }
        try {
            isbn = book.volumeInfo.industryIdentifiers[0].identifier
        } catch (error) {
            console.log("Unable to search for book")
        }
        finalHTML +=
            `<div class="col mb-4">
                        <div class="card">
                            <div class="card-header">
                                <p>${book.volumeInfo.title}</p>
                            </div>
                            <div class="card-body">
                                <img src='${thumbnail}' class='card-img' alt='thumbnail-image' style='width: 70px'>
                                <p>ISBN: ${isbn}</p>
                            </div>
                            <div class="card-footer">
                                <form class='form-inline my-2 my-lg-0' method="GET" action="/book/${book.id}"><button class='btn btn-success my-2 my-sm-0' type='submit' id='viewBookBtn'>View Book</button>
                                </form>
                            </div>
                        </div>
                    </div>`
    }

    return finalHTML;
}

const displaySingleBookCard = (data) => {
    let finalHTML;
    let book = data;
    let thumbnail = 'https://via.placeholder.com/75';
    try {
        thumbnail = book.volumeInfo.imageLinks.thumbnail;
    } catch (error) {
        console.error("No thumbnail exists for this book.")
    }
    finalHTML =
        `<div class="card-header">
             <p>${book.volumeInfo.title}</p>
         </div>
         <div class="card-body">
             <img src='${thumbnail}' class='card-img' alt='thumbnail-image' style='width: 70px'>
             <p>ISBN: ${book.volumeInfo.industryIdentifiers[0].identifier}</p>
             <p>Author: ${book.volumeInfo.authors[0]}</p>
             <p>${book.volumeInfo.description}</p>
             <p>Book ID: ${book.id}</p>
         </div>`
    return finalHTML;
}

const createBookObjects = (data) => {
    let formattedBooks = [];
    for (i = 0; i < data.items.length; i++){
        console.log(data.items[i]);
        let isbnLength = data.items[i].volumeInfo.industryIdentifiers.length;
        let desc = data.items[i].volumeInfo.description || 'Description unavailable';
        let descSm = desc.slice(0, 100) + '...';
        let imgLg = data.items[i].volumeInfo.imageLinks.thumbnail;
        let imgSm = data.items[i].volumeInfo.imageLinks.smallThumbnail;
        let img = imgLg || imgSm || 'http://betterreads.site/img/logo.png';
        let isbnArr = [];
        for (x = 0; x < isbnLength; x++) {
            let isbn = {
                [data.items[i].volumeInfo.industryIdentifiers[x].type]: data.items[i].volumeInfo.industryIdentifiers[x].identifier
            }
            isbnArr.push(isbn);
        }
        let bookObj = {
            title: data.items[i].volumeInfo.title,
            auth: data.items[i].volumeInfo.authors[0],
            id: data.items[i].id,
            desc: desc,
            descSm: descSm,
            pubDate: data.items[i].volumeInfo.publishedDate,
            publisher: data.items[i].volumeInfo.publisher,
            img: img,
            isbn: data.items[i].volumeInfo.industryIdentifiers
        }
        formattedBooks.push(bookObj);
    }
    return formattedBooks;
}

const buildSmallBookCard = (book) => {
    let cardHTML = '';
    cardHTML =
        `<div class="col mb-4">
                        <div class="card" style="width: 14rem;">
                            <img src="${book.img}" class="card-img-top" alt="Book cover for ${book.title}">
                            <div class="card-body">
                                <h6>Title: ${book.title}</h6>
                                <p>Author: ${book.auth}</p>
                                <p>Id: ${book.id}</p>
                                <p>${book.isbn[0].type}: ${book.isbn[0].identifier}</p>
                                <p>Publisher: ${book.publisher}</p>
                                <p>Date Published: ${book.pubDate}</p>
                                <p>Description: ${book.descSm}</p>
                            </div>
                            <div class="card-footer">
                               <form class='form-inline my-2 my-lg-0' method="GET" action="/book/${book.id}"><button class='btn btn-success my-2 my-sm-0' type='submit' id='viewBookBtn'>View Book</button>
                                </form>
                            </div>
                        </div>
                    </div>`
    return cardHTML;
}