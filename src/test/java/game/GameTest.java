package game;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class GameTest {
	private ModelDeck deck;
	
	@BeforeEach
	public void setUp() throws IOException {		
		String fileName = "StarCitizenDeck.txt";	
		deck = new ModelDeck(fileName);				
	}
	
	@Test
	public void buildTest() {
		int numPlayers = 4;
		Game game = new Game(deck,numPlayers);
		assertNotNull(game);
	}
	
	@Test
	public void getRoundCountFirstRoundTest() {
		int numPlayers = 4;
		Game game = new Game(deck,numPlayers);
		int expected = 1;
		int actual = game.getRoundCount();
		assertEquals(expected, actual);
	}
	
	@Test
	public void getRoundWinnerNotNullTest() {
		int numPlayers = 4;
		Game game = new Game(deck,numPlayers);
		ModelPlayer expected = game.getRoundWinner();
		assertNotNull(expected);
	}
	
	@Test
	public void getRoundWinnerTest() {
		int numPlayers = 4;
		Game game = new Game(deck,numPlayers);
		// int choice = 0;
		// Stat stat = game.getStat(choice);
		// game.hasWinner(stat);
		ModelPlayer player = game.getRoundWinner();
		assertNotNull(player.getName());
	}
	
	/*
	 * Number of rounds drawn should be zero to begin
	*/
	@Test
	public void getDrawCountZeroTest() {
		int numPlayers = 4;
		Game game = new Game(deck,numPlayers);
		int expected = 0;
		int actual = game.getDrawCount();
		assertEquals(expected, actual);
	}
	
	@Test
	public void getWinCountRoundNotStartedTest(){
		int numPlayers = 4;
		Game game = new Game(deck,numPlayers);
		int expected = 0;
		int actual = -1;
		String playerName = "";
		for(int i = 0 ; i < numPlayers ; ++i) {
			playerName = game.getPlayerName(i);
			actual = game.getRoundsWon(playerName);
			assertEquals(expected, actual);
		}
	}
	
	@Test
	public void getWinCountRound1Test(){
		int numPlayers = 4;
		Game game = new Game(deck,numPlayers);
		int expected = 0;
		int actual = -1;
		String playerName = "";
		for(int i = 0 ; i < numPlayers ; ++i) {
			playerName = game.getPlayerName(i);
			actual = game.getRoundsWon(playerName);
			assertEquals(expected, actual);
		}
	}
	
	@Test
	public void getRoundsWonPlayerNotFoundNullTest(){
		int numPlayers = 1;
		Game game = new Game(deck,numPlayers);
		String playerName = "Player Two";
		assertNull(game.getRoundsWon(playerName));
	}

	@Test
	public void getRoundsWonPlayerFoundTest(){
		int numPlayers = 1;
		Game game = new Game(deck,numPlayers);
		String playerName = "Player One";
		assertNotNull(game.getRoundsWon(playerName));
	}
	
	@Test
	public void getStatTest(){
		int numPlayers = 1;
		Game game = new Game(deck,numPlayers);
		int choice = 1; // speed
		String expected = "speed";
		String[] attributeList = {"name", "speed", "range"};
		String[] attributeValues = {"Colt", "7", "5"};
		ModelCard card = new ModelCard(attributeValues, attributeList);
		ModelPlayer player = game.getActivePlayer();
		player.addToHand(card);
		assertEquals(expected, game.getStat(choice));
	}

	@AfterEach
	public void tearDown() {
		deck = null;
	}

}
