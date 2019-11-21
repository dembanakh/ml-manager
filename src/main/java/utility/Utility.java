package utility;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Utility {

    public final static String ROOT = "/home/dembanakh/.ml-manager/";
    public final static String WEIGHTS = ROOT + "tasks-weights/";
    public final static String DATASETS = ROOT + "datasets/";

    public final static String SAMPLES = "samples/";
    public final static String LABELS  = "labels/";

    public final static int MAX_INPUT_LENGTH = 32;

    public final static String IMAGENET = "IMAGENET";

    public enum DataType {
        IMAGE
    }


    static Batch filesToBatch(File[] f, DataType type, int batchSize) throws IOException {
        switch (type) {
            case IMAGE:
                int[][][] images = new int[batchSize][][];
                int[] labels = new int[batchSize];
                for (int i = 0; i < images.length; i++) {
                    images[i] = ImageBatch.convertTo2D(ImageIO.read(f[i]));
                    Scanner sc = new Scanner(new File(Utility.sampleToLabelPath(f[i].getAbsolutePath())));
                    labels[i] = sc.nextInt();
                }
                return new ImageBatch(images, labels);
        }

        return null;
    }

    static String sampleToLabelPath(String path) throws IOException {
        String[] spl = path.split("\\.");
        if (spl.length < 2) throw new IOException("path specified is not a file");
        String noExt = spl[0];
        spl = noExt.split(File.separator);
        spl[spl.length-2] = Utility.LABELS;
        noExt = String.join(File.separator, spl);
        return noExt + ".lbl";
    }

}
