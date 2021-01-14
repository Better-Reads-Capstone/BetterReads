$(document).ready(function(){
    hideLoadingIcon();
    let queryTerm = $("#result-term").attr("value");
    let searchTerm = null;
    if (typeof  queryTerm !== 'undefined'){
        searchTerm = queryTerm;
    }
    if (searchTerm != null && searchTerm.length === 0) {
        searchTerm = null;
    }
    if (searchTerm != null) {
        searchRequest();
    }
})

const searchRequest = () => {
    let queryTerm = $("#result-term").attr("value");
    let searchTerm = $("#search-input").val();

    if (queryTerm.length > 0) {
        searchTerm = queryTerm;
    }

    $('#search-value').text(searchTerm);
    $("#results-title").css("display", "block");

    showLoadingIcon();
    let results = $('#search-results');
    results.empty();
    queryFetch(searchTerm)
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

    $(document).on("click", ".book-img", function() {
        let refId = $(this).data("ref");
        console.log(refId);
        window.location = "/book/" + refId;

        // let data = isbnFetch(isbn);
        //
        // data.then(result => {
        //     console.log(result);
        //     console.log(result.items[0].id);
        //     let id = result.items[0].id
        //     let url = "/book/" + id;
        //
        //     window.location = url;
        // })

    })
}

$('.search-form').submit((e) => {
    e.preventDefault();
    searchRequest();
})