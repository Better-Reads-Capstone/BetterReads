const baseUrl = new URL('https://www.googleapis.com/books/v1/volumes?');
const apiKey = booksApiKey;

function simpleQuery(searchTerm){
    fetch(baseUrl + new URLSearchParams({
        q: searchTerm,
        key: apiKey
    }))
        .then(response => response.json())
        .then(result => {
            console.log("Result data: ", result)
        })

}

simpleQuery("frankenstein");