import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Map extends JLayeredPane implements MouseListener, MouseMotionListener{
	private static final long serialVersionUID = 1L;
	private ClientController control;
	private BufferedImage bg = null;
	private JLabel l = new JLabel("", SwingConstants.CENTER);
	private Color labelBackgroundColor = new Color(4, 45, 45, 120);
	public Boolean hovering = true;
	public JLabel hoverBox = new JLabel("");
	
	private int mouseX = 0;
	private int mouseY = 0;
	
	public Map(ClientController clientController){
		control = clientController;
		this.setLayout(null);
		this.setPreferredSize(new Dimension (Globals.mapSize, Globals.mapSize));
		
		// text label
		Font f = new Font("Consolas", Font.PLAIN, 30);
		l.setFont(f);
		l.setForeground(Color.WHITE);
		l.setOpaque(true);
		l.setBackground(labelBackgroundColor);
		l.setLocation(0, 0);
		l.setBounds(0, 0, Globals.mapSize, Globals.mapSize);
		this.add(l, 5);
		
		
		try {                
			bg = ImageIO.read(getClass().getResource("bg.png"));
		} catch (IOException ex) {
			// handle exception...
			System.out.println("IO EXCEPTION: "+ ex);
		}
		
		hoverBox.setFont(Globals.f);
		hoverBox.setPreferredSize(new Dimension(200, 50));
		//hoverBox.setVisible(false);
		hoverBox.setForeground(Color.WHITE);
		hoverBox.setBackground(Color.LIGHT_GRAY);
		hoverBox.setText("");
		hoverBox.setOpaque(false);
		//hoverBox.setLocation(450, 450);
		hoverBox.setBounds(450, 450, 100, 50);
		this.add(hoverBox, 6);
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void hoverBoxOn(String txt) {
		hoverBox.setText(txt);
		hoverBox.setOpaque(true);

	}
	public void hoverBoxOff() {
		hoverBox.setText("");
		hoverBox.setOpaque(false);
	}
	
	public void hover(String txt) {
		l.setText(txt);
		l.setOpaque(true);

		hovering = true;
	}
	
	public void clear() {
		l.setText("");
		l.setOpaque(false);

		hovering = false; 
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Background image
		g.drawImage(bg, 0, 0, Globals.mapSize, Globals.mapSize, null);

		// Area of Influence circle
		g.setColor(control.AoIc);
		g.drawOval(control.AoIx, control.AoIy, control.AoIs, control.AoIs);
		
		
		int size = 10;
		// Drawing the grid lines
		g.setColor(Color.BLACK);
		for(int i = 0; i < Globals.mapSize/size + 1; i++)
			for(int j = 0; j < Globals.mapSize/size + 1; j++)
				g.drawRect(i*size, j*size, size, size);

		
		/* 
		// Highlight sectors with different colors
		Color[] sectorCols = {Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.ORANGE, Color.MAGENTA};
		for (int i = 0; i < 1000; i+=10) {
			for (int j = 0; j < 1000; j+=5) {
				int sector = control.findSector((double)i, (double)j);
				g.setColor(sectorCols[sector-1]);
				g.drawOval(i, j, 1, 1);
			}
		}       */
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		control.findSector((double)e.getX(), (double)e.getY());
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
		//System.out.println("hello?");
	}

	@Override
	public void mouseExited(MouseEvent e) {
	
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//System.out.println("hey!");
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		int x = e.getX();
		int y = e.getY();
		if (x <= 120) x += 120;
		if (y >= (Globals.mapSize - 70)) y -= 70;
		x += 20;
		y -= 50;


		hoverBox.setLocation(x, y);
	}

}
