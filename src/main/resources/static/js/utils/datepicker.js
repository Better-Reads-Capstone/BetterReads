let curYear = new Date().getFullYear();
let picker = new Pikaday({
    field: document.getElementById('dob'),
    yearRange: [1900, curYear],
    format: 'YYYY-MM-DD',
    toString(date, format) {
        const day = date.getDate();
        let month = date.getMonth() + 1;
        const year = date.getFullYear();
        if (month < 10) {
            month = '0' + month.toString();
        }
        return `${year}-${month}-${day}`;
    }
    })