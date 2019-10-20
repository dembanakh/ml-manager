package utility;

import java.io.Serializable;

public class Dataset implements Serializable {

    private final String name;

    public Dataset(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDirectory() {
        return Utility.DATASETS + name;
    }

    @Override
    public String toString() {
        return name;
    }

}
