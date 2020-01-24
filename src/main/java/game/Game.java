package game;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Game {

	private ModelPlayer user;
	private ModelDeck deck;
	private String[] attributeList;
	private ModelCommunalPile cp;
	private ArrayList<ModelPlayer> players;
	private int roundCount;
	private String playerName = "Player One";

	public Game() {
		this.user = new ModelPlayer(this.playerName);
		this.deck = new ModelDeck();
		
		this.cp = this.deck.getCP();
		this.players = new ArrayList<ModelPlayer>();
		this.roundCount = 1;
	}

	// Getters
	public int getRoundCount() {
		return this.roundCount;
	}

	public ModelPlayer getUser() {
		return this.user;
	}

	public ModelDeck getDeck() {
		return this.deck;
	}

	public ArrayList<ModelPlayer> getPlayers() {
		return this.players;
	}

	// Setting up a new game
	public void gameInitialiser() {
		buildDeck();
		buildPlayers();
		dealDeck();
	}

	// Reading deck from text file
	public void buildDeck() {
		String filename = "StarCitizenDeck.txt";
		FileReader fr = null;

		try {
			File file = new File(filename);
			fr = new FileReader(file);
			Scanner text = new Scanner(fr);

			while (text.hasNext()) {
				String shipInfo = text.nextLine();
				String[] stats = shipInfo.split(" ");

				if (stats[0].toLowerCase().equals("description")) {
					this.attributeList = new String[stats.length];
					this.attributeList = stats;
				} else {
					ModelCard card = new ModelCard(stats, this.attributeList);
					this.deck.addCard(card);
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
		return opponents;
	}

	// Creating players based on choice
	public void buildPlayers() {
		this.players.add(this.user);
		Scanner keyboard = new Scanner(System.in);
		int opponents = chooseOpponents(keyboard);

		while (opponents <= 0 || opponents >= 5) {
			System.out.println("Sorry, you must face 1-4 opponents.");
			opponents = chooseOpponents(keyboard);
		}

		for (int i = 0; i < opponents; i++) {
			ModelAIPlayer opponent = new ModelAIPlayer("CPU-" + (i + 1));
			this.players.add(opponent);
		}
	}

	// Distribute shuffled deck amongst players
	public void dealDeck() {
		this.deck.deal(this.players);
	}

	// Print general game info
	public void printInfo() {
		for (int i = 0; i < this.players.size(); i++) {
			System.out.println(this.players.get(i).getInfo());
		}
		if (this.cp.isEmpty()) {
			System.out.println("\nCommunalPile is empty.\n");
		} else {
			System.out.println("\nCommunalPile has: " + this.cp.size() + " cards in it.\n");
		}
	}

	// Choosing which player is to start on 1st round
	public int whoFirst() {
		Random random = new Random();
		int max = this.players.size();
		return random.nextInt(max - 1) + 1;
	}

	// Checking who the stat-picking player will be this round
	public int turnTracker() {
		int theirTurn = 0;
		if (this.getRoundCount() == 1) {
			int firstTurn = whoFirst();
			return firstTurn;
		} else {
			for (int i = 0; i < this.players.size(); ++i) {
				if (this.players.get(i).isWinner()) {
					theirTurn = i;
				}
			}
			return theirTurn;
		}
	}

	// Choosing which card stat will be compared
	public String statPicker(ModelPlayer activePlayer) {
		Scanner scanner = new Scanner(System.in);
		String output;
		if (activePlayer.equals(this.user)) {
			int choice;
			do {
				System.out.println("Which category do you want to select?: ");
				choice = scanner.nextInt();
			} while (choice < 1 || choice > 5);
			output = this.attributeList[choice];
			return output;
		} else {
			ModelAIPlayer AI = (ModelAIPlayer) activePlayer;
			ModelCard AIActiveCard = AI.getActiveCard();
			return AI.selectHighest(AIActiveCard);
		}
	}

	// Compare stats, find a winner/winners
	public void performRound() {
		System.out.println("Round " + this.getRoundCount());
		
		if(this.players.contains(this.user)) {
			System.out.println("You drew " + this.user.getActiveCard().printCardInfo());
		}
		
		// display all player's card names
		for (int i = 1; i < this.players.size(); i++) {
			System.out.println(this.players.get(i).getName() + " has drawn " + this.players.get(i).getActiveCard().getName());
		}

		ModelPlayer activePlayer = this.players.get(this.turnTracker());
		String chosenAttribute = this.statPicker(activePlayer);
		
		Round round = new Round(this.players, activePlayer, chosenAttribute);

		// round.compareStat returns true if a winner was found, false if there is a
		// draw
		if (!round.compareStat()) {
			// on a draw, all cards go to the communal pile
			for (int i = 0; i < this.players.size(); i++) {
				this.cp.addCard(this.players.get(i).getActiveCard());
				this.players.get(i).removeFromHand(this.players.get(i).getActiveCard());
				if(this.players.get(i).getHand().size() <= 0) {
					this.players.remove(this.players.get(i));
				}
				round.getRoundWinner().setWinner(true);
			}
			this.printInfo();
		} else {
			// 1 winner: all cards go to winner, winner picks category
			this.redistributeCards(round.getRoundWinner());
			this.printInfo();
		}
		this.roundCount++;
	}

	public void redistributeCards(ModelPlayer winner) {
		boolean communalPileEmpty;
		if (this.cp.isEmpty()) {
			communalPileEmpty = true;
		} else {
			communalPileEmpty = false;
		}

		// Loop through all players and move card to winner's hand
		for (int i = 0; i < this.players.size(); i++) {
			// find the winning player
			if (!this.players.get(i).equals(winner)) {
				winner.addToHand(this.players.get(i).getActiveCard());
				this.players.get(i).removeFromHand(this.players.get(i).getActiveCard());
				if(this.players.get(i).getHand().size() <= 0) {
					this.players.remove(this.players.get(i));
				}
			}
		}

		ModelCard winningCard = winner.getActiveCard();
		winner.getHand().remove(winner.getHand().size() - 1);
		winner.getHand().add(0, winningCard);

		if (!communalPileEmpty) {
			this.cp.pickedUpByWinner(winner);
		}
	}

	// Counts the amount of players still in the game
	public boolean activePlayers() {
		if(this.players.size() > 1){
			return true;
		} else {
			return false;
		}
	}

	public int deckSize() {
		return this.deck.getCreatedCards();
	}

}
