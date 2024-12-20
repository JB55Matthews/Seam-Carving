import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOError;

public class ImageScreen extends JFrame implements ActionListener {
    private ImagePanel imagePanel;
    private JButton halfButton, thirdButton, quarterButton, enterButton, resetButton, fifthButton;
    private JMenu fileMenu, forcedFiltersMenu;
    private JMenuItem openItem, closeItem, saveItem, edgeDetectItem, energyItem, singleSeamItem;
    private JMenuBar menuBar;
    private ImageManager imageManager;
    private static JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
    private Energy energy;
    private GrayScaleFilter grayScaleFilter;
    private EdgeDetect edgeDetect;
    private EdgeDetect2 edgeDetect2;
    private Carve carve;
    private JTextField pixelField;
    private JLabel workingLabel;
    private SingleSeamCarve singleSeamCarve;

    public ImageScreen(ImageManager imageManager){
        this.imageManager = imageManager;
        energy = new Energy(imageManager);
        grayScaleFilter = new GrayScaleFilter();
        edgeDetect = new EdgeDetect(imageManager);
        edgeDetect2 = new EdgeDetect2(imageManager);
        singleSeamCarve = new SingleSeamCarve(imageManager);
        carve = new Carve(imageManager);
        JPanel contentPane = (JPanel)this.getContentPane();
        contentPane.setBorder(new EmptyBorder(6, 6, 6, 6));

        makeMenuBar();

        contentPane.setLayout(new BorderLayout(6, 6));

        imagePanel = new ImagePanel();
        imagePanel.setBorder(new EtchedBorder());
        contentPane.add(imagePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));

        JLabel topLabel = new JLabel("Pixels to reduce by");
        buttonPanel.add(topLabel);

        fifthButton = new JButton("1/5");
        fifthButton.addActionListener(this);
        buttonPanel.add(fifthButton);

        quarterButton = new JButton("1/4");
        quarterButton.addActionListener(this);
        buttonPanel.add(quarterButton);

        thirdButton = new JButton("1/3");
        thirdButton.addActionListener(this);
        buttonPanel.add(thirdButton);

        halfButton = new JButton("1/2");
        halfButton.addActionListener(this);
        buttonPanel.add(halfButton);

        JLabel pixelHeader = new JLabel("Pixels to cut");
        pixelField = new JTextField();
        enterButton = new JButton("Enter");
        enterButton.addActionListener(this);
        buttonPanel.add(pixelHeader);
        buttonPanel.add(pixelField);
        buttonPanel.add(enterButton);

        JLabel temp = new JLabel();
        buttonPanel.add(temp);

        resetButton = new JButton("Reset Image");
        resetButton.addActionListener(this);
        buttonPanel.add(resetButton);

        JPanel topPanel = new JPanel(new GridLayout(1,0));
        workingLabel = new JLabel("Standby");
        topPanel.add(workingLabel);
        contentPane.add(topPanel, BorderLayout.NORTH);

        JPanel flow = new JPanel();
        flow.add(buttonPanel);

        //contentPane.add(flow, BorderLayout.WEST);
        contentPane.add(flow, BorderLayout.WEST);

//        showFilename(null);
//        setButtonsEnabled(false);
        pack();
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //frame.setResizable(false);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(d.width/2 - getWidth()/2, d.height/2 - getHeight()/2);
        setVisible(true);
    }

    public void makeMenuBar(){
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        this.add(fileMenu);

        openItem = new JMenuItem("Open");
        openItem.addActionListener(this);
        fileMenu.add(openItem);

        closeItem = new JMenuItem("Close");
        closeItem.addActionListener(this);
        fileMenu.add(closeItem);

        saveItem = new JMenuItem("Save As");
        saveItem.addActionListener(this);
        fileMenu.add(saveItem);

        forcedFiltersMenu = new JMenu("Force Filters");

        edgeDetectItem = new JMenuItem("Edge Detect");
        edgeDetectItem.addActionListener(this);
        forcedFiltersMenu.add(edgeDetectItem);

        energyItem = new JMenuItem("Energy");
        energyItem.addActionListener(this);
        forcedFiltersMenu.add(energyItem);

        singleSeamItem = new JMenuItem("Identify Single Seam");
        singleSeamItem.addActionListener(this);
        forcedFiltersMenu.add(singleSeamItem);

        JMenuItem test = new JMenuItem("test");
        test.addActionListener(this);
        forcedFiltersMenu.add(test);

        menuBar.add(fileMenu);
        menuBar.add(forcedFiltersMenu);
        this.setJMenuBar(menuBar);
    }

    public void actionPerformed (ActionEvent aevt){
        if (aevt.getActionCommand().equals("1/2")){
            workingLabel.setText("Working...");
            javax.swing.SwingUtilities.invokeLater(() -> {
                carveImage(imageManager.getCurrentImage().getWidth()/2);
                workingLabel.setText("Done!");
            });        }

        else if (aevt.getActionCommand().equals("1/3")){
            workingLabel.setText("Working...");
            javax.swing.SwingUtilities.invokeLater(() -> {
                carveImage(imageManager.getCurrentImage().getWidth()/3);
                workingLabel.setText("Done!");
            });
        }

        else if (aevt.getActionCommand().equals("1/4")){
            workingLabel.setText("Working...");
            javax.swing.SwingUtilities.invokeLater(() -> {
                carveImage(imageManager.getCurrentImage().getWidth()/4);
                workingLabel.setText("Done!");
            });
        }

        else if (aevt.getActionCommand().equals("1/5")){
            workingLabel.setText("Working...");
            javax.swing.SwingUtilities.invokeLater(() -> {
                carveImage(imageManager.getCurrentImage().getWidth()/5);
                workingLabel.setText("Done!");
            });
        }

        else if (aevt.getActionCommand().equals("Enter")){
            try{
                workingLabel.setText("Working...");
                javax.swing.SwingUtilities.invokeLater(() -> {
                    int pixels = Integer.parseInt(pixelField.getText());
                    carveImage(pixels);
                    workingLabel.setText("Done!");
                });

            }
            catch (NumberFormatException e) {

            }
        }

        else if (aevt.getActionCommand().equals("Reset Image")){
            reset();
        }

        else if (aevt.getActionCommand().equals("Open")){
            openFile();
        }

        else if (aevt.getActionCommand().equals("Close")){
            close();
        }

        else if (aevt.getActionCommand().equals("Save As")){
            saveAs();
        }

        else if (aevt.getActionCommand().equals("Edge Detect")){
            if(imageManager.getCurrentImage() != null) {
                edgeDetect2.apply(imageManager.getCurrentImage());
                imagePanel.setImage(imageManager.getCurrentImage());
                this.repaint();
            }
        }

        else if (aevt.getActionCommand().equals("Energy")){
            if(imageManager.getCurrentImage() != null) {
                edgeDetect2.apply(imageManager.getCurrentImage());
                energy.apply(imageManager.getCurrentImage());
                imagePanel.setImage(imageManager.getCurrentImage());
                this.repaint();
            }
        }

        else if (aevt.getActionCommand().equals("Identify Single Seam")){
            if(imageManager.getCurrentImage() != null) {
                edgeDetect2.apply(imageManager.getCurrentImage());
                energy.apply(imageManager.getCurrentImage());
                singleSeamCarve.apply(imageManager.getCurrentImage());
                imagePanel.setImage(imageManager.getCurrentImage());
                this.repaint();
            }
        }

        else if (aevt.getActionCommand().equals("test")) {
            if (imageManager.getCurrentImage() != null) {
                for (int i = 0; i < 300; i++) {
                    edgeDetect2.apply(imageManager.getCurrentImage());
                    energy.apply(imageManager.getCurrentImage());
                    carve.apply((imageManager.getEnergyImage()));
                }

//          edgeDetect.apply(imageManager.getCurrentImage());
//            edgeDetect2.apply(imageManager.getCurrentImage());
//            energy.apply(imageManager.getCurrentImage());
//            for (int i = 0; i < 300; i++){
//                carve.apply((imageManager.getEnergyImage()));
//                }
                imagePanel.setImage(imageManager.getCurrentImage());
                pack();
                this.repaint();
            }
        }
    }

    private void carveImage(int pixels){
        if (imageManager.getCurrentImage() != null) {
            for (int i = 0; i < pixels; i++) {
                edgeDetect2.apply(imageManager.getCurrentImage());
                energy.apply(imageManager.getCurrentImage());
                carve.apply((imageManager.getEnergyImage()));
            }

//          edgeDetect.apply(imageManager.getCurrentImage());
//            edgeDetect2.apply(imageManager.getCurrentImage());
//            energy.apply(imageManager.getCurrentImage());
//            for (int i = 0; i < 300; i++){
//                carve.apply((imageManager.getEnergyImage()));
//                }
            imagePanel.setImage(imageManager.getCurrentImage());
            pack();
            this.repaint();
        }
    }

    private void openFile() {
        int returnVal = fileChooser.showOpenDialog(this);

        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File selectedFile = fileChooser.getSelectedFile();
        imageManager.loadImage(selectedFile);

        if(imageManager.getCurrentImage() == null) {
            JOptionPane.showMessageDialog(this,
                    "The file was not in a recognized image file format.",
                    "Image Load Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        imagePanel.setImage(imageManager.getCurrentImage());
        workingLabel.setText("Standby");
        //setButtonsEnabled(true);
        this.pack();
    }

    private void close() {
        imageManager.setCurrentImage(null);
        imagePanel.clearImage();
        //setButtonsEnabled(false);
    }

    private void saveAs() {
        if(imageManager.getCurrentImage() != null) {
            int returnVal = fileChooser.showSaveDialog(this);

            if(returnVal != JFileChooser.APPROVE_OPTION) {
                return;
            }
            File selectedFile = fileChooser.getSelectedFile();
            imageManager.saveImage(imageManager.getCurrentImage(), selectedFile);
        }
    }

    private void reset(){
        Image newOriginalImage = new Image(imageManager.getLoadedImage());
        Image newCurrentImage = new Image(imageManager.getLoadedImage());
        Image newEnergyImage = new Image(imageManager.getLoadedImage());
        imageManager.setOriginalImage(newOriginalImage);
        imageManager.setCurrentImage(newCurrentImage);
        imageManager.setEnergyImage(newEnergyImage);
        imagePanel.setImage(imageManager.getLoadedImage());
        pack();
        this.repaint();
    }
}
