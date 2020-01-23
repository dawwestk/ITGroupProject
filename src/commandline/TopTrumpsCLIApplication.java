package commandline;

import org.yaml.snakeyaml.nodes.ScalarNode;

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
        superLoop:while (!userWantsToQuit) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("1. Play Game." + "\n2. Statistics." + "\n3. Quit." + "\nUser Choice: ");
            int userChoice = scanner.nextInt();
            switch (userChoice) {
                case 1:
                    game.gameInitialiser();
                    game.printInfo();
                    while (game.activePlayers()) {
                        game.emptyHandClean();
                        if(game.getPlayers().size() == 1){
                            System.out.println(game.getPlayers().get(0).getName() + " has won the game!");
                        }
                        game.performRound();
                        System.out.println("1: Continue  2: Quit");
                        int continueChoice = scanner.nextInt();
                        switch (continueChoice){
                            case 1:
                                continue;
                            case 2:
                                System.out.println("Thanks for playing.");
                                break superLoop;
                        }
                    }
                    break;

                case 2:
                    // Display Stats
                    break;

                case 3:
                    userWantsToQuit = true;
            }

        }

        userWantsToQuit = true; // use this when the user wants to exit the game

    }
}

