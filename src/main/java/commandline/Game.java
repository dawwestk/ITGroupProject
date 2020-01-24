package commandline;

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
		this.user = new ModelPlayer(playerName);
		this.deck = new ModelDeck();
		
		this.cp = deck.getCP();
		this.players = new ArrayList<ModelPlayer>();
		this.roundCount = 1;
	}

	// Getters
	public int getRoundCount() {
		return roundCount;
	}

	public ModelPlayer getUser() {
		return user;
	}

	public ModelDeck getDeck() {
		return deck;
	}

	public ArrayList<ModelPlayer> getPlayers() {
		return players;
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
				// System.out.println(text.nextLine());
				String shipInfo = text.nextLine();
				String[] stats = shipInfo.split(" ");

				if (stats[0].toLowerCase().equals("description")) {
					// System.out.println("Here are the stats: " + shipInfo);
					attributeList = new String[stats.length];
					attributeList = stats;
				} else {
					ModelCard card = new ModelCard(stats, attributeList);
					deck.addCard(card);
					// System.out.println("Making card: " + stats[0] + ", added to deck");
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
		players.add(user);
		Scanner keyboard = new Scanner(System.in);
		int opponents = chooseOpponents(keyboard);

		while (opponents <= 0 || opponents >= 5) {
			System.out.println("Sorry, you must face 1-4 opponents.");
			opponents = chooseOpponents(keyboard);
		}

		for (int i = 0; i < opponents; i++) {
			ModelAIPlayer opponent = new ModelAIPlayer("CPU-" + (i + 1));
			players.add(opponent);
		}
	}

	// Distribute shuffled deck amongst players
	public void dealDeck() {
		deck.deal(players);
	}

	// Print general game info
	public void printInfo() {
		for (int i = 0; i < players.size(); i++) {
			System.out.println(players.get(i).getInfo());
		}
		if (cp.isEmpty()) {
			System.out.println("\nCommunalPile is empty.\n");
		} else {
			System.out.println("\nCommunalPile has: " + cp.size() + " cards in it.\n");
		}
	}

	// Making an array of active cards (Cards at top of each players deck)
	// public ModelCard[] onTheTableGetter() {
	// ModelCard[] onTheTable = new ModelCard[players.size()];
	// for (int i = 0; i < players.size(); i++) {
	// onTheTable[i] = players.get(i).getHand().get(players.get(i).getHand().size()
	// - 1);
	// }
	// return onTheTable;
	// }
	

	// Choosing which player is to start on 1st round
	public int whoFirst() {
		Random random = new Random();
		int max = players.size();
		return random.nextInt(max - 1) + 1;
	}

	// Checking who the stat-picking player will be this round
	public int turnTracker() {
		int theirTurn = 0;
		if (roundCount == 1) {
			int firstTurn = whoFirst();
			//System.out.println("It is " + players.get(firstTurn).getName() + "'s turn.");
			return firstTurn;
		} else {
			for (int i = 0; i < players.size(); ++i) {
				if (players.get(i).isWinner()) {
					theirTurn = i;
				}
			}
			//System.out.println("It is " + players.get(theirTurn).getName() + "'s turn.");
			return theirTurn;
		}
	}

	// Choosing which card stat will be compared
	public String statPicker(ModelPlayer activePlayer) {
		Scanner scanner = new Scanner(System.in);
		String output;
		if (activePlayer.equals(user)) {
			int choice;
			System.out.println("Which category do you want to select?: ");
			do {
				choice = scanner.nextInt();
			} while (choice < 1 || choice > 5);
			output = attributeList[choice - 1];
			return output;
		} else {
			ModelAIPlayer AI = (ModelAIPlayer) activePlayer;
			ModelCard AIActiveCard = AI.getActiveCard();
			return AI.selectHighest(AIActiveCard);
		}
	}

	// Compare stats, find a winner/winners
	public void performRound() {
		System.out.println("Round " + roundCount);
		
		if(players.contains(user)) {
			System.out.println("You drew " + user.getActiveCard().printCardInfo());
		}
		
		// display all player's card names
		for (int i = 1; i < players.size(); i++) {
			System.out.println(players.get(i).getName() + " has drawn " + players.get(i).getActiveCard().getName());
		}

		ModelPlayer activePlayer = players.get(turnTracker());
		String chosenAttribute = statPicker(activePlayer);
		
		Round round = new Round(players, activePlayer, chosenAttribute);

		// round.compareStat returns true if a winner was found, false if there is a
		// draw
		if (!round.compareStat()) {
			// on a draw, all cards go to the communal pile
			for (int i = 0; i < players.size(); i++) {
				cp.addCard(players.get(i).getActiveCard());
				players.get(i).removeFromHand(players.get(i).getActiveCard());
				if(players.get(i).getHand().size() <= 0) {
					players.remove(players.get(i));
				}
				round.getRoundWinner().setWinner(true);
			}
			printInfo();
		} else {
			// 1 winner: all cards go to winner, winner picks category
			redistributeCards(round.getRoundWinner());
			printInfo();
		}
		roundCount++;
	}

	public void redistributeCards(ModelPlayer winner) {
		boolean communalPileEmpty;
		if (cp.isEmpty()) {
			communalPileEmpty = true;
		} else {
			communalPileEmpty = false;
		}

		// Loop through all players and move card to winner's hand
		for (int i = 0; i < players.size(); i++) {
			// find the winning player
			if (!players.get(i).equals(winner)) {
				winner.addToHand(players.get(i).getActiveCard());
				players.get(i).removeFromHand(players.get(i).getActiveCard());
				if(players.get(i).getHand().size() <= 0) {
					players.remove(players.get(i));
				}
			}
		}

		ModelCard winningCard = winner.getActiveCard();
		winner.getHand().remove(winner.getHand().size() - 1);
		winner.getHand().add(0, winningCard);

		if (!communalPileEmpty) {
			cp.pickedUpByWinner(winner);
		}
	}

	// Counts the amount of players still in the game
	public boolean activePlayers() {
		if(players.size() > 1){
			return true;
		} else {
			return false;
		}
	}

	public int deckSize() {
		return deck.getCreatedCards();
	}

}
