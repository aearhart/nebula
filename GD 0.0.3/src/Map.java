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
		Color[] sectorCols = {Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.ORANGE, Color.MAGENTA};
		for (int i = 0; i < 1000; i+=5) {
			for (int j = 0; j < 1000; j+=5) {
				int sector = control.findSector((double)i, (double)j);
				g.setColor(sectorCols[sector-1]);
				g.drawOval(i, j, 1, 1);
			}
		}
		//g.drawOval(800, 10, 10, 10);
		//g.drawOval(700, 50, 200, 3);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		control.findSector((double)e.getX(), (double)e.getY());
		if (control.getStatus().equals("WIN"))
			control.close();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
