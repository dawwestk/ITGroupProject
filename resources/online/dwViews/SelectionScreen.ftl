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

		<!-- Optional Styling of the Website, for the demo I used Bootstrap (see https://getbootstrap.com/docs/4.0/getting-started/introduction/) -->
		
    	<script src="http://dcs.gla.ac.uk/~richardm/vex.combined.min.js"></script>
    	<script>vex.defaultOptions.className = 'vex-theme-os';</script>
    	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>

    	<script src="/assets/js/lads.js"></script>
    	<link rel="stylesheet" href="/assets/css/lads.css"/>
		
	</head>

    <body id="spaceBackground"> <!-- onload="initalize()"> Call the initalize method when the page loads -->
    	<div class="container" id = "selection-title">

			<h1 id=titleHeader>Top Trumps Game!</h1>

		</div>
		<div class="container" id = "selection-text">
			<h2>Would you like to play a game or view stats?</h2>
		</div>

		<div class="container px-lg-5">
		  <div class="row mx-lg-n5">
		    <div class="col py-3 px-lg-5"><button type="button" class="btn btn-light btn-lg btn-block" id = "new-game-button">Start a New Game</button></div>
		    <div class="col py-3 px-lg-5"><a class="btn btn-info btn-lg btn-block" id = "show-rules-button" data-toggle="collapse" href="#show-rules">Rules</a></div>
		    <!-- was inside new game button onclick="window.location.href = '/toptrumps/game/';" -->
		    <div class="col py-3 px-lg-5"><button type="button" class="btn btn-secondary btn-lg btn-block" onclick="window.location.href = '/toptrumps/stats/';">See Statistics</button></div>
		  </div>
		</div>

		<!--
			<p>
			  <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
			    Link with href
			  </a>
			  <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
			    Button with data-target
			  </button>
			</p>
			
		-->

		<div class = "container" id = "dropdown-select">
			<div class = "col">
				<h3>How many opponents would you like to face?</h3>
			</div>
			<div class = "row">
				<div class="col">
					<label for="exampleFormControlSelect1">Select number of opponents...</label>
				</div>
				<div class="col">
					<div class="form-group">
					    <select class="form-control" id="numberOfOpponents">
					      <option id="1">1</option>
					      <option id="2">2</option>
					      <option id="3">3</option>
					      <option id="4">4</option>
					    </select>  	
					</div>
				</div>
				<div class = "col">
					<button class="btn btn-outline-light" onclick="newGameAndSetPlayers()">Start Game</button>
					<!-- add number of players as URL variable? -->
				</div>
			</div>
			
		</div>

		<div class="collapse" id="show-rules">
			  <div class="card card-body">
			    <h3>Rules of Top Trumps!</h3>
			  </div>
			</div>

		<script> 
			$(document).ready(function(){
			  $("#new-game-button").click(function(){
			    $("#dropdown-select").slideDown("slow");
			  });
			});
		</script>
	</body>
</html>