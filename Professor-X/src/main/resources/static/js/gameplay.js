var URL = "http://localhost:8080/game/";
var ques;
var count = 0;
var quesId;
var quesNo;
var imagelink;
var selection = 1;

window.onload = function() {

	$('.questionpanel').show();
	$('.afterguess').hide();
	$('.thanks').hide();
	$('.wrongAnswer').hide();

	initSession();
	fetchQuestion();
};

var initSession = function() {
	$.get(URL + "init");
}

var fetchQuestion = function() {
	count++;
	setTimeout(function() {
		$.get(URL + "question", function(data, status) {
			data = JSON.parse(data);
			ques = data.question.text;
			quesId = data.question.questionId;
			quesNo = data.questionCount;
			console.log("QNO. " + quesNo);
			$("#qno").html(quesNo);
			$("#questionplaceholder").html(ques);
		});
	}, 500);
};

var submitanswer = function() {

	selection = $('input[name="answerchoice"]:checked').val();
	if (selection === undefined) {
		selection = 1;
	}
	$.post(URL + "answer/" + quesId + "/" + selection);
	console.log("Count: ", count)
	if (count < 20)
		fetchQuestion();
	if (count == randomIntFromInterval(1, 10)) {
		$('#characterimage').attr("src", "../static/img/planb.jpg");
		$('#characterimage').attr("th:src", "@{/img/planb.png}");
	}
	if (count == randomIntFromInterval(10, 20) && count != 20) {
		$('#characterimage').attr("src", "../static/img/bulb.jpg");
		$('#characterimage').attr("th:src", "@{/img/bulb.png}");
	} else if (count == 20) {
		guess();
	}
};

var guess = function() {
	$('.questionpanel').hide();
	$('.afterguess').show();
	$('.thanks').hide();
	$('.wrongAnswer').hide();
	$.get(URL + "guess", function(data, status) {
		$("#charguess").html(data);
		setTimeout(function() {
			$("#charguess").html(data);
		}, 500);
		var guessImage = googleCse(data);
		console.log(guessImage);
		$('#ourguess').attr("src", guessImage);
		// $('#ourguess').attr("th:src", "@{/img/planb.png}");
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

var restart = function() {
	$.get(URL + "restart");
	initSession();
	fetchQuestion();
};

var learn = function() {
	var newCharacter = $('input[name="newCharacter"]:checked').val();
	var characterName = $('input[name="characterName"]:checked').val();
	if (characterName === undefined) {
		characterName = $('#characterName').val();
	}
	var userQuestionText = $('#userQuestionText').val();
	var userQuestionAnswer = $('input[name="userQuestionAnswer"]:checked')
			.val();
	$.post(URL + "learn", {
		newCharacter : newCharacter,
		characterName : characterName,
		userQuestionText : userQuestionText,
		userQuestionAnswer : userQuestionAnswer
	});
	$(location).attr('href', 'http://localhost:8080/index');
};

var viewThanks = function() {
	$('.questionpanel').hide();
	$('.afterguess').hide();
	$('.thanks').show();
	$('.wrongAnswer').hide();
	$.post(URL + "guess");
};

var pageReload = function() {
	location.reload();
};

var homeRedirect = function() {
	$(location).attr('href', 'http://localhost:8080/index');
};

var wrongAnswer = function() {
	$('.questionpanel').hide();
	$('.afterguess').hide();
	$('.thanks').hide();
	$('.wrongAnswer').show();
	$
			.get(
					URL + "learn",
					function(data, status) {
						data = JSON.parse(data);
						for (i = 0; i < 20; i++) {
							var radioBtn = $('<div> <div class="radio-option"> <div class="inner"></div> <input type="radio" name="characterName" value='
									+ data[i]
									+ '/></div><span class="fontcolour">'
									+ data[i] + '</span></div> ');
							radioBtn.appendTo('.radioTarget');
						}
					});
};