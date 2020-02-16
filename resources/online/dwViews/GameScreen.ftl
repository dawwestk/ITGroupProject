<html>

	<head>
		<!-- Web page title -->
    	<title>Top Trumps Game</title>
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

    <body onload="setUpBoard()" id="spaceBackground"> 
    	
    	<div class="container" id = "game-title">
			<h1>Top Trumps Game!</h1>
		</div>
		<hr>
		<div id="text-box-and-button">
			<span class="badge badge-light p-2" id="game-text">Welcome to TopTrumps!</span>
		</div>
		<hr>
		<div id="round-active-player-and-button">
			<span class="badge badge-light p-2" id="round-count-badge">Round Count:</span>
			<span class="badge badge-light p-2" id="game-active-player">Active Player:</span>
			<span class="badge badge-dark p-2" id="game-active-player-name"></span>
			<button type="button" class="btn btn-outline-secondary" id="next-round-button" onclick="startRoundOne()">Next Round</button>
		</div>

		<hr>
		
		<div class="container mt-4" id="round-results">
			<div class="container mt-4">
			<div class = "container" id = 'results'>
				<table id = 'resultsTable'>
					<tr>
			        	<th>Player</th>
			        	<th>Score</th>
			    	</tr>
				</table>
			</div>
		</div>
		</div>

		<div class = "container" id = "game-board">
			<div class = "user-card" id = "game-user-card">
				<h2 id="mainPlayerName"><span class="badge badge-light" id="mainPlayerHandSize"></span></h2>
				<div class = "container" id = "game-user-card-image">
					<img src = "/assets/images/spaceship-test.jpg" alt="missing spaceship">
					<h3 id = "game-user-name"></h3>
				</div>
				<div class="btn-group-vertical" id="game-user-button-group" role="group" aria-label="...">

					<button type="button" class="btn btn-outline-success" id="Size" onclick="clickedAttribute(this)">Size: <span class="badge badge-light" id="playerSizeBadge"></span></button>
  					<button type="button" class="btn btn-outline-success" id="Speed" onclick="clickedAttribute(this)">Speed: <span class="badge badge-light" id="playerSpeedBadge"></span></button>
					<button type="button" class="btn btn-outline-success" id="Range" onclick="clickedAttribute(this)">Range: <span class="badge badge-light" id="playerRangeBadge"></span></button>
  					<button type="button" class="btn btn-outline-success" id="Firepower" onclick="clickedAttribute(this)">Firepower: <span class="badge badge-light" id="playerFirepowerBadge"></span></button>
  					<button type="button" class="btn btn-outline-success" id="Cargo" onclick="clickedAttribute(this)">Cargo: <span class="badge badge-light" id="playerCargoBadge"></span></button>
					<button type="button" class="btn btn-outline-secondary" id="HandSize" disabled>No. Cards in Hand: <span class="badge badge-info" id="playerHandSize"></span></button>

				</div>
			</div>

			<div class="container" id="separator">

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

		<hr>

		<div class="container-fluid" id="quit-footer">
			<span id="communal-pile-text"><span class="badge badge-light">Communal Pile cards: </span><span class="badge badge-light" id="communal-pile-value">0</span></span>
			<button type="button" class="btn btn-secondary" id="quitButton" onclick="quitGame()">
			  Quit Game
			</button>
		</div>

		</body>
		<script>
			$(window).load(getJSON(true));	// execute once window has loaded
		</script>
</html>
