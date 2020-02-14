package game;

import java.util.*;

/*
 * 
 * 	The Game class houses the logic for a game of Top Trumps.
 * 
 */

public class Game {

	private ModelPlayer user;
	private ModelDeck deck;
	private ModelCommunalPile cp;
	private ArrayList<ModelPlayer> players;
	private int roundCount;
	private String playerName = "Player One";
	private ModelPlayer winningPlayer;
	private ModelPlayer activePlayer;
	private HashMap<String, Integer> roundsWon;
	private int numRoundsDrawn;

	// Constructor takes a Deck objects and a number of players
	// numPlayers dictates how cards should be dealt out
	public Game(ModelDeck deck, int numPlayers) {
		this.user = new ModelPlayer(this.playerName);
		this.players = new ArrayList<ModelPlayer>();
		this.players.add(this.user);
		this.deck = deck;
		this.roundsWon = new HashMap<String, Integer>();
		this.numRoundsDrawn = 0;
		this.cp = this.deck.getCP();

		// Populate the AI array list with CPU players
		for (int i = 0; i < numPlayers; i++) {
			String playerName = "CPU-" + (i + 1);
			this.addPlayer(playerName);
		}

		// Add player names to roundsWon dictionary and set rounds won to 0
		this.roundsWon.put(this.user.getName(), 0);
		for (int i = 0; i < players.size(); ++i) {
			this.roundsWon.put(this.getPlayerName(i), 0);
		}

		// Initially, the winner (of the "previous round") and the
		// active player are null - they are decided in the next
		// method playerToGoFirst()
		this.winningPlayer = null;
		this.activePlayer = null;
		this.playerToGoFirst();

		this.roundCount = 1;
		this.dealDeck();
	}

	/*
	 * 
	 * Game Variable Manipulators
	 * 
	 */

	// Uses the whoFirst method to randomly select a first active player
	private void playerToGoFirst() {
		this.activePlayer = this.getPlayer(this.whoFirst());
		this.winningPlayer = this.activePlayer;
	}

	// Chooses which player is to start on 1st round
	private int whoFirst() {
		Random random = new Random();
		int max = this.players.size();

		int r = random.nextInt(max);
		return r;
	}

	// Increase number of rounds a player has won by 1
	private void incrementPlayerWinCount(ModelPlayer player) {
		Integer rounds = this.getRoundsWon(player);
		rounds++;
		this.roundsWon.put(player.getName(), rounds);
	}

	// Increments the number of rounds which ended in a draw
	private void incrementDrawCount() {
		this.numRoundsDrawn++;
	}

	// Add a player to the players array list
	private void addPlayer(String playerName) {
		this.players.add(new ModelAIPlayer(playerName));
	}

	// Remove a player from the players array list
	private void removePlayer(int i) {
		this.players.remove(getPlayer(i));
	}

	// Distribute shuffled deck amongst players
	public void dealDeck() {
		this.deck.deal(this.players);
	}

	// Increment the round counter
	public void advanceRound() {
		this.roundCount++;
	}

	// If a round was won by a player, give them all cards in the
	// communal pile, as well as all active players cards
	public void giveWinnerCards(ModelPlayer winner) {
		
		// Checks if there are cards in the communal pile
		boolean communalPileEmpty;
		if (this.cp.isEmpty()) {
			communalPileEmpty = true;
		} else {
			communalPileEmpty = false;
		}

		// Loop through all players and move card to winner's hand
		for (int i = 0; i < this.players.size(); i++) {
			// Take cards from all but the winning player
			if (!this.getPlayer(i).equals(winner)) {
				winner.addToHand(this.getPlayer(i).getActiveCard());
				this.getPlayer(i).removeFromHand(this.getPlayer(i).getActiveCard());
				// if the removal of a card from a player leaves them with 0
				// cards, they are removed from the players array list
				if (this.getPlayer(i).getHand().size() <= 0) {
					this.removePlayer(i);
				}
			}
		}

		// Takes the winners active card and adds it to the bottom of their hand
		ModelCard winningCard = winner.getActiveCard();
		winner.getHand().remove(winner.getHand().size() - 1);
		winner.getHand().add(0, winningCard);

		// If the communal pile had cards in it, they are shuffled
		// and added to the winners hand using the pickedUpByWinner method
		if (!communalPileEmpty) {
			Collections.shuffle(cp);
			this.cp.pickedUpByWinner(winner);
		}
	}

	// If the active player is the user, retrieve their chosen attribute
	// If an AI is active, pull the highest attribute from their active card.
	public String getStat(int choice) {
		String output = "";
		if (getActivePlayer().equals(this.user)) {
			output = this.user.getActiveCard().getAttribute(choice);
		} else {
			// Can cast from ModelPlayer to ModelAIPlayer as AI extends Player
			ModelAIPlayer AI = (ModelAIPlayer) getActivePlayer();
			ModelCard AIActiveCard = AI.getActiveCard();
			output = AI.selectHighest(AIActiveCard);
		}
		return output;
	}

	/*
	 * 
	 * Variable Checkers
	 * 
	 */

	// Compares the chosen stat and detects whether there is a single winner or a
	// draw. Returns true for a win or false for a draw.
	public boolean hasWinner(String stat) {
		ModelPlayer currentWinningPlayer = this.getRoundWinner();

		int drawCount = 0;
		for (int i = 0; i < getNumPlayers(); i++) {
			// Compare attributes from each players active card
			ModelPlayer otherPlayer = getPlayer(i);
			if (otherPlayer.equals(currentWinningPlayer))
				continue; // Don't compare the same player
			
			int otherAttributeHigher = compareHighestAttribute(otherPlayer, currentWinningPlayer, stat);
			if (otherAttributeHigher == 1) {
				currentWinningPlayer = otherPlayer; // otherPlayer is new currentWinningPlayer
				drawCount = 0;
			} else if (otherAttributeHigher == 0) {
				drawCount++;
			}
		}

		// A draw is only counted when the two HIGHEST valued cards draw
		// If this did not happen, drawCount = 0
		if (drawCount < 1) {
			this.setRoundWinner(currentWinningPlayer);
			this.setActivePlayer(currentWinningPlayer);
			this.incrementPlayerWinCount(currentWinningPlayer);
			return true;
		} else {
			this.roundWasDraw();
		}

		return false;
	}

	// On a draw, all cards go to the communal pile
	private void roundWasDraw() {
		for (int i = 0; i < this.players.size(); i++) {
			this.cp.addCard(this.players.get(i).getActiveCard());
			this.players.get(i).removeFromHand(this.players.get(i).getActiveCard());
			if (this.players.get(i).getHand().size() <= 0) {
				this.players.remove(this.players.get(i));
			}
		}
		this.incrementDrawCount();
	}

	// p1 attribute higher return 1, equal return 0, otherwise return -1
	private int compareHighestAttribute(ModelPlayer p1, ModelPlayer p2, String attr) {
		if (p1.getActiveCard().getValue(attr) > p2.getActiveCard().getValue(attr))
			return 1;
		if (p1.getActiveCard().getValue(attr) == p2.getActiveCard().getValue(attr))
			return 0;
		return -1;
	}

	// It's either the user's turn or it isn't
	public boolean usersTurn() {
		if (getActivePlayer() == getUser())
			return true;
		return false;
	}

	// Checks to see whether the User is still in the game
	// Speeds up User-loss conditions in command line version
	public boolean userActive() {
		if (this.players.contains(this.user))
			return true;
		return false;
	}

	// Check to see who is the winner of the previous round
	public boolean isWinningPlayer(ModelPlayer player) {
		if (player.equals(this.getRoundWinner()))
			return true;
		return false;
	}

	// Counts the number of players still in the game
	public boolean activePlayers() {
		if (this.players.size() > 1) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 
	 * Setter Methods
	 * 
	 */

	public void setActivePlayer(ModelPlayer player) {
		this.activePlayer = player;
	}

	public void setRoundWinner(ModelPlayer player) {
		this.winningPlayer = player;
	}

	/*
	 * 
	 * Getter Methods
	 * 
	 */
	public int getRoundCount() {
		return this.roundCount;
	}

	public ModelPlayer getUser() {
		return this.user;
	}

	public ModelPlayer getPlayer(int i) {
		return players.get(i);
	}

	public String getPlayerName(int i) {
		return players.get(i).getName();
	}

	public ModelDeck getDeck() {
		return this.deck;
	}

	public ArrayList<ModelPlayer> getPlayers() {
		return this.players;
	}

	public int getNumPlayers() {
		return this.players.size();
	}

	public ModelPlayer getRoundWinner() {
		return winningPlayer;
	}

	public ModelPlayer getActivePlayer() {
		return this.activePlayer;
	}

	// get number of rounds that were a draw
	public int getDrawCount() {
		return this.numRoundsDrawn;
	}

	// number of rounds a player has won
	public Integer getRoundsWon(String playerName) {
		return roundsWon.get(playerName);
	}

	// convenience method for number of rounds a player has won
	private Integer getRoundsWon(ModelPlayer player) {
		return getRoundsWon(player.getName());
	}

	public int deckSize() {
		return this.deck.getCreatedCards();
	}

	public int communalDeckSize() {
		return this.cp.size();
	}

	// prints the roundsWon HashMap for debugging
	public String printRoundsWon() {
		String output = "";
		for (String s : this.roundsWon.keySet()) {
			output += s + ": " + this.roundsWon.get(s) + " rounds won.\n";
		}
		return output;
	}
}
