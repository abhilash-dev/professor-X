/*
Onclick event for populating questions

Onclick event for Yes/No for final result

Include Google CSE

*/

//Populate first question and set cookie:

var question;

window.onload = function () {
	//call API here
	fetchQuestion();
	
};

var submitanswer = function(){
	var radioselector = document.querySelector('input[name="answerchoice"]:checked').value;
	document.cookie = ""; //attach answer code
	fetchQuestion();
	//call API
};

//API for question

var fetchQuestion = function(){
	//HTTP call and funtion to handle response

	document.cookie = ""; // attach questionno and id. 
	$('#questionplaceholder').html(); //To add question content.
	//if 20 questions are done then call google CSE function.
	weGuessed();
}

var confirmAnswer = function(){
//call API
//Show appropriate message
}

var weGuessed = function(){
	//call API

	//based on API response get the image from google CSE.
}

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
}