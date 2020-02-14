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
	private Game game;
	private Logger logger;
	private boolean logging;
	private View view;
	
	public CommandLineController(PrintStream printer, boolean writeGameLogsToFile) {
		ps = printer;
		logging = writeGameLogsToFile;
		this.game = null;
		this.view = null;
	}

	// game is over when no players are left active
	public boolean isFinished(){
		if(!game.activePlayers()) return true;
		return false;
	}

	 // hack inserted here
	public void printWinner(){
		this.view.printWinner();
	}

	/*
	 * 
	 * 		Game Setup Function Below
	 * 
	 */
	
	public void createGame(int numPlayers) {
		String filename = "StarCitizenDeck.txt";
		ModelDeck modelDeck = null;
		try {
			modelDeck = new ModelDeck(filename);
		} catch(IOException e) {
			System.out.println("Deck could not be created.");
			System.exit(0);
		}

		// Create new game
		this.game = new Game(modelDeck, numPlayers);
		this.view = new View(this,this.game,System.out);
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
	
	public boolean start(boolean userWantsToQuit){				
		this.view.printIntro();
		this.view.printPlayerInfo();
		this.view.printRoundInfo();
		this.initiateRound();
		this.view.printCommunalPile();
		
		// Ask if the user wants to continue playing
		userWantsToQuit = this.askToContinue(userWantsToQuit);
		return userWantsToQuit;
	}
	 
	public void advanceRoundAndDistributeCards(){
		this.distributeCardsToWinner();
		this.nextRound();
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
			choice = this.view.askForMenuChoice(keyboard, "Which category do you want to select?: ", 
					"Please enter a number (1-5): ", 1, 5, System.out);
			userJustChoseAttribute = true;
		} else {
			userJustChoseAttribute = false;
		}
		
		stat = game.getStat(choice);
		if(logging) {
			logRound(stat);
		}
		
		this.view.printRoundChoice(stat);
		
		// Check if win or draw
		boolean hasWinner = game.hasWinner(stat);
		this.view.printRoundSummary(hasWinner, stat);
		
		if(logging) {
			logCommunalPile();
		}
		
		if(hasWinner) {
			game.giveWinnerCards(game.getRoundWinner());
			
		}
		
		if(logging) {
			logCards(hasWinner);
			logFlush();
		}
	}
	
	public boolean askToContinue(boolean userWantsToQuit) {
		if(game.userActive() && !userJustChoseAttribute) {
			boolean nextRound = this.view.askForNextRound();

			if(!nextRound) {
				userWantsToQuit = true;
			}
		}
		return userWantsToQuit;
	}
	
	public void distributeCardsToWinner() {	
		
	}
	
	public void nextRound() {
		game.advanceRound();
	}
	
	/*
	 * 
	 * 		Logging Functions Below
	 * 
	 */
	
	public void logInitialDeck(String s) {
		// log deck before shuffling
		logger.appendln("\n" + s + ":");
		logger.appendln(game.getDeck().printInitialDeck());
	}
	
	public void logDeck(String s) {
		// log deck after shuffling
		logger.appendln("\n" + s + ":");
		logger.appendln(game.getDeck().toString());
	}
	
	public void logPlayerHands() {
		// log hand of each player
		ArrayList<ModelPlayer> players = game.getPlayers();
		logger.appendln("\nHand of each player:");
		for(ModelPlayer player: players) {
			logger.appendln(player.toString() + "\n");
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
		logger.appendln("\nCurrent cards in play:");
		ArrayList<ModelPlayer> players = game.getPlayers();
		for(ModelPlayer player: players) {
			ModelCard card = player.getActiveCard();
			logger.append(player.getName());
			logger.append(": ");
			logger.appendln(card.toString());
		}
		
		// log category selected and card values
		logger.appendln("\nCategory selected and card values:");
		logger.appendln("Attribute chosen: " + stat);
		for(ModelPlayer player: players) {
			ModelCard card = player.getActiveCard();
			logger.appendln(player.getName() + ": " + card.getValue(stat));
		}
	}
	
	public void logCards(boolean thereWasAWinner) {
		// log communal pile after cards removed from it
		ModelDeck deck = game.getDeck();
		if(thereWasAWinner) {
			// log round winner
			logger.appendln("\nRound Winner:");
			logger.appendln(game.getRoundWinner().getName());
			logger.appendln("\nCommunal pile after cards removed:");
			logger.appendln(deck.getCP().toString());
		} else {
			logger.appendln("Round was a draw.");
		}
		
		// log contents of each deck after a round
		logger.appendln("\nDeck:");
		logger.appendln(deck.toString());
		logger.appendln("\nCommunal Pile:");
		logger.appendln(deck.getCP().toString());
		logPlayerHands();
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
			logger.appendln("\nGame winner:");
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
				ps.println(dbq.addGameToDB(game));
			} catch (Exception e) {
				// no need to print explanation, handled on creation of dbq
			}
		}
	}
	
}
