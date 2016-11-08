import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Map extends JPanel implements MouseListener{
	private static final long serialVersionUID = 1L;
	private ClientController control;
	private BufferedImage bg = null;
	private int winsize = 1000;
	
	public Map(ClientController clientController){
		control = clientController;
		this.setLayout(null);
		this.setPreferredSize(new Dimension (winsize, winsize));
		
		try {                
			bg = ImageIO.read(getClass().getResource("bg.png"));
		} catch (IOException ex) {
			// handle exception...
			System.out.println("IO EXCEPTION: "+ ex);
		}
		addMouseListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(bg, 0, 0, winsize, winsize, null);
		//other
		
		g.setColor(control.AoIc);
		g.drawOval(control.AoIx, control.AoIy, control.AoIs, control.AoIs);
		
		
		g.setColor(Color.BLACK);
		for(int i = 0; i < 11; i++)
			for(int j = 0; j < 11; j++)
				g.drawRect(i*100, j*100, 100, 100);
		//g.drawOval(800, 10, 10, 10);
		//g.drawOval(700, 50, 200, 3);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (control.getStatus().equals("WIN"))
			control.close();
	}

	@Override
	public void mousePressed(MouseEvent e) {

		
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		
	}

	@Override
	public void mouseEntered(MouseEvent e) {

		
	}

	@Override
	public void mouseExited(MouseEvent e) {

		
	}

}
