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

    public void addCard(ModelCard card) {
        this.add(card);
        this.empty = false;
    }

    public void pickedUpByWinner(ModelPlayer player) {
        for(ModelCard card : this) {
            player.addToHand(card);
        }
        this.clear();
        empty = true;
    }

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