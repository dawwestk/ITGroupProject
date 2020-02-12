package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;


/*
 * 
 * 	The class below outlines a Database Query object to pull previous
 * 	game stats from the postgresql server and allows the user to add a new
 *  game to the database as well.
 * 		
 *  
 *  	Currently the code is set up for the AWS server 
 * 
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
	
	public DatabaseQuery() throws Exception {
		String server = "52.24.215.108:5432"; //"yacata.dcs.gla.ac.uk:5432";
		String db = "CompuGlobalHyperMegaNet"; //"m_19_1002243w";
		String pass = "CompuGlobalHyperMegaNet"; //"1002243w"; 
		connString += server + "/" + db;
		database = db;
		password = pass;
		try {	
			c = setup(c, connString, database, password);
		} catch (Exception e) {
			throw e;
		}
		
		// HashMaps are created to hold new/current stats
		statsToAdd = new HashMap<String, Integer>();
		previousStats = new HashMap<String, String>();
		pullTableStats(previousStats);
		statsPopulate(statsToAdd);
		
		// The ID of the last game is calculated
		lastGameID = getTotalGames() + 1;
	}
	
	/*
	 * 
	 * 	Setup Methods
	 * 
	 */
	
	public static Connection setup(Connection conn, String db, String username, String pass) throws ClassNotFoundException, SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {throw e;}
		try {
			conn = DriverManager.getConnection(db, username, pass); 
		} catch (SQLException e) {throw e;}
		return conn;
	}
	
	/*
	 * 
	 * 	SQL Query method
	 * 
	 */
	
	private int query(String s) {
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(s);
			
			if(rs.next()) {
				return rs.getInt(1);
			} else {
				return -1;
			}
		} catch (Exception e) {
			return -1;
		}
	}
	
	/*
	 * 
	 * 	SQL statement Get methods
	 * 
	 */
	
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
	
	public String getAllJSON() {
		String getAllJSONQuery = "with t as (select * from toptrumps.stats) select json_agg(t) from t";
		String jsonOutput = "";
		try {
			Statement stmt = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(getAllJSONQuery);
			while(rs.next()) {
				jsonOutput += rs.getString(1).toString();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return jsonOutput;
	}

	/*
	 * 
	 * 	Data manipulation methods
	 * 
	 */
	
	private void pullTableStats(HashMap<String, String> map) {
		map.put("Total games: ", "" + getTotalGames());
		map.put("Human wins: ", "" + getHumanWins());
		map.put("AI wins: ", "" + getAIWins());
		map.put("Average Draws: ", "" + getAverageDraws());
		map.put("Highest Round Count: ", "" + getHighestRounds());
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
	
	/*
	 * 
	 * 	Add game back to database
	 * 
	 */
	
	public String addGameToDB(Game g) {
		
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

		String insert = "INSERT INTO toptrumps.stats(";
		String values = "VALUES(";
		for(String key : statsToAdd.keySet()) {
			insert += key + ",";
			values += statsToAdd.get(key) + ",";
		}
		
		insert = insert.substring(0, insert.length() - 1) + ")";
		values = values.substring(0, values.length() - 1) + ")";
		
		String insertQuery = insert + " " + values;
		
		try {
			Statement stmt = c.createStatement();
			int status = stmt.executeUpdate(insertQuery);
			if(status == 1) {
				return("Game added to Database successfully.");
			} else {
				return("Unable to add game to DB.");
			}
		} catch (Exception e) {
			return("Unable to connect to DB.");
		}
	}
	
	/*
	 * 
	 * 	Getter Methods
	 * 
	 */
	
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
			return output;
		} catch (Exception e) {
			System.out.println("Something went wrong...");
			return "Unable to print database info";
		}
	}
}



