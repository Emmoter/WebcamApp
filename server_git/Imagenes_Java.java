 
 
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
 
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class Imagenes_Java extends Canvas {
 
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
 
    public Imagenes_Java() {
        JFrame ventana = new JFrame("Prueba de Imagen");
        JPanel panel = (JPanel) ventana.getContentPane();
        setBounds(0, 0, WIDTH, HEIGHT);
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(null);
        panel.add(this);
        ventana.setBounds(0, 0, WIDTH, HEIGHT);
        ventana.setVisible(true);
        ventana.addWindowListener(new WindowAdapter() {
 
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
 
    public BufferedImage Cargar_Imagen(String nombre) {
 
        URL url = null;
        try {
 
          //  Ruta de la imagen
            return ImageIO.read(new File("picture"));
           // return ImageIO.read(url);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen " + nombre + " de " + url);
            System.out.println("El error fue : " + e.getClass().getName() + " " + e.getMessage());
            System.exit(0);
            return null;
        }
    }
 
    public void paint(Graphics g) {
 
 
        BufferedImage imagen = Cargar_Imagen("/home/jose/Documentos/1432475215_f648d7c666.jpg");
        g.drawImage(imagen, 40, 40, this);
    }
 
    public static void main(String[] args) {
        Imagenes_Java img = new Imagenes_Java();
    }
}