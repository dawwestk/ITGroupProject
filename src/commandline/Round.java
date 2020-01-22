package commandline;

import java.util.ArrayList;
//import java.util.List;

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

      for(int i = 0; i < activePlayers.size(); i++) {
          if (activePlayers.get(i).getActiveCard().getAttributes()[stat]>activePlayers.get(winner).getActiveCard().getAttributes()[stat]) {
              winner = i;
              drawCount = 0;
          }if (activePlayers.get(i).getActiveCard().getAttributes()[stat]==activePlayers.get(winner).getActiveCard().getAttributes()[stat]) {
              winner = i;
              drawCount++;
          }
      }
      if (drawCount == 0) {
    	  System.out.print(activePlayers.get(winner).getName());
          return activePlayers.get(winner);
      }
      else {
          return new ModelPlayer("Draw");
      }
  }

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