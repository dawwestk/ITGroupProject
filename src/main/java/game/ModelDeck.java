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
        
        // The deck builder is used to populate the deck from the specified file
        ModelDeckBuilder deckBuilder = new ModelDeckBuilder(this, filename);
        shuffle();
        
    }

    // Creates a new ArrayList of cards and randomly allocates cards from
    // the initial list to it.
    private void shuffle() {
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
    
    // Populates each Player object's hand with the same number of cards
    public void deal(ArrayList<ModelPlayer> players) {
        if (players.size() < 1) throw new IllegalArgumentException("No players to deal cards to");
    	// We can use integer division because we want all hands to be the 
    	// same size (no partial cards allowed - remainder is communal)
    	int handSize = totalCards / players.size();		
        int remainingCards = totalCards % players.size();
        
        // A simple counter to keep track of the next set of cards to be allocated
        int offset = 0;
        for(int i = 0; i < players.size(); i++) {
            for(int j = 0; j < handSize; j++) {
                players.get(i).addToHand(shuffled.get(j + offset));
            }
            offset += handSize;
        }

        // Remaining cards not in a Player's hand are added to the communal pile
        if(remainingCards > 0) {
            for(int i = 0; i < remainingCards; i++) {
                communalPile.addCard(shuffled.get(totalCards - i - 1));
            }
        }

    }

    // Used to add a card to the deck (initial arraylist, pre-shuffle)
    public void addCard(ModelCard card) {
    	if(this.createdCards < this.totalCards) {
	    	createdCards++;
	        initialArrayOfCards.add(card);
    	}
    }
    
    /*
     * 
     * 	Getter Methods
     * 
     */
    
    public ModelCommunalPile getCP() {
        return communalPile;
    }

    public ModelCard getCard(int index) {
        return shuffled.get(index);
    }

    public int getCreatedCards() {
        return createdCards;
    }
    
    // The "normal" toString is used to return the shuffled deck
    public String toString() {
    	String deckString = "";
    	for(ModelCard card: shuffled) {
    		deckString += card.toString();
    		deckString += "\n";
    	}
    	return deckString;
    }
    
    // While this method is used exclusively by the logger to output
    // the initial deck as it was before it was shuffled
    public String printInitialDeck() {
    	String deckString = "";
    	for(ModelCard card: initialArrayOfCards) {
    		deckString += card.toString();
    		deckString += "\n";
    	}
    	return deckString;
    }

}