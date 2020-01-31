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
    		#game-board{display: grid; grid-template-columns: repeat(5, 1fr);}
    		/* change repeat(X, 1fr) to number of opponents somehow */
    		#game-user-card h3,
    		#game-AI-card1 h3, 
    		#game-AI-card2 h3, 
    		#game-AI-card3 h3, 
    		#game-AI-card4 h3{border: solid;}
    		img {max-width:100%;}	

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

    <body> <!--onload="gamePageSetUp()">  onload="initalize()">  Call the initalize method when the page loads -->
    	
    	<div class="container" id = "game-title">
			<h1>Top Trumps Game!</h1>
		</div>

		<div class = "container" id = "game-choose-opponents">
			<!-- will hold the "How many opponents would you like to face?" input/button (then greyed out during game) -->
			<h3>How many opponents would you like to face?</h3><input type="text" name="numberOfOpponents"><input type="submit"> <!-- onclick="unHideBoard()"> -->
		</div>

		<hr>

		<div class = "container" id = "game-board">
			<div class = "user-card" id = "game-user-card">
				<div class = "container" id = "game-user-card-image">
					<img src = "/assets/spaceship_test.jpg">
					<h3>Your card name</h3>
				</div>
				<div class="btn-group-vertical" role="group" aria-label="...">
					<button type="button" class="btn btn-outline-primary">Size</button>
  					<button type="button" class="btn btn-outline-primary">Speed</button>
					<button type="button" class="btn btn-outline-primary">Range</button>
  					<button type="button" class="btn btn-outline-primary">Firepower</button>
  					<button type="button" class="btn btn-outline-primary">Cargo</button>
				</div>
			</div>

			<div class = "AI-card" id = "game-AI-card1">
				<div class = "container" id = "game-AI-card-image">
					<img src = "/assets/spaceship_test.jpg">
					<h3>AI card name</h3>
				</div>
				<div class="btn-group-vertical" role="group" aria-label="...">
					<button type="button" class="btn btn-outline-primary">Size</button>
  					<button type="button" class="btn btn-outline-primary">Speed</button>
					<button type="button" class="btn btn-outline-primary">Range</button>
  					<button type="button" class="btn btn-outline-primary">Firepower</button>
  					<button type="button" class="btn btn-outline-primary">Cargo</button>
				</div>
			</div>
			<div class = "AI-card" id = "game-AI-card2">
				<div class = "container" id = "game-AI-card-image">
					<img src = "/assets/spaceship_test.jpg">
					<h3>AI card name</h3>
				</div>
				<div class="btn-group-vertical" role="group" aria-label="...">
					<button type="button" class="btn btn-outline-primary">Size</button>
  					<button type="button" class="btn btn-outline-primary">Speed</button>
					<button type="button" class="btn btn-outline-primary">Range</button>
  					<button type="button" class="btn btn-outline-primary">Firepower</button>
  					<button type="button" class="btn btn-outline-primary">Cargo</button>
				</div>
			</div>
			<div class = "AI-card" id = "game-AI-card3">
				<div class = "container" id = "game-AI-card-image">
					<img src = "/assets/spaceship_test.jpg">
					<h3>AI card name</h3>
				</div>
				<div class="btn-group-vertical" role="group" aria-label="...">
					<button type="button" class="btn btn-outline-primary">Size</button>
  					<button type="button" class="btn btn-outline-primary">Speed</button>
					<button type="button" class="btn btn-outline-primary">Range</button>
  					<button type="button" class="btn btn-outline-primary">Firepower</button>
  					<button type="button" class="btn btn-outline-primary">Cargo</button>
				</div>
			</div>
			<div class = "AI-card" id = "game-AI-card4">
				<div class = "container" id = "game-AI-card-image">
					<img src = "/assets/spaceship_test.jpg">
					<h3>AI card name</h3>
				</div>
				<div class="btn-group-vertical" role="group" aria-label="...">
					<button type="button" class="btn btn-outline-primary">Size</button>
  					<button type="button" class="btn btn-outline-primary">Speed</button>
					<button type="button" class="btn btn-outline-primary">Range</button>
  					<button type="button" class="btn btn-outline-primary">Firepower</button>
  					<button type="button" class="btn btn-outline-primary">Cargo</button>
				</div>
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