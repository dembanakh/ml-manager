package utility;

import java.io.Serializable;

public class NeuralNet implements Serializable {

    private final String name;

    public NeuralNet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getWeights() {
        return Utility.WEIGHTS + name + ".pb";
    }

    @Override
    public String toString() {
        return name;
    }

}
