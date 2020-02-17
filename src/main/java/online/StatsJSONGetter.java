package online;

import java.util.Scanner;

import game.DatabaseQuery;

public class StatsJSONGetter {

	/*
	 * 
	 * 	Translates the DatabaseQuery output to JSON format
	 * 
	 */
	
	private String output;
	
	public StatsJSONGetter(DatabaseQuery dbq) {
		String s = dbq.toString();
		String full = dbq.getAllJSON();
		Scanner scanner = new Scanner(s);
		output = "[[";
		
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] statAndValue = line.split(":");
			String slug = statAndValue[0].replace(" ", "-");
			output += "{";
			output += "\"" + slug + "\": [";
			output += "\"" + statAndValue[0] + "\",";
			output += statAndValue[1] + "], ";
			output += "}, ";
		}
		
		output += "]";
		output = output.replace(", ]", "]");
		output = output.replace(", }", "}");
		output += ", ";
		output += full + "]";
		
	}
	
	public String getJSON() {
		return output;
	}
	
}
