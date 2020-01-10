$(function() {

    var $name = $('#tag_name');
    var $description = $('#description');
    function addTag(TagEachRow) {
        // var $DeleteButton = '<button class="delete_btn">X</button>';
        // $TagDisplay.append('<li>'+TagEachRow.tag_name +', '+ TagEachRow.description + $DeleteButton+'</li>');
        var rowCnt = $('tag_database tr').length;
        var addDelete = '<td><button class="delBtn" tagId="'+ TagEachRow.tag_name +'">X</button></td>';
        $('#tag_database tr:last').after('<tr><td>'+TagEachRow.tag_name+'</td><td>'+TagEachRow.description +'</td>'+addDelete+'</tr>');
    }

    $.ajax({
        type: "GET",
        url: "/tag/all",
        success: function(tags) {
            $.each(tags, function(idx, tag) {
                // from backend, it will convert all data into a list of json string
                // var checkDelButton =
                var TagJson = JSON.parse(tag);
                addTag(TagJson);

            })
        },
        error: function() {
            alert("Error Loading");
        }
    });


    $(document).on('click', '#tag_database tbody tr td button.delBtn',function() {
        var Tag_Id = $(this).attr('tagId');
        alert(Tag_Id);
    }) ;

    // add new Tag in /tag
    $('#add_newTag').on('click', function() {
        // add input into an object
        var tag = {
            tag_name: $name.val(),
            description: $description.val(),
        };

        if (!tag.tag_name) {
            alert("You need to input tagname!");
        } else {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/tag/add",
                data: JSON.stringify(tag), // convert var tag into json

                success: function(newTag) {
                    if(newTag!="Nothing update") {
                        var newTagJson = JSON.parse(newTag);
                        addTag(newTagJson);
                        window.location.reload(); // refresh current window url webpage
                    } else {
                        alert("Tag already exist");
                    }

                },
                error: function(newTag) {
                    alert("Error Loading!");
                }
            })
        }


    });


});


function deleteRow(tag_name) {
    var DelRow = {
        tag_name: tag_name,
        description: "ready to delete"
    };

    $.ajax({
        type: "POST",
        url: "/tag/delete/" + $(this).attr('tagId'),
        success: function(ReturnStr) {
            if(ReturnStr=="Delete Success") {
                window.location.reload();
            } else {
                alert("Process error, try again");
            }
        }
    })
}