package commandline;

public class ModelAIPlayer extends ModelPlayer{

    public ModelAIPlayer(String name) {
        super(name);
    }

    public int selectHighest(ModelCard card) {
        return card.getHighestAttribute();
    }
}