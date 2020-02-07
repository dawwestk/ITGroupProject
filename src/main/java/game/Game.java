package game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
	int numRoundsDrawn;

	
	public Game(ModelDeck deck, int numPlayers) {
		this.user = new ModelPlayer(this.playerName);
		this.players = new ArrayList<ModelPlayer>();
		this.players.add(this.user);
		this.deck = deck;
		this.roundsWon = new HashMap<String,Integer>();		
		this.numRoundsDrawn = 0;
		this.deck.shuffle(); // moved from ModelDeck
		this.cp = this.deck.getCP();
		
		for (int i = 0; i < numPlayers; i++) {
			String playerName = "CPU-" + (i + 1);
			this.addPlayer(playerName);
		}
		
		// Add player names to roundsWon and set rounds won to 0
		this.roundsWon.put(this.user.getName(), 0);
		for(int i = 0 ; i < players.size(); ++i) {
			this.roundsWon.put(this.getPlayerName(i), 0);
		}
		
		this.winningPlayer = null;
		this.activePlayer = null;
		this.playerToGoFirst();
		
		this.roundCount = 1;
		this.dealDeck();

	}
	
	public Game(ModelDeck deck) {
		this.user = new ModelPlayer(this.playerName);
		this.players = new ArrayList<ModelPlayer>();
		this.players.add(this.user);
		this.deck = deck;
		this.roundsWon = new HashMap<String,Integer>();		
		this.numRoundsDrawn = 0;
		
		this.cp = this.deck.getCP();
		
		this.winningPlayer = null;
		this.activePlayer = null;
		
		this.roundCount = 1;
	}
	
	public void setNumberOfPlayersAndDeal(int numPlayers) {
		for (int i = 0; i < numPlayers; i++) {
			String playerName = "CPU-" + (i + 1);
			this.addPlayer(playerName);
		}
		// Add player names to roundsWon and set rounds won to 0
		this.roundsWon.put(this.user.getName(), 0);
		for(int i = 0 ; i < players.size(); ++i) {
			this.roundsWon.put(this.getPlayerName(i), 0);
		}
		this.playerToGoFirst();
		this.dealDeck();
	}
	
	// prints the roundsWon HashMap for debugging
	public String printRoundsWon() {
		String output = "";
		for(String s : this.roundsWon.keySet()) {
			output += s + ": " + this.roundsWon.get(s) + " rounds won.\n";
		}
		return output;
	}

	// number of rounds a player has won 
	public Integer getRoundsWon(String playerName) {
		return roundsWon.get(playerName);
	}
	
	// convenience method for number of rounds a player has won
	private Integer getRoundsWon(ModelPlayer player) {
		return getRoundsWon(player.getName());
	}
	
	private void playerToGoFirst() {
		this.activePlayer = this.getPlayer(this.whoFirst());
		this.winningPlayer = this.activePlayer;
	}
	
//	public boolean checkIfWinner(String stat) {	
//		boolean hasWinner = this.hasWinner(stat);
//		if (!hasWinner) {
//		} else {
//		}
//		return hasWinner;
//	}

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
		ModelPlayer currentWinningPlayer = this.getRoundWinner();
				
		int drawCount = 0;		
		for (int i = 0; i < getNumPlayers(); i++) {
			// compare attributes
			// if activePlayer attribute > current winningPlayer attribute
			ModelPlayer otherPlayer = getPlayer(i);
			if(otherPlayer.equals(currentWinningPlayer)) continue; // Don't compare the same player
			int otherAttributeHigher = compareHighestAttribute(otherPlayer, currentWinningPlayer, stat);
			if (otherAttributeHigher == 1) {
				currentWinningPlayer = otherPlayer; // otherPlayer is new currentWinningPlayer
				drawCount = 0;
			}else if (otherAttributeHigher == 0) {
				drawCount++;
			}
		}

		// if no draws
		if (drawCount<1) {
			this.setRoundWinner(currentWinningPlayer);
			this.setActivePlayer(currentWinningPlayer);
			this.incrementPlayerWinCount(currentWinningPlayer);
			return true;
		} else {
			this.roundWasDraw();
		}

		return false;
	}
	
	// increase number of rounds a player has won by 1
	private void incrementPlayerWinCount(ModelPlayer player) {
		Integer rounds = this.getRoundsWon(player);
		rounds++;
		this.roundsWon.put(player.getName(), rounds);
	}
	
	// on a draw, all cards go to the communal pile
	private void roundWasDraw() {
		for (int i = 0; i < this.players.size(); i++) {
			this.cp.addCard(this.players.get(i).getActiveCard());
			this.players.get(i).removeFromHand(this.players.get(i).getActiveCard());
			if(this.players.get(i).getHand().size() <= 0) {
				this.players.remove(this.players.get(i));
			}
		}
		this.incrementDrawCount();
	}	
	
	private void incrementDrawCount() {
		this.numRoundsDrawn++;
	}
	
	// get number of rounds that were a draw
	public int getDrawCount() {
		return this.numRoundsDrawn;
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
		
		//return r.nextInt((max - min) + 1) + min	<-- format for random between 2 ints (min = 0, max = players.size())
		int r = random.nextInt(max + 1);
		return r;
	}

	public void setActivePlayer(ModelPlayer player) {
		this.activePlayer = player;
	}

	public ModelPlayer getActivePlayer() {
		return this.activePlayer;
	}
	
	public void setRoundWinner(ModelPlayer player) {
		this.winningPlayer = player;		
	}

	public ModelPlayer getRoundWinner() {
		return winningPlayer;
	}
	
	public boolean userActive() {
		if(this.players.contains(this.user)) return true;
		return false;
	}

	public boolean isWinningPlayer(ModelPlayer player) {
		if(player.equals(this.getRoundWinner())) return true;
		return false;
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

	public void advanceRound() {
		this.roundCount++;
	}
}
