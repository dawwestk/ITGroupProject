<html>

	<head>
		<!-- Web page title -->
    	<title>Top Trumps Selection</title>
    	<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
    	<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
    	<link rel="stylesheet" href="/assets/css/bootstrap.css"/>
    	<link rel="stylesheet" href="/assets/js/bootstrap.js"/>
    	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" crossorigin="anonymous">
    	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    	<script src="/assets/js/lads.js"></script>
    	<link rel="stylesheet" href="/assets/css/lads.css"/>
		
	</head>

    <body id="spaceBackground">
    	<div class="container" id = "selection-title">

			<h1 id=titleHeader>Top Trumps Game!</h1>

		</div>
		<div class="container" id = "selection-text">
			<h2>Would you like to play a game or view stats?</h2>
		</div>

		<div class="container px-lg-5">
		  <div class="row mx-lg-n5">
		    <div class="col py-3 px-lg-5"><button type="button" class="btn btn-light btn-lg btn-block" id = "new-game-button">Start a New Game</button></div>
<<<<<<< HEAD
		    <div class="col py-3 px-lg-5"><button type="button" class="btn btn-secondary btn-lg btn-block" onclick="goToStatsPage()">See Statistics</button></div>
=======
		    <!-- was inside new game button onclick="window.location.href = '/toptrumps/game/';" -->
		    <div class="col py-3 px-lg-5"><button type="button" class="btn btn-secondary btn-lg btn-block" onclick="window.location.href = '/toptrumps/stats/';">See Statistics</button></div>
>>>>>>> f3eb081771eb8c30c6b9f737d1acfc6d74e5378c
		  </div>
		</div>

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
	</body>
</html>
