/*
Onclick event for populating questions

Onclick event for Yes/No for final result

Include Google CSE

 */

//Populate first question and set cookie:
var URL = "http://localhost:8080/game/";

var ques;
var count = 0;
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
	count++;
	setTimeout(function() {
		$.get(URL, function(data, status) {
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
	if (count <= 20)
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

var restart = function(){
	$.get(URL + "restart");
	initSession();
	fetchQuestion();
};

/*
 * var submitanswer = function(){ var radioselector =
 * document.querySelector('input[name="answerchoice"]:checked').value;
 * document.cookie = ""; //attach answer code
 * 
 * //call API
 * 
 * submitanswer();
 * 
 * fetchQuestion(); };
 * 
 * //API for question
 * 
 * var fetchQuestion = function(){ //HTTP call and funtion to handle response
 * count++;
 * 
 * $.get("URL", function(data, status){ alert("Data: " + data + "\nStatus: " +
 * status); document.cookie = ""; // attach questionno and id.
 * $('#questionplaceholder').html(); //To add question content. });
 * 
 * 
 * if(count==randomIntFromInterval(1,10)){
 * $('#ourguess').attr("src","img/planb.jpg"); }
 * 
 * if(count==randomIntFromInterval(10,20)){
 * $('#ourguess').attr("src","img/bulb.jpg"); }
 * 
 * if(count==20){ weGuessed(); } //if 20 questions are done then call google CSE
 * function. };
 * 
 * var confirmAnswer = function(){ $.get("URL", function(data, status){
 * alert("Data: " + data + "\nStatus: " + status); }); //call API //Show
 * appropriate message };
 * 
 * 
 * //API for submit
 * 
 * var postAnswer = function(qId, aSelected){
 * 
 * $.post('URL', {questionId: qId, answerSelected: aSelected},
 * function(returnedData){ console.log(returnedData); }).fail(function(){
 * console.log("error"); }); };
 * 
 * var weGuessed = function(){ //call API
 * 
 * var imgLink = googleCse(character); $('#ourguess').attr("src",imgLink);
 * 
 * //based on API response get the image from google CSE. };
 * 
 * 
 */