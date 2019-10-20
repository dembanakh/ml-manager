package server;

import utility.Dataset;
import utility.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class DatasetManager {

    private List<Dataset> datasets = new ArrayList<>();

    void load() throws FileNotFoundException {
        String sourcePath = Utility.ROOT + "datasets.src";
        Scanner scanner = new Scanner(new File(sourcePath));
        while (scanner.hasNextLine()) {
            String name = scanner.nextLine();
            File dataFolder = new File(Utility.DATASETS + name);
            if (!dataFolder.exists()) {
                datasets.clear();
                throw new FileNotFoundException();
            }
            datasets.add(new Dataset(name));
        }
    }

    List<String> getDatasetNames() {
        List<String> names = new ArrayList<>();
        for (Dataset d : datasets)
            names.add(d.getName());
        return names;
    }

}
