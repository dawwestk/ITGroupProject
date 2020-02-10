<html>

	<head>
		<!-- Web page title -->
    	<title>Top Trumps Statistics</title>

    	<!-- This is the link to the OUR CSS file -->
    	<!--
    		<link rel="stylesheet" type="text/css" href="/assets/css/statistics.css"/>
    	-->

    	<!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) -->
    	<link rel="stylesheet" href="/assets/css/lads.css"/>
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

    	<script src="/assets/js/lads.js"></script>
		
	</head>

    <body onload="loadTable()">  <!-- onload="initalize()"> Call the initalize method when the page loads -->
    	
    	<div class="container">
    		<div class="jumbotron p-4" id = 'header'>
				<!-- Add your HTML Here -->
				<h1 class="jumbotron-heading">Top Trumps Game Stats!</h1>
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
	</body>
</html>