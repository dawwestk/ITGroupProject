package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/*
 * 
 * 		The class below outlines a Database Query object to pull previous
 * 	game stats from the postgresql server and allows the user to add a new
 *  game to the database as well.
 * 		
 * 		Some outputs from the Game class have been assumed for now until
 *  this class is fully developed. But the output will be usable by the
 *  DatabaseQuery class as the form will not change.
 *  
 *  	Currently the code is set up for the localhost server 
 *  	This will be changed to link to a yacata server.
 * 
 


public class DatabaseQuery {

	Connection c = null;
	String connString = "jdbc:postgresql://localhost/postgres";
	String x = "postgres";
	
	public DatabaseQuery() {
		c = setup(c, connString, x, x);
	}
	
	public static void main(String[] args) {
		DatabaseQuery dbq = new DatabaseQuery();
		Game g = new Game();
		
		System.out.println(dbq.printDB());
		System.out.println(dbq.getHumanWins());
		System.out.println(dbq.getAIWins());
		System.out.println(dbq.getAverageDraws());
		System.out.println(dbq.getHighestRounds());
		System.out.println(dbq.getTotalGames());
	}
	
	private int query(String s) {
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(s);
			
			if(rs.next()) {
				//System.out.println("Query executed successfully.");
				return rs.getInt(1);
			} else {
				System.out.println("Unable to execute query.");
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int getTotalGames() {
		String totalGameQuery = "SELECT COUNT(*) FROM toptrumps.stats";
		return query(totalGameQuery);
	}
	
	public int getAIWins() {
		String AIWinQuery = "SELECT COUNT(winAI) FROM toptrumps.stats WHERE winAI = 1";
		return query(AIWinQuery);
	}
	
	public int getHumanWins() {
		String humanWinQuery = "SELECT COUNT(winHuman) FROM toptrumps.stats WHERE winHuman = 1";
		return query(humanWinQuery);
	}
	
	public int getAverageDraws() {
		String aveQuery = "SELECT AVG(draws) FROM toptrumps.stats";
		return query(aveQuery);
	}
	
	public int getHighestRounds() {
		String maxRoundsQuery = "SELECT MAX(rounds) FROM toptrumps.stats";
		return query(maxRoundsQuery);
	}
	
	public void addGameToDB(Game g) {
		int gameID = g.getID();
		int winHuman = 0;
		int winAI = 0;
		if(g.getWinner().equals("Human")) {
			winHuman = 1;
		} else if(g.getWinner().equals("AI")){
			winAI = 1;
		}
		int rounds = g.getRoundsPlayed();
		int draws = g.getDraws();

		String insertQuery = String.format("INSERT INTO toptrumps.stats(gameid, winhuman, winai, rounds, draws) VALUES(%d, %d, %d, %d, %d)", gameID, winHuman, winAI, rounds, draws);

		try {
			Statement stmt = c.createStatement();
			int status = stmt.executeUpdate(insertQuery);

			if(status == 1) {
				System.out.println("Game added successfully.");
			} else {
				System.out.println("Unable to add game to DB.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public String printDB() {
		try {
			String output = "";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM toptrumps.stats");
			while (rs.next()) {
				int gameID = rs.getInt("gameID");
				int winHuman = rs.getInt("winHuman");
				int winAI = rs.getInt("winAI");
				int rounds = rs.getInt("rounds");
				int draws = rs.getInt("draws");
				output += "-----------------------";
				output += "gameID = " + gameID;
				output += "winHuman = " + winHuman;
				output += "winAI = " + winAI;
				output += "rounds = " + rounds;
				output += "draws = " + draws;
				output += "\n";
			}
//			rs.close();
//			stmt.close();
//			c.close();
			return output;
		} catch (Exception e) {
			System.out.println("Something went wrong...");
			return "Unable to print database info";
		}
		//System.out.println("Operation done successfully");
	}
	
	public static Connection setup(Connection conn, String db, String username, String pass) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found");
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection(db, username, pass); 
		} catch (SQLException e) {
			System.out.println("SQL Exception");
			e.printStackTrace();
		}
		
		return conn;
	}

}

*/


