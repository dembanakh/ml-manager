package server;

import utility.Dataset;
import utility.NeuralNet;

class MLManager {

    MLManager() {
        System.loadLibrary("native");
    }

    native boolean train(Dataset dataset, NeuralNet net);

}
