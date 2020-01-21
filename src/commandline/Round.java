package commandline;

import java.util.ArrayList;
//import java.util.List;

public class Round {
	private ArrayList<ModelPlayer> activePlayers;
    //private ModelCard[] onTheTable;
    private int stat;
    private int highestValue;
    private ModelPlayer activePlayer;

    public Round(ArrayList<ModelPlayer> players, ModelPlayer active, int s) {
    	activePlayers = players;
    	activePlayer = active;
        this.stat = s;
        this.highestValue = 1; //findHighestValue(players);
        System.out.println(activePlayer.getName() + " picked attribute number " + this.stat);	// note this is array index, not numbered attribute
        System.out.println("\t" + activePlayer.getActiveCard().printCardInfo());
    }

    public int findHighestValue(ArrayList<ModelPlayer> compare) {
        int highestValue = -1;
        for (int i = 0; i < compare.size(); i++) {
            if(compare.get(i).getActiveCard().getAttributes()[stat] > highestValue) {
                highestValue = compare.get(i).getActiveCard().getAttributes()[stat];
            }
        }
        return highestValue;
    }

    /*
    public int compareStat() {
        int winner = 0;
        int drawCount = 0;

        for(int i = 0; i < onTheTable.length; i++) {
            if (onTheTable[i].getAttributes()[stat]>onTheTable[winner].getAttributes()[stat]) {
                winner = i;
                drawCount = 0;
            }if (onTheTable[i].getAttributes()[stat]==onTheTable[winner].getAttributes()[stat]) {
                winner = i;
                drawCount++;
            }
        }
        if (drawCount == 0)
            return winner;
        else {
            return -1;
        }
    }

    public int[] Draw() {
        ArrayList<Integer> winnersList = new ArrayList<Integer>();
        int[] winnersArray;
        for(int i = 0; i < onTheTable.length; i++){
            if (onTheTable[i].getAttributes()[stat] == highestValue) {
                winnersList.add(i);
            }
        }
        winnersArray = new int[winnersList.size()];
        for (int i = 0; i< winnersArray.length; i++) {
            winnersArray[i] = winnersList.get(i);
        }

        return winnersArray;
    }
    */
}