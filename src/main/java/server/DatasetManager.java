package server;

import utility.Dataset;
import utility.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class DatasetManager {

    private List<Dataset> datasets = new ArrayList<>();

    /*
     * Loads list of available datasets from $HOME/ml-manager/datasets.src
     * For each dataset X (except IMAGENET) checks $HOME/.ml-manager/datasets/X structure (/samples and /labels subfolders)
     * and whether each sample has its label
     */
    void load() throws FileNotFoundException {
        String sourcePath = Utility.ROOT + "datasets.src";
        Scanner scanner = new Scanner(new File(sourcePath));
        while (scanner.hasNextLine()) {
            String name = scanner.nextLine();
            if (!Files.exists(Paths.get(Utility.DATASETS + name))) {
                this.clear();
                throw new FileNotFoundException("Data folder for dataset " + name + " dosen't exist");
            }
            if (!Files.exists(Paths.get(Utility.DATASETS + name + File.separator + Utility.SAMPLES))) {
                this.clear();
                throw new FileNotFoundException("Samples subdirectory of dataset " + name + " doesn't exist");
            }
            if (!Files.exists(Paths.get(Utility.DATASETS + name + File.separator + Utility.LABELS))) {
                this.clear();
                throw new FileNotFoundException("Labels subdirectory of dataset " + name + " doesn't exist");
            }
            try {
                Iterable<Path> samplesIterable = Files.walk(Paths.get(Utility.DATASETS + name + File.separator + Utility.SAMPLES))
                        .filter(Files::isRegularFile)::iterator;
                for (Path p : samplesIterable) {
                    if (!Files.exists(Paths.get(Utility.sampleToLabelPath(p.toFile().getPath()))))
                        throw new FileNotFoundException("There is a sample without a corresponding label in dataset " + name);
                }
            } catch (IOException ignored) {
                // it cannot happen!
            }
            datasets.add(new Dataset(name));
        }
        datasets.add(new Dataset(Utility.IMAGENET));
    }

    List<String> getDatasetNames() {
        List<String> names = new ArrayList<>();
        for (Dataset d : datasets)
            names.add(d.getName());
        return names;
    }

    boolean hasDataset(String name) {
        for (Dataset d : datasets) {
            if (d.getName().equals(name)) return true;
        }
        return false;
    }

    void clear() {
        datasets.clear();
    }

}
