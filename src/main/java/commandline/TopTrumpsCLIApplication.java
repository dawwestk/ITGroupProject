package commandline;

import org.yaml.snakeyaml.nodes.ScalarNode;

import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import game.Game;
import game.ModelAIPlayer;
import game.ModelCard;
import game.ModelDeck;
import game.ModelPlayer;

/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApplication {

	// Reading deck from text file
	public static void buildDeck(ModelDeck deck) {
		String filename = "StarCitizenDeck.txt";
		FileReader fr = null;
		String[] attributeList = null;
		try {
			File file = new File(filename);
			fr = new FileReader(file);
			Scanner text = new Scanner(fr);

			while (text.hasNext()) {
				String shipInfo = text.nextLine(); // shipInfo == card description + stats
				String[] stats = shipInfo.split(" "); // card description + stats 

				if (stats[0].toLowerCase().equals("description")) { // First row of text file
					attributeList = new String[stats.length];
					attributeList = stats;
				} else { // every other row of text file
					ModelCard card = new ModelCard(stats, attributeList);
					deck.addCard(card);
				}
			}

		} catch (IOException e) {
			System.out.println("Could not open file.");
			System.exit(0);
		} 		
	}

	// Choosing amount of players
	public static int chooseOpponents(Scanner keyboard) {
		System.out.print("How many opponents would you like to face (max 4)? ");
		int opponents = keyboard.nextInt();
		keyboard.nextLine();
		return opponents;
	}

	// Creating players based on choice
	public static int askForNumberOfPlayers() {
		Scanner keyboard = new Scanner(System.in);
		int opponents = chooseOpponents(keyboard);

		while (opponents <= 0 || opponents >= 5) {
			System.out.println("Sorry, you must face 1-4 opponents.");
			opponents = chooseOpponents(keyboard);
		}
		return opponents;
	}

	// Choosing which card stat will be compared
	public static int askForStat() {
		Scanner scanner = new Scanner(System.in);
		int choice = 1;
		do {
			System.out.println("Which category do you want to select?: ");
			choice = scanner.nextInt();
			scanner.nextLine();
		} while (choice < 1 || choice > 5);
		return choice;
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

		// State
		boolean userWantsToQuit = false; // flag to check whether the user wants to quit the application
		Scanner scanner = new Scanner(System.in);
		// Loop until the user wants to exit the game
		superLoop:while (!userWantsToQuit) {
			System.out.print("1. Play Game." + "\n2. Statistics." + "\n3. Quit." + "\nUser Choice: ");
			int userChoice = scanner.nextInt();
			scanner.nextLine();
			switch (userChoice) {
			case 1:
				// create new Deck
				ModelDeck modelDeck = new ModelDeck();
				buildDeck(modelDeck);

				// get number of players from user
				int numPlayers = askForNumberOfPlayers();

				// Create new game
				Game game = new Game(modelDeck, numPlayers);

				// While the game isn't finished
				while (game.activePlayers()) {		
					System.out.println("Round " + game.getRoundCount());

					// Get and display human players information
					if(game.userActive()) {
						System.out.println("You drew " + game.getUser().getActiveCard().printCardInfo());
					}

					// display all player's card names
					for (int i = 1; i < game.getNumPlayers(); i++) {
						System.out.println(game.getPlayerName(i) + " has drawn " + game.getPlayer(i).getActiveCardName());
					}            	

					String stat = "";
					int choice = 0;
					// user goes first
					if(game.usersTurn()) {	                    
						// ask user for the stat they want to play                        
						choice = askForStat();
					}

					stat = game.getStat(choice);

					System.out.println("It is " + game.getActivePlayer().getName() + "'s turn.");
					System.out.println(game.getActivePlayer().getName() + " picked attribute " + stat +"\n");    // note this is array index, not numbered attribute
					System.out.println("Score to beat is: " + game.getActivePlayer().getActiveCard().getValue(stat) + "\n");
					System.out.println("\t" + game.getActivePlayer().getActiveCard().printCardInfo());

					boolean hasWinner = game.checkIfWinner(stat);

					for (int i = 0; i < game.getNumPlayers(); i++) {
						System.out.println(game.getPlayer(i).getInfo());
					}

					if (game.communalDeckSize() == 0) {
						System.out.println("\nCommunalPile is empty.\n");
					} else {
						System.out.println("\nCommunalPile has: " + game.communalDeckSize() + " cards in it.\n");
					}

					if(hasWinner) {
						System.out.println(game.getRoundWinner().getName() + " has won!  His card was: " + 
								game.getRoundWinner().getActiveCard().getName() + " and it's " + stat + " attribute was " + 
								game.getRoundWinner().getActiveCard().getValue(stat)+"\n");
					} else {
						System.out.println("There has been a draw.\n");
					}

					game.advanceRound();
				}

				// Display winning player's information, winner is the last player left
				System.out.println(game.getPlayers().get(0).getName() + " is the winner!");
				break;
			case 2:
				// Display Stats
				break;

			case 3:
				userWantsToQuit = true;
			}

		}
		scanner.close();
	}
}

