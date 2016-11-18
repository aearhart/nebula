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
	
	private Player controller = null;

	private int maxFuel = 10;
	private int currFuel;
	private int costFuel = 2;
	private int range = 100;

	private Satellite currSat = null;
	private String currSatNum = "";
	private BufferedImage image = null;
	private int size = 20;
	private Boolean drawOutline = false;
	
	public Spaceship(Player p, String k) {
		super(0, 0);
		this.setType("SS");
		controller = p;
		setOwner(p);
		num = k;
	}
	
	public Spaceship(ClientController clientController, String playerNum, String n) {
		super(clientController, -100, -100);
		currFuel = maxFuel;
		setImage("Spaceship.png");
		this.setType("SS");
		this.setNum(n);

		if (playerNum.equals(control.getPlayer().getNum()))
			controller = control.getPlayer();
		else controller=  control.getOpponent();
		setOwner(controller);
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
		aList.add(currSatNum);
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
		String replaceship = ary[i++];

		size = Integer.parseInt(ary[i++]);
		currFuel = Integer.parseInt(ary[i++]);
		maxFuel = Integer.parseInt(ary[i++]);
		setOwner(ary[i++]);
		replaceSpaceship(replaceship);
		return i;
	}
	
	public void replaceSpaceship(String newSat) {
		if (newSat.equals(currSatNum)) return; // nothing new
		//else it's new
		currSatNum = newSat;
		if (! (control == null)) { // update the sat object
			currSat = control.getSat(newSat);
			control.placeShip(this);
		}
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
		if (! (currSat==null)) {
		str += "Currently orbiting " + currSat.getName();
		}
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
		setCurrSat(dest);
	}

	protected void setImage(String i) {
		try {                
			image = ImageIO.read(getClass().getResource(i)); } 
		catch (IOException ex) {
			// handle exception...
			System.out.println("IO EXCEPTION: "+ ex); }
	}
	
	public void waiting() {
		// spaceship waits, collects fuel
		currFuel += 1;
		if (currFuel > maxFuel) currFuel = maxFuel;
	}
	
	public int getFullSize() { //TODO: fully integrate spaceship as a subset of satellite 
		return size;
	}
	
	public int getHalfSize() {
		return size/2;
	}

	public int getFuel() {
		return currFuel;
	}

	public int costFuel() {
		return costFuel;
	}
	
	public void setFuel(int fuel) {
		this.currFuel = fuel;
	}
	
	
	public Satellite getCurrSat() {
		return currSat;
	}
	
	public void setCurrSat(Satellite sat) {
		currSat = sat;
		currSatNum = sat.getNum();
		control.placeShip(this);
	}

	public Player getController() {
		return controller;
	}
	
	public void setController(Player player) {
		controller = player;
	}

	public int getRange() {
		return range;
	}
	
	@Override 
	public Integer getMidX() {
		return currSat.getMidX();
	}
	
	@Override
	public Integer getMidY() {
		return currSat.getMidY();
	}
	
	public void select() {
		control.drawAoI(this);
		drawOutline = true;
		repaint();
	}
	
	public void deselect() {
		drawOutline = false;
		control.removeAoI();
		repaint();

	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		control.infoPanel2.selectSatellite(this);
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		select();
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		deselect();
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
	
}
