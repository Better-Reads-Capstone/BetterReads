const url = "https://api.nytimes.com/svc/books/v3/lists.json?list="
const apiKey = `&api-key=${nytAPIKey}`
const urlList = ["combined-print-and-e-book-fiction","hardcover-fiction","trade-fiction-paperback","combined-print-and-e-book-nonfiction","hardcover-nonfiction","paperback-nonfiction", "advice-how-to-and-miscellaneous"];
const selectors = ["print-ebook-fiction-list","hardcover-fiction-list", "trade-fiction-paperback-list", "print-ebook-nonfiction-list", "hardcover-nonfiction-list", "paperback-nonfiction-list", "advice-how-to-list"]


function displayBestSellers() {
    for(let i = 0; i < urlList.length; i++) {
        fetch(`${url}${urlList[i]}${apiKey}`)
            .then(response => response.json())
            .then(data => {
                document.querySelector(`.${selectors[i]}`).innerHTML = displayBookCard(data);
            })
            .catch(errors => console.log(errors))
    }
}

displayBestSellers();

function displayBookCard(data) {
    let finalHTML = '';
    console.log(data.results[0]);
    for(let i = 0; i < data.results.length; i++) {
        finalHTML +=
            `<div class="col mb-4">
                        <div class="card">
                            <div class="card-header">
                                <p>${data.results[i].book_details[0].title}</p>
                            </div>
                            <div class="card-body">
                                <p>${data.results[i].isbns[0].isbn10}</p>
                            </div>
                            <div class="card-footer">
                                <form>
                                    <input hidden value="${data.results[i].isbns[0].isbn10}">
                                    <button type="submit" class="btn btn-primary">Add Book!</button>
                                </form>
                            </div>
                        </div>
                    </div>`
    }

    return finalHTML;
}


