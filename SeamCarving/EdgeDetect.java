import java.awt.Color;

import java.util.List;
import java.util.ArrayList;


public class EdgeDetect
{
    //private static final int TOLERANCE = 20;

    //private static final double sobel[] = new double[]{-0.125, -0.25, -0.125, 0.0, 0.0, 0.0, 0.125, 0.25, 0.125};
    //x
    private static final double sobelx[] = new double[]{-1.0, -2.0, -1.0, 0.0, 0.0, 0.0, 1.0, 2.0, 1.0};
    // y
    private static final double sobely[] = new double[]{-1.0, 0.0, 1.0, -2.0, 0.0, 2.0, -1.0, 0.0, 1.0};
    // // x
    // private static final double sobelx[] = new double[]{-0.125, -0.25, -0.125, 0.0, 0.0, 0.0, 0.125, 0.25, 0.125};
    // // y
    // private static final double sobely[] = new double[]{-0.125, 0.0, 0.125, -0.25, 0.0, 0.25, -0.125, 0.0, 0.125};
    
    private Image original;
    private int width;
    private int height;
    private ImageManager imageManager;

    public EdgeDetect(ImageManager imageManager){
        this.imageManager = imageManager;
    }

    public void apply(Image image)
    {
        original = new Image(image);
        GrayScaleFilter grayfilter = new GrayScaleFilter();
        grayfilter.apply(original);
        width = original.getWidth();
        height = original.getHeight();
        
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                image.setPixel(x, y, edge(x, y));
            }
        }
        imageManager.setCurrentImage(image);
    }

    private Color edge(int xpos, int ypos)//(0,0) (0,1) (0,2)
                                          //(1,0) (1,1) (1,2)
                                          //(2,0) (2,1) (2,2)
    {
        List<Color> pixels = new ArrayList<Color>(9);
        List<Double> sobelxVals = new ArrayList<Double>(9);
        List<Double> sobelyVals = new ArrayList<Double>(9);
       //(1,1)
       // (0,0) (1,0) (2,0) (0,1) (1,1) (2,1) (0,2) (1,2) (2,2)
       //-0.125 -0.25  -0.125  0     0     0     0.125 0.25 0.125 for x
       // -0.125  0    0.125  -0.25  0    0.25   -0.125   0   0.125
       //or -1 and -2
       int icount = -1;
        for(int y = ypos-1; y <= ypos+1; y++) {
            for(int x = xpos-1; x <= xpos+1; x++) {
                icount = icount + 1;
                if( x >= 0 && x < width && y >= 0 && y < height ) {
                    pixels.add(original.getPixel(x, y));
                    sobelxVals.add(sobelx[icount]);
                    sobelyVals.add(sobely[icount]);
                }
            }
        }
        //horizontal sobel
        return new Color(sobelRed(pixels, sobelxVals, sobelyVals), sobelGreen(pixels, sobelxVals, sobelyVals), 
                        sobelBlue(pixels, sobelxVals, sobelyVals));
    }


    private int sobelRed(List<Color> pixels, List<Double> sobelxVal,List<Double> sobelyVal)
    {
        int redx = sobeldirectionRed(pixels, sobelxVal);
        int redy = sobeldirectionRed(pixels, sobelyVal);
        int redgrad = (int)Math.round(Math.sqrt(Math.pow(redx, 2) + Math.pow(redy, 2)));
        if (redgrad > 255){
            redgrad = 255;
        }
        if (redgrad < 0){
            redgrad = 0;
        }
        return redgrad;

    }

    private int sobelGreen(List<Color> pixels, List<Double> sobelxVal,List<Double> sobelyVal)
    {
        int greenx = sobeldirectionGreen(pixels, sobelxVal);
        int greeny = sobeldirectionGreen(pixels, sobelyVal);
        int greengrad = (int)Math.round(Math.sqrt(Math.pow(greenx, 2) + Math.pow(greeny, 2)));
        if (greengrad > 255){
            greengrad = 255;
        }
        if (greengrad < 0){
            greengrad = 0;
        }
        return greengrad;

    }

    private int sobelBlue(List<Color> pixels, List<Double> sobelxVal,List<Double> sobelyVal)
    {
        int bluex = sobeldirectionBlue(pixels, sobelxVal);
        int bluey = sobeldirectionBlue(pixels, sobelyVal);
        int bluegrad = (int)Math.round(Math.sqrt(Math.pow(bluex, 2) + Math.pow(bluey, 2)));
        if (bluegrad > 255){
            bluegrad = 255;
        }
        if (bluegrad < 0){
            bluegrad = 0;
        }
        return bluegrad;

    }

    private int sobeldirectionRed(List<Color> pixels, List<Double> sobelVals)
    {
        int max = 255;
        int min = 0;
        double total = 0;
        int sobelCount = 0;
        for(Color color : pixels) {
            int val = color.getRed();
            total += val*sobelVals.get(sobelCount);
            sobelCount = sobelCount + 1;
            }
        if (total > max){
                total = max;
        }
        if (total < min){
                total = min;
        }

        return(int)Math.round(total);
    }

    private int sobeldirectionGreen(List<Color> pixels, List<Double> sobelVals)
    {
        int max = 255;
        int min = 0;
        double total = 0;
        int sobelCount = 0;
        for(Color color : pixels) {
            int val = color.getGreen();
            total += val*sobelVals.get(sobelCount);
            sobelCount = sobelCount + 1;
            }
        if (total > max){
                total = max;
        }
        if (total < min){
                total = min;
        }

        return(int)Math.round(total);
    }

    private int sobeldirectionBlue(List<Color> pixels, List<Double> sobelVals)
    {
        int max = 255;
        int min = 0;
        double total = 0;
        int sobelCount = 0;
        for(Color color : pixels) {
            int val = color.getBlue();
            total += val*sobelVals.get(sobelCount);
            sobelCount = sobelCount + 1;
            }
        if (total > max){
                total = max;
        }
        if (total < min){
                total = min;
        }

        return(int)Math.round(total);
    }

}