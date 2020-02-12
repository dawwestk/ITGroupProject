package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelCommunalPileTest {
	private ModelCommunalPile pile = null;
	private String[] attributeList = null;
	private String[] info = null;
	private ModelCard card = null;
	private ModelPlayer player = null;

	public ModelCard newCard(String[] cardValues){
		ModelCard card = new ModelCard(cardValues, this.attributeList);
		return card;
	}

	public String[] newCardValuesArray(String...s){
		String[] cardValues = new String[s.length];
		for(int i = 0 ; i < s.length; ++i){
			cardValues[i] = s[i];
		}
		return cardValues;
	}

	@BeforeEach
	public void setUp(){
		this.pile = new ModelCommunalPile();
		this.attributeList = new String[3];
		this.attributeList[0] = "name";
		this.attributeList[1] = "speed";
		this.attributeList[2] = "thrust";
		this.info = new String[3];
		this.info[0] = "shipName";
		this.info[1] = "0";
		this.info[2] = "1";
		this.card = new ModelCard(this.info, this.attributeList);
		this.player = new ModelPlayer("Henrietta");
	}

	@Test
	public void addToHandTest() {
		this.pile.add(this.card);
		assertEquals(this.card,this.pile.get(0));
	}

	@Test
	public void giveToPlayerOneCardTest(){
		this.pile.add(this.card);
		this.pile.pickedUpByWinner(this.player);
		assertTrue(this.pile.size() == 0);
		assertEquals(this.card,this.player.getActiveCard());
	}

	@Test
	public void giveToPlayerThreeCardsTest(){
		this.pile.add(this.card);
		String [] attributes = newCardValuesArray("0","1","10");
		ModelCard card = newCard(attributes);
		this.pile.addCard(card);
		attributes = newCardValuesArray("5","2","2");
		card = newCard(attributes);
		this.pile.addCard(card);
		this.pile.pickedUpByWinner(this.player);
		assertTrue(this.pile.size() == 0);
		assertEquals(this.card,this.player.getActiveCard());
	}

	@AfterEach
	public void tearDown(){
		this.pile = null;
		this.attributeList = null;
		this.info = null;
		this.card = null;
		this.player = null;
	}
}
