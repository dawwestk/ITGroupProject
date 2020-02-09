package online;

import java.util.Scanner;

public class StatsJSONGetter {

	/*
	 * Printout from DatabaseQuery
	 * 
	 * 	Human wins: 0
		Average Draws: 4
		AI wins: 7
		Highest Round Count: 125
		Total games: 7
	 */
	
	private String output;
	
	public StatsJSONGetter(String s) {
		Scanner scanner = new Scanner(s);
		output = "{";
		
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] statAndValue = line.split(":");
			String slug = statAndValue[0].replace(" ", "-");
			output += "\"" + slug + "\": [";
			output += "\"" + statAndValue[0] + "\",";
			output += statAndValue[1] + "], ";
		}
		
		output += "}";
		output = output.replace(", }", "}");
		
	}
	
	public String getJSON() {
		return output;
	}
	
}
