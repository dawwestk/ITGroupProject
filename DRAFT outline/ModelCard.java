import java.util.HashMap;

public class ModelCard {

	private String name;
	private int shipSize;
	private int shipSpeed;
	private int shipRange;
	private int shipFirepower;
	private int shipCargo;
	private int[] attributes;
	// etc

	public ModelCard(String[] info) {
		// info comes from cards.txt
		// arrays.split etc
		
		name = info[0];
		shipSize = Integer.parseInt(info[1]);
		shipSpeed = Integer.parseInt(info[2]);
		shipRange = Integer.parseInt(info[3]);
		shipFirepower = Integer.parseInt(info[4]);
		shipCargo = Integer.parseInt(info[5]);
		
		
		attributes = new int[5];		// currently only 5 attributes - specs say do not validate
		attributes[0] = shipSize;
		attributes[1] = shipSpeed;
		attributes[2] = shipRange;
		attributes[3] = shipFirepower;
		attributes[4] = shipCargo;
		
	}

	public String getName() {
		return name;
	}
	
	public int getShipSpeed() {
		return shipSpeed;
	}

	public int getShipSize() {
		return shipSize;
	}

	public int getShipFirepower() {
		return shipFirepower;
	}

	public int getShipRange() {
		return shipRange;
	}

	public int getShipCargo() {
		return shipCargo;
	}

	public int[] getAttributes() {
		return attributes;
	}

	public int getHighestAttribute() {
		// look through objects
		int highest = 0;
		for(int i = 0; i < attributes.length; i++) {
			if(attributes[i] >= highest) {
				highest = i;
			}
		}
		return highest;
	}
	
}
