import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Sun extends Satellite implements MouseListener{

	private Color blank = new Color(0, 0, 0, 0);
	
	public Sun() {
		super(450, 450);
	}
	
	public Sun(ClientController clientController) {
		super(clientController, 450, 450);
		
		// image
		setImage("Sun.png");
		//num = "s0";
		this.setColors(blank, Color.BLACK, Color.ORANGE);
		addMouseListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(image, 0, 0, s, s, null);
		
		// outline
		g.setColor(Color.BLACK);
		g.drawOval(0, 0, s, s);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (control.getStatus()) {
		case "Claiming": {
			// do nothing
			return;
			}
		case "Upgrade": { // Sun taking place as "skip" option.
			// 
			control.setStatus("collectResources");
			control.printToInstructionArea("Skipping turn.");
			control.printToPlayerArea();
			return;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		switch (control.getStatus()) {
		case "Claiming": {
			// do nothing
			return;
			}
		case "Wait": {
			return;
		}
		case "Upgrade": {
			// 
			switchColors();
			control.printToHoverArea("Click to skip turn");
			return;
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		switch (control.getStatus()) {
		case "Claiming": {
			// do nothing
			return;
			}
		case "Wait": {
			// do nothing
			return;
			}
		case "Upgrade": {
			// within AoI
			switchColors();
			control.printToHoverArea();
				return;
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
