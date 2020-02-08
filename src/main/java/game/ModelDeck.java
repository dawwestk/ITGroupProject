package game;

import java.util.ArrayList;
import java.util.Random;

public class ModelDeck {

    private int totalCards;
    private int createdCards;
    private ArrayList<ModelCard> initialArrayOfCards;
    private ArrayList<ModelCard> shuffled;
    private ArrayList<Integer> shuffledIndex;
    private ModelCommunalPile communalPile;

    public ModelDeck() {
        totalCards = 40;		// hard coded for now as specified
        createdCards = 0;
        initialArrayOfCards = new ArrayList<ModelCard>();
        communalPile = new ModelCommunalPile();
    }
    
//    private void initializeShuffledDeck() {
//        shuffled = new ArrayList<ModelCard>();
//        for()
//    }

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
        initialArrayOfCards = shuffled; // hack because we use two arrays for the deck, consider using a single array
    }

    public void deal(ArrayList<ModelPlayer> players) {
        int handSize = totalCards / players.size();		// can use integer division because we want all hands to be the same size (no partial cards)
        int remainingCards = totalCards % players.size();

        //System.out.println("Expecting hand size of " + handSize + ", with " + remainingCards + " left over.");
        int offset = 0;
        for(int i = 0; i < players.size(); i++) {
            //System.out.println("\ni = " + i);
            //System.out.println("shuffled: " + shuffled.size());
            for(int j = 0; j < handSize; j++) {
                //System.out.print(shuffled.get(j + offset).getName() + " [" + (j + offset) + "], ");
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

    	// shuffle() now called in Game
//        if(createdCards == totalCards) {
//            shuffle();
//        }
    }

    public int getCreatedCards() {
        return createdCards;
    }
    
    public String toString() {
    	String deckString = "";
    	for(ModelCard card: initialArrayOfCards) {
    		deckString += card.toString();
    		deckString += "\n";
    	}
    	return deckString;
    }

}