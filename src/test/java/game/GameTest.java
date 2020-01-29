package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class GameTest {
	private Game game;
	
	@BeforeEach
	public void setUp() {
		ModelDeck deck = new ModelDeck();
		int numPlayers = 4;
		game = new Game(deck,numPlayers);
	}
	
	@Test
	public void roundWinnerTest() {
		int roundNumber = 0;
//		game.getRoundWinner(roundNumber);
	}
	
	@AfterEach
	public void tearDown() {
		game = null;
	}

}
