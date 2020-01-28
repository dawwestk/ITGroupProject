package game;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ModelDeckBuilder {

	private ModelDeck deck;
	private String filename;
	
	public ModelDeckBuilder(ModelDeck d, String s) {
		deck = d;
		filename = s;
		this.buildDeck(deck, filename);
	}
	
	// Reading deck from text file
	private static void buildDeck(ModelDeck deck, String filename) {
		FileReader fr = null;
		String[] attributeList = null;
		try {
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

		} catch (IOException e) {
			System.out.println("Could not open file.");
			System.exit(0);
		} 		
	}
	
}
