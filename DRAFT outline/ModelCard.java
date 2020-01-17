import java.util.HashMap;

public class ModelCard {

	private String name;
	private int[] attributes;
	// etc
	
	public ModelCard(String info) {
		// info comes from cards.txt
		// arrays.split etc
		attributes = new int[5];		// currently only 5 attributes - specs say do not validate
	}

	public String getName() {
		return name;
	}

	public int[] attributes() {
		return attributes;
	}

	public int getHighestAttribute() {
		// look through objects
		int highest = 0;
		for(int i = 0; i < attributes.length; i++) {
			if(attributes[i] >= highest) {
				highest = i;
			}
		}
		return highest;
	}
	
}
