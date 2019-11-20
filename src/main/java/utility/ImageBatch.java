package utility;

import java.awt.*;

class ImageBatch implements Batch {

    private final int batchSize;
    private final Image[] images;

    ImageBatch(Image[] img) {
        this.images = img;
        this.batchSize = this.images.length;
    }

    public int getSize() {
        return batchSize;
    }

    @Override
    public Object[] getData() {
        return images;
    }

}
