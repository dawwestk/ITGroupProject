package game;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

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
	public void incorrectFileNameTest(){
		String localFileName = "";
		assertThrows(IOException.class, () -> new ModelDeck(localFileName));
	}

	@AfterEach
	public void tearDown(){

	}
}
