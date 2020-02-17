
/*
***************		Scripts pertaining to Game Screen	*******************
*/

var chosenAttribute = null;

function setUpBoard(){
	getPlayers();
	$(document).ready(function(){
		updateText("Welcome to Top Trumps - hit this button to begin...");
		updateButtonText("Begin round 1");
	})
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
	unHideResults();
	getJSON(false);	// boolean is used to indicate if this is the first load or not
}

function clickedAttribute(item) {
	// Selection of an attribute by the user
	// The user can change their mind, and each change will be recorded
	var choice = $(item).attr("id"); 
	$('#game-user-button-group').children().attr("class", "btn btn-outline-success");
	$(item).attr("class", "btn btn-success");
	selectAttributeAsPOST(choice);
	chosenAttribute = choice;
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

<<<<<<< HEAD
			// Round winner has been decided - message is posted to text bar
			// Also, we want to show all player attributes in results table
=======
>>>>>>> f3eb081771eb8c30c6b9f737d1acfc6d74e5378c
			showAllAttributes();
		};
		xhr.send();
	} else {
		// Warn the user that they must make a selection - no change to button function
		$('#game-text').css('color', 'red');
		updateText("You must choose an Attribute first!");
	}
	updateButtonText('Next Round');
}

function showAllAttributes(){
	unHideBoard();
	var idOfHiddenAttribute = '#ai' + chosenAttribute + 'Hidden';
<<<<<<< HEAD
=======
	var idOfAttributeBadge = '#ai' + chosenAttribute + 'Badge';
>>>>>>> f3eb081771eb8c30c6b9f737d1acfc6d74e5378c
	var idOfPlayerAttribute = '#player' + chosenAttribute + 'Badge';
	var playerAttribute = $(idOfPlayerAttribute).text();

	var resultTable = $('#resultsTable');
	resultsTable.innerHTML = "<tr><th>Player</th><th>Score</th></tr>";

<<<<<<< HEAD
	// If the user is still an active player in the game
	// add their attribute data to the table
=======
>>>>>>> f3eb081771eb8c30c6b9f737d1acfc6d74e5378c
	if($(idOfPlayerAttribute).length){
		var row = $('<tr />');
		resultTable.append(row)
		row.append($('<td>Player One</td>'));
		row.append($('<td>' + playerAttribute + '</td>'));
	}
<<<<<<< HEAD

=======
>>>>>>> f3eb081771eb8c30c6b9f737d1acfc6d74e5378c
	var i;
	for(i = 1; i <= 4; i++){
		var cardID = '#game-AI-card-container-' + i;
		var hiddenLabel = $(cardID).find(idOfHiddenAttribute);
		var hiddenAttr = hiddenLabel.text();
<<<<<<< HEAD

		// Only add the player attribute if they exist
		if((hiddenLabel).length){
=======
		// Only add the player attribute if they exist
		if((hiddenLabel).length){
			//alert("CPU-" + i + " exists");
			//output += "CPU-" + i + " scored " + hiddenAttr + "\n";
>>>>>>> f3eb081771eb8c30c6b9f737d1acfc6d74e5378c
			var row = $("<tr />")
		    	$("#resultsTable").append(row);
		    	row.append($("<td>CPU-" + i + "</td>"));
		    	row.append($("<td>" + hiddenAttr + "</td>"));
		}else{
<<<<<<< HEAD
			// The player has been eliminated - no data to add to the table
=======
			//alert("No player " + i);
>>>>>>> f3eb081771eb8c30c6b9f737d1acfc6d74e5378c
		}
	}
	$('#next-round-button').attr('onclick', 'advance()');
	unHideResults();
}
	
function nextRound(){
	/*
		Utilises the game.advanceRound() function in the API
		Increases the round counter variable
	*/
	var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/game/nextRound/"); 

	if (!xhr) {alert("CORS not supported");}

	xhr.onload = function(e) {
		var responseText = xhr.response;
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

function unHideResults(){

	/*
		Unhides the results table
	*/
	var x = document.getElementById("round-results");
	if (x.style.visibility === "visible") {
	  	x.style.visibility = "hidden";
	  	x.style.display = 'none';
	} else {
	  	x.style.visibility = "visible";
	  	x.style.display = 'block';
	}
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
	
	var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/game/getJSON"); 

	if (!xhr) {alert("CORS not supported");}

	xhr.onload = function(e) {
		var responseText = xhr.response;
		
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

		if(players[0].activePlayer){
			setActivePlayerStyle($('#game-user-card'));
		} else {
			setInactivePlayerStyle($('#game-user-card'));
		}

		if(players[0].eliminated){
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
			var cardID = '#game-AI-card-container-' + i;

			if(players[i].eliminated){
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
							chosenAttribute = players[i].highestAttribute;
							updateButtonText('Compare');
							selectAttributeAsPOST(players[i].highestAttribute);
						}
					}
					setActivePlayerStyle($(cardID));
				} else {
					setInactivePlayerStyle($(cardID));
				}


				// Populate the AI card with name, card name and hand size information
				$(cardID).find("h3").text(players[i].cardName);
				$(cardID).find('h2').text(players[i].name);
				$(cardID).find('img').attr('src', '/assets/images/' + players[i].cardName + '.jpg');
				$(cardID).find('#aiHandSize').text(players[i].handSize);
				
<<<<<<< HEAD
				// Populate each attribute with it's value from the JSON information
=======
>>>>>>> f3eb081771eb8c30c6b9f737d1acfc6d74e5378c
				$(cardID).find('#aiSizeHidden').text(players[i].Size);
				$(cardID).find('#aiSpeedHidden').text(players[i].Speed);
				$(cardID).find('#aiRangeHidden').text(players[i].Range);
				$(cardID).find('#aiFirepowerHidden').text(players[i].Firepower);
				$(cardID).find('#aiCargoHidden').text(players[i].Cargo);

				$(cardID).find('#aiSizeBadge').text("?");
				$(cardID).find('#aiSpeedBadge').text("?");
				$(cardID).find('#aiRangeBadge').text("?");
				$(cardID).find('#aiFirepowerBadge').text("?");
				$(cardID).find('#aiCargoBadge').text("?");
			}
		}

		for(i = 0; i < playersLength; i++){
			if(parseInt(players[i].handSize) >= 40){
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
			$('#HandSize').attr('disabled', true);
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

function setActivePlayerStyle(element){
	element.css('border-style', 'outset');
	element.css('border-color', 'yellow');
	element.css('border-width', 'thick');
	element.css('background-color', 'rgba(47, 3, 79, 1)');
}

function setInactivePlayerStyle(element){
	element.css('border-style', 'none');
	element.css('background-color', 'rgba(47, 3, 79, 0.3)');
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

/*
***************		Scripts pertaining to Selection Screen	*******************
*/
 
$(document).ready(function(){
  $("#new-game-button").click(function(){
    $("#dropdown-select").slideDown("slow");
  });
});
<<<<<<< HEAD

function goToStatsPage(){
	window.location.href = '/toptrumps/stats/';
}
=======
>>>>>>> f3eb081771eb8c30c6b9f737d1acfc6d74e5378c

function newGameAndSetPlayers(){
	selectPlayers();
}

function selectPlayers() {
  var x = document.getElementById("numberOfOpponents").selectedIndex;
  setPlayers(document.getElementsByTagName("option")[x].value);
  window.location.href = '/toptrumps/game/';
}

function newGame(){
	var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/game/newGame"); 
	
	if (!xhr) {alert("CORS not supported");}

	xhr.onload = function(e) {
		var responseText = xhr.response; // the text of the response
	};
	
	xhr.send();
}

function setPlayers(int){
	var xhr = createCORSRequest('POST', "http://localhost:7777/toptrumps/game/setPlayers?players="+int); 

	if (!xhr) {alert("CORS not supported");}

	xhr.onload = function(e) {
		var responseText = xhr.response;
	};
	
	xhr.send();
}


/*
***************		Scripts pertaining to Stats Screen	*******************
*/

function loadTable(){
	getStats();
	$('#graphs').css('visibility', 'visible');
}

function getStats(){
	var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getStats"); 

	if (!xhr) {alert("CORS not supported");}

	xhr.onload = function(e) {
		var responseText = xhr.response;
		var stats = JSON.parse(responseText);
		var tableStats = stats[0];
		var lineStats = stats[1];
		var pieData = [];
		for(var attr = 0; attr < tableStats.length; attr++){
			for (var key of Object.keys(tableStats[attr])) {
		    	var row = $("<tr />")
		    	$("#statsTable").append(row);
		    	for(var i = 0; i < 2; i++){
		    		row.append($("<td>" + tableStats[attr][key][i] + "</td>"));
		    	}
		    	if(key == "Human-wins" || key == "AI-wins"){
		    		pieData.push({label: tableStats[attr][key][0], y: tableStats[attr][key][1]});
		    	}
			}
		}

		var chart1 = new CanvasJS.Chart("piechart",{
			animationEnabled: true,
		    title :{
		        text: "Human vs AI wins"
		    },
		    data: [{
				type: "doughnut",
				height:200,
				innerRadius: "40%",
				showInLegend: true,
				legendText: "{label}",
				indexLabel: "{label}: #percent%",
				dataPoints: pieData,
			}]
		});
		chart1.render();

		var lineData = []

		for(var i = 0; i < lineStats.length; i++){
			lineData.push({x: lineStats[i]["gameid"], y: lineStats[i]["rounds"]});
		}

		var chart2 = new CanvasJS.Chart("linechart",{
		    title :{
			text: "Rounds per Game"
		    },
		    legendText: "Game ID",
		    data: [{
				type: "line",
            	color: "rgba(255,12,32,.3)",
				dataPoints : lineData,
		    }]
		});
		chart2.render();

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
