package commandline;

import org.yaml.snakeyaml.nodes.ScalarNode;

import java.util.Scanner;
import java.util.ArrayList;

import game.Game;

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
                    Game game = new Game();
                    game.gameInitialiser();
                    while (game.activePlayers()) {		
                    	System.out.println("Round " + game.getRoundCount());
                    	
                		// Get and display player information
                		if(game.userActive()) {
                			System.out.println("You drew " + game.getUser().getActiveCard().printCardInfo());
                		}
                		
            			// display all player's card names
            			for (int i = 1; i < game.getNumPlayers(); i++) {
            				System.out.println(game.getPlayerName(i) + " has drawn " + game.getPlayer(i).getActiveCardName());
            			}
            			
                        game.newRound();
                    }
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

