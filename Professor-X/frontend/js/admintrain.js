//ACTUAL CODE
var URL = "http://localhost:8080/admin/";
window.onload = function () {
    fetchTrain();
}

var dataSet = [];
var createDataSet = function (data) {
    for (var i = 0; i < data.length; i++) {
        var temp = [];
        temp.push(data[i].charId);
        temp.push(data[i].name);
        temp.push(data[i].noOfTimesPlayed);
        temp.push(data[i].lastPlayedDateTime);
        dataSet.push(...temp);
    }
    var tableData = function () {
        var table = $('#allData').DataTable({
            'fnCreatedRow': function (nRow, aData, iDataIndex) {
                $(nRow).attr('id', 'my' + iDataIndex);
            },
            data: dataSet,
            columns: [
                { title: "charId" },
                { title: "name" },
                { title: "noOfTimesPlayed" },
                { title: "lastPlayedDateTime" }
            ]

        });

        $('#allData tbody').on('click', 'tr', function () {
            $(this).toggleClass('active');
        });

        $('#rowsel').click(function () {
            var dqArray = [];
            var idselected = table.rows('.active').data();

            var temp = idselected[0];
            dqArray.push(temp[0]);

            reTrain(dqArray);
        });
    };
};



var fetchTrain = function () {
    $.get(URL + "data", function (data, status) {
        createDataSet(data);
    });

};

var reTrain = function (train) {
    $.get(URL + "retrain/" + train);
    //  location.reload();
};

var redirectToQues = function () {
    location.href = "/adminques.html";
};

var redirectToChar = function () {
    location.href = "/adminchar.html";
}