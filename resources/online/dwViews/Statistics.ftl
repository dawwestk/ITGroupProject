<html>

	<head>
		<!-- Web page title -->
    	<title>Top Trumps Statistics</title>

    	<!-- This is the link to the OUR CSS file -->
    	<!--
    		<link rel="stylesheet" type="text/css" href="/assets/css/statistics.css"/>
    	-->

    	<!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) -->
    	<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
    	<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
    	<link rel="stylesheet" href="/assets/css/bootstrap.css"/>
    	<link rel="stylesheet" href="/assets/js/bootstrap.js"/>
    	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    	

		<!-- Optional Styling of the Website, for the demo I used Bootstrap (see https://getbootstrap.com/docs/4.0/getting-started/introduction/) -->
		
    	<script src="http://dcs.gla.ac.uk/~richardm/vex.combined.min.js"></script>
    	<script>vex.defaultOptions.className = 'vex-theme-os';</script>
    	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
		<script src="https://canvasjs.com/assets/script/jquery-1.11.1.min.js"></script>
		<script src="https://canvasjs.com/assets/script/jquery.canvasjs.min.js"></script>

    	<style>
    		table {
			  border: 1px solid #666;   
			    width: 100%;
			}
			th {
			  background: #f8f8f8; 
			  font-weight: bold;    
			    padding: 2px;
			}
		</style>
		
	</head>

    <body onload="loadTable()">  <!-- onload="initalize()"> Call the initalize method when the page loads -->
    	
    	<div class="container">
    		<div id = 'header'>
				<!-- Add your HTML Here -->
				<h1>Top Trumps Game Stats!</h1>
			</div>
		</div>

		<div class = "container" id = 'stats'>
			<table id = 'statsTable'>
				<tr>
		        	<th>Statistic</th>
		        	<th>Value</th>
		    	</tr>
			</table>
		</div>

		<div class = "container" id="piechart">
			
		</div>

		<script>
			function loadTable(){
				getStats();
			}

		</script>

		<script>
			function makeChart(JSONData) {
				var data = JSONData;
				var options = {
					animationEnabled: true,
					title: {
						text: "Human vs AI wins"
					},
					data: [{
						type: "doughnut",
						innerRadius: "40%",
						showInLegend: true,
						legendText: "{label}",
						indexLabel: "{label}: #percent%",
						dataPoints: [
							{ label: data["Human-wins"][0], y: data["Human-wins"][1] },
							{ label: data["AI-wins"][1], y: data["AI-wins"][1] },
						]
					}]
				};
				$("#piechart").CanvasJSChart(options);

			}
			</script>

		<script type="text/javascript">
			// -----------------------------------------
			// Add your other Javascript methods Here
			// -----------------------------------------

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
 					
 					var stats = JSON.parse(responseText);

 					for (var key of Object.keys(stats)) {
					    //alert(key + " -> " + stats[key][0] + ": " + stats[key][1]);
					    var row = $("<tr />")
					    $("#statsTable").append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
					    for(var i = 0; i < 2; i++){
					    	row.append($("<td>" + stats[key][i] + "</td>"));
					    }
					}

					makeChart(stats);

					/*

					{
						"Human-wins": ["Human wins", 0], 
						"Average-Draws": ["Average Draws", 4], 
						"AI-wins": ["AI wins", 7], 
						"Highest-Round-Count": ["Highest Round Count", 125], 
						"Total-games": ["Total games", 7]
					}

				      */
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
	</body>
</html>