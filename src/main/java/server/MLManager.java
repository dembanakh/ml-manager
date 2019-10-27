package server;

import utility.Dataset;
import utility.NeuralNet;

class MLManager {

    native boolean train(Dataset dataset, NeuralNet net);

}
