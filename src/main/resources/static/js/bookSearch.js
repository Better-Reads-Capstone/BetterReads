$(document).ready(function(){
    let searchvalue = null;
    try {
        searchvalue = $("#result-term").attr("value");
    } catch (err) {}

    if (searchvalue != null) {
        // Deploy loading icon
        showLoadingIcon();

        // Empty HTML element
        let results = $('#search-results');
        results.empty();

        // Fetch data, format data, populate
        fetchQuery(searchvalue).then(data => {
            let booksArr = (createBookObjects(data));
            let finalHTML = '';
            for (const book of booksArr) {
                finalHTML += buildSmallBookCard(book);
            }
            results.append(finalHTML);

            // remove the loading icon
            hideLoadingIcon();
        })
            .catch(err => {
                console.log(err)
            })
    }
})

const searchRequest = () => {
    let searchTerm = $("#search-term").val();
    let url = "/booksearch?searchvalue=";
    window.location.replace(url + searchTerm);
}

$('.search-form').submit((e) => {
    e.preventDefault();
    searchRequest();
})