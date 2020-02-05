package online;

import java.util.ArrayList;

import game.*;

public class JSONGetter{ 
	
	private String output = "[";
	
	public JSONGetter(ArrayList<ModelPlayer> players) {
		// processes players/card objects to produce JSON string
		// only for use in the online application
		
		int numAttributes = players.get(0).getActiveCard().getNumberOfAttributes();
		
		for(ModelPlayer p : players) {
			output += "{";
			ModelCard activeCard = p.getActiveCard();
			output += "\"name\":\"" + p.getName() + "\", ";
			output += "\"cardName\":\"" + activeCard.getName() + "\", ";
			for(int i = 0; i <= numAttributes; i++) {
				String currentAttribute = activeCard.getAttribute(i);
				output += "\"" + currentAttribute + "\":" + activeCard.getValue(currentAttribute) + ", ";
			}
			output += "}, ";
		}
		
		output += "]";
		output = output.replace(", ]", "]").replace(", }", "}");		// removes trailing comma
		
	}
	
	public String getJSON() {
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
