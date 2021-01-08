$(document).ready(function(){
    let searchvalue = null;
    try {
        searchvalue = $("#result-term").attr("value");
    } catch (err) {}

    if (searchvalue != null) {
        simpleQuery(searchvalue, "search-results", displayMultiBookCards);
    } else {
        // console.error("Search value is null.")
    }
})

const searchRequest = () => {
    let searchTerm = $("#search-term").val();
    window.location.replace("/booksearch?searchvalue=" + searchTerm);
}

$('.search-form').submit((e) => {
    e.preventDefault();
    searchRequest();
})