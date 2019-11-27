package server;

import utility.Dataset;
import utility.NeuralNet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Task implements Serializable {

    private static final long serialVersionUID = 230L;

    private String title;

    private Dataset dataset;
    private NeuralNet neuralNet;

    public Task(String title, String dataset, String neuralNet) {
        this.title = title;
        this.dataset = new Dataset(dataset);
        this.neuralNet = NeuralNet.valueOf(neuralNet);
    }

    public String getTitle() {
        return title;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = new Dataset(dataset);
    }

    public NeuralNet getNeuralNet() {
        return neuralNet;
    }

    void setNeuralNet(String neuralNet) {
        this.neuralNet = NeuralNet.valueOf(neuralNet);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(dataset.toString());
        builder.append(" + ");
        builder.append(neuralNet.toString());
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Task)) return false;

        Task t = (Task) o;
        return t.title.equals(title) && t.dataset.equals(dataset) && t.neuralNet.equals(neuralNet);
    }

    private void readObject(ObjectInputStream os) throws IOException, ClassNotFoundException {
        os.defaultReadObject();
        this.dataset = new Dataset(this.dataset);
    }
}
