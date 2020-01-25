package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class GameTest {
	
	public void GameReadsDeck() {
		Game game = new Game();
		game.buildDeck();
		int deckSizeExpected = 40;
		int deckSizeActual = game.deckSize();
		assertEquals(deckSizeExpected,deckSizeActual);
	}

}