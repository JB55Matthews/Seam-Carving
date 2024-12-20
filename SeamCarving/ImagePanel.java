import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JComponent
{
    private int width, height;
    private Image panelImage;

    public ImagePanel()
    {
        // Just arbitrary for default
        width = 350;
        height = 250;
        panelImage = null;
    }

    public void setImage(Image image)
    {
        if(image != null) {
            width = image.getWidth();
            height = image.getHeight();
            panelImage = image;
            repaint();
        }
    }

    public void clearImage()
    {
        Graphics imageGraphics = panelImage.getGraphics();
        imageGraphics.setColor(Color.LIGHT_GRAY);
        imageGraphics.fillRect(0, 0, width, height);
        repaint();
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(width, height);
    }

    public void paintComponent(Graphics g)
    {
        Dimension size = getSize();
        g.clearRect(0, 0, size.width, size.height);
        if(panelImage != null) {
            g.drawImage(panelImage, 0, 0, null);
        }
    }
}
