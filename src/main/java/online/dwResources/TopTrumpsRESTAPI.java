package online.dwResources;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import online.configuration.TopTrumpsJSONConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import game.*;
import online.*;

@Path("/toptrumps") // Resources specified here should be hosted at http://localhost:7777/toptrumps
@Produces(MediaType.APPLICATION_JSON) // This resource returns JSON content
@Consumes(MediaType.APPLICATION_JSON) // This resource can take JSON content as input
/**
 * This is a Dropwizard Resource that specifies what to provide when a user
 * requests a particular URL. In this case, the URLs are associated to the
 * different REST API methods that you will need to expose the game commands
 * to the Web page.
 * 
 * Below are provided some sample methods that illustrate how to create
 * REST API methods in Dropwizard. You will need to replace these with
 * methods that allow a TopTrumps game to be controled from a Web page.
 */
public class TopTrumpsRESTAPI {

	/** A Jackson Object writer. It allows us to turn Java objects
	 * into JSON strings easily. */
	ObjectWriter oWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
	private String deckFile;
	private DatabaseQuery dbq = null;
	private int numPlayers = 0;
	private Game game = null;
	private ModelDeck deck;
	private String JSONoutput = "";
	private JSONGetter j = null;
	private String lastChosenAttribute;
	private StatsJSONGetter statsJSON;
	private String statsOutput = "";
	private PrintStream ps = System.out;
	
	/**
	 * Contructor method for the REST API. This is called first. It provides
	 * a TopTrumpsJSONConfiguration from which you can get the location of
	 * the deck file and the number of AI players.
	 * @param conf
	 */
	public TopTrumpsRESTAPI(TopTrumpsJSONConfiguration conf) {
		
		// Connects from the API to the database
		resetDatabaseQuery();
		
		// The deck filename is stored in the conf JSON 
		deckFile = conf.getDeckFile();
		
		// Set up the deck
		try {
			deck = new ModelDeck(deckFile);
		} catch(IOException e) {
			ps.println("Deck file could not be opened.");
			System.exit(0);
		}

	}
	
	/*
	 * 
	 * 	Setter Methods
	 * 
	 */
	
	// int is passed back to the API from the selection screen
	@POST
	@Path("game/setPlayers")
	public void setNumberOfPlayers(@QueryParam("players") int players) throws IOException {
		ps.println("Setting number of players to " + players);
		numPlayers = players;
		
		// A new game is created
		game = new Game(deck, numPlayers);
		ps.println("Game created with " + numPlayers + " players");
		ModelPlayer activePlayer = game.getActivePlayer();
		
		// Game data is exported to JSON format
		j = new JSONGetter(game);
		JSONoutput = j.updateJSONwithNameCheck(game.getPlayers(), activePlayer);
		writeJSONtoFile(JSONoutput);
		
		// Initially, the lastChosenAttribute should be null
		// No player has chosen an attribute yet
		lastChosenAttribute = null;
	}
	
	// Establishes/resets the database query
	public void resetDatabaseQuery() {
		try {
			dbq = new DatabaseQuery();
			statsJSON = new StatsJSONGetter(dbq);
			statsOutput = statsJSON.getJSON();
		} catch (Exception e) {
			ps.println(dbq.getNoConnection());
		}
	}
	
	@POST
	@Path("/game/userQuit/")
	public void userQuit(String s) throws IOException {
		// Quits the game and sets the Game object to null
		if(s.toLowerCase().equals("quit")){
			game = null;
			ps.println("User quit.");
		}
	}
	
	@POST
	@Path("/game/weHaveAWinner/")
	public String weHaveAWinner(String winnerName) throws IOException {
		// If Game finishes completely, add to the database
		try {
			dbq.addGameToDB(game);
			resetDatabaseQuery();
			game = null;	// stops the game being written to the database twice		
		} catch (Exception e) {}
		
		String output = winnerName + " is the winner!";
		if(winnerName.equals(game.getUser().getName())) {
			output += " Congratulations!";
		} else {
			output += " Better luck next time...";
		}
		ps.println(output);
		return output;
	}
	
	@POST
	@Path("/game/selectAttribute/")
	public void selectAttribute(String attribute) throws IOException{
		// reads which attribute was chosen and compares etc
		String choice = attribute;
		ps.println("Attribute " + choice);
		ModelCard activeCard = game.getActivePlayer().getActiveCard();
		Integer attributeName = activeCard.getValue(choice);
		ps.println("User chose " + choice + " => " + attributeName);
		lastChosenAttribute = choice;
	}
	
	@GET
	@Path("/game/compare/")
	public String compare() throws IOException {
		// Compares the chosen attribute from each card
		String output = "";
		if(lastChosenAttribute != null) {
			ModelPlayer winner = null;
			if(game.hasWinner(lastChosenAttribute)) {
				winner = game.getRoundWinner();
				game.giveWinnerCards(winner);
				output = winner.getName() + " has won!";
			} else {
				output = "This round was a draw!";
			}
			JSONoutput = j.updateJSONwithNameCheck(game.getPlayers(), game.getActivePlayer());
		} else {
			output = "You must choose an Attribute first!";
		}
		
		lastChosenAttribute = null;
		return output;
	}
	
	@GET
	@Path("/game/nextRound/")
	public int nextRound() throws IOException {
		// Advances the game round counter
		int prev = game.getRoundCount();
		game.advanceRound();
		int current = game.getRoundCount();
		JSONoutput = j.updateJSONwithNameCheck(game.getPlayers(), game.getActivePlayer());
		writeJSONtoFile(JSONoutput);
		
		// Returns an integer to indicate status
		if(prev != current) {
			return current; 	//round has advanced with no issue
		} else {
			return 0;
		}
	}
	
	@GET
	@Path("/game/getJSON/")
	public String getJSON() throws IOException{
		// Read the JSON file into Javascript
		return JSONoutput;
	}
	
	public void writeJSONtoFile(String s) {
		// Writing JSON to a file for testing/debugging
		File JSONfile = new File("resources/assets/", "JSONtest.json");
		try {
			FileWriter fw = new FileWriter(JSONfile);
			fw.write(s);
			fw.flush();
			fw.close();
		} catch(Exception e) {
			ps.println("Couldn't write to file");
		}
	}
	
	@GET
	@Path("game/getPlayers")
	public int getPlayers() throws IOException {
		ps.println("Number of players = " + numPlayers);
		return numPlayers;
	}
	
	@GET
	@Path("/getStats/")
	public String getStats() throws IOException {
		return statsOutput;
	}
}
