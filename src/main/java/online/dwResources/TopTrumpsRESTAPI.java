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
	private File JSONfile = new File("resources/assets", "JSONtest.json");
	private String JSONoutput = "";
	
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
	@Path("/game/selectAttribute/")
	public void selectAttribute(@QueryParam("attribute") int attribute) throws IOException{
		// reads which attribute was chosen and compares etc
		// needs active player to be chosen by this point
		game.setActivePlayerToUserForGUITest();
		int choice = attribute;
		String attributeName = game.getStat(choice);
		System.out.println("User chose " + attributeName);
	}
	
	@GET
	@Path("/game/getRoundCount/")
	public int getRoundCount() throws IOException{
		return game.getRoundCount();
	}
	
	@GET
	@Path("/game/nextRound/")
	public void nextRound() throws IOException {
		game.advanceRound();
	}
	
	@GET
	@Path("/game/getJSON/")
	public String getJSON() throws IOException{
		return JSONoutput;
	}
	
	@GET
	@Path("game/newGame")
	public void newGame() throws IOException{
		// Create new Game with only deck - need to set players before playing
		// this logic will be handled by the GUI
		game = new Game(deck);
		game.setNumberOfPlayersAndDeal(numPlayers);
		System.out.println("Game created with " + numPlayers + " players");
		JSONGetter j = new JSONGetter(game.getPlayers());
		try {
			FileWriter fw = new FileWriter(JSONfile);
			JSONoutput = j.getJSON();
			fw.write(JSONoutput);
			fw.flush();
			fw.close();
		} catch(Exception e) {
			System.out.println("Couldn't write to file");
		}
		System.out.println("Saved JSON of player names and cards");
	}
	
	@POST
	@Path("game/setPlayers")
	/*
	 * 
	 * See GameScreen.ftl for method called setPlayers(int) which calls this
	 * 
	 * This has @POST above as we are using it to pass information back.
	 * 
	 */
	public void setNumberOfPlayers(@QueryParam("players") int players) throws IOException {
		System.out.println("Setting number of players to " + players);
		numPlayers = players;
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
		String stats = dbq.toString();
		Scanner s = new Scanner(stats);
		
		ArrayList<String> list = new ArrayList<String>();
		while(s.hasNext()) {
			String nextEntry = s.nextLine();
			
			list.add(nextEntry);
		}
		
		String statsAsJSONString = oWriter.writeValueAsString(list);
		
		return statsAsJSONString;
	}
	
	@GET
	@Path("/helloJSONList")
	/**
	 * Here is an example of a simple REST get request that returns a String.
	 * We also illustrate here how we can convert Java objects to JSON strings.
	 * @return - List of words as JSON
	 * @throws IOException
	 */
	public String helloJSONList() throws IOException {
		
		List<String> listOfWords = new ArrayList<String>();
		listOfWords.add("Hello");
		listOfWords.add("World!");
		
		// We can turn arbitrary Java objects directly into JSON strings using
		// Jackson seralization, assuming that the Java objects are not too complex.
		String listAsJSONString = oWriter.writeValueAsString(listOfWords);
		listAsJSONString = listAsJSONString.replace("[", "");
		listAsJSONString = listAsJSONString.replace("]", "");
		
		return listAsJSONString;
	}
	
	@GET
	@Path("/helloWord")
	/**
	 * Here is an example of how to read parameters provided in an HTML Get request.
	 * @param Word - A word
	 * @return - A String
	 * @throws IOException
	 */
	public String helloWord(@QueryParam("Word") String Word) throws IOException {
		return "Hello "+Word;
	}
	
}
