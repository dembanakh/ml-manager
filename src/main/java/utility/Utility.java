package utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Utility {

    public final static String ROOT = "/home/dembanakh/.ml-manager/";
    public final static String WEIGHTS = ROOT + "weights/";
    public final static String DATASETS = ROOT + "datasets/";

    public final static String IMAGENET = "IMAGENET";

    public enum DataType {
        IMAGE
    }


    static Batch filesToBatch(File[] f, DataType type, int batchSize) throws IOException {
        switch (type) {
            case IMAGE:
                BufferedImage[] images = new BufferedImage[batchSize];
                for (int i = 0; i < batchSize; i++) images[i] = ImageIO.read(f[i]);
                return new ImageBatch(images);
        }

        return null;
    }

}
