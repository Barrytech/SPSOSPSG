

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

      $("#addanother").click(function(){
        $("#container").append('<div class="form-group"><label class="control-label col-md-4">File upload</label><div class="col-md-6"><input type="file" /><textarea required placeholder="Description of the document" class="form-control" name="description-of-incident" id="description-of-incident" rows="2"></textarea><div class="help-block with-errors"></div><a href="javascript:void(0);" class="remove-document-upload">Remove</a></div></div>');
      });

      $(".remove-document-upload").click(function(){
          alert("dasdasdas");
        //$(this).closest('.addanother').remove();
      });


    //   $("#addanother").click(function(){
    //     $("#container").append($("#cmt"));
    //   });
      
    $(document).on('click', ".remove-document-upload", function() {
        alert("dasdasdas");

    });

});