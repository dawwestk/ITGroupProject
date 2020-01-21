package commandline;

import java.util.ArrayList;

public class ModelPlayer {

    private String name;
    private ArrayList<ModelCard> hand;
    private boolean isWinner;

    public ModelPlayer(String name) {
        this.name = name;
        hand = new ArrayList<ModelCard>();
        isWinner = false;
    }

    public ArrayList<ModelCard> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public boolean isWinner(){
        return isWinner;
    }

    // either pass one card at a time
    // or pass in ArrayList object containing all cards to be added
    public void addToHand(ModelCard card) {
        hand.add(card);
    }

    public void removeFromHand(ModelCard card) {
        hand.remove(card);
    }


    // used AFTER cards are dealt to check whether player is still in the game
    // end of each round (winning condition of the game)
    public boolean isHandEmpty() {
        if(hand.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public String getInfo() {
        return name + ":\t" + hand.size() + " cards in hand.";
    }

}