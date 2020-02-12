package game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeckBuilderTest {
	private ModelDeck deck;
	
	@BeforeEach
	public void setUp() {
		String fileName = "";
		try{
			new ModelDeck(fileName);	
		} catch(IOException e) {
			
		}
	}
	
	@Test
	public void createDeckFailsToOpenFileNameWrongTest() {
		String fileName = "";		
		assertThrows(IOException.class, () -> new ModelDeckBuilder(deck, fileName));
//		assertThrows(IOException.class,() -> new AClassThatThrowsIOException());
	}
	
	@AfterEach
	public void tearDown() {
		
	}
	
	
}
