import java.awt.*;

public class SingleSeamCarve {
    private Image original;
    private int width;
    private int height;
    private ImageManager imageManager;

    public SingleSeamCarve(ImageManager imageManager){
        this.imageManager = imageManager;
    }

    public void apply(Image image) {
        original = new Image(image);
        width = original.getWidth();
        height = original.getHeight();

       int[][] seam = new int[height][2];
       seam[0][1] = 0;

       int min_energy = 255;
       for (int x = 0; x < width; x++){
           if (image.getPixel(x,0).getRed() < min_energy){
               min_energy = image.getPixel(x,0).getRed();
               seam[0][0] = x;
           }
       }
       for (int y = 1; y < height; y++){
           if(seam[y-1][0] == width-1){
               if (image.getPixel(seam[y-1][0]-1,y).getRed() < image.getPixel(seam[y-1][0],y).getRed()){
                   seam[y][0] = seam[y-1][0]-1;
                   seam[y][1] = y;
               }
               else{
                   seam[y][0] = seam[y-1][0];
                   seam[y][1] = y;
               }
           }
           else if (seam[y-1][0] == 0){
               if (image.getPixel(seam[y-1][0]+1,y).getRed() < image.getPixel(seam[y-1][0],y).getRed()){
                   seam[y][0] = seam[y-1][0]+1;
                   seam[y][1] = y;
               }
               else{
                   seam[y][0] = seam[y-1][0];
                   seam[y][1] = y;
               }
           }
           else{
               if (image.getPixel(seam[y-1][0],y).getRed() < image.getPixel(seam[y-1][0]+1,y).getRed() && image.getPixel(seam[y-1][0],y).getRed() < image.getPixel(seam[y-1][0]-1,y).getRed()){
                   seam[y][0] = seam[y-1][0];
                   seam[y][1] = y;
               }
               else if (image.getPixel(seam[y-1][0]-1,y).getRed() < image.getPixel(seam[y-1][0]+1,y).getRed() && image.getPixel(seam[y-1][0]-1,y).getRed() < image.getPixel(seam[y-1][0],y).getRed()){
                   seam[y][0] = seam[y-1][0]-1;
                   seam[y][1] = y;
               }
               else{
                   seam[y][0] = seam[y-1][0]+1;
                   seam[y][1] = y;
               }

           }
       }
       for (int y = 0; y < height; y++){
           image.setPixel(seam[y][0], seam[y][1], new Color(255,0,0));
       }

    }
}
