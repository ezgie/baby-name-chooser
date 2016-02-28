$(function(){

    $("#openFilters").click(function() {
      $( "#filters-content" ).slideToggle( 100 );
    });

    $(".letter").click(function() {
      $(this).toggleClass("selected");
    });

    $("#firstLetter .letter").click(deselectOnlyEnglishIfTurkishLetterSelected);
    $("#lastLetter .letter").click(deselectOnlyEnglishIfTurkishLetterSelected);
    $("#contains .letter").click(deselectOnlyEnglishIfTurkishLetterSelected);
    $("#notContains .letter").click(deselectOnlyEnglishIfTurkishLetterSelected);

    function deselectOnlyEnglishIfTurkishLetterSelected(){
        if($.inArray($(this).data('letter'), ['ç', 'ğ', 'ı', 'ö', 'ş', 'ü']) != -1) {
            $('#onlyEnglish').attr('checked', false);
          }
    }

    $("#resetFilters").click(function(){
        jQuery.map($("span.letter"), function(element){$(element).removeClass("selected")});
        $("div#containingText > input").val("");
        jQuery.map($("input[name='gender']:checked"), function(element){$(element).prop("checked", false)});
        $('#origin select').val("");
    });

    $("#closeFilters").click(function() {
        var queryParams = "";
        queryParams = updateQueryParams(queryParams, findLetters("firstLetter"), "fl" );
        queryParams = updateQueryParams(queryParams, findLetters("lastLetter"), "ll" );
        queryParams = updateQueryParams(queryParams, findLetters("contains"), "contains" );
        queryParams = updateQueryParams(queryParams, findLetters("notContains"), "notContains" );

        queryParams = addToQueryParam(queryParams, $("div#containingText > input").val(), "containsText" );
        queryParams = updateQueryParams(queryParams, getSelectedGenders(), "gender" );
        queryParams = updateQueryParams(queryParams, getSelectedOrigins(), "origin" );
        window.location.href = "/?" + queryParams;
    });

    var getSelectedGenders = function() {
        return jQuery.map(
            $("input[name='gender']:checked"),
            function(genderElement) {
                return $(genderElement).val();
            });
    }

    var getSelectedOrigins = function() {
        return $('#origin select option:selected').map(function() {return this.value })
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

    $('#onlyEnglish').change(function() {
        if($(this).is(":checked")) {
            jQuery.map($('#firstLetter span.letter, #lastLetter span.letter, #contains span.letter')
                .filter('[data-letter="ç"], [data-letter="ğ"], [data-letter="ı"], [data-letter="ö"], [data-letter="ş"], [data-letter="ü"]'),
                function(element){$(element).removeClass("selected")});

            jQuery.map($('#notContains span.letter')
                .filter('[data-letter="ç"], [data-letter="ğ"], [data-letter="ı"], [data-letter="ö"], [data-letter="ş"], [data-letter="ü"]'),
                function(element){$(element).addClass("selected")});

            var containingText = $("div#containingText > input");
            if(containsTurkishCharacter(containingText.val())) {
                containingText.val("");
            }
        };
    });

    function containsTurkishCharacter(text) {
        return (text.indexOf('ç') > -1
            || text.indexOf('ğ') > -1
            || text.indexOf('ı') > -1
            || text.indexOf('ö') > -1
            || text.indexOf('ş') > -1
            || text.indexOf('ü') > -1);
    }
});