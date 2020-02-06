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
    		img {max-width:100%;}
    		#text-box-and-button{width: 100%; }
    		#game-text{width: 80%;}
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
			<h4><span class="badge badge-light" id="game-text">Welcome to TopTrumps!</span>
			<button type="button" class="btn btn-outline-secondary" id="next-round-button" onclick="startRoundOne()">Next Round</button><button onclick="advance()">Advance</button></h4>
		</div>


		<hr>

		<div class = "container" id = "game-board">
			<div class = "user-card" id = "game-user-card">
				<h2 id="mainPlayerName">Placeholder<span class="badge badge-light" id="mainPlayerHandSize">temp</span></h2>
				<div class = "container" id = "game-user-card-image">
					<img src = "/assets/images/spaceship_test.jpg" alt="missing spaceship">
					<h3 id = "game-user-name">Your card name</h3>
				</div>
				<div class="btn-group-vertical" role="group" aria-label="...">
					<button type="button" class="btn btn-outline-primary" id="Size" onclick="clickedAttribute(this)">Size:<span class="badge badge-light" id="playerSizeBadge">temp</span></button>
  					<button type="button" class="btn btn-outline-primary" id="Speed" onclick="clickedAttribute(this)">Speed:<span class="badge badge-light" id="playerSpeedBadge">temp</span></button>
					<button type="button" class="btn btn-outline-primary" id="Range" onclick="clickedAttribute(this)">Range:<span class="badge badge-light" id="playerRangeBadge">temp</span></button>
  					<button type="button" class="btn btn-outline-primary" id="Firepower" onclick="clickedAttribute(this)">Firepower:<span class="badge badge-light" id="playerFirepowerBadge">temp</span></button>
  					<button type="button" class="btn btn-outline-primary" id="Cargo" onclick="clickedAttribute(this)">Cargo:<span class="badge badge-light" id="playerCargoBadge">temp</span></button>
					<button type="button" class="btn btn-outline-primary" id="HandSize">No. Cards in Hand:<span class="badge badge-info" id="playerHandSize">New</span></button>

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
		    selectAttribute(choice);
		   }
		</script>

		<script>

			function updateText(textString, buttonString){
				$('#game-text').text(textString);
				$('#next-round-button').text(buttonString);
			}

			function selectAttribute(attrName){
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('POST', "http://localhost:7777/toptrumps/game/selectAttribute?attribute=" + attrName); // Request type and URL+parameters
				
				// Message is not sent yet, but we can check that the browser supports CORS
				if (!xhr) {
  					alert("CORS not supported");
				}

				// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
				// to do when the response arrives 
				xhr.onload = function(e) {
 					var responseText = xhr.response; // the text of the response
					//alert("User chose " + int);
					updateText("Player One chose " + attrName, "Next round");

				};
				
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();
			}

			function setUpBoard(){
				getPlayers();
				updateText("Welcome to Top Trumps - hit this button to begin...", "Begin round 1");
			}

			function startRoundOne(){
				getJSON();
				unHideBoard();
			}

			function advance(){
				nextRound();
				getJSON();
				getRoundCount();
			}

			function getRoundCount(){
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/game/getRoundCount/"); // Request type and URL
				
				// Message is not sent yet, but we can check that the browser supports CORS
				if (!xhr) {
  					alert("CORS not supported");
				}

				// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
				// to do when the response arrives 
				xhr.onload = function(e) {
 					var responseText = xhr.response; // the text of the response
					//return responseText; // lets produce an alert
					updateText("Starting round " + responseText, "Next")
				};
				
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();
			}

			function nextRound(){
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/game/nextRound/"); // Request type and URL
				
				// Message is not sent yet, but we can check that the browser supports CORS
				if (!xhr) {
  					alert("CORS not supported");
				}

				// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
				// to do when the response arrives 
				xhr.onload = function(e) {
 					var responseText = xhr.response; // the text of the response
					//alert(responseText); // lets produce an alert
				};
				
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();
			}

			function unHideBoard(){
				var x = document.getElementById("game-board");
				if (x.style.visibility === "visible") {
				  x.style.visibility = "hidden";
				} else {
				  	x.style.visibility = "visible";
				}
			}

			function getPlayers(){
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/game/getPlayers"); // Request type and URL+parameters
				
				// Message is not sent yet, but we can check that the browser supports CORS
				if (!xhr) {
  					alert("CORS not supported");
				}

				// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
				// to do when the response arrives 
				xhr.onload = function(e) {
 					var responseText = xhr.response; // the text of the response
					//alert("Number of players = " + responseText); // lets produce an alert
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
				
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();	
			}


			function getJSON(){
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/game/getJSON"); // Request type and URL+parameters

				// Message is not sent yet, but we can check that the browser supports CORS
				if (!xhr) {
  					alert("CORS not supported");
				}

				// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
				// to do when the response arrives 
				xhr.onload = function(e) {
 					var responseText = xhr.response; // the text of the response
					//alert(responseText); // lets produce an alert
					var players = JSON.parse(responseText);
					//alert(players[0].name);

					$('#mainPlayerName').text(players[0].name);
					$('#game-user-name').text(players[0].cardName);
					$('#playerSizeBadge').text(players[0].Size);
					$('#playerSpeedBadge').text(players[0].Speed);
					$('#playerRangeBadge').text(players[0].Range);
					$('#playerFirepowerBadge').text(players[0].Firepower);
					$('#playerCargoBadge').text(players[0].Cargo);
					$('#playerHandSize').text(players[0].handSize);

					var i;
					for(i = 1; i < players.length; i++){
						//alert("finding i " + i);
						var cardID = '#game-AI-card-container-' + i;

						if(players[i].activePlayer){
							$(cardID).css('border-style', 'solid');
							$(cardID).css('border-color', 'blue');
						} else {
							$(cardID).css('border-stle', 'none');
						}

						$(cardID).find("h3").text(players[i].cardName);
						$(cardID).find('h2').text(players[i].name);
						$(cardID).find('#aiHandSize').text(players[i].handSize);
						$(cardID).find('#aiSizeBadge').text(players[i].Size);
						$(cardID).find('#aiSpeedBadge').text(players[i].Speed);
						$(cardID).find('#aiRangeBadge').text(players[i].Range);
						$(cardID).find('#aiFirepowerBadge').text(players[i].Firepower);
						$(cardID).find('#aiCargoBadge').text(players[i].Cargo);

					}

					// $("#Size").text( "Size: " + players[0].Size);
					// $('#Speed').text( "Speed: " + players[0].Speed);
					// $('#Range').text( "Range: " + players[0].Range);
					// $('#Firepower').text( "Firepower: " + players[0].Firepower);
					// $('#Cargo').text( "Cargo: " + players[0].Cargo);

				};
				
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();	
			}

			function myFunction(a, b){
				return a * b;
			}
			document.getElementById("demo").innerHTML = myFunction(10, 2);

			// Method that is called on page load
			function initalize() {
			
				// --------------------------------------------------------------------------
				// You can call other methods you want to run when the page first loads here
				// --------------------------------------------------------------------------
				
				// For example, lets call our sample methods
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
		
		</script>

		<script>
			$(window).load(getJSON());	// execute once window has loaded
		</script>
		
		<!-- Here are examples of how to call REST API Methods -->
		<script type="text/javascript">
		
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

		</script>
		
		</body>
</html>