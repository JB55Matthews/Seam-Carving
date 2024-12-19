import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EdgeDetect2
{
    private static final int TOLERANCE = 20;

    private Image original;
    private int width;
    private int height;
    private ImageManager imageManager;

    public EdgeDetect2(ImageManager imageManager){
        this.imageManager = imageManager;
    }

    public void apply(Image image)
    {
        original = new Image(image);
        width = original.getWidth();
        height = original.getHeight();

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                image.setPixel(x, y, edge(x, y));
            }
        }
        imageManager.setCurrentImage(image);
    }

    private Color edge(int xpos, int ypos)
    {
        java.util.List<Color> pixels = new ArrayList<Color>(9);

        for(int y = ypos-1; y <= ypos+1; y++) {
            for(int x = xpos-1; x <= xpos+1; x++) {
                if( x >= 0 && x < width && y >= 0 && y < height ) {
                    pixels.add(original.getPixel(x, y));
                }
            }
        }

        return new Color(diffRed(pixels), diffGreen(pixels), diffBlue(pixels));
    }

    private int diffRed(java.util.List<Color> pixels)
    {
        int max = 0;
        int min = 255;
        for(Color color : pixels) {
            int val = color.getRed();
            if(val > max) {
                max = val;
            }
            if(val < min) {
                min = val;
            }
        }
        int difference = max - min - TOLERANCE;
        if(difference < 0) {
            difference = 0;
        }
        return difference;
    }


    private int diffGreen(java.util.List<Color> pixels)
    {
        int max = 0;
        int min = 255;
        for(Color color : pixels) {
            int val = color.getGreen();
            if(val > max) {
                max = val;
            }
            if(val < min) {
                min = val;
            }
        }
        int difference = max - min - TOLERANCE;
        if(difference < 0) {
            difference = 0;
        }
        return difference;
    }

    private int diffBlue(List<Color> pixels)
    {
        int max = 0;
        int min = 255;
        for(Color color : pixels) {
            int val = color.getBlue();
            if(val > max) {
                max = val;
            }
            if(val < min) {
                min = val;
            }
        }
        int difference = max - min - TOLERANCE;
        if(difference < 0) {
            difference = 0;
        }
        return difference;
    }

}
