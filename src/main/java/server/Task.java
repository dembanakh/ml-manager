package server;

import utility.Dataset;
import utility.NeuralNet;

import java.io.Serializable;

public class Task implements Serializable {

    private Integer id = -1;

    private Dataset dataset;
    private NeuralNet neuralNet;
    private boolean trained;

    public Task(String dataset, String neuralNet) {
        this.dataset = new Dataset(dataset);
        this.neuralNet = new NeuralNet(neuralNet);
        trained = false;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = new Dataset(dataset);
        trained = false;
    }

    public NeuralNet getNeuralNet() {
        return neuralNet;
    }

    public void setNeuralNet(String neuralNet) {
        this.neuralNet = new NeuralNet(neuralNet);
        trained = false;
    }

    public Integer getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }
}
