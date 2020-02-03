
// any global variables needed
let opponents;

$(document).ready(function(){
  $("#new-game-button").click(function(){
    $("#dropdown-select").slideDown("slow");
  });
});


function newGameAndSetPlayers(){
	selectPlayers();
	newGame();
}

function selectPlayers() {
  var x = document.getElementById("numberOfOpponents").selectedIndex;
  //alert(document.getElementsByTagName("option")[x].value);
  setPlayers(document.getElementsByTagName("option")[x].value);
  window.location.href = '/toptrumps/game/';
}

function setPlayers(int){
	// First create a CORS request, this is the message we are going to send (a get request in this case)
	var xhr = createCORSRequest('POST', "http://localhost:7777/toptrumps/game/setPlayers?players="+int); // Request type and URL+parameters
	
	opponents = int;
	
	// Message is not sent yet, but we can check that the browser supports CORS
	if (!xhr) {
			alert("CORS not supported");
	}
	// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
	// to do when the response arrives 
	xhr.onload = function(e) {
			var responseText = xhr.response; // the text of the response
		//alert(responseText + " TEST"); // lets produce an alert
	};
	// We have done everything we need to prepare the CORS request, so send it
	xhr.send();
}

function unHideBoard(){
	var x = document.getElementById("game-board");
	if (x.style.visibility === "visible") {
	  x.style.visibility = "hidden";
	} else {
		alert("making " + opponents + " columns");
		x.style.gridTemplateColumns = '1fr 20px 1fr';
	  	x.style.visibility = "visible";
	  	/*
	  	var columns = "1fr 1fr";
	  	var i;
	  	var z = getPlayers();
	  	for(i = 0; i < z; i++){
	  		//alert("i = " + i);
	  		columns += " 1fr"
	  	}

	  	x.style.gridTemplateColumns = columns;
	  	*/
	}
}

function newGame(){
	// First create a CORS request, this is the message we are going to send (a get request in this case)
	var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/game/newGame"); // Request type and URL+parameters
	
	// Message is not sent yet, but we can check that the browser supports CORS
	if (!xhr) {
			alert("CORS not supported");
	}

	// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
	// to do when the response arrives 
	xhr.onload = function(e) {
			var responseText = xhr.response; // the text of the response
		//alert(responseText + " TEST"); // lets produce an alert
	};
	
	// We have done everything we need to prepare the CORS request, so send it
	xhr.send();
}

function loadTable(){
	getStats();
}

function getStats(){
// First create a CORS request, this is the message we are going to send (a get request in this case)
var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getStats"); // Request type and URL

// Message is not sent yet, but we can check that the browser supports CORS
if (!xhr) {
		alert("CORS not supported");
}

// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
// to do when the response arrives 
xhr.onload = function(e) {
		var responseText = xhr.response; // the text of the response
		//alert("1 - " + responseText);
		var r = responseText;
		var string = r.replace('[', '').replace(']', '');
		var array = string.split(",");
		var i;
		var output = "<ul>";
		for(i = 0; i < array.length; i++){
			output += "<li>" + array[i] + "</li>";
		}
		output += "</ul>";
	document.getElementById('tablePlaceholder').innerHTML = output// Changing element innerHTML from function - works
	//putStatsInTable(responseText);
	//return responseText;
};

// We have done everything we need to prepare the CORS request, so send it
xhr.send();
}

// Method that is called on page load
function initalize() {

	// --------------------------------------------------------------------------
	// You can call other methods you want to run when the page first loads here
	// --------------------------------------------------------------------------
	
	// For example, lets call our sample methods
	helloJSONList();
	helloWord("Student");
	
}

// -----------------------------------------
// Add your other Javascript methods Here
// -----------------------------------------

// This is a reusable method for creating a CORS request. Do not edit this.
function createCORSRequest(method, url) {
		var xhr = new XMLHttpRequest();
		if ("withCredentials" in xhr) {

		// Check if the XMLHttpRequest object has a "withCredentials" property.
		// "withCredentials" only exists on XMLHTTPRequest2 objects.
		xhr.open(method, url, true);

		} else if (typeof XDomainRequest != "undefined") {

		// Otherwise, check if XDomainRequest.
		// XDomainRequest only exists in IE, and is IE's way of making CORS requests.
		xhr = new XDomainRequest();
		xhr.open(method, url);

		 } else {

		// Otherwise, CORS is not supported by the browser.
		xhr = null;

		 }
		 return xhr;
}



//		<!-- Here are examples of how to call REST API Methods -->


// This calls the helloJSONList REST method from TopTrumpsRESTAPI
function helloJSONList() {

	// First create a CORS request, this is the message we are going to send (a get request in this case)
	var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/helloJSONList"); // Request type and URL
	
	// Message is not sent yet, but we can check that the browser supports CORS
	if (!xhr) {
			alert("CORS not supported");
	}

	// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
	// to do when the response arrives 
	xhr.onload = function(e) {
			var responseText = xhr.response; // the text of the response
		alert(responseText); // lets produce an alert
	};
	
	// We have done everything we need to prepare the CORS request, so send it
	xhr.send();		
}

// This calls the helloJSONList REST method from TopTrumpsRESTAPI
function helloWord(word) {

	// First create a CORS request, this is the message we are going to send (a get request in this case)
	var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/helloWord?Word="+word); // Request type and URL+parameters
	
	// Message is not sent yet, but we can check that the browser supports CORS
	if (!xhr) {
			alert("CORS not supported");
	}

	// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
	// to do when the response arrives 
	xhr.onload = function(e) {
			var responseText = xhr.response; // the text of the response
		alert(responseText); // lets produce an alert
	};
	
	// We have done everything we need to prepare the CORS request, so send it
	xhr.send();		
}

