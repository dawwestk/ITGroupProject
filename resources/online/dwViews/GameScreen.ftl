<html>

	<head>
		<!-- Web page title -->
    	<title>Top Trumps Game</title>
    	
    	<!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) -->
    	<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
    	<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
    	<link rel="stylesheet" href="/assets/css/bootstrap.css"/>
    	<link rel="stylesheet" href="/assets/js/bootstrap.js"/>
    	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">


    	<style>
    		.container{color: black; text-align: center; margin-left: auto; margin-right: auto; padding: auto}
    		#game-board{display: grid; visibility: hidden}
    		/* change repeat(X, 1fr) to number of opponents somehow */
    		#game-user-card h3,
    		#game-AI-card1 h3, 
    		#game-AI-card2 h3, 
    		#game-AI-card3 h3, 
    		#game-AI-card4 h3{border: solid;}
    		img {max-width:100%; height:100px}
    		#text-box-and-button{width: 100%; }
    		#game-active-player{width: 10%; visibility: hidden}
    		#game-active-player-name{width: 10%; visibility: hidden}
    		#game-text{width: 60%;}
			#game-AI-card-container-1,
    		#game-AI-card-container-2,
    		#game-AI-card-container-3,
    		#game-AI-card-container-4{}
    		#separator{background-color: rgb(211, 211, 211)}
    		

    	</style>

    	<!--
		<script>
			$(document).ready(function(){
			  $("#game-user-card-image").click(function(){
			    $(".container-attribute").slideDown("slow");
			  });
			});
		</script>
		
		<script>
			$(document).ready(function(){
			  $("[id*='game-user-card-attr']").mouseenter(function(){
			    $(this).css("background-color", "red");
			  });
			});

		</script>
		<script>
			$(document).ready(function(){
			  $("[id*='game-user-card-attr']").mouseleave(function(){
			    $(this).css("background-color", "transparent");
			  });
			});

		</script>
	-->

		<!-- Optional Styling of the Website, for the demo I used Bootstrap (see https://getbootstrap.com/docs/4.0/getting-started/introduction/) -->
		
    	<script src="http://dcs.gla.ac.uk/~richardm/vex.combined.min.js"></script>
    	<script>vex.defaultOptions.className = 'vex-theme-os';</script>
    	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
		
	</head>

    <body onload="setUpBoard()"> <!-- onload="initalize()">  Call the initalize method when the page loads -->
    	
    	<div class="container" id = "game-title">
			<h1>Top Trumps Game!</h1>
		</div>

		<div class = "container" id="text-box-and-button">
			<span class="badge badge-light" id="game-active-player">Active Player:</span>
			<span class="badge badge-dark" id="game-active-player-name">{player}</span>
			<span class="badge badge-light" id="game-text">Welcome to TopTrumps!</span>
			<button type="button" class="btn btn-outline-secondary" id="next-round-button" onclick="startRoundOne(this)">Next Round</button>
		</div>


		<hr>

		<div class = "container" id = "game-board">
			<div class = "user-card" id = "game-user-card">
				<h2 id="mainPlayerName">Placeholder<span class="badge badge-light" id="mainPlayerHandSize">temp</span></h2>
				<div class = "container" id = "game-user-card-image">
					<img src = "/assets/spaceship-test.jpg" alt="missing spaceship">
					<h3 id = "game-user-name">Your card name</h3>
				</div>
				<div class="btn-group-vertical" role="group" aria-label="...">
					<button type="button" class="btn btn-outline-primary" id="Size" onclick="clickedAttribute(this)">Size:<span class="badge badge-light" id="playerSizeBadge">temp</span></button>
  					<button type="button" class="btn btn-outline-primary" id="Speed" onclick="clickedAttribute(this)">Speed:<span class="badge badge-light" id="playerSpeedBadge">temp</span></button>
					<button type="button" class="btn btn-outline-primary" id="Range" onclick="clickedAttribute(this)">Range:<span class="badge badge-light" id="playerRangeBadge">temp</span></button>
  					<button type="button" class="btn btn-outline-primary" id="Firepower" onclick="clickedAttribute(this)">Firepower:<span class="badge badge-light" id="playerFirepowerBadge">temp</span></button>
  					<button type="button" class="btn btn-outline-primary" id="Cargo" onclick="clickedAttribute(this)">Cargo:<span class="badge badge-light" id="playerCargoBadge">temp</span></button>
					<button type="button" class="btn btn-outline-secondary" id="HandSize" disabled>No. Cards in Hand:<span class="badge badge-info" id="playerHandSize">New</span></button>

				</div>
			</div>

			<div class = "container" id="separator">
				
			</div>

			<div id = "game-AI-card-container-1">

			</div>
			<div id = "game-AI-card-container-2">

			</div>
			<div id = "game-AI-card-container-3">

			</div>
			<div id = "game-AI-card-container-4">

			</div>
		</div>

		<script type="text/javascript">
		   function clickedAttribute(item) {
		    var choice = $(item).attr("id"); 
		    //alert(choice);
		    selectAttributeAsPOST(choice);
		   }
		</script>

		<script>

			function updateText(textString, buttonString){
				/*
					Helper method to update the status bar and "next" button
				*/
				$('#game-text').text(textString);
				$('#next-round-button').text(buttonString);
			}

			function selectAttributeAsPOST(attrName){

				/*
					Sends the chosen attribute from the player back to the API
				*/
				
				$.ajax({
			        type: 'POST',
			        dataType: "json",
			        url: "http://localhost:7777/toptrumps/game/selectAttribute",
			        contentType: "application/json; charset=UTF-8", 
			        data: attrName,
			        /*		SUCCESS function not required - but can be used
			        success: function (attrName) {
			            alert(attrName + " written to API");
			        }
			        */
			    });
			}

			function selectAttribute(attrName){
				
				/*
					Sends the chosen attribute from the player back to the API
					Currently not a real POST request
				*/
				var xhr = createCORSRequest('POST', "http://localhost:7777/toptrumps/game/selectAttribute?attribute=" + attrName); 
				if (!xhr) {alert("CORS not supported");}

				xhr.onload = function(e) {
 					var responseText = xhr.response; // the text of the response
					//alert("User chose " + int);
					updateText("Player One chose " + attrName, "Next round");

				};
				xhr.send();
			}

			function setUpBoard(){
				getPlayers();
				updateText("Welcome to Top Trumps - hit this button to begin...", "Begin round 1");
			}

			function startRoundOne(button){
				getJSON();			// pulls in JSON, populates cards
				unHideBoard();		// unhides board elements revealing cards, active player
				getRoundCount();	// display the round counter
				$(button).attr('onclick', 'advance()');	// change the button functionality to the advance() function
			}

			function advance(){
				nextRound();
				getRoundCount();
				getJSON();
				
			}

			function getRoundCount(){
				
				/*
					Retrieves the roundCounter variable from the API
				*/
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/game/getRoundCount/"); // Request type and URL
				
				if (!xhr) {alert("CORS not supported");}

				xhr.onload = function(e) {
 					var responseText = xhr.response; // the text of the response
					//return responseText; // lets produce an alert
					updateText("Starting round " + responseText, "Next")
				};
				xhr.send();
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
					updateText(responseText, 'Next');
				};
				xhr.send();
			}

			function unHideBoard(){

				/*
					Unhides the game-board and the Active Player sections
					Can also re-hide between rounds if desired(?)
				*/
				var x = document.getElementById("game-board");
				if (x.style.visibility === "visible") {
				  x.style.visibility = "hidden";
				} else {
				  	x.style.visibility = "visible";
				}
				var y = document.getElementById("game-active-player");
				var z = document.getElementById("game-active-player-name");
				y.style.visibility = "visible";
				z.style.visibility = "visible";
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
					
					return responseText;
				};
				xhr.send();	
			}


			function getJSON(){

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
					//alert(players[0].name);

					if(playersLength < 5 && playersLength > 1){
						var i = playersLength;
						var cardID = '#game-AI-card-container-' + i;
						for(i; i<5; i++){
							removeContainers(cardID)
						}
					}

					$.ajaxSetup ({
					    // Disable caching of AJAX responses
					    cache: false
					});

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
								}
								$(cardID).css('border-style', 'solid');
								$(cardID).css('border-color', 'blue');
							} else {
								$(cardID).css('border-style', 'none');
							}


							$(cardID).find("h3").text(players[i].cardName);
							$(cardID).find('h2').text(players[i].name);
							$(cardID).find('img').attr('src', '/assets/images/' + players[i].cardName + '.jpg');
							$(cardID).find('#aiHandSize').text(players[i].handSize);
							$(cardID).find('#aiSizeBadge').text(players[i].Size);
							$(cardID).find('#aiSpeedBadge').text(players[i].Speed);
							$(cardID).find('#aiRangeBadge').text(players[i].Range);
							$(cardID).find('#aiFirepowerBadge').text(players[i].Firepower);
							$(cardID).find('#aiCargoBadge').text(players[i].Cargo);
						}
					}

					if(!activePlayerSet){
						$('#game-active-player-name').text(players[0].name);
								activePlayerSet = true;
					}
				};
				xhr.send();
			}

			function playerEliminated(){
				$('#game-user-card').empty();
				$('#game-user-card').append("<h1>&#9760</h1>");
				//$('#game-user-card').append("<img src = 'assets/SpaceBackgroundSmoothed.jpg' alt='Player Eliminated'>");
			}

			function AIeliminated(cardID){
				$(cardID).empty();
				$(cardID).append("<h1>&#9760</h1>");
				//$(cardID).append("<img src = 'assets/SpaceBackgroundSmoothed.jpg' alt='AI Eliminated'>");
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

				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();

			}

		
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
		
		</script>

		<script>
			$(window).load(getJSON());	// execute once window has loaded
		</script>
		</body>
</html>
