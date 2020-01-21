import java.util.ArrayList;
//import java.util.List;

public class Round {
	private ModelCard[] onTheTable;
	private int stat;
	private int highestValue;
	
	public Round(ModelCard[] c, int s) {	
		this.onTheTable = c;
		this.stat = s;
		this.highestValue = findHighestValue(c);
		
	}
	
	public int findHighestValue(ModelCard[] compare) {
		int highestValue = -1;
		for (int i = 0; i < compare.length; i++) {
			if(compare[i].getAttributes()[stat] >= highestValue)
				highestValue = compare[i].getAttributes()[stat];
		}
		return highestValue;
	}
	
	public int compareStat() {
		int winner = 0;
		int drawCount = 0;
		
		for(int i = 0; i<onTheTable.length; i++) {
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
}
