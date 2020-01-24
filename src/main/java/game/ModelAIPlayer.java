package game;

public class ModelAIPlayer extends ModelPlayer{

    public ModelAIPlayer(String name) {
        super(name);
    }

    public String selectHighest(ModelCard card) {
        return card.getHighestAttribute();
    }
}