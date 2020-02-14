package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelAIPlayerTest {
	private String[] attributeList = null;
	private String[] info = null;
	private ModelCard card = null;

	@BeforeEach
	public void setUp(){
		this.attributeList = new String[3];
		this.attributeList[0] = "name";
		this.attributeList[1] = "speed";
		this.attributeList[2] = "thrust";
		this.info = new String[3];
		this.info[0] = "shipName";
		this.info[1] = "0";
		this.info[2] = "1";
		this.card = new ModelCard(this.info, this.attributeList);
	}

	@Test
	public void getHighestAttributeTest(){
		ModelAIPlayer ai = new ModelAIPlayer("CPU");
		String expected = "thrust";
		ai.addToHand(card);
		assertEquals(expected, ai.selectHighest(card));
	}

	

	@AfterEach
	public void tearDown(){
		this.attributeList = null;
		this.info = null;
		this.card = null;
	}
}
