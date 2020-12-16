// Make sure to put your personal FileStackKey in the keys.js file
console.log("hello");
const client = filestack.init(filestackAPIKey);
client.picker().open();

// we will pass this object as an argument in the picker method.
const options = {
    onFileUploadFinished: callback =>{
        const imgURL = callback.url;
        $('#image').val(imgURL);
        $('#imagePreview').attr('src',imgURL);
    }
}
// This is an event listen for listening to a click on a button
$('#addPicture').click(function (event){
    event.preventDefault();

    //we use this to tell filestack to open their file picker interface.
    // the picker method can take an argument of a options object
    // where you can specify what you want the picker to do
    client.picker(options).open();
})