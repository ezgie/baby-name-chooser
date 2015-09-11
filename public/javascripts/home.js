$(function(){
//    $.getJSON( "/names", function( json ) {
//      $.each(json, function (index, obj) {
//              $("#names").append('<li>' + obj.firstname + '</li>');
//          });
//    });

    $("#openFilters").click(function() {
      $( "#filters-content" ).slideToggle( 100 );
    });

    $(".letter").click(function() {
      $(this).toggleClass("selected");
    });

    $("#resetFilters").click(function(){
        jQuery.map($("span.letter"), function(element){$(element).removeClass("selected")});
        $("div#containingText > input").val("");
        jQuery.map($("input[name='gender']:checked"), function(element){$(element).prop("checked", false)});
    });

    $("#closeFilters").click(function() {
        var queryParams = "";
        queryParams = updateQueryParams(queryParams, findLetters("firstLetter"), "fl" );
        queryParams = updateQueryParams(queryParams, findLetters("lastLetter"), "ll" );
        queryParams = updateQueryParams(queryParams, findLetters("contains"), "contains" );
        queryParams = updateQueryParams(queryParams, findLetters("notContains"), "notContains" );

        queryParams = addToQueryParam(queryParams, $("div#containingText > input").val(), "containsText" );
        queryParams = updateQueryParams(queryParams, getSelectedGenders(), "gender" );
        window.location.href = "/?" + queryParams;
    });

    var getSelectedGenders = function() {
        return jQuery.map(
            $("input[name='gender']:checked"),
            function(genderElement) {
                return $(genderElement).val();
            });
    }

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

    var addToQueryParam = function(queryParams, value, queryParamName){
        return queryParams + queryParamName + "=" + value + "&";
    }
});