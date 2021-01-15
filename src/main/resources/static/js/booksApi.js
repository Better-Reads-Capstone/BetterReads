const baseUrl = new URL('https://www.googleapis.com/books/v1/volumes');
const apiKey = googleBooksAPIKey;

const queryFetch = (searchTerm) => {
    return new Promise((resolve) => {
        fetch(baseUrl + '?' + new URLSearchParams({
            q: searchTerm,
            printType: 'books',
            maxResults: 40,
            key: apiKey
        }))
            .then(response => response.json())
            .then(data => {resolve(data)})
            .catch(error => {
                console.error(error);
            })
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

const referenceFetch = (gbid) => {
    return new Promise((resolve) => {
        fetch(baseUrl + '/' + gbid)
            .then(response => response.json())
            .then(data => {resolve(data)});
        })
}

const titleAuthorFetch = (title, author) => {
    return new Promise((resolve) => {
        fetch(baseUrl + '?' + new URLSearchParams({
            q: title + '+inauthor' + author,
            printType: 'books',
            key: apiKey
        }))
            .then(response => response.json())
            .then(data => {resolve(data)});
    })
}

const createBookObjects = (data) => {
    let formattedBooks = [];
    for (i = 0; i < data.items.length; i++){
        const bookAPIObj = {
            title: data.items[i].volumeInfo.title,
            auth: data.items[i].volumeInfo.authors ?? 'Data unavailable',
            id: data.items[i].id,
            desc: data.items[i].volumeInfo.description ?? 'Unavailable',
            descSm: data.items[i].volumeInfo.description ?? 'Unavailable',
            pubDate: data.items[i].volumeInfo.publishedDate,
            publisher: data.items[i].volumeInfo.publisher,
            img: data.items[i].volumeInfo.imageLinks ?? '/img/logo.png',
            isbn: data.items[i].volumeInfo.industryIdentifiers ?? [{type: 'ISBN', value: 'Unavailable'}],
            result: i + 1
        }

        if (bookAPIObj.auth !== 'Data unavailable') {
            bookAPIObj.auth = data.items[i].volumeInfo.authors[0];
        }

        if (bookAPIObj.img !== '/img/logo.png'){
            if (typeof data.items[i].volumeInfo.imageLinks.thumbnail !== 'undefined') {
                bookAPIObj.img = data.items[i].volumeInfo.imageLinks.thumbnail;
            } else if (typeof data.items[i].volumeInfo.imageLinks.smallThumbnail !== 'undefined') {
                bookAPIObj.img = data.items[i].volumeInfo.imageLinks.smallThumbnail;
            }
        }
        if(bookAPIObj.desc.length > 100) {
            bookAPIObj.descSm = bookAPIObj.desc.slice(0, 100) + '...'
        }
        formattedBooks.push(bookAPIObj);
    }

    return formattedBooks;
}

const createSingleBookObj = (data) => {
    const bookAPIObj = {
        title: data.volumeInfo.title,
        auth: data.volumeInfo.authors ?? 'Data unavailable',
        id: data.id,
        desc: data.volumeInfo.description ?? 'Unavailable',
        descSm: data.volumeInfo.description ?? 'Unavailable',
        pubDate: data.volumeInfo.publishedDate,
        publisher: data.volumeInfo.publisher,
        img: data.volumeInfo.imageLinks ?? '/img/logo.png',
        isbn: data.volumeInfo.industryIdentifiers ?? [{type: 'ISBN', value: 'Unavailable'}]
    }

    if (bookAPIObj.auth !== 'Data unavailable') {
        bookAPIObj.auth = data.volumeInfo.authors[0];
    }

    if (bookAPIObj.img !== '/img/logo.png'){
        if (typeof data.volumeInfo.imageLinks.thumbnail !== 'undefined') {
            bookAPIObj.img = data.volumeInfo.imageLinks.thumbnail;
        } else if (typeof data.volumeInfo.imageLinks.smallThumbnail !== 'undefined') {
            bookAPIObj.img = data.volumeInfo.imageLinks.smallThumbnail;
        }
    }

    if(bookAPIObj.desc.length > 100) {
        bookAPIObj.descSm = bookAPIObj.desc.slice(0, 100) + '...'
    }

    return bookAPIObj;
}

const buildSmallBookCard = (book) => {
    let cardHTML = '';
    cardHTML =
        `<div class="book-cover">
                <div class="book-card">
                    <img src="${book.img}" class="book-img" alt="Book cover for ${book.title}" data-ref="${book.id}"/>
                </div>
                <div class="book-info-background">
                    
                </div>
                <div class="book-card book-search-info">
                    <p class="book-title">${book.title}</p>
                    <p class="book-author">${book.auth}</p>
                </div>
            </div>`
    return cardHTML;
}

const buildLargeSingleCard = (book) => {
    let cardHTML = '';
    cardHTML =
        `<div class="card-header">
             <p>${book.title}</p>
         </div>
         <div class="card-body">
             <img src='${book.img}' class='card-img' alt="Book cover for ${book.title}" style='width: 18rem'>
             <p>ISBN: ${book.isbn[0].identifier}</p>
             <p>Author: ${book.auth}</p>
             <p>Description:</p>
             <p>${book.desc}</p>
         </div>`
    return cardHTML;

}