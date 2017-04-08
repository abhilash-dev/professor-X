var URL = "http://localhost:8080/game/";

var ques;
var quesId;
var quesNo;
var imagelink;
var characterID;

window.onload = function() {
    $('.questionpanel').show();
    	$('.afterguess').hide();
    	$('.thanks').hide();
    	$('.wrongAnswer').hide();

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
		$("#charguess").html(data.name);
		characterID = data.charId;
		googleCse(data.name);
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

var learn = function () {
	var newCharacter = $('input[name="newCharacter"]:checked').val();
	if(newCharacter > 0){
	    newCharacter = true;
	}else{
	    newCharacter = false;
	}
	var characterName = $('input[name="characterName"]:checked').val();
	if (characterName === undefined) {
		characterName = $('#characterName').val();
	}
	var userQuestionText = $('#userQuestionText').val();
	var userQuestionAnswer = $('input[name="userQuestionAnswer"]:checked').val();

	$.post(URL + "learn", {
		"newCharacter": newCharacter,
		"characterName": characterName,
		"userQuestionText": userQuestionText,
		"userQuestionAnswer": userQuestionAnswer
	});
	$(location).attr('href', '/');
};

var viewThanks = function () {
	$('.questionpanel').hide();
	$('.afterguess').hide();
	$('.thanks').show();
	$('.wrongAnswer').hide();
	$.post(URL + "guess",{
	    "characterId":characterID,
	    "finalAnswer":true
	});
};

var homeRedirect = function () {
	$(location).attr('href', '/');
};

var wrongAnswer = function () {
	$('.questionpanel').hide();
	$('.afterguess').hide();
	$('.thanks').hide();
	$('.wrongAnswer').show();
	$.get(URL + "learn",function (data, status) {
			data = JSON.parse(data);
			for (i = 0; i < 20; i++) {
				data[i].name = toTitleCase(data[i].name);
			}
			for (i = 0; i < 20; i++) {
                var id = "charid"+(i+1);
                var name = "char"+(i+1);

                $('#'+id).attr('value',data[i].charId);
                $('#'+name).html(data[i].name);
			}

		});
};

function toTitleCase(str) {
	return str.replace(/\w\S*/g, function (txt) { return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase(); });
};