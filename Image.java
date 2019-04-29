import javax.imageio.ImageIO;
import java.awt.image. BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;

public class Image {

    /*
     * Show an image stored in an int matrix
     **/ 
    public static void show ( int [][] img ) {
	
	// Create image from integer matrix
	int width = img[0].length;
	int height = img.length;
	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
	//BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
	
	for(int i=0; i<height; i++) {
	    for(int j=0; j<width; j++) {
		int value = img[i][j] << 16 | img[i][j] << 8 | img[i][j];
		image.setRGB(j, i, value);
	    }
	}

	// Show image
	JFrame myWin = new JFrame();
	myWin.setTitle ("Image");
	JLabel label = new JLabel( new ImageIcon(image) );
	myWin.add(label);
	myWin.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
	myWin.pack();
	myWin.setVisible(true);
    }

    /*
     * Read image from file and stores it in an int matrix de fichero y la almacena en una matriz de enteros
    **/
    public static int [][] load ( String fileName ) {
	int [][] img;
	try {
	    BufferedImage imageRGB = ImageIO.read( new File( fileName ) );
	    BufferedImage image = getGrayScale( imageRGB );
	    int height = image.getHeight();
	    int width = image.getWidth();
	    img = new int[height][width];

	    for(int i=0; i<height; i++) {
		for(int j=0; j<width; j++) {
		    int pix = image.getRGB(j,i);
		    Color c = new Color(pix);
		    int R = c.getRed();
		    int G = c.getGreen();
		    int B = c.getBlue();
		    img[i][j] = (int)((R+G+B)/3);
		}
	    }
	}
	catch(Exception e) {
	    return null;
	}
	return img;
    }


    /*
     * Saves in JPG format an image stored in an int matrix
    **/
    public static boolean save ( int [][] img, String fileName ) {
	// Create image from int matrix
	int width = img[0].length;
	int height = img.length;
	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
	
	for(int i=0; i<height; i++) {
	    for(int j=0; j<width; j++) {
		int gray = img[i][j];
		Color c = new Color(gray, gray, gray);
		image.setRGB(j, i, c.getRGB());
	    }
	}

	// Save image
	try {
	    File f = new File(fileName);
	    return ImageIO.write( image, "jpg", f);
	}
	catch( IOException e ) {
	    System.out.println("Error saving image " + fileName);
	    System.out.println(e.getMessage());
	}
	return false;
    }

    /*
     * Converts color image into grayscale
     **/
    private static BufferedImage getGrayScale(BufferedImage inputImage){
	BufferedImage img = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), 
					      BufferedImage.TYPE_BYTE_GRAY);
	Graphics g = img.getGraphics();
	g.drawImage(inputImage, 0, 0, null);
	g.dispose();
	return img;
    }
}