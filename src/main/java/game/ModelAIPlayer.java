package game;

/*
 * 
 * 	Extends the ModelPlayer object and adds the functionality to auto-select
 * 		the highest attribute on the players active card.
 * 
 */

public class ModelAIPlayer extends ModelPlayer{

    public ModelAIPlayer(String name) {
        super(name);
    }

    public String selectHighest(ModelCard card) {
        return card.getHighestAttribute();
    }
}