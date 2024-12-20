
public class Carve {
    private Image original;
    private int width;
    private int height;
    private ImageManager imageManager;

    public Carve(ImageManager imageManager){
        this.imageManager = imageManager;
    }

    public void apply(Image image) {
        original = new Image(image);
        width = original.getWidth();
        height = original.getHeight();
//        System.out.println("width "+width);
//        System.out.println("height "+height);

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
//       for (int y = 0; y < height; y++){
//           image.setPixel(seam[y][0], seam[y][1], new Color(255,0,0));
//       }


       image = imageManager.getOriginalImage();
       Image energyImage = imageManager.getEnergyImage();
       Image newEnergyImage = new Image(width-1, height);
       Image newImage = new Image(width-1, height);
       for(int y = 0; y < height; y++){
           for (int x = 0; x < width-1; x++){
               if (seam[y][0] != x){
                   if (seam[y][0] > x){
                       newImage.setPixel(x,y,image.getPixel(x,y));
                       newEnergyImage.setPixel(x,y,energyImage.getPixel(x,y));
                   }
                   else{
                       newImage.setPixel(x-1,y,image.getPixel(x,y));
                       newEnergyImage.setPixel(x-1,y,image.getPixel(x,y));
                   }
               }
           }
       }
       imageManager.setCurrentImage(newImage);
       imageManager.setEnergyImage(newEnergyImage);
       imageManager.setOriginalImage(new Image(newImage));


//       for (int y = 0; y < height; y++){
//           System.out.println(newImage.getPixel(seam[y][0]-1, seam[y][1]));
//       }


//        for(int x = 0; x < width; x++) {
//            //System.out.println(image.getPixel(x,0));
//            image.setPixel(x,0,new Color(255,0,0));
//        }

//        for (int y = 0; y < height; y++){
//            image.setPixel(width/2,y,new Color(255,0,0));
//        }



//        Image newImage = new Image(width/4, height);
//        for(int y = 0; y < height; y++) {
//            for(int x = 0; x < (width/4); x++) {
//                newImage.setPixel(x, y, image.getPixel(x,y));
//            }
//        }
//        imageManager.setCurrentImage(newImage);

//        image = imageManager.getOriginalImage();
//        Image newImage = new Image(width-20, height);
//        for(int y = 0; y < height; y++) {
//            for(int x = 0; x < (width-20); x++) {
//                newImage.setPixel(x,y,image.getPixel(x,y));
////                if (image.getPixel(x,y).getRed() != 255){
////                    newImage.setPixel(x, y, image.getPixel(x,y));
////                }
//            }
//        }
//        imageManager.setCurrentImage(newImage);

    }
}
