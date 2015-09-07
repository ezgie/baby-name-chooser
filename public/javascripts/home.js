$(function(){
//    $.getJSON( "/names", function( json ) {
//      $.each(json, function (index, obj) {
//              $("#names").append('<li>' + obj.firstname + '</li>');
//          });
//    });

    $("#open-filters").click(function() {
      $( "#filters-content" ).slideToggle( 100 );
    });

    $(".letter").click(function() {
      $(this).toggleClass("selected");
    });

    $("#close-filters").click(function() {
        var queryParams = "";
        queryParams = updateQueryParams(queryParams, findLetters("firstLetter"), "fl" );
        queryParams = updateQueryParams(queryParams, findLetters("lastLetter"), "ll" );
        queryParams = updateQueryParams(queryParams, findLetters("contains"), "contains" );
        queryParams = updateQueryParams(queryParams, findLetters("notContains"), "notContains" );

        window.location.href = "/?" + queryParams;
    });

    var findLetters = function(idOfAlphabetContainer){
        return jQuery.map($("#"+idOfAlphabetContainer).find(".alphabet").find(".letter.selected"), extractText);
    }

    var extractText = function(letterSpan) {
        return $(letterSpan).data('letter');
    };

    var updateQueryParams = function(queryParams, lettersArray, queryParamName){
        $.each(lettersArray, function(index, value){
            queryParams = queryParams + queryParamName + "=" + value + "&";
        });
        return queryParams;
    }
});