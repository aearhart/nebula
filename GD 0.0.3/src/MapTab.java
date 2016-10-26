

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MapTab extends JPanel{
	private ClientController control;
	public String name = "MapTab";
	
	protected BufferedImage img = null;
	

	
	public MapTab(ClientController clientController) {
		control = clientController;
		//this.setBackground(new Color(142, 255, 253, 255));
		try {
			img = ImageIO.read(getClass().getResourceAsStream("menuBackground.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img,  0,  0, getWidth(), getHeight(), this);
		
	}
	
	public void addComponents(InfoPanel p1, Map m, InfoPanel2 p2) {
		this.add(p1);
		this.add(m);
		this.add(p2);
	}

	public void update() {
		this.setVisible(true);
	}
	
	public String getName() {
		return name;
	}

}
