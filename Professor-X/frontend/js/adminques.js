//ACTUAL CODE
var dqArray = [];
var URL = "http://localhost:8080/admin/";
$(document).ready(function () {
    $.ajax({
        url: 'http://localhost:8080/admin/dq',
        success: function (response) {
            var table = $('#allData').DataTable({
                data: response,
                columns: [
                    { "data": "questionId" },
                    { "data": "text" },
                    { "data": "createdDateTime" }
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
                    dqArray.push(temp.questionId);
                }
                //rIds Data Population
              //  deleteClick();
            });
        }
    });
});


var deleteClick = function () {
    deleteQuestions(dqArray);
};

var deleteQuestions = function (quesDel) {
    //quesDel=quesDel.serialize();
    if(quesDel!=""){
        quesDel = {questionIds:quesDel}
        quesDel = JSON.stringify(quesDel);
       var fd = new FormData();
        console.log(quesDel);
    fd.append('questionIds',quesDel);
    console.log(fd);
    $.ajax({
  url: 'http://localhost:8080/admin/dq',
  data: fd,
  processData: false,
  contentType: false,
  type: 'POST',
  success: function(data){
    location.reload();
  }
}); 
    }
    
   
};

var redirectToChar = function () {
    location.href = "/adminchar.html";
};

var redirectToTrain = function () {
    location.href = "/admintrain.html";
}
