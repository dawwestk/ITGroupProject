package commandline;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import game.DatabaseQuery;
import game.Game;
import game.ModelCard;
import game.ModelCommunalPile;
import game.ModelDeck;
import game.ModelDeckBuilder;
import game.ModelPlayer;

public class CommandLineController {
	
	private static PrintStream ps;
	private String logFileName = "toptrumps.log"; 
	private DatabaseQuery dbq = null;
	private Scanner keyboard = new Scanner(System.in);
	private boolean userJustChoseAttribute = false;
	private String filename = "StarCitizenDeck.txt";
	private Game game;
	private Logger logger;
	private boolean logging;
	
	public CommandLineController(PrintStream printer, boolean writeGameLogsToFile) {
		ps = printer;
		logging = writeGameLogsToFile;
		
	}
	
	/*
	 * 
	 * 		Game Setup Function Below
	 * 
	 */
	
	public void createGame() {
		ModelDeck modelDeck = new ModelDeck();
		try {
			ModelDeckBuilder deckBuilder = new ModelDeckBuilder(modelDeck, filename);
		} catch(IOException e) {
			ps.println("Deck file could not be opened.");
			System.exit(0);
		}
		
		// create a new file to output logs to
		PrintStream fileStream = null;
		if(logging) {
			try {
				fileStream = new PrintStream(logFileName);					
			} catch(IOException e) {
				ps.println("log file could not be opened");
				System.exit(0);
			}
		}
		
		// log files if -t passed through command line
		logger = new Logger(fileStream, logging);					
		
		// get number of players from user
		int numPlayers = askForMenuChoice(keyboard, "How many opponents would you like to face (max 4)? ", 
				"Please enter a number (1-4): ", 1, 4);

		// Create new game
		game = new Game(modelDeck, numPlayers);
		
		if(logging) {
			// log deck before shuffling
			logInitialDeck("Deck before shuffling");
			// log deck after shuffling
			logDeck("Deck after shuffling");
		}
	}
	
	public void addAllPlayers() {
		// Get all players
		ArrayList<ModelPlayer> players = new ArrayList<ModelPlayer>(game.getNumPlayers());
		for(int i = 0 ; i < game.getNumPlayers(); ++i) {
			players.add(game.getPlayer(i));
		}
		
		if(logging) {
			logPlayerHands();
		}
	}
	
	/*
	 * 
	 * 		User Input Functions Below
	 * 
	 */
	
	// Combines all integer inputs with optional Strings/boundaries based on requirement
	// Avoids re-use of code
	public int askForMenuChoice(Scanner scanner, String question, String invalidMessage, int lower, int upper) {
		int choice = 0;
		do {
			ps.print(question);
			while(!scanner.hasNextInt()) {	
				ps.print(invalidMessage);
				scanner.next();
			}
			choice = scanner.nextInt();
		} while (choice < lower || choice > upper);
		
		return choice;
	}

	// User is asked whether to move to the next round - String input, so differing logic from above
	public static boolean askForNextRound() {
		Scanner scanner = new Scanner(System.in);
		String choice = "";
		do {
			ps.print("Move to next round? (Y/N) ");
			choice = scanner.next();
		} while (!choice.toLowerCase().equals("y") && !choice.toLowerCase().equals("n"));
		
		boolean answer = false;
		if(choice.toLowerCase().contains("y")) {
			answer = true; 
		}
		return answer;
	}
	
	/*
	 * 
	 * 		Database Function Below
	 * 
	 */
	
	public void connectToDB(boolean showStats, boolean addGame) {
		try {
			// "yacata.dcs.gla.ac.uk:5432", "m_19_1002243w", "1002243w"
			// "52.24.215.108:5432"; "CompuGlobalHyperMegaNet"; "CompuGlobalHyperMegaNet";
			dbq = new DatabaseQuery();
		} catch (Exception e){
			ps.println(dbq.getNoConnection());
		}
		
		if(showStats) {
			try {
				ps.println("\n-------- Stats --------\n" + dbq.toString());
			} catch(Exception e) {
				ps.println(dbq.getNoConnection());
			}
		}
		if(addGame) {
			try {
				dbq.addGameToDB(game);
			} catch (Exception e) {
				// no need to print explanation, handled on creation of dbq
			}
		}
	}
	
	/*
	 * 
	 * 		Game/Round Functions Below
	 * 
	 */
	
	public void initiateRound() {
		String stat = "";
		int choice = 0;
		// user goes first
		if(game.usersTurn()) {	                    
			// ask user for the stat they want to play                        
			choice = askForMenuChoice(keyboard, "Which category do you want to select?: ", 
					"Please enter a number (1-5): ", 1, 5);
			userJustChoseAttribute = true;
		} else {
			userJustChoseAttribute = false;
		}
		
		stat = game.getStat(choice);
		
		if(logging) {
			logRound(stat);
		}
		
		printRoundChoice(stat);
		
		// Check if win or draw
		boolean hasWinner = game.hasWinner(stat);
		if(logging) {
			logCommunalPile();
		}
		printRoundSummary(hasWinner, stat);
	}
	
	public boolean askToContinue(boolean userWantsToQuit) {
		if(game.userActive() && !userJustChoseAttribute) {
			boolean nextRound = askForNextRound();

			if(!nextRound) {
				userWantsToQuit = true;
			}
		}
		return userWantsToQuit;
	}
	
	public void distributeCardsToWinner() {
		game.giveWinnerCards(game.getRoundWinner());
		
		if(logging) {
			logCommunalPile();
			logCards();
			logFlush();
		}
	}
	
	public void nextRound() {
		game.advanceRound();
	}
	
	/*
	 * 
	 * 		Printing Functions Below
	 * 
	 */
	
	
	public static String roundIntro(int x) {
		String output = "\n";
		String line = "-----------------------------------------------------";
		output +=  line + "\n";
		output += "------------------- ROUND " + String.format("%3s", x) + " -----------------------\n";
		output += line + "\n";
		return output;
	}
	
	public void printIntro() {
		ps.println(roundIntro(game.getRoundCount()));
	}
	
	public void printPlayerInfo() {
		for (int i = 0; i < game.getNumPlayers(); i++) {
			ps.println(game.getPlayer(i).getInfo());
		}
	}
	
	public void printRoundInfo() {
		// Get and display human players information
		if(game.userActive()) {
			ps.println("You drew " + game.getUser().getActiveCard().printCardInfo());
		}

		/* "Cheat mode" - prints all CPU cards
		ps.println("");
		for (int i = 1; i < game.getNumPlayers(); i++) {
			ps.println(game.getPlayerName(i) + " has drawn " + game.getPlayer(i).getActiveCard().printCardInfo());
		}  
		*/
		
		// display all player's card names
		for (int i = 1; i < game.getNumPlayers(); i++) {
			ps.println(game.getPlayerName(i) + " has drawn " + game.getPlayer(i).getActiveCardName());
		}    
	}
	
	public void printRoundChoice(String stat) {
		ps.println("It is " + game.getActivePlayer().getName() + "'s turn.");
		ps.println(game.getActivePlayer().getName() + " picked attribute " + stat +"\n");    // note this is array index, not numbered attribute
		ps.println("Score to beat is: " + game.getActivePlayer().getActiveCard().getValue(stat) + "\n");
		ps.println("\t" + game.getActivePlayer().getActiveCard().printCardInfo());
	}
	
	public void printRoundSummary(boolean hasWinner, String stat) {
		ps.println("------------------Round Summary----------------------");
		if(hasWinner) {
			ps.println(game.getRoundWinner().getName() + " has won!  Their card was: " + 
			game.getRoundWinner().getActiveCard().getName() + " and it's " + stat + " attribute was " + 
			game.getRoundWinner().getActiveCard().getValue(stat)+"\n");
			
		} else {
			ps.println("There has been a draw.\n");
		}
		ps.println("-----------------------------------------------------");
	}
	
	public void printWinner() {
		// Display winning player's information, winner is the last player left
		ps.println(game.getPlayers().get(0).getName() + " is the winner!");
	}
	
	public void printCommunalPile() {
		// Check size of communal pile
		if (game.communalDeckSize() == 0) {
			ps.println("\nCommunalPile is empty.\n");
		} else {
			ps.println("\nCommunalPile has: " + game.communalDeckSize() + " cards in it.\n");
		}
	}
	
	/*
	 * 
	 * 		Logging Functions Below
	 * 
	 */
	
	public void logInitialDeck(String s) {
		// log deck before shuffling
		logger.appendln("\n" + s);
		logger.appendln(game.getDeck().printInitialDeck());
	}
	
	public void logDeck(String s) {
		// log deck after shuffling
		logger.appendln("\n" + s);
		logger.appendln(game.getDeck().toString());
	}
	
	public void logPlayerHands() {
		// log hand of each player
		ArrayList<ModelPlayer> players = game.getPlayers();
		logger.appendln("Hand of each player");
		for(ModelPlayer player: players) {
			logger.appendln(player.toString());
		}
	}
	
	public void logCommunalPile() {
		// log cards in communal pile (cards added or no cards in it)
		ModelCommunalPile cp = game.getDeck().getCP();
		logger.appendln("\nCards in communal pile (cards added or no cards):");
		logger.appendln(cp.toString());
	}
	
	public void logRound(String stat) {
		// log cards in play
		logger.appendln("\nCurrent cards in play");
		ArrayList<ModelPlayer> players = game.getPlayers();
		for(ModelPlayer player: players) {
			ModelCard card = player.getActiveCard();
			logger.append(player.getName());
			logger.append(" ");
			logger.appendln(card.toString());
		}
		
		// log category selected and card values
		logger.appendln("\nCategory selected and card values");
		for(ModelPlayer player: players) {
			ModelCard card = player.getActiveCard();
			logger.appendln("" + card.getValue(stat));
			logger.appendln("");
		}
	}
	
	public void logCards() {
		// log communal pile after cards removed from it
		ModelDeck deck = game.getDeck();
		logger.appendln("\nCommunal pile after cards removed");
		logger.appendln(deck.getCP().toString());

		// log round winner
		logger.appendln("\nRound Winner");
		logger.appendln(game.getRoundWinner().getName());
		
		// log contents of each deck after a round
		logger.appendln("Deck");
		logger.appendln(deck.toString());
		logger.appendln("\nCommunal Pile:");
		logger.appendln(deck.toString());
		logger.appendln("\nUsers Decks:");
	}
	
	public void logPlayers() {
		ArrayList<ModelPlayer> players = game.getPlayers();
		for(ModelPlayer player: players) {
			logger.appendln(player.toString());
		}
	}
	
	public void logWinner() {
		if(logging) {
			// log game winner
			logger.appendln("Game winner");
			logger.appendln(game.getRoundWinner().getName());
		}
	}
	
	public void logFlush() {
		// write to file
		logger.flush();
	}
	
	// Getters
	public Game getGame() {
		return game;
	}
	
	
	
}
