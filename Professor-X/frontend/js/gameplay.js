var URL = "http://localhost:8080/game/";
var ques;
var count = 0;
var quesId;
var quesNo;
var imagelink;
var selection = 1;
var i;

window.onload = function () {

	$('.questionpanel').show();
	$('.afterguess').hide();
	$('.thanks').hide();
	$('.wrongAnswer').hide();

	initSession();
	fetchQuestion();
};

var initSession = function () {
	$.get("http://localhost:8080/game/init");
}

var fetchQuestion = function () {
	setTimeout(function () {
		$.get("http://localhost:8080/game/question", function (data, status) {
			data = JSON.parse(data);
			ques = data.question.text;
			quesId = data.question.questionId;
			quesNo = data.questionCount;
			console.log("QNO. " + quesNo);
			$("#qno").html(quesNo);
			$("#questionplaceholder").html(ques);
		});
	}, 5000);
};

var submitanswer = function () {

	selection = $('input[name="answerchoice"]:checked').val();
	if (selection === undefined) {
		selection = 1;
	}
	$.post(URL + "answer/" + quesId + "/" + selection);
	if (quesNo < 20) {
		fetchQuestion();
	}

	if (count == randomIntFromInterval(1, 10)) {
		$('#characterimage').attr("src", "img/planb.jpg");
	}
	if (count == randomIntFromInterval(10, 20) && count != 20) {
		$('#characterimage').attr("src", "img/bulb.jpg");
	} else if (quesNo == 20) {
		guess();
	}
};

var guess = function () {
	$('.questionpanel').hide();
	$('.afterguess').show();
	$('.thanks').hide();
	$('.wrongAnswer').hide();
	$.get(URL + "guess", function (data, status) {
		$("#charguess").html(data);
		setTimeout(function () {
			$("#charguess").html(data);
		}, 500);
		//var guessImage = googleCse(data);
		//console.log(guessImage);
		//$('#ourguess').attr("src", guessImage);
		// $('#ourguess').attr("th:src", "@{/img/planb.png}");
	});

};

var randomIntFromInterval = function (min, max) {
	return Math.floor(Math.random() * (max - min + 1) + min);
};

var googleCse = function (character) {
	var key = "AIzaSyAkrlCU06um54Zz2gvMHeJZTudq30xqPB4";
	var q = character;
	var urlKey = "https://www.googleapis.com/customsearch/v1?q="
		+ q
		+ "&cx=007472085462375901923%3Aa7ahjxglkne&fileType=png%2Cjpg&num=1&searchType=image&key="
		+ key;
	$.get(urlKey, function (data) {
		imagelink = data.items[image].thumbnailLink;
		console.log(imagelink);
	});
	return imagelink;
};

var restart = function () {
	$.get(URL + "restart");
	initSession();
	fetchQuestion();
};

var learn = function () {
	var newCharacter = $('input[name="newCharacter"]:checked').val();
	var characterName = $('input[name="characterName"]:checked').val();
	if (characterName === undefined) {
		characterName = $('#characterName').val();
	}
	var userQuestionText = $('#userQuestionText').val();
	var userQuestionAnswer = $('input[name="userQuestionAnswer"]:checked').val();

	$.post(URL + "learn", {
		newCharacter: newCharacter,
		characterName: characterName,
		userQuestionText: userQuestionText,
		userQuestionAnswer: userQuestionAnswer
	});
	$(location).attr('href', '/index.html');
};

var viewThanks = function () {
	$('.questionpanel').hide();
	$('.afterguess').hide();
	$('.thanks').show();
	$('.wrongAnswer').hide();
	$.post(URL + "guess");
};

var pageReload = function () {
	location.reload();
};

var homeRedirect = function () {
	$(location).attr('href', '/index.html');
};

var wrongAnswer = function () {
	$('.questionpanel').hide();
	$('.afterguess').hide();
	$('.thanks').hide();
	$('.wrongAnswer').show();
	$
		.get(
		URL + "learn",
		function (data, status) {
			data = JSON.parse(data);
			for (i = 0; i < 20; i++) {
				data[i].name = toTitleCase(data[i].name);
			}
			for (i = 0; i < 20; i++) {
				var radioBtn = $('<div> <div class="radio-option"><div class="inner"></div><input type="radio" name="characterName" value=' + data[i].charId + '></div><span class="fontcolour" id="spanspace">' + "   " + data[i].name + '</span></div>');
				radioBtn.appendTo('.radioTarget');
			}
			$('.radioTarget').append("<br />");
		});
};

function toTitleCase(str) {
	return str.replace(/\w\S*/g, function (txt) { return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase(); });
};