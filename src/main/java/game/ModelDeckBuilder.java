package game;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/*
 * 
 * 	The Deck Builder class is used to populate a deck object from a text file.
 * 
 * 	It throws an IOException if an incorrect filename is used.	
 * 
 */

public class ModelDeckBuilder {

	private ModelDeck deck;
	private String filename;
	
	public ModelDeckBuilder(ModelDeck d, String s) throws IOException{
		deck = d;
		filename = s;
		this.buildDeck(deck, filename);
	}
	
	// Reading deck from text file
	private void buildDeck(ModelDeck deck, String filename) throws IOException {
		FileReader fr = null;
		String[] attributeList = null;
		File file = new File(filename);
		fr = new FileReader(file);
		Scanner text = new Scanner(fr);

		while (text.hasNext()) {
			String shipInfo = text.nextLine(); // shipInfo == card description + stats
			String[] stats = shipInfo.split(" "); // card description + stats 

			if (stats[0].toLowerCase().equals("description")) { // First row of text file
				attributeList = new String[stats.length];
				attributeList = stats;
			} else { // every other row of text file
				ModelCard card = new ModelCard(stats, attributeList);
				deck.addCard(card);
			}
		}		
		text.close();
	}
	
}
