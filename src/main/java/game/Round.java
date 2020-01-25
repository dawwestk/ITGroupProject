package game;

import java.util.ArrayList;

public class Round {
    private ArrayList<ModelPlayer> activePlayers;
    private int stat;
    private String attr;
    private ModelPlayer activePlayer;
    private ModelPlayer winningPlayer;

    public Round(ArrayList<ModelPlayer> players, ModelPlayer active, String s) {
    	this.activePlayers = players;
    	this.activePlayer = active;
    	this.winningPlayer = activePlayers.get(0);
    	this.attr = s;
        displayRoundInfo();
    }
    
    public void displayRoundInfo() {
        System.out.println("It is " + this.activePlayer.getName() + "'s turn.");
        System.out.println(this.activePlayer.getName() + " picked attribute " + this.attr +"\n");    // note this is array index, not numbered attribute
        System.out.println("Score to beat is: " + this.activePlayer.getActiveCard().getValue(this.attr) + "\n");
        System.out.println("\t" + this.activePlayer.getActiveCard().printCardInfo());
    }

// Finds the highest value present among the chosen stat 
//  public int findHighestValue(ArrayList<ModelPlayer> compare) {
//      int highestValue = -1;
//      for (int i = 0; i < compare.size(); i++) {
//          if(compare.get(i).getActiveCard().getValue(attr) > highestValue) {
//              highestValue = compare.get(i).getActiveCard().getValue(attr);
//          }
//      }
//      return highestValue;
//  }
    
    // p1 attribute higher return 1, equal return 0, otherwise return -1
    private int compareHighestAttribute(ModelPlayer p1, ModelPlayer p2) {
    	if(p1.getActiveCard().getValue(attr) > p2.getActiveCard().getValue(attr)) return 1;
    	if(p1.getActiveCard().getValue(attr) == p2.getActiveCard().getValue(attr)) return 0;
    	return -1;
    }

    // Compares the chosen stat and detects whether there is a single winner or a draw. Returns true for a win or null for a draw.
    public boolean hasWinner() {
        int drawCount = 0;
        ModelPlayer currentWinningPlayer = this.winningPlayer;
        for (int i = 1; i < activePlayers.size(); i++) {
        	// compare attributes
        	// if activePlayer attribute > current winningPlayer attribute
        	ModelPlayer otherPlayer = activePlayers.get(i);
        	int otherAttributeHigher = compareHighestAttribute(otherPlayer, currentWinningPlayer);
            if (otherAttributeHigher == 1) {
            	currentWinningPlayer = otherPlayer;
            	currentWinningPlayer.setWinner(true);
                drawCount = 0;
            }else if (otherAttributeHigher == 0) {
            	drawCount++;
            	currentWinningPlayer.setWinner(false);
                otherPlayer.setWinner(false);
            }else {
            	otherPlayer.setWinner(false);
            }
            
        }
        
        this.winningPlayer = currentWinningPlayer;
        
        if (drawCount<1) {
        	this.activePlayer = this.winningPlayer;
            System.out.println(this.winningPlayer.getName() + " has won!  His card was: " + this.winningPlayer.getActiveCard().getName() + " and it's " + this.attr + " attribute was " + this.winningPlayer.getActiveCard().getValue(attr)+"\n");
            return true;
        } else {
            System.out.println("There has been a draw.\n");
            this.winningPlayer = this.activePlayer;
            return false;
        }
    }

    public ModelPlayer getRoundWinner() {
        return winningPlayer;
    }

// Testing Round. Currently if 1st Player wins result is always draw[Fixed]
//  public static void main(String[] args) {
//	  ArrayList<ModelPlayer> testPlayers = new ArrayList<ModelPlayer>();
//	  ModelPlayer testActive = new ModelPlayer("Player1"); testPlayers.add(testActive);
//	  ModelPlayer CPU1 = new ModelPlayer("Player2");testPlayers.add(CPU1);
//	  ModelPlayer CPU2 = new ModelPlayer("Player3");testPlayers.add(CPU2);
//	  ModelCard Active1 = new ModelCard(new String[] {"Card1", "1", "12", "12", "4", "20"});testActive.addToHand(Active1);
//	  ModelCard Active2 = new ModelCard(new String[] {"Card2", "0", "1", "8", "3", "12"});CPU1.addToHand(Active2);
//	  ModelCard Active3 = new ModelCard(new String[] {"Card3", "20", "112", "3", "2", "14"});CPU2.addToHand(Active3);
//	  Round round = new Round(testPlayers, testActive, 2);
//	  round.compareStat();
//  }
//  public boolean returnWinner(ModelPlayer winner) {
//	  
//  }
// In the event of multiple winners, an array of indexes will be returned
//  public int[] Draw() {
//      ArrayList<Integer> winnersList = new ArrayList<Integer>();
//      int[] winnersArray;
//      for(int i = 0; i < onTheTable.length; i++){
//          if (onTheTable[i].getValue(attr) == highestValue) {
//              winnersList.add(i);
//          }
//      }
//      winnersArray = new int[winnersList.size()];
//      for (int i = 0; i< winnersArray.length; i++) {
//          winnersArray[i] = winnersList.get(i);
//      }
//
//      return winnersArray;
//  }

}