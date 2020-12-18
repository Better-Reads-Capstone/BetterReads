const baseUrl = new URL('https://www.googleapis.com/books/v1/volumes');
const apiKey = gbKey;

function simpleQuery(searchTerm) {
    document.getElementById("search-results").innerHTML = "";
    fetch(baseUrl + '?' + new URLSearchParams({
        q: searchTerm,
        printType: 'books',
        key: apiKey
    }))
        .then(response => response.json())
        .then(result => {
            let finalhtml = displayMultiBookCards(result);
            // let finalhtml = buildIsbns(result);
            document.getElementById("search-results").innerHTML += finalhtml;
            })
        .catch(e => {
            console.log(e);
        })
}

function isbnQuery(isbn) {
    document.getElementById("search-results").innerHTML = "";
    fetch(baseUrl + '?' + new URLSearchParams({
        q: 'isbn:' + isbn,
        printType: 'books',
        key: apiKey
    }))
        .then(response => response.json())
        .then(result => {
            let finalhtml = displayMultiBookCards(result);
            document.getElementById("search-results").innerHTML += finalhtml;
        })
        .catch(e => {
            console.log(e);
        })
}

function referenceQuery(gbid) {
    document.getElementById("search-results").innerHTML = "";
    fetch(baseUrl + '/' + gbid)
        .then(response => response.json())
        .then(result => {
            let finalhtml = displaySingleBookCard(result);
            document.getElementById("search-results").innerHTML += finalhtml;
        })
        .catch(e => {
            console.log(e);
        })
}

function buildIsbns(data){
    console.log(data)
    let isbn = [];
    for (let i = 0; i < data.items.length; i++){
        let isbnObject = {};
        let book = data.items[i];
        for (let x = 0; x < book.volumeInfo.industryIdentifiers.length; x++) {
            isbnObject.type = book.volumeInfo.industryIdentifiers[x].type;
            isbnObject.isbn = book.volumeInfo.industryIdentifiers[x].identifier;
            isbn.push(isbnObject)
            console.log(isbnObject);
        }
    }
    return isbn;
}

function displayMultiBookCards(data) {
    console.log(data);
    let finalHTML = '';
    for(let i = 0; i < data.items.length; i++) {
        let book = data.items[i];

        // set a default thumbnail, overwrite if possible
        let thumbnail = 'https://via.placeholder.com/75';

        // This is needed in case a book resource doesn't have a thumbnail
        try {
            thumbnail = book.volumeInfo.imageLinks.thumbnail;
        } catch (e) {
            console.log("api thumbnail is undefined");
        }

        finalHTML +=
            `<div class="col mb-4">
                        <div class="card">
                            <div class="card-header">
                                <p>${book.volumeInfo.title}</p>
                            </div>
                            <div class="card-body">
                                <img src='${thumbnail}' class='card-img' alt='thumbnail-image' style='width: 70px'>
                                <p>${book.volumeInfo.industryIdentifiers[0].identifier}</p>
                            </div>
                            <div class="card-footer">
                                <form>
                                    <input hidden value="${book.volumeInfo.industryIdentifiers[0].identifier}">
                                    <button type="submit" class="btn btn-primary">Add Book!</button>
                                </form>
                            </div>
                        </div>
                    </div>`
    }

    return finalHTML;
}

function getGoogleRef(isbn){
    // make an API query that takes an ISBN number and returns a Google Books reference ID
}

function displaySingleBookCard(data) {
    console.log(data);
    let finalHTML;
    let book = data;

    // set a default thumbnail, overwrite if possible
    let thumbnail = 'https://via.placeholder.com/75';
    try {
        thumbnail = book.volumeInfo.imageLinks.thumbnail;
    } catch (e) {
        console.log("api thumbnail is undefined");
    }

    finalHTML =
        `<div class="col mb-4">
                    <div class="card">
                        <div class="card-header">
                            <p>${book.volumeInfo.title}</p>
                        </div>
                        <div class="card-body">
                            <img src='${thumbnail}' class='card-img' alt='thumbnail-image' style='width: 70px'>
                            <p>${book.volumeInfo.industryIdentifiers[0].identifier}</p>
                        </div>
                        <div class="card-footer">
                            <form>
                                <input hidden value="${book.volumeInfo.industryIdentifiers[0].identifier}">
                                <button type="submit" class="btn btn-primary">Add Book!</button>
                            </form>
                        </div>
                    </div>
                </div>`
    return finalHTML;
}

$('#searchbtn').click(function(event) {
    event.preventDefault();
    let searchterm = $('#searchterm').val();
    simpleQuery(searchterm);
})

$('#isbntermbtn').click(function(event) {
    event.preventDefault();
    let isbn = $('#isbnterm').val();
    isbnQuery(isbn)
})

$('#reftermbtn').click(function(event) {
    event.preventDefault();
    let reference = $('#refterm').val();
    referenceQuery(reference)
})