import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Sun extends Satellite {


	public Sun(Controller ctrl) {
		super(ctrl, 450, 450, 100);
		
		try {                
			image = ImageIO.read(getClass().getResource("Sun.png"));
		} catch (IOException ex) {
			// handle exception...
			System.out.println("IO EXCEPTION: "+ ex);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(image, 0, 0, s, s, null);
		
		g.setColor(Color.BLACK);
		g.drawOval(0, 0, s, s);
	}
}
