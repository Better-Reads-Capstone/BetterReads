const url = "https://api.nytimes.com/svc/books/v3/lists.json?list="
const apiKey = `&api-key=${nytAPIKey}`

fetch(`${url}young-adult${apiKey}`)
    .then(response => response.json())
    .then(data => {
        console.log(data);
    })
    .catch(errors => console.log(errors))
