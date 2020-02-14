package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DatabaseQueryTest {

	@Test
	public void test() {
		DatabaseQuery dbq = null;
		try{
			dbq = new DatabaseQuery();
			assertEquals(1,dbq.testDB());
		} catch(Exception e) {
			String error = "Could not connect to database. Stats can not be viewed or recorded.";
			assertEquals(error, dbq.getNoConnection());
		}
	}

}
