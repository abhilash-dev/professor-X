var URL = "http://localhost:8080/game/";

var ques;
var quesId;
var quesNo;
var imagelink;

window.onload = function() {
	initSession();

	fetchQuestion();
};

var initSession = function() {
	$.get(URL + "init");
}

var fetchQuestion = function() {
	setTimeout(function() {
		$.get(URL+"question", function(data, status) {
			data = JSON.parse(data);
			ques = data.question.text;
			quesId = data.question.questionId;
			quesNo = data.questionCount;
			$("#qno").html(quesNo);
			$("#questionplaceholder").html(ques);
		});
	}, 500);
};

var submitanswer = function() {

	var selection = $('input[name="answerchoice"]:checked').val();
	$.post(URL + "answer/" + quesId + "/" + selection);
	if (quesNo <= 20){
		fetchQuestion();
	    if (randomIntFromInterval(1, 10) < 5) {
		    //$('#characterimage').attr("src", "../static/img/planb.png");
		    $('#characterimage').attr("th:src", "@{/img/planb.png}");
	    }else{
		    //$('#characterimage').attr("src", "../static/img/bulb.png");
		    $('#characterimage').attr("th:src", "@{/img/bulb.png}");
	    }
	}else{
		guess();
	}
};

var guess = function() {
	$.get(URL + "guess", function(data, status) {
		$('.questionpanel').hide();
		$('.afterguess').show();
		$("#charguess").html(data);
		setTimeout(function() {
			$("#charguess").html(data);
		}, 500);
		var guessImage = googleCse(data);
		console.log(guessImage);
		$('#ourguess').attr("src", guessImage);
	});

};

var randomIntFromInterval = function(min, max) {
	return Math.floor(Math.random() * (max - min + 1) + min);
};

var googleCse = function(character) {
	var key = "AIzaSyAkrlCU06um54Zz2gvMHeJZTudq30xqPB4";
	var q = character;
	var urlKey = "https://www.googleapis.com/customsearch/v1?q="
			+ q
			+ "&cx=007472085462375901923%3Aa7ahjxglkne&fileType=png%2Cjpg&num=1&searchType=image&key="
			+ key;
	$.get(urlKey, function(data) {
		imagelink = data.items[image].thumbnailLink;
		console.log(imagelink);
	});
	return imagelink;
};

var restart = function(){
	$.get(URL + "restart");
	initSession();
	fetchQuestion();
};