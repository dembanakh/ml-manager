package utility;

import java.io.Serializable;

public class Dataset implements Serializable {

    private static final long serialVersionUID = 227L;

    private final String name;

    public Dataset(String name) {
        this.name = name;
    }

    public Dataset(Dataset dataset) {
        this.name = dataset.name;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Dataset)) return false;

        Dataset d = (Dataset) o;
        return d.name.equals(name);
    }

}
