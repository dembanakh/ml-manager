package server;

import java.io.Serializable;

public class Task implements Serializable {

    private String dataset;
    private String neuralNet;

    public Task(String dataset, String neuralNet) {
        this.dataset = dataset;
        this.neuralNet = neuralNet;
    }

    public String getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public String getNeuralNet() {
        return neuralNet;
    }

    public void setNeuralNet(String neuralNet) {
        this.neuralNet = neuralNet;
    }
}
