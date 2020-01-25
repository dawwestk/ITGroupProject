package game;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

	private ModelPlayer user;
	private ModelDeck deck;
	private ModelCommunalPile cp;
	private ArrayList<ModelPlayer> players;
	private int roundCount;
	private String playerName = "Player One";
	private ModelPlayer winningPlayer;

	public Game(ModelDeck deck, int numPlayers) {
		this.user = new ModelPlayer(this.playerName);
		this.players = new ArrayList<ModelPlayer>(numPlayers);
		this.players.add(this.user);
		this.deck = deck;
		this.winningPlayer = this.user;
		this.cp = this.deck.getCP();
		for (int i = 0; i < numPlayers; i++) {
			String playerName = "CPU-" + (i + 1);
			this.addPlayer(playerName);
		}
		this.roundCount = 1;
		this.dealDeck();
	}

	public boolean checkIfWinner(String stat) {	
		boolean hasWinner = this.hasWinner(stat);
		if (!hasWinner) {
			this.roundWasDraw();
		} else {
			this.giveWinnerCards(this.getRoundWinner());
		}
		return hasWinner;
	}

	public void advanceRound() {
		this.roundCount++;
	}

	// If active player is user, return their choice of Stat. Otherwise have AIPlayer choose their highest card.
	public String getStat(int choice) {
		String output = "";
		if (getActivePlayer().equals(this.user)) {
			output = this.user.getActiveCard().getAttribute(choice);
		} else {
			ModelAIPlayer AI = (ModelAIPlayer) getActivePlayer();
			ModelCard AIActiveCard = AI.getActiveCard();
			output = AI.selectHighest(AIActiveCard);
		}
		return output;
	}

	// Compares the chosen stat and detects whether there is a single winner or a draw. Returns true for a win or null for a draw.
	public boolean hasWinner(String stat) {
		int drawCount = 0;
		ModelPlayer currentWinningPlayer = this.winningPlayer;
		for (int i = 1; i < getNumPlayers(); i++) {
			// compare attributes
			// if activePlayer attribute > current winningPlayer attribute
			ModelPlayer otherPlayer = getPlayer(i);
			int otherAttributeHigher = compareHighestAttribute(otherPlayer, currentWinningPlayer, stat);
			if (otherAttributeHigher == 1) {
				currentWinningPlayer = otherPlayer;

				currentWinningPlayer.setWinner(true);
				this.winningPlayer = currentWinningPlayer;

				drawCount = 0;
			}else if (otherAttributeHigher == 0) {
				drawCount++;
				currentWinningPlayer.setWinner(false);            	
				otherPlayer.setWinner(false);
			}else {
				otherPlayer.setWinner(false);
			}
		}

		// if no draws
		if (drawCount<1) {
			this.winningPlayer = currentWinningPlayer;
			return true;
		}

		return false;
	}


	// p1 attribute higher return 1, equal return 0, otherwise return -1
	private int compareHighestAttribute(ModelPlayer p1, ModelPlayer p2, String attr) {
		if(p1.getActiveCard().getValue(attr) > p2.getActiveCard().getValue(attr)) return 1;
		if(p1.getActiveCard().getValue(attr) == p2.getActiveCard().getValue(attr)) return 0;
		return -1;
	}

	// It's either the user's turn or it isn't
	public boolean usersTurn() {
		if(getActivePlayer() == getUser()) return true;
		return false;
	}

	// Choosing which player is to start on 1st round
	private int whoFirst() {
		Random random = new Random();
		int max = this.players.size();
		return random.nextInt(max - 1) + 1;
	}

	// Checking who the stat-picking player will be this round
	public int turnTracker() {
		int theirTurn = 0;

		// if round 1 choose random player to go first
		if (this.getRoundCount() == 1) {
			int firstTurn = whoFirst();
			return firstTurn;
		} else { // choose winning player from last round
			for (int i = 0; i < this.players.size(); ++i) {
				if (this.players.get(i).isWinner()) {
					theirTurn = i;
				}
			}
			return theirTurn;
		}
	}

	// on a draw, all cards go to the communal pile
	private void roundWasDraw() {
		for (int i = 0; i < this.players.size(); i++) {
			this.cp.addCard(this.players.get(i).getActiveCard());
			this.players.get(i).removeFromHand(this.players.get(i).getActiveCard());
			if(this.players.get(i).getHand().size() <= 0) {
				this.players.remove(this.players.get(i));
			}
			this.getRoundWinner().setWinner(true);
		}
	}

	public boolean userActive() {
		if(this.players.contains(this.user)) return true;
		return false;
	}

	public ModelPlayer getRoundWinner() {
		return winningPlayer;
	}

	public void giveWinnerCards(ModelPlayer winner) {
		boolean communalPileEmpty;
		if (this.cp.isEmpty()) {
			communalPileEmpty = true;
		} else {
			communalPileEmpty = false;
		}

		// Loop through all players and move card to winner's hand
		for (int i = 0; i < this.players.size(); i++) {
			// find the winning player
			if (!this.getPlayer(i).equals(winner)) {
				winner.addToHand(this.getPlayer(i).getActiveCard());
				this.getPlayer(i).removeFromHand(this.getPlayer(i).getActiveCard());
				if(this.getPlayer(i).getHand().size() <= 0) {
					this.removePlayer(i);
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
	
	public int communalDeckSize() {
		return this.cp.size();
	}

	private void addPlayer(String playerName) {
		this.players.add(new ModelAIPlayer(playerName));
	}

	private void removePlayer(int i) {
		this.players.remove(getPlayer(i));
	}

	// Getters
	public int getRoundCount() {
		return this.roundCount;
	}

	public ModelPlayer getUser() {
		return this.user;
	}

	public ModelPlayer getPlayer(int i) {
		return players.get(i);
	}

	public ModelPlayer getActivePlayer() {
		ModelPlayer activePlayer = this.players.get(this.turnTracker());
		return activePlayer;
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

	// Distribute shuffled deck amongst players
	public void dealDeck() {
		this.deck.deal(this.players);
	}

	// Print general game info
	public void printInfo() {
	}

}
