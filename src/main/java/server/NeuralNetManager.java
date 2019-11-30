package server;

import utility.Dataset;
import utility.NeuralNet;
import utility.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class NeuralNetManager {

    private List<NeuralNet> neuralNets = new ArrayList<>();

    /*
     * Loads list of available network architectures from $HOME/ml-manager/networks.src
     */
    void load() throws NoSuchMLObjectException, FileNotFoundException {
        /*
        String sourcePath = Utility.ROOT + "networks.src";
        Scanner scanner = new Scanner(new File(sourcePath));
        while (scanner.hasNextLine()) {
            String name = scanner.nextLine();
            try {
                NeuralNet value = NeuralNet.valueOf(name);
                neuralNets.add(value);
            } catch (IllegalArgumentException e) {
                this.clear();
                throw new NoSuchMLObjectException();
            }
        }
        */
        NeuralNet[] nets = NeuralNet.values();
        Collections.addAll(neuralNets, nets);
    }

    List<String> getNeuralNetNames() {
        List<String> names = new ArrayList<>();
        for (NeuralNet n : neuralNets)
            names.add(n.toString());
        return names;
    }

    boolean hasNeuralNet(String name) {
        for (NeuralNet nn : neuralNets) {
            if (nn.toString().equals(name)) return true;
        }
        return false;
    }

    void clear() {
        neuralNets.clear();
    }

}
