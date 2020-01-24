package game;

import java.util.HashMap;

public class ModelCard {

	/*
	 * 
	 * ModelCard class with a HashMap (dictionary) rather than an integer array.
	 * 
	 * Inputs
	 * 	- String[] info is a line item in the StarCitizenDeck.txt file representing a ship (card)
	 *  - String[] attributeList is the first line item in the same file which gives the attribute descriptions
	 *  
	 */
	
    private String name;
    private HashMap<String, Integer> attributeMap;
    private String[] attributeDefinitions;

    public ModelCard(String[] info, String[] attributeList) {
        // info  and attributeList come from StarCitizenDeck.txt in Game method
    	
    	// The input from StarCitizenDeck.txt will not change, so we can read the 0th index as the name
        name = info[0];
        
        // attributeDefinitions are copied from the attributeList array 
        attributeDefinitions = attributeList.clone();
        
        // A new HashMap is populated with the attributeDefinition (String) as the key
        // and the attribute value (Integer) from the info array as the value
        // NOTE: Loop counter starts at 1 because 0th index is description/ship name
        attributeMap = new HashMap<String, Integer>();
        for(int i = 1; i < info.length; i++) {
        	attributeMap.put(attributeDefinitions[i], Integer.parseInt(info[i]));
        }

    }
    
    public String printCardInfo() {
    	String output = name + "\n";
    	int menuOption = 1;
    	
    	// For each attributeDefinition, add a menu option to the output String
    	for(String x : attributeDefinitions) {
    		if(x.toLowerCase().equals("description")) {
    			// Not required for the description entry as this is a default value 
    			continue;
    		} else {
    			output += String.format("\t%d. %s: %d\n", menuOption++, x, attributeMap.get(x));
    		}
    	}
    	
    	return output;
    }

    // Returns the name of the ship on the card
    public String getName() {
        return name;
    }
    
    public Integer getValue(String s) {
    	// Checks the HashMap for existence of a key, then returns the value or null
    	if(attributeMap.containsKey(s)) {	
    		return attributeMap.get(s);
    	} else {
    		return null;
    	}
    }

    public String getHighestAttribute() {
        // Loops through attribute HashMap to find the highest value
    	// Returns the attributeDefinition associated with the highest (best chance of winning)
        String highest = attributeDefinitions[1];
        for(String x : attributeMap.keySet()) {
            if(attributeMap.get(x).compareTo(attributeMap.get(highest)) >= 0) {
                highest = x;
            }
        }
        return highest;
    }

}
