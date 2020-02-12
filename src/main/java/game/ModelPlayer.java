package game;

import java.util.ArrayList;

/*
 * 
 * 	The ModelPlayer object represents a player in the TopTrumps game
 * 
 * 	Each player has a name, a hand (collection of cards) and a flag 
 * 		which indicates whether they won the last round of play
 * 
 */

public class ModelPlayer {

    private String name;
    private ArrayList<ModelCard> hand;

    public ModelPlayer(String name) {
        this.name = name;
        hand = new ArrayList<ModelCard>();
    }
    
    /*
     * 
     * 	Getter Methods
     * 
     */
    
    public ModelCard getActiveCard() {
    	// links the last card in the player's hand for use in Round object
    	return hand.get(hand.size()-1);
    }
    
    public String getActiveCardName() {
    	return getActiveCard().getName();
    }

    public ArrayList<ModelCard> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return name + ":\t" + hand.size() + " cards in hand.";
    }
    
    public String toString() {
    	String playerString = this.getName();    	
    	for(ModelCard card: this.hand) {
    		playerString += "\n";
    		playerString += card.toString();
    	}
    	return playerString;
    }
    
    /*
     * 
     * 	Setter Methods
     * 
     */
    
    // Adds a card to the Player's hand at index 0
    public void addToHand(ModelCard card) {
        hand.add(0, card);
    }

    public void removeFromHand(ModelCard card) {
        hand.remove(card);
    }

}