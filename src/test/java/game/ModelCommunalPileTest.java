package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelCommunalPileTest {
	ModelCommunalPile pile = null;
	String[] attributeList = null;
	String[] info = null;
	ModelCard card = null;
	ModelPlayer player = null;

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
		this.pile.pickedUpByWinner(this.player);
		assertFalse(true);
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
