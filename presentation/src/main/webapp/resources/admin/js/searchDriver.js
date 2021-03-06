/**
 * @author Katia Stetsiuk
 */
$(document).ready(function () {
    $("#search").on('input', function () {
        var keyUpTime = 2000;
        var keyUpTimeout = null;
        $("#search").on('input', function () {
            clearTimeout(keyUpTimeout);
            var $item = $(this),
                value = $item.val();
            var email = value;
            keyUpTimeout = setTimeout(function() { $.ajax({
                type: 'POST',
                url: 'searchdriver',
                data: {email: email},
                success: function(data){
                    var usersArray = JSON.parse(data);
                    var tBody = document.getElementById('table-body');
                    var rows = tBody.children;
                    var i;
                    for (i = 0; i < Math.max(rows.length,
					usersArray.length); i++) {
                        if (i >= rows.length) {
                            var newTr = document.createElement('tr');
                            //TODO Don't forget to change magic number of attribute tr count
                            for(var j = 0; j < 4; j++){
                                newTr.appendChild(document.createElement('td'));
                            }
                            tBody.appendChild(newTr);
                        }
                        if (i < usersArray.length){
                            var email = usersArray[i].email;
                                rows[i].children[0].innerHTML = '<button class="btn btn-default" value="' + usersArray[i].userId +
                                '" onclick="goToUserDash(this)" name="'+ email + '">' + email + '</button>';

                            rows[i].children[1].innerHTML = usersArray[i].phone != null? usersArray[i].phone : "-";
                            rows[i].children[2].innerHTML = usersArray[i].sex != null? usersArray[i].sex : "-";
                            rows[i].children[3].innerHTML = usersArray[i].groupName != null? usersArray[i].groupName : "-";

                        } else {
                            tBody.removeChild(rows[i]);
                        }
                    }
                }
            }); }, keyUpTime);

        });
    });
});

function goToUserDash(el) {
    var id = el.value;

    $.ajax({
        type: 'GET',
        url: 'userdash',
        data: {userIdAdmin: id},
        success: function() {
            window.location.replace("../driver/assigned-order");
        }
    });
}