package commandline;

import java.util.Scanner;
import java.util.ArrayList;

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

		Game game = new Game();
		boolean writeGameLogsToFile = false; // Should we write game logs to file?
		if (args[0].equalsIgnoreCase("true")) writeGameLogsToFile = true; // Command line selection

		// State
		boolean userWantsToQuit = false; // flag to check whether the user wants to quit the application

		// Loop until the user wants to exit the game
		while (!userWantsToQuit) {
				game.gameInitialiser();
				game.printInfo();
				while(game.activePlayers()){
					//game sequence
					game.performRound();
					/*
					ArrayList<ModelPlayer> players = game.getPlayers();
					ModelCard[] activeCards = new ModelCard[players.size()];
					for(int i = 0; i < players.size(); i++) {
						activeCards[i] = players.get(i).getActiveCard();
					}
					Round r = new Round(activeCards, 0);
					*/
				}
				
			userWantsToQuit = true; // use this when the user wants to exit the game

		}
	}
}
