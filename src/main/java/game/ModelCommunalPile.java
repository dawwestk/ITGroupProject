package game;

import java.util.ArrayList;

/*
 * 
 * 	The communal pile acts as a storage location for cards in the event of a draw. 
 * 
 */

public class ModelCommunalPile extends ArrayList<ModelCard>{

    private boolean empty;

    public ModelCommunalPile() {
        empty = true;
    }

    // Adds a card to the communal pile - thus, it is no longer empty
    public void addCard(ModelCard card) {
        this.add(card);
        this.empty = false;
    }

    // When a player wins, all cards from the communal pile are
    // added to their hand (then the pile is empty again)
    public void pickedUpByWinner(ModelPlayer player) {
        for(ModelCard card : this) {
            player.addToHand(card);
        }
        this.clear();
        empty = true;
    }
    
    /*
     * 
     * 	Getter Methods
     * 
     */

    public boolean isEmpty() {
        return empty;
    }
   
    public String toString() {
    	String communalString = "";
    	for(ModelCard card: this) {
    		communalString += card.toString();
    		communalString += "\n";
    	}
    	return communalString;
    }

}