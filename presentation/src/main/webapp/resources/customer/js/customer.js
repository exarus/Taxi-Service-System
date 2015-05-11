/**
 * Created by Vadym Akymov on 29.04.15.
 */

var dataType = $('.active').val();
console.log('dataType value: ' + dataType);

//click on old button
$('.completed').click(function(){
    window.location.href = '?type=completed';
});
//click on active button
$('.act').click(function(){
    window.location.href = '?type=active';
});

//temporary
var pNumber = $('.pageNumber').val();
console.log("pageNumber: " + pNumber);
var pCount = $('.pagesCount').val();
console.log("pageCount: " + pCount);

//if there is one page of orders
if(pCount == 1){
    $('.nextButton').attr('disabled','disabled');
    $('.prevButton').attr('disabled','disabled');
}

$('.nextButton').click(function(){
    var pageNumber = $('.pageNumber').val();
    var pagesCount = $('.pagesCount').val();
    if((pageNumber + 1) >= pagesCount){
        $('.nextButton').attr('disabled','disabled');
    } else {
        $('.nextButton').removeAttr('disabled');
    }
    if((pageNumber - 1) < 1){
        $('.prevButton').attr('disabled','disabled');
    } else {
        $('.prevButton').removeAttr('disabled');
    }
    $.ajax({
        type: 'GET',
        url: 'customer/get-orders',
        data: 'pageNumber=' + (++pageNumber) + '&type=' + dataType,
        success: function(data){
            $('.pageNumber').val(pageNumber);
            var ordersArray = JSON.parse(data);
            for(var i = 0; i < ordersArray.length; i++){
                $('.order' + i).text('Order № ' + ordersArray[i].trackingNumber);
                $('.service' + i).html('<b>SERVICE:</b> ' + ordersArray[i].service);
                $('.status' + i).html('<b>STATUS:</b> ' + ordersArray[i].status);
                $('.price' + i).html('<b>PRICE:</b> ' + ordersArray[i].price.toFixed(2) + ' grn');
                var orderedDate = new Date(ordersArray[i].orderedDate);
                $('.date' + i).html('<b>DATE:</b> ' + formatDate(orderedDate));
            }
        }
    });
});


//Previous button click
$('.prevButton').click(function(){
    console.log('dataType ' + dataType);
    var pageNumber = $('.pageNumber').val();
    var pagesCount = $('.pagesCount').val();
    if((pageNumber - 1) <= 1){
        $('.prevButton').attr('disabled','disabled');
    } else {
        $('.prevButton').removeAttr('disabled');
    }
    if((pageNumber + 1) > pagesCount){
        $('.nextButton').attr('disabled','disabled');
    } else {
        $('.nextButton').removeAttr('disabled');
    }
    $.ajax({
        type: 'GET',
        url: 'customer/get-orders',
        data: 'pageNumber=' + (--pageNumber) + '&type=' + dataType,
        success: function(data){
            $('.pageNumber').val(pageNumber);
            var ordersArray = JSON.parse(data);
            for(var i = 0; i < ordersArray.length; i++){
                $('.order' + i).text('Order № ' + ordersArray[i].trackingNumber);
                $('.service' + i).html('<b>SERVICE:</b> ' + ordersArray[i].service);
                $('.status' + i).html('<b>STATUS:</b> ' + ordersArray[i].status);
                $('.price' + i).html('<b>PRICE:</b> ' + ordersArray[i].price.toFixed(2) + ' grn');
                var orderedDate = new Date(ordersArray[i].orderedDate);
                $('.date' + i).html('<b>DATE:</b> ' + formatDate(orderedDate));
            }
        }
    });
});

//tracking number href
$('.track').click(function(){
    var trackingNumber = $('.trackingNumber').val();
    alert("GET doesn't allowed!!")
});

function formatDate(d) {

    var dd = d.getDate();
    if ( dd < 10 ) dd = '0' + dd;

    var mm = d.getMonth()+1;
    if ( mm < 10 ) mm = '0' + mm;

    var yy = d.getFullYear();
    //if ( yy < 10 ) yy = '0' + yy;

    return dd+'-'+mm+'-'+yy
}