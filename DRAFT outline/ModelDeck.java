import java.util.ArrayList;
import java.util.Random;

public class ModelDeck {

	private int totalCards;
	private int createdCards;
	private ModelCard[] initialArrayOfCards;
	private ArrayList<ModelCard> shuffled;
	private ArrayList<Integer> shuffledIndex;
	
	public ModelDeck() {
		totalCards = 40;		// hard coded for now as specified
		createdCards = 0;
		initialArrayOfCards = new ModelCard[totalCards];
		
	}
	
	public void shuffle() {
		shuffled = new ArrayList<ModelCard>();
		shuffledIndex = new ArrayList<Integer>();
		Random r = new Random();
		for(int i = 0; i < totalCards; i++) {
			int rand = r.nextInt(totalCards);
			while(shuffledIndex.contains(rand)) {
				rand = r.nextInt(totalCards);
			}
			shuffled.add(initialArrayOfCards[rand]);
		}
	}
	
	public ModelCard getCard(int index) {
		return shuffled.get(index);
	}
	
	public void addCard(ModelCard card) {
		initialArrayOfCards[createdCards++] = card;
	}
	
	public int getCreatedCards() {
		return createdCards;
	}
	
}
