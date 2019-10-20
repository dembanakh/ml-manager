package utility;

public class NeuralNet {

    private final String name;

    public NeuralNet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getWeights() {
        return Utility.WEIGHTS + name;
    }

}
