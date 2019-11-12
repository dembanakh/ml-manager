package utility;

import java.awt.*;

class ImageBatch implements Batch {

    private int batchSize;
    private Image[] images;

    ImageBatch(Image[] img) {
        this.images = img;
        this.batchSize = this.images.length;
    }

    public int getSize() {
        return batchSize;
    }

}
