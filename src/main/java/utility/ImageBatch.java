package utility;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

class ImageBatch implements Batch {

    private static final long serialVersionUID = 228L;

    private final int batchSize;
    private final int[][][] images;
    private final int[] labels;

    ImageBatch(int[][][] img, int[] labels) {
        this.images = new int[][][]{};
        this.labels = new int[]{};
        this.batchSize = this.labels.length;
        System.arraycopy(img, 0, this.images, 0, img.length * img[0].length * img[0][0].length);
        System.arraycopy(labels, 0, this.labels, 0, this.batchSize);
    }

    public int getSize() {
        return batchSize;
    }

    @Override
    public Object[] getData() {
        return images;
    }

    @Override
    public Object[] getLabels() {
        return new Object[] {labels};
    }

    /*
     * Converts an BufferedImage to 2-D array of pixels.
     */
    static int[][] convertTo2D(BufferedImage image) {

        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        int[][] result = new int[height][width];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }

        return result;
    }

}
