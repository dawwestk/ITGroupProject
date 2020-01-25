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
//        displayRoundInfo();
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