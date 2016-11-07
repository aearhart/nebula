import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class Spaceship extends JComponent implements MouseListener {

	private static final long serialVersionUID = 1L;
	
	protected ClientController control;
	
	private Player controller;

	private int maxFuel = 10;
	private int currFuel;
	private int costFuel = 2;
	
	private int range = 100;

	private Satellite currSat;
	
	private BufferedImage image = null;
	private int size = 20;
	
	public Spaceship(ClientController clientController, Player p, Satellite s) {
		control = clientController;
		controller = p;  
		
		currSat = s;
		
		currFuel = maxFuel;
		
		setImage("Spaceship.png");
		
		
	}

	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(image, 0, 0, size, size, null);
		
		// outline
		//g.setColor(Color.BLACK);
		//g.drawOval(0, 0, size, size);
	}
	
	
	public Boolean withinRange(Satellite dest) {
		int distance = (int) Math.sqrt(Math.pow(Math.abs(currSat.getMidX() - dest.getMidX()), 2) + Math.pow(Math.abs(currSat.getMidY() - dest.getMidY()), 2));
		if (distance <= (range + currSat.getSz() + dest.getSz()))
			return true;
		return false;
	}
	
	public void move (Satellite dest) {
		// ASSUMED THAT WITHIN RANGE AND THAT YOU HAVE ENOUGH FUEL
		currFuel -= costFuel;
		currSat = dest;
		control.placeShip(this);
	}

	protected void setImage(String i) {
		try {                
			image = ImageIO.read(getClass().getResource(i)); } 
		catch (IOException ex) {
			// handle exception...
			System.out.println("IO EXCEPTION: "+ ex); }
	}
	
	public int getFullSize() {
		return size;
	}
	
	public int getHalfSize() {
		return size/2;
	}

	public int getFuel() {
		return currFuel;
	}


	public void setFuel(int fuel) {
		this.currFuel = fuel;
	}
	
	
	public Satellite getCurrSat() {
		return currSat;
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
