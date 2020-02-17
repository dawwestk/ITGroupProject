package online;

import java.util.ArrayList;
import java.util.HashMap;

import game.*;

public class JSONGetter{ 
	
	/*
	 * 
	 * 	Translates the card data output to JSON format
	 * 
	 */
	
	private String[] originalPlayerNames;
	private Game game;
	
	public JSONGetter(Game g) {
		game = g;
		int numPlayers = g.getNumPlayers();
		originalPlayerNames = new String[numPlayers];
		for(int i = 0; i < originalPlayerNames.length; i++) {
			originalPlayerNames[i] = g.getPlayerName(i);
		}
	}
	
	// Processes players/card objects to produce JSON string
	// Only for use in the online application
	public String updateJSONwithNameCheck(ArrayList<ModelPlayer> players, ModelPlayer activePlayer) {
		
		HashMap<String, ModelPlayer> map = new HashMap<String, ModelPlayer>();
		ArrayList<String> activeNames = new ArrayList<String>();
		for(ModelPlayer p : players) {
			map.put(p.getName(), p);
			activeNames.add(p.getName());
		}
		String output = "[";
		int numAttributes = players.get(0).getActiveCard().getNumberOfAttributes();
		
		for(int j = 0; j < originalPlayerNames.length; j++) {
			output += "{";
			if(activeNames.contains(originalPlayerNames[j])) {
				// write info as usual
				ModelPlayer p = map.get(originalPlayerNames[j]);
				boolean active = false;
				active = p.equals(activePlayer) ? true: false;
				ModelCard activeCard = p.getActiveCard();
				output += "\"communalPile\":" + game.communalDeckSize() + ", ";
				output += "\"eliminated\":" + false + ", ";
				output += "\"name\":\"" + p.getName() + "\", ";
				int handSize = p.getHand().size();
				output += "\"handSize\":" + handSize + ", ";
				output += "\"activePlayer\":" + active + ", ";
				output += "\"cardName\":\"" + activeCard.getName() + "\", ";
				output += "\"highestAttribute\":\"" + p.getActiveCard().getHighestAttribute() + "\", ";
				for(int i = 0; i <= numAttributes; i++) {
					String currentAttribute = activeCard.getAttribute(i);
					output += "\"" + currentAttribute + "\":" + activeCard.getValue(currentAttribute) + ", ";
				}
				output += "}, ";
				
			} else {
				// player is no longer in activePlayers list
				output += "\"name\":\"" + originalPlayerNames[j] + "\", ";
				output += "\"eliminated\":" + true + ", ";
				output += "}, ";
			}
		}
		
		output += "]";
		output = output.replace(", ]", "]").replace(", }", "}");		// removes trailing comma
		
		return output;
	}

	} 
