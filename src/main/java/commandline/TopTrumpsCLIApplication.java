package commandline;

import org.yaml.snakeyaml.nodes.ScalarNode;

import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import game.*;

/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApplication {

	private static PrintStream ps = System.out;

	// Combines all integer inputs with optional Strings/boundaries based on requirement
	// Avoids re-use of code
	public static int askForMenuChoice(Scanner scanner, String question, String invalidMessage, int lower, int upper) {
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
	
	public static String roundIntro(int x) {
		String output = "\n";
		String line = "-----------------------------------------------------";
		output +=  line + "\n";
		output += "------------------- ROUND " + String.format("%3s", x) + " -----------------------\n";
		output += line + "\n";
		return output;
	}


	/**
	 * This main method is called by TopTrumps.java when the user specifies that they want to run in
	 * command line mode. The contents of args[0] is whether we should write game logs to a file.
	 *
	 * @param args
	 */

	public static void main(String[] args) {

		boolean writeGameLogsToFile = false; // Should we write game logs to file?
		if (args[0].equalsIgnoreCase("true")) writeGameLogsToFile = true; // Command line selection
		
		DatabaseQuery dbq = null;
		Scanner keyboard = new Scanner(System.in);
		
		// State
		boolean userWantsToQuit = false; // flag to check whether the user wants to quit the application
		boolean userJustChoseAttribute = false;
		// Loop until the user wants to exit the game
		while (!userWantsToQuit) {
			// Read stats from the database
			try {
				dbq = new DatabaseQuery("localhost", "postgres", "postgres");
			} catch (Exception e){
				ps.println(dbq.getNoConnection());
			}
			
			int userChoice = askForMenuChoice(keyboard, "1. Play Game." + "\n2. Statistics." + "\n3. Quit." + "\nUser Choice: ", 
					"Please enter a valid menu option (1-3): ", 1, 3);
			switch (userChoice) {
			case 1:
				// create new Deck
				String filename = "StarCitizenDeck.txt";
				ModelDeck modelDeck = new ModelDeck();
				try {
					ModelDeckBuilder deckBuilder = new ModelDeckBuilder(modelDeck, filename);
				} catch(IOException e) {
					ps.println("Deck file could not be opened.");
					System.exit(0);
				}

				// get number of players from user
				int numPlayers = askForMenuChoice(keyboard, "How many opponents would you like to face (max 4)? ", 
						"Please enter a number (1-4): ", 1, 4);

				// Create new game
				Game game = new Game(modelDeck, numPlayers);

				System.err.print(game.printRoundsWon());
				
				// While the game isn't finished
				while (game.activePlayers() && !userWantsToQuit) {	
					
					ps.println(roundIntro(game.getRoundCount()));

					for (int i = 0; i < game.getNumPlayers(); i++) {
						ps.println(game.getPlayer(i).getInfo());
					}
					
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

					ps.println("It is " + game.getActivePlayer().getName() + "'s turn.");
					ps.println(game.getActivePlayer().getName() + " picked attribute " + stat +"\n");    // note this is array index, not numbered attribute
					ps.println("Score to beat is: " + game.getActivePlayer().getActiveCard().getValue(stat) + "\n");
					ps.println("\t" + game.getActivePlayer().getActiveCard().printCardInfo());

					// Check if win or draw
					boolean hasWinner = game.hasWinner(stat);

					// Check size of communal pile
					if (game.communalDeckSize() == 0) {
						ps.println("\nCommunalPile is empty.\n");
					} else {
						ps.println("\nCommunalPile has: " + game.communalDeckSize() + " cards in it.\n");
					}

					ps.println("------------------Round Summary----------------------");
					if(hasWinner) {
						ps.println(game.getRoundWinner().getName() + " has won!  Their card was: " + 
						game.getRoundWinner().getActiveCard().getName() + " and it's " + stat + " attribute was " + 
						game.getRoundWinner().getActiveCard().getValue(stat)+"\n");
						
					} else {
						ps.println("There has been a draw.\n");
					}
					ps.println("-----------------------------------------------------");

					if(game.userActive() && !userJustChoseAttribute) {
						boolean nextRound = askForNextRound();

						if(!nextRound) {
							userWantsToQuit = true;
						}
					}
					game.giveWinnerCards(game.getRoundWinner());
					game.advanceRound();
				}
				
				// If the user has indicated that they wish to quit, there should be no winner statement
				// And the game shouldn't be added to the database
				if(!userWantsToQuit) {
					// Display winning player's information, winner is the last player left
					ps.println(game.getPlayers().get(0).getName() + " is the winner!");

					try {
						dbq.addGameToDB(game);
					} catch (Exception e) {
						// no need to print explanation, handled on creation of dbq
					}
				}
				break;
			case 2:
				
				// DatabaseQuery object dbq returns a String with the stats
				// Output can be altered to be more visually appealing
				try {
					ps.println("\n-------- Stats --------\n" + dbq.toString());
				} catch(Exception e) {
					ps.println(dbq.getNoConnection());
				}
				break;

			case 3:
				userWantsToQuit = true;
			}

		}
	}
}

