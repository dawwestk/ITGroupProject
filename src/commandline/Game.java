package commandline;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private ModelPlayer user;
    private ModelDeck deck;
    ArrayList<ModelPlayer> players;

    public Game() {
        this.user = new ModelPlayer("Player One");
        this.deck = new ModelDeck();
        this.players = new ArrayList<ModelPlayer>();
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

    public void buildDeck(){
        String filename = "StarCitizenDeck.txt";
        FileReader fr = null;

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
                    System.out.println("Making card: " + stats[0] + ", added to deck");
                }
            }

        } catch (IOException e) {
            System.out.println("Could not open file.");
            System.exit(0);
        }
    }

    public void buildPlayers(){
        players.add(user);
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
    }

    public void dealDeck(){
        deck.deal(players);
    }

    public void gameInitialiser(){
        buildDeck();
        buildPlayers();
        dealDeck();
    }

    public void printInfo(){
        for(int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).getInfo());
        }
        System.out.println("\tIs CommunalPile empty? " + deck.getCP().isEmpty());
    }


        // the above is fine - only uses the model and some system output
        // the following is CLI specific

    public static int chooseOpponents(Scanner keyboard) {
        System.out.print("How many opponents would you like to face (max 4)? ");
        int opponents = keyboard.nextInt();
        return opponents;
    }

    public boolean activePlayers(){
        int activeCount = this.players.size();
        for(int i = 0; i<this.players.size(); i++){
            if(!this.players.get(i).isHandEmpty()){
                activeCount--;
                if(activeCount > 1){
                    return true;
                }
            }
        }
        return false;
    }

}
