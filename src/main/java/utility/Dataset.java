package utility;

public class Dataset {

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

}
