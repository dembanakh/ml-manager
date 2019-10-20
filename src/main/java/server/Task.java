package server;

import java.io.Serializable;

public class Task implements Serializable {

    private Integer id = -1;

    private String dataset;
    private String neuralNet;
    private boolean trained;

    public Task(String dataset, String neuralNet) {
        this.dataset = dataset;
        this.neuralNet = neuralNet;
        trained = false;
    }

    public String getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
        trained = false;
    }

    public String getNeuralNet() {
        return neuralNet;
    }

    public void setNeuralNet(String neuralNet) {
        this.neuralNet = neuralNet;
        trained = false;
    }

    public Integer getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }
}
