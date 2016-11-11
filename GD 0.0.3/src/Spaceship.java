import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class Spaceship extends Satellite implements MouseListener {

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
	private Boolean drawOutline = false;
	
	public Spaceship(ClientController clientController, Player p, Satellite s, String n) {
		super(0, 0);
		control = clientController;
		controller = p;  
		currSat = s;
		currFuel = maxFuel;
		setImage("Spaceship.png");
		this.setType("SS");
		this.setNum(n);
		if (control == null)
			this.setName("ss");
		else
			this.setName(controller.getName() + "'s spaceship");
		addMouseListener(this);
		
	}
	
	@Override
	public String printState() {
		ArrayList<String> aList = new ArrayList<String>();
		aList.add("spaceship");
		aList.add(t);
		aList.add(num);
		aList.add(name);
		aList.add(currSat.getNum());
		aList.add(Integer.toString(size));
		aList.add(Integer.toString(currFuel));
		aList.add(Integer.toString(maxFuel));
		aList.add(ownerNum);
		
		return Globals.addDelims(aList);
	}
	
	@Override
	public int update(String [] ary, int i) {
		if (! ary[i++].equals("spaceship"))
			return -1;
		t = ary[i++];
		num = ary[i++];
		name = ary[i++];
		replaceSpaceship(ary[i++]);
		size = Integer.parseInt(ary[i++]);
		currFuel = Integer.parseInt(ary[i++]);
		maxFuel = Integer.parseInt(ary[i++]);
		setOwner(ary[i++]);
		return i;
	}
	
	public void replaceSpaceship(String newSat) {
		System.out.println("NEWW " + newSat);
		if (control == null) { // we're in server
			currSat = new Planet(0, 0, newSat, newSat); // something it can getNum() from
			return;
		}
		if (newSat.equals(currSat.getNum())) return;
		// it's really new
		
		currSat = control.getSat(newSat);
		control.placeShip(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(image, 0, 0, size, size, null);
		if (drawOutline) {
			g.setColor(Color.ORANGE);
			g.drawOval(0, 0, size, size);
		}
	}
	
	@Override
	public String info() {
		String str = "";
		str += controller.getName() + "'s spaceship:\n";
		str += "Currently orbiting " + currSat.getName();
		str += "\n Max Fuel: " + maxFuel;
		str += "\n Current Fuel: " + currFuel;
		str += "\n\nMovement costs 2 fuel.";
		return str;
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

	public Player getController() {
		return controller;
	}
	
	public void setController(Player player) {
		controller = player;
	}

	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		control.infoPanel2.selectSatellite(this);
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		drawOutline = true;
		repaint();
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		drawOutline = false;
		repaint();
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
	
}
