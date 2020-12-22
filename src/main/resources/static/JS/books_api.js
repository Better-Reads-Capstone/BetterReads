const baseUrl = new URL('https://www.googleapis.com/books/v1/volumes?');
const apiKey = googleBooksAPIKey;

function simpleQuery(searchTerm) {
    fetch(baseUrl + new URLSearchParams({
        q: searchTerm,
        key: apiKey
    }))
        .then(response => response.json())
        .then(result => {
            for (let i = 0; i < result.items.length; i++) {
                let item = result.items[i];
                console.log(item);
                document.getElementById("search-results").innerHTML += "<div class='card mb-3' style='max-width: 540px;'>";
                document.getElementById("search-results").innerHTML += "<div class='row no-gutters'>";
                document.getElementById("search-results").innerHTML += "<div class='col-md-4'>";
                document.getElementById("search-results").innerHTML += `<img src='${item.volumeInfo.imageLinks.thumbnail}' class='card-img' alt='thumbnail-image' style='width: 70px'>`;
                document.getElementById("search-results").innerHTML += "</div>";
                document.getElementById("search-results").innerHTML += "<div class='col-md-8'>";
                document.getElementById("search-results").innerHTML += "<div class='card-body'>";
                document.getElementById("search-results").innerHTML += `<h5 class="card-title>">${item.volumeInfo.title}</h5>`;
                document.getElementById("search-results").innerHTML += `<p class='card-text'>${item.volumeInfo.description}</p>`;
                document.getElementById("search-results").innerHTML += `<p class='card-text'>${item.volumeInfo.industryIdentifiers[0].identifier}</p>`;
                document.getElementById("search-results").innerHTML += `<form class='form-inline my-2 my-lg-0' method="POST" action="/book/${item.volumeInfo.industryIdentifiers[0].identifier}"><button class='btn my-2 my-sm-0' type='submit' id='addBookBtn'>Add Book</button>`;
                document.getElementById("search-results").innerHTML += "</form>";
                document.getElementById("search-results").innerHTML += "</div>";
                document.getElementById("search-results").innerHTML += "</div>";
                document.getElementById("search-results").innerHTML += "</div>";
                document.getElementById("search-results").innerHTML += "</div>";
            }
        })
}

$('#searchbtn').click(function(event) {
    event.preventDefault();
    let searchterm = $('#searchterm').val();
    simpleQuery(searchterm);
})