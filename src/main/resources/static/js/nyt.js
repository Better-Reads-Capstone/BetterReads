$(document).ready(function() {
    const url = "https://api.nytimes.com/svc/books/v3/lists/overview.json?api-key=" + nytAPIKey;
    let booksList = [];


    //Loading Icon
    function showLoadingIcon() {
        $(".loading-icon").removeAttr("style").addClass("show");
    }
    function hideLoadingIcon() {
        $(".loading-icon").css("display", "none");
    }

    retrieveNYTBookInfo();

    // retrieveNYTBookInfo saves info from the NYT api, saves the info as book objects and then stores those objects into the booksList array. displayBestSellers is called in this function.
    function retrieveNYTBookInfo(){
        showLoadingIcon();
        fetch(url)
            .then(response => response.json())
            .then(books => {
                hideLoadingIcon();
                const bestSellerLists = books.results.lists;
                // console.log(bestSellerLists);
                for(let i = 0; i < bestSellerLists.length; i++) {
                    // console.log(bestSellerLists[i]);
                    for(let x = 0; x < bestSellerLists[i].books.length; x++) {
                        console.log(bestSellerLists[i].books[x]);
                        booksList.push({
                            listName: bestSellerLists[i].list_name,
                            isbn: bestSellerLists[i].books[x].primary_isbn13,
                            image: bestSellerLists[i].books[x].book_image,
                            title: bestSellerLists[i].books[x].title
                        })
                    }
                }
                console.log(booksList);
                displayBestSellers(booksList);
            })
            .catch(err => {
                showLoadingIcon();
                setTimeout( () => {
                    location.reload();
                }, 10000);
                console.log(err)
            });
    }



    // This loops through the booksList and creates a card for each book object using displayBookCard. During each iteration, the "card" gets added to the appropriate variable, depending on the listName (using a switch case for conditional). Once the loop is complete, each variable gets added to it's corresponding div class.
    function displayBestSellers(books) {
        let printAndEbookFic = "";
        let printAndEbookNon = "";
        let hardcoverFiction = "";
        let hardcoverNonfiction = "";
        let tradeFictionPaperback = "";
        let paperbackNonfiction = "";
        let adviceHowToAndMiscellaneous = "";
        let childrensMiddleGradeHardcover = "";
        let pictureBooks = "";
        let seriesBooks = "";
        let youngAdultHardcover = "";

        books.forEach(book => {
            switch(book.listName) {
                case "Combined Print and E-Book Fiction":
                    printAndEbookFic += displayBookCard(book);
                    break;
                case "Combined Print and E-Book Nonfiction":
                    printAndEbookNon += displayBookCard(book);
                    break;
                case "Hardcover Fiction":
                    hardcoverFiction += displayBookCard(book);
                    break;
                case "Hardcover Nonfiction":
                    hardcoverNonfiction += displayBookCard(book);
                    break;
                case "Trade Fiction Paperback":
                    tradeFictionPaperback += displayBookCard(book);
                    break;
                case "Paperback Nonfiction":
                    paperbackNonfiction += displayBookCard(book);
                    break;
                case "Advice How-To and Miscellaneous":
                    adviceHowToAndMiscellaneous += displayBookCard(book);
                    break;
                case "Childrens Middle Grade Hardcover":
                    childrensMiddleGradeHardcover += displayBookCard(book);
                    break;
                case "Picture Books":
                    pictureBooks += displayBookCard(book);
                    break;
                case "Series Books":
                    seriesBooks += displayBookCard(book);
                    break;
                case "Young Adult Hardcover":
                    youngAdultHardcover += displayBookCard(book);
                    break;
                default:
                    console.log("none");
                    break;
            }
        })

        document.querySelector(".print-ebook-fiction-list").innerHTML = printAndEbookFic;
        document.querySelector(".print-ebook-nonfiction-list").innerHTML = printAndEbookNon;
        document.querySelector(".hardcover-fiction-list").innerHTML = hardcoverFiction;
        document.querySelector(".hardcover-nonfiction-list").innerHTML = hardcoverNonfiction;
        document.querySelector(".trade-fiction-paperback-list").innerHTML = tradeFictionPaperback
        document.querySelector(".paperback-nonfiction-list").innerHTML = paperbackNonfiction;
        document.querySelector(".advice-how-to-list").innerHTML = adviceHowToAndMiscellaneous;
        document.querySelector(".middle-grade-list").innerHTML = childrensMiddleGradeHardcover;
        document.querySelector(".picture-books-list").innerHTML = pictureBooks;
        document.querySelector(".series-books-list").innerHTML = seriesBooks;
        document.querySelector(".ya-hardcover-list").innerHTML = youngAdultHardcover;
    }

    // Card creation
    function displayBookCard(book) {
        let finalHTML;
        finalHTML =
            `<div class="col mb-4">
                        <div class="card" style="width: 12rem;">
                            <img src="${book.image}" class="card-img-top" alt="Book cover for ${book.title}">
                            <div class="card-body">
                                <p>Author: ${book.author}</p>
                                <p>Rank: ${book.rank}</p>
                                <p>Rank last week: ${book.lastWeekRank}</p>
                                <p>ISBN: ${book.isbn}</p>
                                <p>Publisher: ${book.publisher}</p>
                                <p>Description: ${book.description}</p>
                            </div>
                            <div class="card-footer">
                                <a href="/booksearch?searchvalue=${book.title}">Search</a>
                            </div>
                        </div>
                    </div>`
        return finalHTML;
    }
});


