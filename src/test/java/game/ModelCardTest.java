package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelCardTest {
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
	public void cardNameTest(){
		assertEquals(this.info[0], this.card.getName());
	}

	@Test
	public void getAttributeStringTest(){
		assertEquals(this.attributeList[1], this.card.getAttribute(1));
	}

	@Test
	public void getAttributeTest(){
		assertEquals(Integer.parseInt(this.info[1]),this.card.getValue("speed"));
	}

	@Test
	public void getHighestAttributeTest(){
		assertEquals(this.attributeList[2], this.card.getHighestAttribute());
	}

	@Test
	public void toStringTest(){
		String expected = "shipName 0 1";
		assertEquals(expected, this.card.toString());
	}

	@AfterEach
	public void tearDown(){
		this.attributeList = null;
		this.info = null;
		this.card = null;
	}
}
