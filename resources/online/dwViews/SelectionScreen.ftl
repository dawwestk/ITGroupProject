<html>

	<head>
		<!-- Web page title -->
    	<title>Top Trumps Selection</title>
    
    	<!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) -->
    	<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
    	<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
    	<link rel="stylesheet" href="/assets/css/bootstrap.css"/>
    	<link rel="stylesheet" href="/assets/js/bootstrap.js"/>
    	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    	<style>
    		.container{color: black; text-align: center; margin-left: auto; margin-right: auto; padding: auto}
    		#selection-grid{display: grid; grid-template-columns: repeat(2, 1fr); grid-auto-rows: 200px}
    		#dropdown-select{display: none}
    		body{
    			background-image: url("https://raw.githubusercontent.com/Caimpson/ITTeamProject_Documentation/master/SpaceBackgroundSmoothed.jpg?token=ANPHJFV5FUOJQFUQMNHLEUS6IFJT6");
    			background-size: cover;
    		}
    		#titleHeader{
    			color: white;	
    		}
    		#selection-text{
    			color: white;
    		}
    	</style>

		<!-- Optional Styling of the Website, for the demo I used Bootstrap (see https://getbootstrap.com/docs/4.0/getting-started/introduction/) -->
		
    	<script src="http://dcs.gla.ac.uk/~richardm/vex.combined.min.js"></script>
    	<script>vex.defaultOptions.className = 'vex-theme-os';</script>
    	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
		
	</head>

    <body> <!-- onload="initalize()"> Call the initalize method when the page loads -->
    	<div class="container" id = "selection-title">

			<!-- Add your HTML Here -->
			<h1 id=titleHeader>Top Trumps Game!</h1>

		</div>
		<div class="container" id = "selection-text">
			<h2>Would you like to play a game or view stats?</h2>
		</div>

		<div class="container px-lg-5">
		  <div class="row mx-lg-n5">
		    <div class="col py-3 px-lg-5 border bg-light"><button type="button" class="btn btn-primary btn-lg btn-block" id = "new-game-button">Start a New Game</button></div>
		    <!-- was inside new game button onclick="window.location.href = '/toptrumps/game/';" -->
		    <div class="col py-3 px-lg-5 border bg-light"><button type="button" class="btn btn-secondary btn-lg btn-block" onclick="window.location.href = '/toptrumps/stats/';">See Statistics</button></div>
		  </div>
		</div>

		<div class = "row" id = "dropdown-select">
			<div class = "col">
				<h3>How many opponents would you like to face?</h3>
			</div>
			<div class = "col">
				<div class="form-group">
				    <label for="exampleFormControlSelect1">Select number of opponents...</label>
				    <select class="form-control" id="numberOfOpponents">
				      <option id="1">1</option>
				      <option id="2">2</option>
				      <option id="3">3</option>
				      <option id="4">4</option>
				    </select>  	
				</div>
			</div>
			<div class = "col">
				<button class="btn btn-outline-primary" onclick="selectPlayers()">Start Game</button>
				<!-- add number of players as URL variable? -->
			</div>
		</div>

		<script> 
			$(document).ready(function(){
			  $("#new-game-button").click(function(){
			    $("#dropdown-select").slideDown("slow");
			  });
			});
		</script>
		
		<script type="text/javascript">
			
			function selectPlayers() {
			  var x = document.getElementById("numberOfOpponents").selectedIndex;
			  //alert(document.getElementsByTagName("option")[x].value);
			  setPlayers(document.getElementsByTagName("option")[x].value);
			  window.location.href = '/toptrumps/game/';
			}

			function setPlayers(int){
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('POST', "http://localhost:7777/toptrumps/game/setPlayers?players="+int); // Request type and URL+parameters
				
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