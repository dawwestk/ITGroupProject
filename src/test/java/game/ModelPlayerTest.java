package game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelPlayerTest {	
	ModelPlayer player = null;
	String name = null;
	String[] attributeList = null;

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
		this.name = "Henrietta";
		this.player = new ModelPlayer(this.name);
		this.attributeList = new String[3];
		this.attributeList[0] = "name";
		this.attributeList[1] = "speed";
		this.attributeList[2] = "thrust";
	}

	@Test
	public void getNameTest(){
		assertEquals(this.name, this.player.getName());
	}

	@Test
	public void initialHandEmptyTest(){
		assertTrue(this.player.isHandEmpty());
	}

	public void getActiveCardTest(){
		String[] cardValues = newCardValuesArray("0","1","2");
		ModelCard card = newCard(cardValues);
		this.player.addToHand(card);
		assertEquals(card,this.player.getActiveCard());
	}

	/*
	 * Cards are inserted at the beginning of the hand array.
	 * Active card is the last element of the hand array
	*/
	@Test
	public void getActiveCardHandSize2Test(){
		String[] cardValues = newCardValuesArray("0","1","2");		
		ModelCard card1 = newCard(cardValues);
		this.player.addToHand(card1);
		cardValues = newCardValuesArray("3","0","2");
		ModelCard card2 = newCard(cardValues);
		this.player.addToHand(card2);
		assertEquals(card1,this.player.getActiveCard());
	}

	@Test
	public void removeOnlyCardTest(){
		String[] cardValues = newCardValuesArray("0","1","2");		
		ModelCard card1 = newCard(cardValues);
		this.player.addToHand(card1);
		this.player.removeFromHand(card1);
		assertTrue(this.player.isHandEmpty());
	}

	@Test
	public void remove1Of2CardsTest(){
		String[] cardValues = newCardValuesArray("0","1","2");		
		ModelCard card1 = newCard(cardValues);
		this.player.addToHand(card1);
		cardValues = newCardValuesArray("3","0","2");
		ModelCard card2 = newCard(cardValues);
		this.player.addToHand(card2);
		this.player.removeFromHand(card1);
		ArrayList<ModelCard> cards = this.player.getHand();
		assertTrue(cards.size() == 1);
		assertEquals(card2,cards.get(0));	
	}

	@Test
	public void toStringTest(){
		String[] cardValues = newCardValuesArray("0","1","2");		
		ModelCard card1 = newCard(cardValues);
		this.player.addToHand(card1);
		String expected = this.player.getName();
		expected += "\n";
		expected += card1.toString();
		assertEquals(expected,this.player.toString());
	}

	@AfterEach
	public void tearDown(){
		this.player = null;
		this.name = null;
		this.attributeList = null;
	}
}
