package commandline;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private ModelPlayer user;
    private ModelDeck deck;
    private ArrayList<ModelPlayer> players;
    private int roundCount;
    private String playerName = "Player One";

    public Game() {
        this.user = new ModelPlayer(playerName);
        this.deck = new ModelDeck();
        this.players = new ArrayList<ModelPlayer>();
        this.roundCount = 1;
    }

    // Getters
    public int getRoundCount() {
        return roundCount;
    }

    public ModelPlayer getUser() {
        return user;
    }

    public ModelDeck getDeck() {
        return deck;
    }

    public ArrayList<ModelPlayer> getPlayers() {
        return players;
    }

    // Setting up a new game
    public void gameInitialiser() {
        buildDeck();
        buildPlayers();
        dealDeck();
    }

    // Reading deck from text file
    public void buildDeck() {
        String filename = "StarCitizenDeck.txt";
        FileReader fr = null;

        try {
            File file = new File(filename);
            fr = new FileReader(file);
            Scanner text = new Scanner(fr);

            while (text.hasNext()) {
                // System.out.println(text.nextLine());
                String shipInfo = text.nextLine();
                String[] stats = shipInfo.split(" ");

                if (stats[0].toLowerCase().equals("description")) {
                    // System.out.println("Here are the stats: " + shipInfo);
                } else {
                    ModelCard card = new ModelCard(stats);
                    deck.addCard(card);
                    // System.out.println("Making card: " + stats[0] + ", added to deck");
                }
            }

        } catch (IOException e) {
            System.out.println("Could not open file.");
            System.exit(0);
        }
    }

    // Choosing amount of players
    public static int chooseOpponents(Scanner keyboard) {
        System.out.print("How many opponents would you like to face (max 4)? ");
        int opponents = keyboard.nextInt();
        return opponents;
    }

    // Creating players based on choice
    public void buildPlayers() {
        players.add(user);
        Scanner keyboard = new Scanner(System.in);
        int opponents = chooseOpponents(keyboard);

        while (opponents <= 0 || opponents >= 5) {
            System.out.println("Sorry, you must face 1-4 opponents.");
            opponents = chooseOpponents(keyboard);
        }

        for (int i = 0; i < opponents; i++) {
            ModelAIPlayer opponent = new ModelAIPlayer("CPU-" + (i + 1));
            players.add(opponent);
        }
    }

    // Distribute shuffled deck amongst players
    public void dealDeck() {
        deck.deal(players);
    }

    // Choosing which player is to start on 1st round
    public int whoFirst() {
        Random random = new Random();
        int max = players.size();
        return random.nextInt(max - 1) + 1;
    }

    // Print general game info
    public void printInfo() {
        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).getInfo());
        }
        if (deck.getCP().isEmpty()) {
            System.out.println("CommunalPile is empty.");
        }
        if (!deck.getCP().isEmpty()) {
            System.out.println("CommunalPile has: " + deck.getCP().size() + " cards in it.");
        }
    }

// Making an array of active cards (Cards at top of each players deck)
//	public ModelCard[] onTheTableGetter() {
//		ModelCard[] onTheTable = new ModelCard[players.size()];
//		for (int i = 0; i < players.size(); i++) {
//			onTheTable[i] = players.get(i).getHand().get(players.get(i).getHand().size() - 1);
//		}
//		return onTheTable;
//	}

    // Checking who the stat-picking player will be this round
    public int turnTracker() {
    	int count = 0;
    	if(roundCount == 1){
    		return whoFirst();
		} else {
    		for(int i=0; i<players.size(); ++i){
    			if(players.get(i).isWinner()){
					System.out.println("It is " + players.get(i).getName() + "'s turn.");
    				count = i;
				}
			}
    		return count;
		}
	}

    // Choosing which card stat will be compared
    public int statPicker() {
        Scanner scanner = new Scanner(System.in);
        if (players.get(turnTracker()).equals(user)) {
            int choice;
            System.out.println("Which category do you want to select?: ");
            do {
                choice = scanner.nextInt();
            } while (choice < 1 || choice > 5);
            return choice;
        } else {
            return players.get(turnTracker()).getHand().get(players.get(turnTracker()).getHand().size() - 1)
                    .getHighestAttribute();
        }
    }

    // Compare stats, find a winner/winners
    public void performRound() {
        System.out.println("Round " + roundCount + ": " + players.get(turnTracker()).getName() + "'s turn to choose!");
        System.out.println("You drew " + user.getActiveCard().printCardInfo());
        for(int i =1; i < players.size(); i++){
			System.out.println(players.get(i).getName() + " has drawn " + players.get(i).getActiveCard().getName());
		}
		System.out.println(turnTracker());
        Round round = new Round(players, players.get(turnTracker()), statPicker());

        if (round.compareStat()) {
            // 1 winner: all cards go to winner, winner picks category
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).equals(round.compareStat())) {
                    players.get(i).setWinner(true);
                } else
                    players.get(i).setWinner(false);
            }

            redistributeCards(round.getRoundWinner());

        } else {
            for (int i = 0; i < players.size(); i++) {
                deck.getCP().add(players.get(i).getActiveCard());
                players.get(i).removeFromHand(players.get(i).getActiveCard());
            }
            printInfo();
        }
        roundCount++;
    }

    public void redistributeCards(ModelPlayer winner) {
        for (int i = 0; i < players.size(); i++) {
            if (!players.get(i).equals(winner)) {
                winner.addToHand(players.get(i).getActiveCard());
                players.get(i).removeFromHand(players.get(i).getActiveCard());
            } else
            continue;

        }
    }

    // Counts the amount of players still in the game
    public boolean activePlayers() {
        int activeCount = this.players.size();
        for (int i = 0; i < this.players.size(); i++) {
            if (!this.players.get(i).isHandEmpty()) {
                activeCount--;
                if (activeCount > 1 || !user.isHandEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

}
