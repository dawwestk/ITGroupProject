package commandline;

import java.util.HashMap;

public class ModelCard {

	/*
	 * 
	 * ModelCard class with a HashMap (dictionary) rather than an integer array
	 * 
	 */
	
    private String name;
    private HashMap<String, Integer> attributeMap;
    private String[] attributeDefinitions;

    public ModelCard(String[] info, String[] attributeList) {
        // info  and attributeList come from cards.txt
        name = info[0];
        attributeDefinitions = attributeList;
        attributeMap = new HashMap<String, Integer>();
        
        for(int i = 1; i < info.length; i++) {
        	attributeMap.put(attributeDefinitions[i], Integer.parseInt(info[i]));
        }

    }
    
    public String printCardInfo() {
    	
    	// re-do with HashMap
    	String output = name + "\n";
    	int menuOption = 1;
    	for(String x : attributeDefinitions) {
    		if(x.toLowerCase().equals("description")) {
    			continue;
    		} else {
    			output += String.format("\t%d. %s: %d\n", menuOption++, x, attributeMap.get(x));
    		}
    	}
    	
    	return output;
    }

    public String getName() {
        return name;
    }
    
    public Integer getValue(String s) {
    	if(attributeMap.containsKey(s)) {	
    		return attributeMap.get(s);
    	} else {
    		return null;
    	}
    }

    public String getHighestAttribute() {
        // look through objects
        String highest = "";
        for(String x : attributeMap.keySet()) {
            if(attributeMap.get(x) >= attributeMap.get(highest)) {
                highest = x;
            }
        }
        return highest;
    }

}
