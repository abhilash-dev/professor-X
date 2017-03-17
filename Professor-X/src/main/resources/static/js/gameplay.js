/*
Onclick event for populating questions

Onclick event for Yes/No for final result

Include Google CSE

*/

//Populate first question and set cookie:

var question;
var count = 0;

window.onload = function () {
	//call API here
	fetchQuestion();
	
};

var submitanswer = function(){
	var radioselector = document.querySelector('input[name="answerchoice"]:checked').value;
	document.cookie = ""; //attach answer code 

	//call API

	submitanswer();

	fetchQuestion();

};

//API for question

var fetchQuestion = function(){
	//HTTP call and funtion to handle response
	count++;

	$.get("URL", function(data, status){
		alert("Data: " + data + "\nStatus: " + status);
		document.cookie = ""; // attach questionno and id. 
		$('#questionplaceholder').html(); //To add question content.
	});


	if(count==randomIntFromInterval(1,10)){
		$('#ourguess').attr("src","img/planb.jpg");
	}

	if(count==randomIntFromInterval(10,20)){
		$('#ourguess').attr("src","img/bulb.jpg");
	}

	if(count==20){
		weGuessed();
	}
	//if 20 questions are done then call google CSE function.
	
};

var confirmAnswer = function(){
	$.get("URL", function(data, status){
		alert("Data: " + data + "\nStatus: " + status);
	});
//call API
//Show appropriate message
};


//API for submit

var postAnswer(qId, aSelected) = function(){

	$.post('URL', {questionId: qId, answerSelected: aSelected}, 
		function(returnedData){
			console.log(returnedData);
		}).fail(function(){
			console.log("error");
		});
	};

	var weGuessed = function(){
	//call API

	var imgLink = googleCse(character);
	$('#ourguess').attr("src",imgLink);

	//based on API response get the image from google CSE.
};

var googleCse = function(character){
	var key = "AIzaSyAkrlCU06um54Zz2gvMHeJZTudq30xqPB4";
	var q = character; 
	var cx  = "007472085462375901923:a7ahjxglkne";
	var fileType = "png,jpg";
	var searchType = "image";
	var num = "1";
	var theUrl = "https://www.googleapis.com/customsearch/v1?q=" +q+ "&amp;cx=" +cx+ "&amp;fileType="+fileType+"&amp;num"+num+ "&amp;searchType="+searchType+"&amp;key"+key;
	$.get(urlKey, function(data){
		var imagelink = data.items.thumbnailLink;
	});
	return imagelink;
};

var randomIntFromInterval = function (min,max)
{
	return Math.floor(Math.random()*(max-min+1)+min);
};