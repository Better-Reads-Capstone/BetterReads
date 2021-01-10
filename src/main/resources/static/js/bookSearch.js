$(document).ready(function(){
    let searchvalue = $("#result-term").attr("value") ?? null;

    if (searchvalue != null) {
        showLoadingIcon();
        let results = $('#search-results');
        results.empty();
        queryFetch(searchvalue)
            .then(data => {
                let booksArr = (createBookObjects(data));
                let finalHTML = '';
                for (const book of booksArr) {
                    finalHTML += buildSmallBookCard(book);
                }
                results.append(finalHTML);
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