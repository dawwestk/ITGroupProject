<html>

	<head>
		<!-- Web page title -->
    	<title>Top Trumps Game</title>
    	
    	<!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) -->
    	<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
    	<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>

    	<link rel="stylesheet" href="toptrumps.css"/>

    	<style>
    		.container{color: black; text-align: center; margin-left: auto; margin-right: auto; padding: auto}
    		#game-board{display: grid; grid-template-columns: repeat(6, 1fr);}
    		/* change repeat(X, 1fr) to number of opponents somehow */
    		#game-user-card{display: grid; grid-template-rows: repeat(6, auto);}
    		/* 6 rows - one for picture, 5 for attribute buttons? */
    		#game-board-separator{grid-column-start: 2; grid-column-end: 2; grid-row-start: 1; grid-row-end: 6; background-color: black;}
    		/* again, change 6 here if needed (just a separator) */
    		#game-AI-card1{display: grid; grid-template-rows: repeat(6, auto);}
    		#game-AI-card2{display: grid; grid-template-rows: repeat(6, auto);}
    		#game-AI-card3{display: grid; grid-template-rows: repeat(6, auto);}
    		#game-AI-card4{display: grid; grid-template-rows: repeat(6, auto);}
    		#game-AI-card1 h3, 
    		#game-AI-card2 h3, 
    		#game-AI-card3 h3, 
    		#game-AI-card4 h3{border: solid;}
    		button{width: 10em;}
    		img {max-width:100%; height:auto;}
    		.container-attribute{display: none;}

    		/* unsure how to get all rows to only fill the used height - lots of whitespace currently */    		

    	</style>

    	<script>

			function gamePageSetUp(){
				setUpButtonNames();
				setUpHideBoard();
				createAICards();
			}

    		// Replace hardcoded strings from dictionary somehow?
    		function setUpButtonNames(){
    			var exampleStat = 5;
	    		document.getElementById('game-user-card-attr1').innerHTML = "<button id = 'game-user-card-attr1' onclick='highlightAttribute(this.id)'><h3>Size: " + exampleStat + "</h3></button>";
	    		document.getElementById('game-user-card-attr2').innerHTML = "<button id = 'game-user-card-attr2' onclick='highlightAttribute(this.id)'><h3>Speed: " + exampleStat + "</h3></button>";
	    		document.getElementById('game-user-card-attr3').innerHTML = "<button id = 'game-user-card-attr3' onclick='highlightAttribute(this.id)'><h3>Range: " + exampleStat + "</h3></button>";
	    		document.getElementById('game-user-card-attr4').innerHTML = "<button id = 'game-user-card-attr4' onclick='highlightAttribute(this.id)'><h3>Firepower: " + exampleStat + "</h3></button>";
	    		document.getElementById('game-user-card-attr5').innerHTML = "<button  id = 'game-user-card-attr5'onclick='highlightAttribute(this.id)'><h3>Cargo: " + exampleStat + "</h3></button>";
	    	}

	    	function setUpHideBoard(){
	    		document.getElementById("game-board").style.visibility = "hidden";
	    	}

	    	function unHideBoard(){
	    		document.getElementById("game-board").style.visibility = "visible";
	    		/*	CODE HERE DOES NOT WORK - need to use HTTP POST maybe to read input?
	    		var numberOfOpponents = document.getElementById("numberOfOpponents");
	    		alert("Creating game with " + numberOfOpponents + " opponents!");
	    		*/
	    	}


	    	// createAICards will take an integer 1-4 once we can read from the input
	    	function createAICards(){
	    		var cardHTML = "<div class = 'container' id = 'game-AI-card-image'>					<img src = '/assets/spaceship_test.jpg'><h2> AI active card placeholder </h2>				</div>				<div class = 'game-AI-card-attr1' id = 'game-AI-card-attr1'>					<h3>AI Attribute 1</h3>				</div>				<div class = 'game-AI-card-attr2' id = 'game-AI-card-attr2'>					<h3>AI Attribute 2</h3>				</div>				<div class = 'game-AI-card-attr3' id = 'game-AI-card-attr3'>					<h3>AI Attribute 3</h3>				</div>				<div class = 'game-AI-card-attr4' id = 'game-AI-card-attr4'>					<h3>AI Attribute 4</h3>				</div>				<div class = 'game-AI-card-attr5' id = 'game-AI-card-attr5'>					<h3>AI Attribute 5</h3>				</div>";
	    		var i;
	    		for(i = 1; i < 5; i++){
	    			var AIname = 'game-AI-card' + i;
	    			document.getElementById(AIname).innerHTML = cardHTML;
	    		}

	    	}

	    	function highlightAttribute(chosenAttribute){
	    		var attr = chosenAttribute.substring(chosenAttribute.length - 1, chosenAttribute.length);
	    		var attribute = 'game-AI-card-attr' + attr;
	    		var i;
	    		//alert('updating ' + attribute + '...');
	    		var listOfAICards = document.getElementsByClassName("AI-card");
	    		
	    		//alert("found " + listOfAICards.length + " AI-cards");

	    		for(i = 0; i < listOfAICards.length; i++){
	    			//alert(listOfAICards[i].getElementsByClassName(attribute)[0].innerHTML);
	    			listOfAICards[i].getElementsByClassName(attribute)[0].style.color = "red";
	    		} 
	    		//alert(i);
	    	}

    	</script>

		<script>
			/* 
			 - $(variable)
			 -	
			*/
			$(document).ready(function(){
			  $(".AI-card").click(function(){
			    $(this).hide();		// can change $(this) to something else $("<here>")
			  });
			});
		</script>

		<script> 
			$(document).ready(function(){
			  $("#game-user-card-image").click(function(){
			    $(".container-attribute").slideDown("slow");
			  });
			});
		</script>

    	<!-- ALL stylesheets here
    	
    	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    	<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/flick/jquery-ui.css">
		<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/TREC_IS/bootstrap.min.css">
		<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex.css"/>
    	<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex-theme-os.css"/>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
		
		-->

		<!-- Optional Styling of the Website, for the demo I used Bootstrap (see https://getbootstrap.com/docs/4.0/getting-started/introduction/) -->
		
    	<script src="http://dcs.gla.ac.uk/~richardm/vex.combined.min.js"></script>
    	<script>vex.defaultOptions.className = 'vex-theme-os';</script>
    	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
		
	</head>

    <body onload="gamePageSetUp()"> <!-- onload="initalize()"> <!-- Call the initalize method when the page loads -->
    	
    	<div class="container" id = "game-title">
	
			<!-- Add your HTML Here -->
			
			<h1>Top Trumps Game!</h1> 

		<!--
			<p>Using myFunction(10, 2) = </p>
			<p id="demo"></p>
		-->
	
		</div>

		<div class = "container" id = "game-choose-opponents">
			<!-- will hold the "How many opponents would you like to face?" input/button (then greyed out during game) -->
			<h3>How many opponents would you like to face?</h3><input type="text" name="numberOfOpponents"><input type="submit" onclick="unHideBoard()">
		</div>

		<hr>

		<div class = "container" id = "game-board">
			<div class = "container" id = "game-user-card">
				<div class = "container" id = "game-user-card-image">
					<img src = "/assets/spaceship_test.jpg">
					<h2> User active card placeholder </h2>
				</div>
				<div class = "container-attribute" id = "game-user-card-attr1">
					<!--<button><h3>User Attribute 1</h3></button> -->
				</div>
				<div class = "container-attribute" id = "game-user-card-attr2">
					<!--<button><h3>User Attribute 2</h3></button> -->
				</div>
				<div class = "container-attribute" id = "game-user-card-attr3">
					<!--<button><h3>User Attribute 3</h3></button> -->
				</div>
				<div class = "container-attribute" id = "game-user-card-attr4">
					<!--<button><h3>User Attribute 4</h3></button> -->
				</div>
				<div class = "container-attribute" id = "game-user-card-attr5">
					<!--<button><h3>User Attribute 5</h3></button> -->
				</div>
			</div>

			<div class = "container" id = "game-board-separator">
				<!-- keeping the user card separate from the AI cards (for now) -->
				<div class = "container">
					Separator
				</div>
			</div>
			<div class = "AI-card" id = "game-AI-card1">

				<!-- <div class = "container" id = "game-AI-card-image">
					<img TBD>
					<img src = "https://static7.depositphotos.com/1224164/721/i/450/depositphotos_7211858-stock-photo-spaceship.jpg">
					<h2> AI active card placeholder </h2>
				</div>
				<div class = "container" id = "game-AI-card-attr1">
					<h3>AI Attribute 1</h3>
				</div>
				<div class = "container" id = "game-AI-card-attr2">
					<h3>AI Attribute 2</h3>
				</div>
				<div class = "container" id = "game-AI-card-attr3">
					<h3>AI Attribute 3</h3>
				</div>
				<div class = "container" id = "game-AI-card-attr4">
					<h3>AI Attribute 4</h3>
				</div>
				<div class = "container" id = "game-AI-card-attr5">
					<h3>AI Attribute 5</h3>
				</div>
			-->
			</div>
			<div class = "AI-card" id = "game-AI-card2">

			</div>
			<div class = "AI-card" id = "game-AI-card3">

			</div>
			<div class = "AI-card" id = "game-AI-card4">

			</div>
		</div>

		<!-- Could keep this column layout as container for Grid items
			<div class="container">
			  <div class="row">
			    <div class="col-sm">
			      1) One of three columns
			    </div>
			    <div class="col-sm">
			      2) Two of three columns
			    </div>
			    <div class="col-sm">
			      3) Three of three columns
			    </div>
			  </div>
			  <div class="row">
			    <div class="col-sm">
			      A) One of three columns
			    </div>
			    <div class="col-sm">
			      B) One of three columns
			    </div>
			    <div class="col-sm">
			      C) One of three columns
			    </div>
			  </div>
			</div>

		-->
		
		<script type="text/javascript">
			
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
					alert(responseText + " TEST"); // lets produce an alert
				};
				
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();		
			}

		</script>
		
		</body>
</html>