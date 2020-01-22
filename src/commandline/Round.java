package commandline;

import java.util.ArrayList;

public class Round {
	private ArrayList<ModelPlayer> activePlayers;
  //private ModelCard[] onTheTable;
  private int stat;
  //private int highestValue;
  private ModelPlayer activePlayer;

  public Round(ArrayList<ModelPlayer> players, ModelPlayer active, int s) {
	  activePlayers = players;
	  activePlayer = active;
      this.stat = s;
      //this.highestValue = 1; //findHighestValue(players);
      System.out.println(activePlayer.getName() + " picked attribute number " + this.stat);	// note this is array index, not numbered attribute
      System.out.println("\t" + activePlayer.getActiveCard().printCardInfo());
  }
  
// Finds the highest value present among the chosen stat 
//  public int findHighestValue(ArrayList<ModelPlayer> compare) {
//      int highestValue = -1;
//      for (int i = 0; i < compare.size(); i++) {
//          if(compare.get(i).getActiveCard().getAttributes()[stat] > highestValue) {
//              highestValue = compare.get(i).getActiveCard().getAttributes()[stat];
//          }
//      }
//      return highestValue;
//  }

// Compares the chosen stat and detects whether there is a single winner or a draw. Returns the player who won or null for a draw. 
  public ModelPlayer compareStat() {
      int winner = 0;
      int drawCount = 0;

      for(int i = 1; i < activePlayers.size(); i++) {
          if (activePlayers.get(i).getActiveCard().getAttributes()[stat-1]>activePlayers.get(winner).getActiveCard().getAttributes()[stat-1]) {
              winner = i;
              drawCount = 0;
          }else if (activePlayers.get(i).getActiveCard().getAttributes()[stat-1]==activePlayers.get(winner).getActiveCard().getAttributes()[stat-1]) {
              winner = i;
              drawCount++;
          }
      }
      if (drawCount == 0) {
    	  System.out.println(activePlayers.get(winner).getName());
          return activePlayers.get(winner);
      }
      else {
    	  System.out.println("draw");
          return new ModelPlayer("Draw");
      }
  }

// Testing Round. Currently if 1st Player wins result is always draw[Fixed]
//  public static void main(String[] args) {
//	  ArrayList<ModelPlayer> testPlayers = new ArrayList<ModelPlayer>();
//	  ModelPlayer testActive = new ModelPlayer("Player1"); testPlayers.add(testActive);
//	  ModelPlayer CPU1 = new ModelPlayer("Player2");testPlayers.add(CPU1);
//	  ModelPlayer CPU2 = new ModelPlayer("Player3");testPlayers.add(CPU2);
//	  ModelCard Active1 = new ModelCard(new String[] {"Card1", "1", "2", "12", "4", "20"});testActive.addToHand(Active1);
//	  ModelCard Active2 = new ModelCard(new String[] {"Card2", "0", "6", "8", "3", "12"});CPU1.addToHand(Active2);
//	  ModelCard Active3 = new ModelCard(new String[] {"Card3", "1", "2", "3", "2", "14"});CPU2.addToHand(Active3);
//	  Round round = new Round(testPlayers, testActive, 5);
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
//          if (onTheTable[i].getAttributes()[stat] == highestValue) {
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