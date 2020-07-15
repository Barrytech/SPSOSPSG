

function myFunction() {
  // Declare variables
  var input, filter, ul, li, a, i;
  input = document.getElementById("mySearch");
  filter = input.value.toUpperCase();
  ul = document.getElementById("myMenu");
  li = ul.getElementsByTagName("li");

  // Loop through all list items, and hide those who don't match the search query
  for (i = 0; i < li.length; i++) {
    a = li[i].getElementsByTagName("a")[0];
    if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
      li[i].style.display = "";
    } else {
      li[i].style.display = "none";
    }
  }
}

//once new thread clicked
$(document).ready(function() {
    //onload() comment form is hidden
    $("#hidden").hide();
    $("#cmt").hide();
    $("#newthread").click(function() {
        $('#hidden').show();
    });


     $('input[type="radio"]').click(function() {
             if($(this).attr('id') == 'peopleinvolvedyes') {
                  $('#involvement-section').show();
             }

             else {
                  $('#involvement-section').hide();
             }
         });
//adds another comment on the page
      $("#addanother").click(function(){
           $("#container").append($("#cmt"));
        // $("#container").append('<div id="cmt"> <div class="row"><div class="col-md-1"></div><div class ="col-md-9"><textarea required placeholder="Description of the document" class="form-control" name="description-of-incident" id="comments" rows="1"> </textarea></div>  <div class ="col-md-2" id="uploadfile"><button> <img id="up" src="upload.png"/></button></div>    <br><br><div class ="row"> <button type="submit" class="btn btn-primary" id="submitbut"> Submit </button></div> <div class="help-block with-errors"></div></div></div><a href="javascript:void(0);" class="remove-document-upload">Remove</a></div></div>');
      
      });

      $(".remove-document-upload").click(function(){
      
        $(this).closest('.addanother').remove();
      });



      


});