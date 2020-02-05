package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;


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
 
*/

public class DatabaseQuery {

	private Connection c = null;
	private String connString = "jdbc:postgresql://";
	private String database;
	private String password;
	private HashMap<String, Integer> statsToAdd;
	private HashMap<String, String> previousStats;
	private int lastGameID;
	private String humanPlayerName = "Player One";
	private static String noConnection = "Could not connect to database. Stats can not be viewed or recorded.";
	
	public DatabaseQuery(String server, String db, String pass) throws Exception {
		connString += server + "/" + db;
		database = db;
		password = pass;
		try {	
			c = setup(c, connString, database, password);
		} catch (Exception e) {
			throw e;
		}
		statsToAdd = new HashMap<String, Integer>();
		previousStats = new HashMap<String, String>();
		pullTableStats(previousStats);
		statsPopulate(statsToAdd);
		lastGameID = getTotalGames() + 1;
		
	}
	
	public String toString() {
		String output = "";
		for(String key : previousStats.keySet()) {
			output += key + previousStats.get(key) + "\n";
		}
		return output;
	}
	
	public static String getNoConnection() {
		return noConnection;
	}
	
	private void pullTableStats(HashMap<String, String> map) {
		map.put("\n Total games: ", "" + getTotalGames());
		map.put("\nHuman wins: ", "" + getHumanWins());
		map.put("\nAI wins: ", "" + getAIWins());
		map.put("\nAverage Draws: ", "" + getAverageDraws());
		map.put("\nHighest Round Count: ", "" + getHighestRounds());
	}
	
	private void statsPopulate(HashMap<String, Integer> map) {
		map.put("gameid", 0);
		map.put("winhuman", 0);
		map.put("winai", 0);
		map.put("rounds", 0);
		map.put("draws", 0);
		map.put("humanrounds", 0);
		map.put("cpu1rounds", 0);
		map.put("cpu2rounds", 0);
		map.put("cpu3rounds", 0);
		map.put("cpu4rounds", 0);
		
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
			//e.printStackTrace();
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
		
		statsToAdd.put("gameid", lastGameID); // pulled from previous database info
		
		if(g.getPlayers().get(0).getName().equals(humanPlayerName)) {
			statsToAdd.put("winhuman", 1);
		} else if(g.getPlayers().get(0).getName().substring(0,3).equals("CPU")){
			statsToAdd.put("winai", 1);
		}
		
		statsToAdd.put("rounds", g.getRoundCount());
		statsToAdd.put("draws", g.getDrawCount());
		statsToAdd.put("humanrounds", g.getRoundsWon(humanPlayerName));
		statsToAdd.put("cpu1rounds", g.getRoundsWon("CPU-1"));
		statsToAdd.put("cpu2rounds", g.getRoundsWon("CPU-2"));
		statsToAdd.put("cpu3rounds", g.getRoundsWon("CPU-3"));
		statsToAdd.put("cpu4rounds", g.getRoundsWon("CPU-4"));
		
		
		// Test Data
		/*
		statsToAdd.put("winai", 1);
		statsToAdd.put("rounds", 40);
		statsToAdd.put("draws", 4);
		statsToAdd.put("humanrounds", 10);
		statsToAdd.put("cpu1rounds", 20);
		statsToAdd.put("cpu2rounds", 5);
		statsToAdd.put("cpu3rounds", 0);
		statsToAdd.put("cpu4rounds", 5);
		*/

		// INSERT INTO toptrumps.stats(gameid, winhuman, winai, rounds, draws, humanrounds, cpu1rounds, cpu2rounds, cpu3rounds, cpu4rounds) 
		// VALUES(2, 0, 1, 50, 4, 5, 10, 10, 5, 10)
		String insert = "INSERT INTO toptrumps.stats(";
		String values = "VALUES(";
		for(String key : statsToAdd.keySet()) {
			insert += key + ",";
			values += statsToAdd.get(key) + ",";
		}
		
		insert = insert.substring(0, insert.length() - 1) + ")";
		values = values.substring(0, values.length() - 1) + ")";
		
		String insertQuery = insert + " " + values;
		
		//String insertQuery = "INSERT INTO toptrumps.stats(gameid, winhuman, winai, rounds, draws, humanrounds, cpu1rounds, cpu2rounds, cpu3rounds, cpu4rounds)"
		//		+ "VALUES(%d, %d, %d, %d, %d, %d, %d, %d, %d, %d)", stats.get("gameid"),);
		
		try {
			Statement stmt = c.createStatement();
			int status = stmt.executeUpdate(insertQuery);

			if(status == 1) {
				System.out.println("Game added successfully.");
			} else {
				System.out.println("Unable to add game to DB.");
			}
		} catch (Exception e) {
			//e.printStackTrace();
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
	
	public static Connection setup(Connection conn, String db, String username, String pass) throws ClassNotFoundException, SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			//System.out.println("Class not found");
			//e.printStackTrace();
			throw e;
		}
		
		try {
			conn = DriverManager.getConnection(db, username, pass); 
		} catch (SQLException e) {
			//System.out.println("SQL Exception");
			//e.printStackTrace();
			throw e;
		}
		
		return conn;
	}

}



