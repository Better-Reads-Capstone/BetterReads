//Styling is found in nyt.css

//HTML : <div class="loading-icon mx-auto"></div>

//Loading Icon
function showLoadingIcon() {
    $(".loading-icon").removeAttr("style").addClass("show");
}
function hideLoadingIcon() {
    $(".loading-icon").css("display", "none");
}