const url = "https://api.nytimes.com/svc/books/v3/lists.json?list="
const apiKey = `&api-key=${nytAPIKey}`

function displayBestSellers() {
    displayPrintAndEbook();
    displayHardcoverFiction();
    displayTradeFictionPaperback();
    displayPrintAndEbookNonfiction();
    displayHardcoverNonfiction();
    displayPaperbackNonfiction();
    displayAdviceAndHowTo();
}

displayBestSellers();

function displayPrintAndEbook() {
    fetch(`${url}combined-print-and-e-book-fiction${apiKey}`)
        .then(response => response.json())
        .then(data => {
            document.querySelector(".print-ebook-fiction-list").innerHTML = displayBookCard(data);
        })
        .catch(errors => console.log(errors))
}

function displayHardcoverFiction() {
    fetch(`${url}hardcover-fiction${apiKey}`)
        .then(response => response.json())
        .then(data => {
            document.querySelector(".hardcover-fiction-list").innerHTML = displayBookCard(data);
        })
        .catch(errors => console.log(errors))
}

function displayTradeFictionPaperback() {
    fetch(`${url}trade-fiction-paperback${apiKey}`)
        .then(response => response.json())
        .then(data => {
            document.querySelector(".trade-fiction-paperback-list").innerHTML = displayBookCard(data);
        })
        .catch(errors => console.log(errors))
}

function displayPrintAndEbookNonfiction() {
    fetch(`${url}combined-print-and-e-book-nonfiction${apiKey}`)
        .then(response => response.json())
        .then(data => {
            document.querySelector(".print-ebook-nonfiction-list").innerHTML = displayBookCard(data);
        })
        .catch(errors => console.log(errors))
}

function displayHardcoverNonfiction() {
    fetch(`${url}hardcover-nonfiction${apiKey}`)
        .then(response => response.json())
        .then(data => {
            document.querySelector(".hardcover-nonfiction-list").innerHTML = displayBookCard(data);
        })
        .catch(errors => console.log(errors))
}

function displayPaperbackNonfiction() {
    fetch(`${url}paperback-nonfiction${apiKey}`)
        .then(response => response.json())
        .then(data => {
            document.querySelector(".paperback-nonfiction-list").innerHTML = displayBookCard(data);
        })
        .catch(errors => console.log(errors))
}

function displayAdviceAndHowTo() {
    fetch(`${url}advice-how-to-and-miscellaneous${apiKey}`)
        .then(response => response.json())
        .then(data => {
            document.querySelector(".advice-how-to-list").innerHTML = displayBookCard(data);
        })
        .catch(errors => console.log(errors))
}

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


