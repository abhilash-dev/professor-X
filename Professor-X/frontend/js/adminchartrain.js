//ACTUAL CODE
//ACTUAL CODE
var dcArray = [];
var URL = "http://localhost:8080/admin/";
$(document).ready(function () {
    
    $.ajax({
        url: URL + 'retrain/' + sessionStorage["charTrain"], 
        success: function (response) {
            var table = $('#allData').DataTable({
                data: response.questionList,
                columns: [
                    { "questionId": "questionId" },
                    { "questionId": "text" },
                    { "createdDateTime": "createdDateTime" }
                ],
            });
            $('#allData tbody').on('click', 'tr', function () {
                $(this).toggleClass('active');
            });

            $('#rowsel').click(function () {

                var idselected = table.rows('.active').data();
                var temp = idselected[0];
                var trainChar = temp.charId;
                //rIds Data Population
                //reTrain(trainChar);
            });
        }
    });
});



var reTrain = function (train) {
   // $.get(URL + "retrain/" + train);
    if(train!=null)
    sessionStorage["charTrain"] = train;
    //  location.reload();
};

var redirectToQues = function () {
    location.href = "/adminques.html";
};

var redirectToChar = function () {
    location.href = "/adminchar.html";
}