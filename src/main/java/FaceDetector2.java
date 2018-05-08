/**
 * Created by Dost Muhammad on 4/26/2018.
 */

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.BradleyLocalThreshold;
import Catalano.Imaging.Filters.Grayscale;
import Catalino.ImprovedLocalBinaryPattern;
import ImageUtils.CompareImage;
import ImageUtils.ImageComparator;
import Services.service;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class FaceDetector2 extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final HaarCascadeDetector detector = new HaarCascadeDetector();
    private Webcam webcam = null;
    private BufferedImage img = null;
    private List < DetectedFace > faces = null;
    private JLabel lblname;
    private JTextField txtname;
    private JButton btnsave;
    private JPanel jp2;
    private FImage image1=null;
    private ImagePanel panel=null;
    public FaceDetector2() throws IOException {
        webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.open(true);
        img = webcam.getImage();
        webcam.close();
         panel = new ImagePanel(img);
        panel.setPreferredSize(WebcamResolution.VGA.getSize());
        add(panel);
        setTitle("Face Recognizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void detectFace() {
        JFrame fr = new JFrame("Discovered Faces");
        lblname=new JLabel("Enter Face name ");
        txtname=new JTextField(20);

        jp2=new JPanel();


        btnsave=new JButton("Save");

        faces = detector.detectFaces(ImageUtilities.createFImage(img));
        if (faces == null) {
            System.out.println("No faces found in the captured image");
            return;
        }
        BufferedImage bf=null;
        Iterator < DetectedFace > dfi = faces.iterator();
        while (dfi.hasNext()) {
            DetectedFace face = dfi.next();
             image1 = face.getFacePatch();
//             FImage bfg = face.getFacePatch();
//            String name= CompareImage.compar(image1);
//            System.out.println("welcome ! "+name);



            ImagePanel p = new ImagePanel(ImageUtilities.createBufferedImage(image1));

            fr.add(p);
          bf= ImageUtilities.createBufferedImage(image1);
        }
        ImageComparator comparator=new ImageComparator(bf,bf);

        System.out.println("face2face "+comparator.getPixelDiffPercent(bf,bf));

        // Will be left-aligned.
        JPanel configurePanel = new JPanel();
        //configurePanel.add(new JButton("Save"));


        // Will be right-aligned.
        JPanel okCancelPanel = new JPanel();
        okCancelPanel.add(new JLabel("Enter face Name"));
        txtname.setBounds(140, 110, 200,30);
        txtname.getPreferredSize();
        okCancelPanel.add(txtname);

        // The full panel.
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(okCancelPanel,  BorderLayout.WEST);
        buttonPanel.add(configurePanel, BorderLayout.AFTER_LAST_LINE);


        fr.add(buttonPanel);
       // btnsave.addActionListener(e -> System.out.println("Handled by Lambda listener"));

//        btnsave.addMouseListener(new MouseAdapter(){
//
//            public void mouseClicked(MouseEvent evt) {
//
//                if(SwingUtilities.isLeftMouseButton(evt)){
//
//                  System.out.println("mouse is clicked"+txtname.getText());
//                }
//
//            }
//
//        });

        btnsave.addActionListener(new ActionListener() {
            //@Override
            public void actionPerformed(ActionEvent evt) {

                service obj=new service();
                BufferedImage bf= ImageUtilities.createBufferedImage(image1);
                int x=obj.saveImage(bf,txtname.getText());
                if(x>0){
                    JOptionPane.showMessageDialog(null, "Person is added to the Database ");
                    txtname.setText("");
                    fr.setVisible(false);
                    panel.setVisible(false);
                }else
                {
                    JOptionPane.showMessageDialog(null, "Error Occur! ");
                    txtname.setText("");
                    fr.setVisible(false);
                    panel.setVisible(false);
                }


            }
        });

        // Show it.
//        JFrame t = new JFrame("Save user Image");
//        t.setContentPane(buttonPanel);
//        t.setContentPane(buttonPanel);
//        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        t.setSize(400, 65);
//        t.setVisible(true);
//        fr.add(t);
        fr.add(btnsave);
        System.out.println("Snapshot taken from cam and stored at "+new File("").getAbsolutePath()+"\\picture.png");

        try {
            ImageIO.write(bf, "PNG", new File("picture.png"));
            FastBitmap fb = new FastBitmap("D:\\workspace\\_faceStarter\\4mHm\\Recognition4MHM\\picture.png");

            ImprovedLocalBinaryPattern obj=new ImprovedLocalBinaryPattern();
            Grayscale g = new Grayscale();
            //g.applyInPlace(fb);
            BradleyLocalThreshold bradley = new BradleyLocalThreshold();
            bradley.applyInPlace(fb);
            obj.ProcessImage(fb);
            int count = (int) ((obj.ProcessImage(fb).getEntropy()+obj.ProcessImage(fb).getKurtosis()+obj.ProcessImage(fb).getMax()+obj.ProcessImage(fb).getMean()+obj.ProcessImage(fb).getKurtosis()+obj.ProcessImage(fb).getStdDev())/obj.ProcessImage(fb).getMedian());
            System.out.println("hash code : "+count);
            System.out.print("mean"+obj.ProcessImage(fb).getMean()+"--etropy"+obj.ProcessImage(fb).getEntropy()+"values"+obj.ProcessImage(fb).getValues().length+"total"+obj.ProcessImage(fb).getTotal());


        } catch (IOException e) {
            e.printStackTrace();
        }
        fr.setLayout(new FlowLayout(0));
        fr.setSize(500, 500);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setVisible(true);
    }
    public static void main(String[] args) throws IOException {
        new FaceDetector2().detectFace();
    }
}
