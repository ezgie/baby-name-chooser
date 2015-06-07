$(function(){
    $.getJSON("/names", function( json ) {
      $.each(json, function (index, obj) {
              $("#names").append('<li>' + obj.firstname + '</li>');
          });
    });
});