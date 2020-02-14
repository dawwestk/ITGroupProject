package commandline;

import java.io.PrintStream;
import java.util.Scanner;

import game.Game;

public class View { 
    private CommandLineController controller;
    private Game game;
    private PrintStream ps;

    public View(CommandLineController controller, Game game, PrintStream outputStream){
        this.controller = controller;
        this.ps = outputStream;
        this.game = game;
    }

	
	/*
	 * 
	 * 		User Input Functions Below
	 * 
	 */

	// User is asked whether to move to the next round - String input, so differing logic from above
	public boolean askForNextRound() {
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

    public int askForMenuChoice(Scanner scanner, String question, String invalidMessage, int lower, int upper, PrintStream ps) {
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

}