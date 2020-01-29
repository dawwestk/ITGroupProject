package game;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class GameTest {
	private ModelDeck deck;
	
	@BeforeEach
	/*
	 * Construct a deck before loading a test
	 * Game is dependent on Deck being constructed fully, this isn't ideal.
	 * Throw IOException to indicate test can't continue
	 */
	public void setUp() throws IOException {		
		String fileName = "StarCitizenDeck.txt";	
		deck = new ModelDeck();
		ModelDeckBuilder builder = new ModelDeckBuilder(deck, fileName);				
	}
	
	@Test
	public void buildTest() {
		int numPlayers = 4;
		Game game = new Game(deck,numPlayers);
		assertNotNull(game);
	}
	
	@Test
	public void getRoundCountTest() {
		int numPlayers = 4;
		Game game = new Game(deck,numPlayers);
		int expected = 1;
		int actual = game.getRoundCount();
		assertEquals(expected, actual);
	}
	
	@Test
	public void getRoundWinnerNotNull() {
		int numPlayers = 4;
		Game game = new Game(deck,numPlayers);
		ModelPlayer expected = game.getRoundWinner();
		assertNotNull(expected);
	}
	
	/*
	 * This test won't work 100% of the time until we can
	 * construct the game to replicate a single player winning on a
	 * particular round.
	 */
	@Test
	public void getRoundWinnerHumanPlayer() {
		int numPlayers = 4;
		Game game = new Game(deck,numPlayers);
		// int choice = 0;
		// Stat stat = game.getStat(choice);
		// game.hasWinner(stat);
		ModelPlayer player = game.getRoundWinner();
		String expected = "Player One";
		assertEquals(expected, player.getName());
	}
	
	@AfterEach
	/*
	 * deck is null after each test
	 */
	public void tearDown() {
		deck = null;
	}

}
