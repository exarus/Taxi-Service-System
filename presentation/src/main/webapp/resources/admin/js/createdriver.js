/**
 * Created by kstes_000 on 25-Apr-15.
 */
$(document).ready(function () {


        $('#createDriver').validate({
        rules: {
            email: {
                required: true,
                email:true
            },
            password: {
                required: true,
                minlength:5
            },
            confirmpassword: {
                required: true,
                minlength:5,
                equalTo: "#password"
            },
            phone: {
                required: true,
                minlength:9
            }
        }
    });

    $(".dropdown-menu").on('click', 'li a', function(){
        $(".btn-sm:first-child").text($(this).text());
        $(".btn-sm:first-child").val($(this).text());
     });


    var data = {};

    $('#addDriver').bind('click', function () //noinspection UnterminatedStatementJS
{


        data["email"] = $('input[name = email]', '#createDriver').val();
        data["password"] = $('input[name = password]', '#createDriver').val();
        data["phone"] = $('input[name = phone]', '#createDriver').val();
        data["car"] =$('#make').val();


 data = JSON.stringify(data);
        $.ajax({
            type: 'POST',
            url: 'createdriver',
            dataType: 'json',
            data: 'data=' + data
        });
    });
});