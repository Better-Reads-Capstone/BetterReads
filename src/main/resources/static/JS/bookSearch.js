$(document).ready(function(){
    let searchvalue = null;
    try {
        searchvalue = document.getElementById("result-term").getAttribute("value");
    } catch (err) {
    }
    if (searchvalue != null) {
        simpleQuery(searchvalue, "search-results", displayMultiBookCards);
    }
})

const searchRequest = () => {
    let searchTerm = document.getElementById("search-term").value
    window.location.replace("/booksearch?searchvalue=" + searchTerm);
}

$('#search-btn').click((e) => {
    e.preventDefault();
    searchRequest();
});



