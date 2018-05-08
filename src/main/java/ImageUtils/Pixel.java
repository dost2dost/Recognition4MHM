package ImageUtils;

/**
 * Created by Dost Muhammad on 5/8/2018.
 */
public class Pixel {

    /** The x. */
    private int x;

    /** The y. */
    private int y;

    /**
     * Instantiates a new pixel.
     *
     * @param x the x
     * @param y the y
     */
    public Pixel(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x.
     *
     * @return the x
     */
    public int getX(){
        return x;
    }

    /**
     * Gets the y.
     *
     * @return the y
     */
    public int getY(){
        return y;
    }
}
