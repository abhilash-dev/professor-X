var URL = "http://localhost:8080/game/";

var ques;
var quesId;
var quesNo;
var imagelink;

window.onload = function() {
	initSession();
    setTimeout(function() {
	    fetchQuestion();
    }, 500);
};

var initSession = function() {
	$.get(URL + "init");
}

var fetchQuestion = function() {
		$.get(URL+"question", function(data, status) {
			data = JSON.parse(data);
			ques = data.question.text;
			quesId = data.question.questionId;
			quesNo = data.questionCount;
			$("#qno").html(quesNo);
			$("#questionplaceholder").html(ques);
		});
};

var submitanswer = function() {

	var selection = $('input[name="answerchoice"]:checked').val();
	$.when($.post(URL + "answer/" + quesId + "/" + selection)).then(function(){
	    if (quesNo < 20){
            fetchQuestion();
        }else{
        	guess();
        }
	});
};

var guess = function() {
	$.get(URL + "guess", function(data, status) {
		$('.questionpanel').hide();
		$('.afterguess').show();
		$("#charguess").html(data);
		googleCse(data);
	});
};

var googleCse = function(character) {
	var key = "AIzaSyAkrlCU06um54Zz2gvMHeJZTudq30xqPB4";
	var q = character;
	var urlKey = "https://www.googleapis.com/customsearch/v1?q="
			+ q
			+ "&cx=007472085462375901923%3Aa7ahjxglkne&fileType=png%2Cjpg&num=1&searchType=image&key="
			+ key;
	var imagelink = "";
	$.get(urlKey, function(data) {
		imagelink = data.items[0].link;
		console.log(character+" Image Link = "+imagelink);
		var image = "src=@{"+imagelink+"}";
        $('#ourguess').attr("th:attr", image);
        window.open(imagelink,'_blank');
	});

}

var restart = function(){
    window.location.reload(true);
};