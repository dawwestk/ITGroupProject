
/*
***************		Scripts pertaining to Game Screen	*******************
*/
function setUpBoard(){
	getPlayers();
	$(document).ready(updateText("Welcome to Top Trumps - hit this button to begin..."));
	$(document).ready(updateButtonText("Begin round 1"));
}

function startRoundOne(){
	getJSON(false);			// pulls in JSON, populates cards
	unHideBoard();		// unhides board elements revealing cards, active player
	$('#round-count-badge').text("Round: " + 1);
	$('#next-round-button').attr('onclick', 'showResults()');
}

function showResults(){
	compare();
}

function advance(){
	// Reset all buttons to be outlines - i.e. not selected yet
	$('#game-user-button-group').children().attr("class", "btn btn-outline-success");
	nextRound();
	getJSON(false);	// boolean is used to indicate if this is the first load or not
}

function clickedAttribute(item) {
	var choice = $(item).attr("id"); 
	$('#game-user-button-group').children().attr("class", "btn btn-outline-success");
	$(item).attr("class", "btn btn-success");
	selectAttributeAsPOST(choice);
}

function updateText(textString){
	/*
		Helper method to update the status bar and "next" button
	*/
	$('#game-text').text(textString);
}

function updateButtonText(buttonString){
	/*
		Helper method to update the status bar and "next" button
	*/
	$('#next-round-button').text(buttonString);
}

function selectAttributeAsPOST(attrName){

	/*
		Sends the chosen attribute from the player back to the API
	*/
	updateButtonText("Compare");
	$.ajax({
        type: 'POST',
        dataType: "json",
        url: "http://localhost:7777/toptrumps/game/selectAttribute",
        contentType: "application/json; charset=UTF-8", 
        data: attrName,
    });
}

function weHaveAWinner(winnerName){

	/*
		Sends the winners name to allow the database to log the win
	*/
	$.ajax({
        type: 'POST',
        dataType: "json",
        url: "http://localhost:7777/toptrumps/game/weHaveAWinner",
        contentType: "application/json; charset=UTF-8", 
        data: winnerName,        
    });
}



function compare(){
	
	/*
		Checks user input/CPU input function in the API
		Increases the round counter variable
	*/
	var userSelection = false;
	var userActive = false;
	var activePlayer = $('#game-active-player-name').text();

	// The following check is only needed if Player One is the active player
	if(activePlayer == "Player One"){userActive = true;}
	
	// If Player One is active, we need to check that they have made a choice
	if(userActive){

		$('#game-user-button-group').children().each(function(i) {
			if($(this).attr("class") == "btn btn-success"){
				userSelection = true;
			}
		});
	} else {
		// If CPU player is active, we know they have "chosen" already
		userSelection = true;
	}

	if(userSelection){
		$('#game-text').css('color', 'black');
		var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/game/compare/");
		if (!xhr) {alert("CORS not supported");}
		xhr.onload = function(e) {
			var responseText = xhr.response;
			updateText(responseText);

			// User successfully made a choice, change button to advance function
			unHideBoard();
			$('#next-round-button').attr('onclick', 'advance()');
		};
		xhr.send();
	} else {
		// Warn the user that they must make a selection - no change to button function
		$('#game-text').css('color', 'red');
		updateText("You must choose an Attribute first!");
	}
	updateButtonText('Next');
}
	
function nextRound(){
	/*
		Utilises the game.advanceRound() function in the API
		Increases the round counter variable
	*/
	var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/game/nextRound/"); // Request type and URL
	
	if (!xhr) {alert("CORS not supported");}

	xhr.onload = function(e) {
		var responseText = xhr.response; // the text of the response
		//alert(responseText); // lets produce an alert
		if(parseInt(responseText) >= 1){
			$('#next-round-button').attr('onclick', 'showResults()');
			$('#round-count-badge').text("Round: " + responseText);
			unHideBoard();
		} else {
			// round did not successfully advance
		}
	};
	xhr.send();
}

function quitGame(){
	window.location.href = '/toptrumps/';
	var x = "Quit";
	$.ajax({
        type: 'POST',
        dataType: "json",
        url: "http://localhost:7777/toptrumps/game/userQuit/",
        contentType: "application/json; charset=UTF-8", 
        data: x,		        
    });
}

function unHideBoard(){

	/*
		Unhides the game-board and the Active Player sections
		Can also re-hide between rounds if desired(?)
	*/
	var x = document.getElementById("game-board");
	var communal = document.getElementById("communal-pile-text");
	if (x.style.visibility === "visible") {
	  	x.style.visibility = "hidden";
	  	communal.style.visibility = "hidden";
	} else {
	  	x.style.visibility = "visible";
	  	communal.style.visibility = "visible";
	}
	var y = document.getElementById("game-active-player");
	var z = document.getElementById("game-active-player-name");
	var roundCount = document.getElementById("round-count-badge");
	y.style.visibility = "visible";
	z.style.visibility = "visible";
	roundCount.style.visibility = "visible";
}

function getPlayers(){
	
	/*
		Receives the number of AI players from the API and populates the game-board
		section with the correct number of AI cards (from the AIcard html file)
	*/

	var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/game/getPlayers"); 
	if (!xhr) {alert("CORS not supported");}
	xhr.onload = function(e) {
			var responseText = xhr.response; // the text of the response

		$.ajaxSetup ({
		    // Disable caching of AJAX responses
		    cache: false
		});
		var i;
		var opponents = parseInt(responseText, 10);
		for(i = 0; i < opponents; i++){
			$('#game-AI-card-container-' + (i+1)).load('/assets/html/AIcard.html');
		}
		$('#game-board').css('grid-template-columns', '1fr 20px repeat(' + opponents + ', 1fr)');
		
	};
	xhr.send();	
}


function getJSON(boolean){

	/*
		Retrieves the JSON file which houses the current player and active
		card info. Then populates the correct elements on the page with all
		attributes and active player info as needed
	*/
	
	var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/game/getJSON"); // Request type and URL+parameters

	if (!xhr) {alert("CORS not supported");}

	xhr.onload = function(e) {
		var responseText = xhr.response; // the text of the response
		
		var players = JSON.parse(responseText);
		var playersLength = players.length;

		$.ajaxSetup ({
		    // Disable caching of AJAX responses
		    cache: false
		});

		if(parseInt(players[0].communalPile) > 0){
			$('#communal-pile-value').text(players[0].communalPile);
		} else {
			$('#communal-pile-value').text(0);
		}

		if(players[0].eliminated){
			//alert("Player One eliminated");
			playerEliminated();
		} else {
			if((players[0].name.localeCompare('Player One')) === 0){
				$('#mainPlayerName').text(players[0].name);
				$('#game-user-name').text(players[0].cardName);
				$('#game-user-card-image').find('img').attr('src', '/assets/images/' + players[0].cardName + '.jpg');
				$('#playerSizeBadge').text(players[0].Size);
				$('#playerSpeedBadge').text(players[0].Speed);
				$('#playerRangeBadge').text(players[0].Range);
				$('#playerFirepowerBadge').text(players[0].Firepower);
				$('#playerCargoBadge').text(players[0].Cargo);
				$('#playerHandSize').text(players[0].handSize);
			} else {
				$('#game-user-card').empty();
			}
		}

		var activePlayerSet = false;
		var i;

		for(i = 1; i < players.length; i++){
			//alert("finding i " + i);
			var cardID = '#game-AI-card-container-' + i;

			if(players[i].eliminated){
				//alert(players[i].name + " is eliminated");
				AIeliminated(cardID);
			} else {
				if(players[i].activePlayer){
					if(!activePlayerSet){	
						$('#game-active-player-name').text(players[i].name);
						activePlayerSet = true;
						$('#game-user-button-group').children().attr("disabled", true);
						if(boolean){
							// do not update the text bar
						} else {
							updateText(players[i].name + ' is the active player... they choose ' + players[i].highestAttribute + '!');
							updateButtonText('Compare');
							selectAttributeAsPOST(players[i].highestAttribute);
						}
					}
					$(cardID).css('border-style', 'outset');
					$(cardID).css('border-color', 'white');
				} else {
					$(cardID).css('border-style', 'none');
				}


				$(cardID).find("h3").text(players[i].cardName);
				$(cardID).find('h2').text(players[i].name);
				$(cardID).find('img').attr('src', '/assets/images/' + players[i].cardName + '.jpg');
				$(cardID).find('#aiHandSize').text(players[i].handSize);
				
				/*
				$(cardID).find('#aiSizeBadge').text(players[i].Size);
				$(cardID).find('#aiSpeedBadge').text(players[i].Speed);
				$(cardID).find('#aiRangeBadge').text(players[i].Range);
				$(cardID).find('#aiFirepowerBadge').text(players[i].Firepower);
				$(cardID).find('#aiCargoBadge').text(players[i].Cargo);
				*/
				$(cardID).find('#aiSizeBadge').text("?");
				$(cardID).find('#aiSpeedBadge').text("?");
				$(cardID).find('#aiRangeBadge').text("?");
				$(cardID).find('#aiFirepowerBadge').text("?");
				$(cardID).find('#aiCargoBadge').text("?");
			}
		}

		for(i = 0; i < playersLength; i++){
			if(parseInt(players[i].handSize) >= 40){
				/* 

					Need some winner notification/image/animation

				*/
				updateButtonText('Game Over');
				updateText(players[i].name + " is the winner!");
				$('#next-round-button').attr('disabled', true);
				weHaveAWinner(players[i].name);
				return players[i].name;
			}
		}

		// if the active player is not CPU, it is Player One
		if(!activePlayerSet){
			$('#game-active-player-name').text(players[0].name);
			activePlayerSet = true;
			$('#game-user-button-group').children().removeAttr("disabled");
			if(boolean){
				// do not update text bar
			} else {
				updateText("You are the active player! Choose an attribute then confirm your selection.");
				updateButtonText("Compare");
			}	
		}
	};
	xhr.send();
}

function playerEliminated(){
	$('#game-user-card').empty();
	$('#game-user-card').append("<h1 class='vertical-center'>&#9760</h1>");
}

function AIeliminated(cardID){
	$(cardID).empty();
	$(cardID).css('position', 'relative');
	$(cardID).append("<h1 class='vertical-center'>&#9760</h1>");
}

function removeContainers(containerID){
	/*
		Empties a specified container?
	*/
	
	var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/game/getJSON"); // Request type and URL+parameters
	if (!xhr) {alert("CORS not supported");}
	xhr.onload = function(e) {
		$(containerID).empty();
	};
	xhr.send();
}

/*
***************		Scripts pertaining to Selection Screen	*******************
*/

function newGameAndSetPlayers(){
	selectPlayers();
}

function selectPlayers() {
  var x = document.getElementById("numberOfOpponents").selectedIndex;
  setPlayers(document.getElementsByTagName("option")[x].value);
  window.location.href = '/toptrumps/game/';
}

function newGame(){
	var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/game/newGame"); // Request type and URL+parameters
	
	if (!xhr) {alert("CORS not supported");}

	xhr.onload = function(e) {
		var responseText = xhr.response; // the text of the response
	};
	
	xhr.send();
}

function setPlayers(int){
	var xhr = createCORSRequest('POST', "http://localhost:7777/toptrumps/game/setPlayers?players="+int); // Request type and URL+parameters
	
	if (!xhr) {alert("CORS not supported");}

	xhr.onload = function(e) {
		var responseText = xhr.response; // the text of the re
	};
	
	xhr.send();
}


/*
***************		Scripts pertaining to Stats Screen	*******************
*/

function loadTable(){
	getStats();
}

function makeChart(JSONData) {
	var data = JSONData;
	var options = {
		animationEnabled: true,
		title: {
			text: "Human vs AI wins"
		},
		data: [{
			type: "doughnut",
			height:200,
			innerRadius: "40%",
			showInLegend: true,
			legendText: "{label}",
			indexLabel: "{label}: #percent%",
			dataPoints: [
				{ label: data["Human-wins"][0], y: data["Human-wins"][1] },
				{ label: data["AI-wins"][0], y: data["AI-wins"][1] },
			]
		}]
	};
	$("#piechart").CanvasJSChart(options);

}

function getStats(){
	var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getStats"); // Request type and URL
	
	if (!xhr) {alert("CORS not supported");}

	xhr.onload = function(e) {
		var responseText = xhr.response; // the text of the response
		var stats = JSON.parse(responseText);
		for (var key of Object.keys(stats)) {
	    	var row = $("<tr />")
	    	$("#statsTable").append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
	    	for(var i = 0; i < 2; i++){
	    		row.append($("<td>" + stats[key][i] + "</td>"));
	    	}
		}
		makeChart(stats);
	};
	
	xhr.send();
}


/*
***************		Scripts used across many screens	*******************
*/

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