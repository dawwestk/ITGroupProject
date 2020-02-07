package game;

import java.util.ArrayList;

public class ModelCommunalPile extends ArrayList<ModelCard>{

    private int numberOfCards;
    private boolean empty;

    public ModelCommunalPile() {
        numberOfCards = 0;
        empty = true;
    }

    public void addCard(ModelCard card) {
        this.add(card);
        numberOfCards++;
        this.empty = false;
    }

    public void pickedUpByWinner(ModelPlayer player) {
        for(ModelCard card : this) {
            player.addToHand(card);
        }
        this.clear();
        this.numberOfCards = 0;
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