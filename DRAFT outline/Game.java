import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String filename = "StarCitizenDeck.txt";
		FileReader fr = null;
		ModelDeck deck = new ModelDeck();
		ModelPlayer human = new ModelPlayer("You");
		ArrayList<ModelPlayer> players = new ArrayList<ModelPlayer>();
		
		try {
			File file = new File(filename);
			fr = new FileReader(file);
			Scanner text = new Scanner(fr);
			
			while(text.hasNext()) {
				//System.out.println(text.nextLine());
				String shipInfo = text.nextLine();
				String[] stats = shipInfo.split(" ");
				
				if(stats[0].toLowerCase().equals("description")) {
					System.out.println("Here are the stats: " + shipInfo);
				} else {
					ModelCard card = new ModelCard(stats);
					deck.addCard(card);
					//System.out.println("Making card: " + stats[0] + ", added to deck");
				}
			}
			
			/*
			System.out.println("\tTotal cards made = " + deck.getCreatedCards());
			System.out.println("\tDeck is now populated.");
			System.out.println("\tIs CommunalPile empty? " + deck.getCP().isEmpty());
			*/
			
		} catch (IOException e) {
			System.out.println("Could not open file.");
			System.exit(0);
		}
		
		// the above is fine - only uses the model and some system output
		// the following is CLI specific
		players.add(human);
		Scanner keyboard = new Scanner(System.in);
		int opponents = chooseOpponents(keyboard);
		
		while(opponents <= 0 || opponents >= 5) {
			System.out.println("Sorry, you must face 1-4 opponents.");
			opponents = chooseOpponents(keyboard);
		}
			
		for(int i = 0; i < opponents; i++) {
			ModelAIPlayer opponent = new ModelAIPlayer("CPU-" + (i + 1));
			players.add(opponent);
		}
		
		deck.deal(players);
		
		/*
		for(int i = 0; i < players.size(); i++) {
			System.out.println(players.get(i).getInfo());
		}

		System.out.println("\tIs CommunalPile empty? " + deck.getCP().isEmpty());
		*/
		
	}

	public static int chooseOpponents(Scanner keyboard) {
		System.out.print("How many opponents would you like to face (max 4)? ");
		int opponents = keyboard.nextInt();
		return opponents;
	}
}

	/*
	public boolean playerCheck(ArrayList<ModelPlayer> players){

	int activePlayers = 0;
	for(int i=0; i<players.getLength; ++i){
		if(!players[i].isHandEmpty){
			activePlayers++;
		}
		if(activePlayers > 1){
			return true;
		}
	}
	return false;
	}

	while(playerCheck){

	// game loop
	// method to determine who's turn it is needed

	public boolean playerTurn(ModelPlayer player){
		if(player.userCheck == true){
			Scanner scanner = new Scanner(System.in);
			// switch statement with scanner to take user choice of attribute
			switch(playerChoice){
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
			}
		} else if(player.userCheck == false){
			player.selectHighest;
		}
	}

	}
	 */






