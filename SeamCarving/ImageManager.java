import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageManager {

    private static final String IMAGE_FORMAT = "jpg";
    private Image currentImage, originalImage, energyImage, loadedImage;

    public ImageManager(){
        currentImage = null;
    }

    public Image loadImage(File imageFile)
    {
        try {
            BufferedImage image = ImageIO.read(imageFile);
            if(image == null || (image.getWidth(null) < 0)) {
                return null;
            }
            Image newImage = new Image(image);
            Image newImage2 = new Image(image);
            Image newImage3 = new Image(image);
            setCurrentImage(newImage);
            setOriginalImage(newImage2);
            setLoadedImage(newImage3);
            return newImage;
        }
        catch(IOException exc) {
            return null;
        }

    }

    public static void saveImage(Image image, File file)
    {
        try {
            ImageIO.write(image, IMAGE_FORMAT, file);
        }
        catch(IOException exc) {
            return;
        }
    }

    public void setCurrentImage(Image newImage){
        currentImage = newImage;
    }

    public Image getCurrentImage(){
        return currentImage;
    }

    public void setOriginalImage(Image newImage){
        originalImage = newImage;
    }

    public Image getOriginalImage(){
        return originalImage;
    }

    public void setEnergyImage(Image newImage){
        energyImage = newImage;
    }

    public Image getEnergyImage(){
        return energyImage;
    }

    public void setLoadedImage(Image newImage){
        loadedImage = newImage;
    }

    public Image getLoadedImage(){
        return loadedImage;
    }
}
