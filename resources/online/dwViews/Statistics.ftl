<html>

	<head>
		<!-- Web page title -->
    	<title>Top Trumps Statistics</title>
    	<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
    	<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
    	<link rel="stylesheet" href="/assets/css/bootstrap.css"/>
    	<link rel="stylesheet" href="/assets/js/bootstrap.js"/>
    	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" crossorigin="anonymous">
    	<link rel="stylesheet" href="/assets/css/lads.css"/>
    	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
		<script src="https://canvasjs.com/assets/script/jquery-1.11.1.min.js"></script>
		<script src="https://canvasjs.com/assets/script/jquery.canvasjs.min.js"></script>

    	<script src="/assets/js/lads.js"></script>
		
	</head>

    <body onload="loadTable()">  <!-- onload="initalize()"> Call the initalize method when the page loads -->
    	
    	<div class="container mt-4">
    		<div class="jumbotron p-4" id = 'header'>
				<!-- Add your HTML Here -->
				<h1 class="jumbotron-heading">Top Trumps Game Stats!</h1>
			</div>
		</div>
		<hr>

		<div class="container mt-4">
			<div class = "container" id = 'stats'>
				<table id = 'statsTable'>
					<tr>
			        	<th>Statistic</th>
			        	<th>Value</th>
			    	</tr>
				</table>
				<button type="button" class="btn btn-secondary" id="quitButton" onclick="quitGame()">
				  Back
				</button>
			</div>
		</div>

		<div class="container" id="graphs">
			<div class = "container" id="piechart">
			</div>
			<div class = "container" id="linechart">
			</div>
		</div>

	</body>
</html>