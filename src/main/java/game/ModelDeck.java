package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/*
 * 
 * 	The deck contains all cards in the game of Top Trumps.
 * 	It is populated using a Deck Builder object.
 * 
 * 	It contains a reference to both the initial deck (as built from a text file)
 * 		and a shuffled deck (used in gameplay)
 * 
 * 	The initial deck is kept separate for logging purposes.
 * 
 */


public class ModelDeck {

    private int totalCards;
    private int createdCards;
    private ArrayList<ModelCard> initialArrayOfCards;
    private ArrayList<ModelCard> shuffled;
    private ArrayList<Integer> shuffledIndex;
    private ModelCommunalPile communalPile;

    public ModelDeck(String filename) throws IOException{
        totalCards = 40;		// hard coded for now as specified
        createdCards = 0;
        initialArrayOfCards = new ArrayList<ModelCard>();
        communalPile = new ModelCommunalPile();
        ModelDeckBuilder deckBuilder = new ModelDeckBuilder(this, filename);
    }
    
    public int initialDeckSize(){
        return this.initialArrayOfCards.size();
    }

    public int shuffledDeckSize(){
        return this.shuffled.size();
    }

    public void shuffle() {
        shuffled = new ArrayList<ModelCard>();
        shuffledIndex = new ArrayList<Integer>();
        Random r = new Random();
        for(int i = 0; i < totalCards; i++) {
            int rand = r.nextInt(totalCards);
            while(shuffledIndex.contains(rand)) {
                rand = r.nextInt(totalCards);
            }
            shuffled.add(initialArrayOfCards.get(rand));
            shuffledIndex.add(rand);
        }
    }

    public void deal(ArrayList<ModelPlayer> players) {
        int handSize = totalCards / players.size();		// can use integer division because we want all hands to be the same size (no partial cards)
        int remainingCards = totalCards % players.size();

        int offset = 0;
        for(int i = 0; i < players.size(); i++) {
            for(int j = 0; j < handSize; j++) {
                players.get(i).addToHand(shuffled.get(j + offset));
            }
            offset += handSize;
        }

        if(remainingCards > 0) {
            for(int i = 0; i < remainingCards; i++) {
                communalPile.addCard(shuffled.get(totalCards - i - 1));
            }
        }

    }

    public ModelCommunalPile getCP() {
        return communalPile;
    }

    public ModelCard getCard(int index) {
        return shuffled.get(index);
    }

    public void addCard(ModelCard card) {
    	if(this.createdCards < this.totalCards) {
	    	createdCards++;
	        initialArrayOfCards.add(card);
    	}
    }

    public int getCreatedCards() {
        return createdCards;
    }
    
    public String toString() {
    	String deckString = "";
    	for(ModelCard card: shuffled) {
    		deckString += card.toString();
    		deckString += "\n";
    	}
    	return deckString;
    }
    
    public String printInitialDeck() {
    	String deckString = "";
    	for(ModelCard card: initialArrayOfCards) {
    		deckString += card.toString();
    		deckString += "\n";
    	}
    	return deckString;
    }

}