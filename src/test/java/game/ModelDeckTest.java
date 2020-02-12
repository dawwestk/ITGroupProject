package game;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelDeckTest {
	private ModelDeck deck;
	private String fileName;

	/*
	 * Alert test runner that file couldn't be opened
	 * or construct deck.
	*/
	@BeforeEach
	public void setUp() throws IOException {
		this.fileName = "StarCitizenDeck.txt";
		this.deck = new ModelDeck(this.fileName);
	}

	@Test
	public void incorrectFileNameThrowsTest(){
		String localFileName = "";
		assertThrows(IOException.class, () -> new ModelDeck(localFileName));
	}

	/*
	 * nullptr exception from shuffled index not existing
	*/
	@Test
	public void getCardEmptyDeckThrowsTest(){
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> this.deck.getCard(0));
	}

	@Test
	public void getCardNegativeIndexThrowsTest(){
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> this.deck.getCard(-1));
	}
	
	@Test
	public void initialDeckSize40Test(){
		assertEquals(40,this.deck.getCreatedCards());
	}

	@Test
	public void add1CardNoDeckSizeIncreaseTest(){
		String[] attributeList = new String[3];
		attributeList[0] = "name";
		attributeList[1] = "speed";
		attributeList[2] = "thrust";
		String[] info = new String[3];
		info[0] = "shipName";
		info[1] = "0";
		info[2] = "1";
		ModelCard card = new ModelCard(info, attributeList);
		this.deck.addCard(card);		
		assertEquals(40,this.deck.getCreatedCards());
	}

	@Test
	public void deal1PlayerTest(){
		ArrayList<ModelPlayer> players = new ArrayList<ModelPlayer>();
		players.add(new ModelPlayer("Geoff"));
		assertDoesNotThrow(() -> this.deck.deal(players));
	}

	@Test
	public void dealNoPlayersTest(){
		ArrayList<ModelPlayer> players = new ArrayList<ModelPlayer>();
		assertThrows(Exception.class, () -> this.deck.deal(players));
	}

	@Test
	public void toStringDoesNotThrowTest(){
		assertDoesNotThrow(() -> this.deck.toString());
	}

	@Test
	public void printInitialDeckDoesNotThrowTest(){
		assertDoesNotThrow(() -> this.deck.printInitialDeck());
	}

	@Test
	public void shuffleDoesNotThrowTest(){
		assertDoesNotThrow(() -> this.deck.shuffle());
	}

	@AfterEach
	public void tearDown(){
		this.deck = null;
		this.fileName = null;
	}
}
