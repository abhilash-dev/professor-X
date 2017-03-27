//ACTUAL CODE
var URL = "http://localhost:8080/admin/";
window.onload = function () {
    fetchQuestion();
}

var dataSet = [];
var createDataSet = function (data) {
    for (var i = 0; i < data.length; i++) {
        var temp = [];
        temp.push(data[i].questionId);
        temp.push(data[i].text);
        temp.push(data[i].createdDateTime);
        dataSet.push(...temp);
    }
    var tableData = function () {
        var table = $('#allData').DataTable({
            'fnCreatedRow': function (nRow, aData, iDataIndex) {
                $(nRow).attr('id', 'my' + iDataIndex);
            },
            data: dataSet,
            columns: [
                { title: "questionId" },
                { title: "text" },
                { title: "createdDateTime" }
            ]

        });

        $('#allData tbody').on('click', 'tr', function () {
            $(this).toggleClass('active');
        });

        $('#rowsel').click(function () {
            var dqArray = [];
            var idselected = table.rows('.active').data();
            for (var i = 0; i < idselected.length; i++) {
                var temp = idselected[i];
                dqArray.push(temp[0]);
            }

            //rIds Data Population
            deleteQuestions(dqArray);
        });
    };
};



var fetchQuestion = function () {
    $.get(URL + "dq", function (data, status) {
        createDataSet(data);
    });

};

var deleteQuestions = function (quesDel) {
    $.post(URL + "dq/" + quesDel);
    location.reload();
};

var redirectToChar = function () {
    location.href = "/adminchar.html";
};

var redirectToTrain = function () {
    location.href = "/admintrain.html";
}