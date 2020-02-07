package online;

import java.util.ArrayList;
import java.util.HashMap;

import game.*;

public class JSONGetter{ 
	
	private String[] originalPlayerNames;
	
	public JSONGetter(Game g) {
		int numPlayers = g.getNumPlayers();
		originalPlayerNames = new String[numPlayers];
		for(int i = 0; i < originalPlayerNames.length; i++) {
			originalPlayerNames[i] = g.getPlayerName(i);
			//System.out.println("Added " + g.getPlayerName(i) + " to original list");
		}
	}
	
	public String updateJSONwithNameCheck(ArrayList<ModelPlayer> players, ModelPlayer activePlayer) {
		// processes players/card objects to produce JSON string
		// only for use in the online application
		HashMap<String, ModelPlayer> map = new HashMap<String, ModelPlayer>();
		ArrayList<String> activeNames = new ArrayList<String>();
		for(ModelPlayer p : players) {
			map.put(p.getName(), p);
			activeNames.add(p.getName());
			//System.out.println(p.getName() + " is still active");
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
	
	public String updateJSON(ArrayList<ModelPlayer> players, ModelPlayer activePlayer) {
		// processes players/card objects to produce JSON string
		// only for use in the online application
		
		String output = "[";
		int numAttributes = players.get(0).getActiveCard().getNumberOfAttributes();
		
		for(ModelPlayer p : players) {
			boolean active = false;
			active = p.equals(activePlayer) ? true: false;
			output += "{";
			ModelCard activeCard = p.getActiveCard();
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
		}
		
		output += "]";
		output = output.replace(", ]", "]").replace(", }", "}");		// removes trailing comma
		
		return output;
	}
	
	/*
	   public static void main(String[] args) { 
	      String jsonString = "{\"name\":\"Mahesh\", \"age\":21}"; 
	      
	      GsonBuilder builder = new GsonBuilder(); 
	      builder.setPrettyPrinting(); 
	      
	      Gson gson = builder.create(); 
	      Student student = gson.fromJson(jsonString, Student.class); 
	      System.out.println(student);    
	      
	      jsonString = gson.toJson(student); 
	      System.out.println(jsonString);  
	   } 
	 */
	} 
