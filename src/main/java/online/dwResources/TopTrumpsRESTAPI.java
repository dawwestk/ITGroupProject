package online.dwResources;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
	
	/**
	 * Contructor method for the REST API. This is called first. It provides
	 * a TopTrumpsJSONConfiguration from which you can get the location of
	 * the deck file and the number of AI players.
	 * @param conf
	 */
	public TopTrumpsRESTAPI(TopTrumpsJSONConfiguration conf) {
		// ----------------------------------------------------
		// Add relevant initalization here
		// ----------------------------------------------------
		
		try {
			dbq = new DatabaseQuery("localhost", "postgres", "postgres");
			statsJSON = new StatsJSONGetter(dbq.toString());
			statsOutput = statsJSON.getJSON();
		} catch (Exception e) {
			System.out.println(dbq.getNoConnection());
		}
		
		deckFile = conf.getDeckFile();
		
		deck = new ModelDeck();
		try {
			ModelDeckBuilder deckBuilder = new ModelDeckBuilder(deck, deckFile);
		} catch(IOException e) {
			System.out.println("Deck file could not be opened.");
			System.exit(0);
		}

		
		
		
		
		
	}
	
	// ----------------------------------------------------
	// Add relevant API methods here
	// ----------------------------------------------------
	
	@POST
	@Path("/game/weHaveAWinner/")
	public String weHaveAWinner(String winnerName) throws IOException {
		try {
			dbq.addGameToDB(game);
		} catch (Exception e) {
			// no need to print explanation, handled on creation of dbq
		}
		String output = winnerName + " is the winner!";
		if(winnerName.equals(game.getUser().getName())) {
			output += " Congratulations!";
		} else {
			output += " Better luck next time...";
		}
		System.out.println(output);
		return output;
	}
	
	@POST
	@Path("/game/selectAttribute/")
	public void selectAttribute(String attribute) throws IOException{
		// reads which attribute was chosen and compares etc
		// needs active player to be chosen by this point
		String choice = attribute;
		System.out.println("Attribute " + choice);
		ModelCard activeCard = game.getActivePlayer().getActiveCard();
		Integer attributeName = activeCard.getValue(choice);
		System.out.println("User chose " + choice + " => " + attributeName);
		lastChosenAttribute = choice;
	}
	
	@GET
	@Path("/game/getRoundCount/")
	public int getRoundCount() throws IOException{
		int roundCount = game.getRoundCount();
		System.out.println("Round Count: " + roundCount);
		return roundCount;
	}
	
	@GET
	@Path("/game/compare/")
	public String compare() throws IOException {
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
		} else {
			output = "You must choose an Attribute first!";
		}
		
		lastChosenAttribute = null;
		return output;
	}
	
	@GET
	@Path("/game/nextRound/")
	public boolean nextRound() throws IOException {
		/*
		// this logic is just for testing purposes
		ModelPlayer activePlayer = game.getActivePlayer();
		game.giveWinnerCards(activePlayer);
		*/
		int prev = game.getRoundCount();
		game.advanceRound();
		int current = game.getRoundCount();
		JSONoutput = j.updateJSONwithNameCheck(game.getPlayers(), game.getActivePlayer());
		writeJSONtoFile(JSONoutput);
		
		if(prev != current) {
			return true; 	//round has advanced with no issue
		} else {
			return false;
		}
		
	}
	
	@GET
	@Path("/game/getJSON/")
	public String getJSON() throws IOException{
		//System.out.println("Returning JSON");
		return JSONoutput;
	}

	@GET
	@Path("/game/getActivePlayer")
	public String getActivePlayer(){
		return game.getActivePlayer().getName();
	}
	
	public void writeJSONtoFile(String s) {
		// is writing the JSON to a file necessary?
		File JSONfile = new File("resources/assets/", "JSONtest.json");
		try {
			FileWriter fw = new FileWriter(JSONfile);
			fw.write(s);
			fw.flush();
			fw.close();
			//System.out.println("Wrote to file successfully");
		} catch(Exception e) {
			System.out.println("Couldn't write to file");
		}
	}
	
	@GET
	@Path("/game/getCPUChoice")
	public String getCPUChoice() {
		ModelPlayer activePlayer = game.getActivePlayer();
		if(!activePlayer.equals(game.getUser())) {
			// CPU player is active player
			ModelCard activeCard = activePlayer.getActiveCard();
			String choice = activeCard.getHighestAttribute();
			Integer attributeName = activeCard.getValue(choice);
			System.out.println("CPU chose " + choice + " => " + attributeName);

			return choice;
		} else {
			return "Awaiting CPU choice";
		}
	}
	
	@POST
	@Path("game/setPlayers")
	/*
	 * 
	 * See GameScreen.ftl for method called setPlayers(int) which calls this
	 *
	 */
	public void setNumberOfPlayers(@QueryParam("players") int players) throws IOException {
		System.out.println("Setting number of players to " + players);
		numPlayers = players;
		game = new Game(deck);
		game.setNumberOfPlayersAndDeal(numPlayers);
		System.out.println("Game created with " + numPlayers + " players");
		ModelPlayer activePlayer = game.getActivePlayer();
		j = new JSONGetter(game);
		JSONoutput = j.updateJSONwithNameCheck(game.getPlayers(), activePlayer);
		writeJSONtoFile(JSONoutput);
		//System.out.println(statsOutput);
		lastChosenAttribute = null;
	}
	
	@GET
	@Path("game/getPlayers")
	/*
	 * 
	 * See GameScreen.ftl for method called getPlayers which calls this
	 * 
	 * This has @GET above as we are requesting info.
	 * 
	 * To test this just go to the URL http://localhost:7777/toptrumps/game/getPlayers and 
	 * 	it should say the number (and nothing else)
	 * 
	 */
	public int getPlayers() throws IOException {
		System.out.println("Number of players = " + numPlayers);
		return numPlayers;
	}
	
	@GET
	@Path("/getStats/")
	public String getStats() throws IOException {
		
		/*
		String stats = dbq.toString();
		Scanner s = new Scanner(stats);
		
		ArrayList<String> list = new ArrayList<String>();
		while(s.hasNext()) {
			String nextEntry = s.nextLine();
			
			list.add(nextEntry);
		}
		
		String statsAsJSONString = oWriter.writeValueAsString(list);
		
		return statsAsJSONString;
		*/
		return statsOutput;
	}
}
