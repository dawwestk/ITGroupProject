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

		// Combines all integer inputs with optional Strings/boundaries based on requirement
	// Avoids re-use of code
	public static int askForMenuChoice(Scanner scanner, String question, String invalidMessage, int lower, int upper, PrintStream ps) {
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

	/**
	 * This main method is called by TopTrumps.java when the user specifies that they want to run in
	 * command line mode. The contents of args[0] is whether we should write game logs to a file.
	 *
	 * @param args
	 */

	public static void main(String[] args) {
		
		// Should the game be written to a Log file?
		// Comes from command line selection of -c -t
		boolean writeGameLogsToFile = false;
		if (args[0].equalsIgnoreCase("true")) writeGameLogsToFile = true;
		
		// Main boolean to indicate that the user still wants to continue
		boolean userWantsToQuit = false; // flag to check whether the user wants to quit the application
		Scanner keyboard = new Scanner(System.in);

				// get number of players from user
		// int numPlayers = askForMenuChoice(keyboard, "How many opponents would you like to face (max 4)? ", 
		// 		"Please enter a number (1-4): ", 1, 4, System.out);

		CommandLineController control = new CommandLineController(System.out, writeGameLogsToFile);
		
		// Main Game Loop
		while (!userWantsToQuit ) {
			
			// Read stats from the database
			// both parameters are false as we just want to connect, not show or add a game
			control.connectToDB(false, false);
			
			int userChoice = askForMenuChoice(keyboard, "1. Play Game." + "\n2. Statistics." + "\n3. Quit." + "\nUser Choice: ", 
					"Please enter a valid menu option (1-3): ", 1, 3, System.out);
			switch (userChoice) {
			case 1:
				int numPlayers = askForMenuChoice(keyboard, "How many opponents would you like to face (max 4)? ", 
				"Please enter a number (1-4): ", 1, 4, System.out);

				control.createGame(numPlayers);
				control.addAllPlayers();
				
				// While the game isn't finished
				while (!control.isFinished() && !userWantsToQuit) {	
					userWantsToQuit = control.start(userWantsToQuit);
					if(userWantsToQuit) {
						break;
					} else {
						control.advanceRoundAndDistributeCards();
					}
				}
				
				//Logging of winner 
				control.logWinner();
				
				// If the user has indicated that they wish to quit, there should be no winner statement
				// And the game shouldn't be added to the database
				if(!userWantsToQuit) {
					control.printWinner();
					control.connectToDB(false, true);
				}
				
				break;
			case 2:
				
				// DatabaseQuery object dbq returns a String with the stats
				// Output can be altered to be more visually appealing
				control.connectToDB(true, false);
				break;

			case 3:
				userWantsToQuit = true;
			}

		}
	}
}

