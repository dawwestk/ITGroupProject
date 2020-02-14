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
		CommandLineController control = new CommandLineController(System.out, writeGameLogsToFile);
		
		// Main Game Loop
		while (!userWantsToQuit) {
			
			// Read stats from the database
			// both parameters are false as we just want to connect, not show or add a game
			control.connectToDB(false, false);
			
			int userChoice = control.askForMenuChoice(keyboard, "1. Play Game." + "\n2. Statistics." + "\n3. Quit." + "\nUser Choice: ", 
					"Please enter a valid menu option (1-3): ", 1, 3);
			switch (userChoice) {
			case 1:
				
				control.createGame();
				
				// While the game isn't finished
				while (control.getGame().activePlayers() && !userWantsToQuit) {	
					
					control.printIntro();
					control.printPlayerInfo();
					control.printRoundInfo();
					control.initiateRound();
					control.printCommunalPile();
					
					// Ask if the user wants to continue playing
					userWantsToQuit = control.askToContinue(userWantsToQuit);
					if(userWantsToQuit) {
						break;
					} else {
						control.distributeCardsToWinner();
						control.nextRound();
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

