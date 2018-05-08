package Catalino;

/**
 * Created by Dell_Pc on 5/6/2018.
 */
import Catalano.Imaging.FastBitmap;

public class ImprovedLocalBinaryPattern {

    /**
     * Initialize a new instance of the ImprovedLocalBinaryPattern class.
     */
    public ImprovedLocalBinaryPattern() {}

    /**
     * Process image.
     * @param fastBitmap Image to be processed.
     * @return ILBP Histogram.
     */
    public ImageHistogram ProcessImage(FastBitmap fastBitmap){
        if (!fastBitmap.isGrayscale()) {
            try {
                throw new Exception("ILBP works only with grayscale images.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int width = fastBitmap.getWidth() - 1;
        int height = fastBitmap.getHeight() - 1;

        int sum;
        int[] g = new int[511];
        int mean;
        for (int x = 1; x < height; x++) {
            mean = 0;
            for (int y = 1; y < width; y++) {
                mean += fastBitmap.getGray(x-1, y-1);
                mean += fastBitmap.getGray(x-1, y);
                mean += fastBitmap.getGray(x-1, y+1);
                mean += fastBitmap.getGray(x, y-1);
                mean += fastBitmap.getGray(x, y);
                mean += fastBitmap.getGray(x, y+1);
                mean += fastBitmap.getGray(x+1, y-1);
                mean += fastBitmap.getGray(x+1, y);
                mean += fastBitmap.getGray(x+1, y+1);
                mean /= 9;

                sum = 0;
                if (mean < fastBitmap.getGray(x - 1, y - 1))    sum += 128;
                if (mean < fastBitmap.getGray(x - 1, y))        sum += 64;
                if (mean < fastBitmap.getGray(x - 1, y + 1))    sum += 32;
                if (mean < fastBitmap.getGray(x, y + 1))        sum += 16;
                if (mean < fastBitmap.getGray(x + 1, y + 1))    sum += 8;
                if (mean < fastBitmap.getGray(x + 1, y))        sum += 4;
                if (mean < fastBitmap.getGray(x + 1, y - 1))    sum += 2;
                if (mean < fastBitmap.getGray(x, y - 1))        sum += 1;

                //2^9
                if(mean < fastBitmap.getGray(x, y))             sum += 256;

                //all zeros and all ones are the same
                if(sum == 511) sum = 0;

                g[sum]++;
            }
        }
        return new ImageHistogram(g);
    }
}

