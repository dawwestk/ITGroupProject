package online;

import java.util.ArrayList;

import game.*;

public class JSONGetter{ 
	
	public JSONGetter(Game g) {
		
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
