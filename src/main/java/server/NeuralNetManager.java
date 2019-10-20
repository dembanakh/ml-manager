package server;

import utility.Dataset;
import utility.NeuralNet;
import utility.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class NeuralNetManager {

    private List<NeuralNet> neuralNets;

    void load() throws FileNotFoundException {
        String sourcePath = Utility.ROOT + "networks.src";
        Scanner scanner = new Scanner(new File(sourcePath));
        while (scanner.hasNextLine()) {
            String name = scanner.nextLine();
            File dataFolder = new File(Utility.WEIGHTS + name);
            if (!dataFolder.exists()) {
                neuralNets.clear();
                throw new FileNotFoundException();
            }
            neuralNets.add(new NeuralNet(name));
        }
    }

    List<String> getNeuralNetNames() {
        List<String> names = new ArrayList<>();
        for (NeuralNet n : neuralNets)
            names.add(n.getName());
        return names;
    }

}
