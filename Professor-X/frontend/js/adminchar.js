//ACTUAL CODE
var dcArray = [];
var URL = "http://localhost:8080/admin/";
$(document).ready(function () {
    $.ajax({
        url: URL + 'dc',
        success: function (response) {
            var table = $('#allData').DataTable({
                data: response,
                columns: [
                    { "data": "charId" },
                    { "data": "name" },
                    { "data": "createdDateTime" },
                    { "data": "noOfTimesPlayed" },
                    { "data": "lastPlayedDateTime" }
                ],
            });
            $('#allData tbody').on('click', 'tr', function () {
                $(this).toggleClass('active');
            });

            $('#rowsel').click(function () {

                var idselected = table.rows('.active').data();
                for (var i = 0; i < idselected.length; i++) {
                    var temp = idselected[i];
                    //console.log(temp);
                    dcArray.push(temp.charId);
                }
                //rIds Data Population
            //    deleteClick();
            });
        }
    });
});


var deleteClick = function () {
    deleteChar(dcArray);
};

var deleteChar = function (charDel) {
    console.log(charDel);
    charDel = {characterIds:charDel}
        charDel = JSON.stringify(charDel);
     var fd = new FormData();
    fd.append('characterIds',charDel);
    console.log(fd);
    $.ajax({
  url: 'http://localhost:8080/admin/dc',
  data: fd,
  processData: false,
  contentType: false,
  type: 'POST',
  success: function(data){
    location.reload();
  }
});
};

var redirectToQues = function () {
    location.href = "/adminques.html";
};

var redirectToTrain = function () {
    location.href = "/admintrain.html";
}