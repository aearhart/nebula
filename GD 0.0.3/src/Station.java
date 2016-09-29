import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.sound.sampled.Control;

public class Station extends Satellite implements MouseListener {

	private static final long serialVersionUID = 1L;
	private int AreaOfInfluence = 100;
	
	protected int costGas = 0;
	protected int costWater = 0;
	protected int costMineral = 0;
	
	public Station(Integer locX, Integer locY, Integer sz, String n) {
		super(locX, locY, sz);
		
		this.setType("S");
		this.setResource(0);
		costWater = 5;
		costMineral = 5;
		costGas = 5;
		this.setNum(n);
		this.setColors(Color.GREEN, Color.BLACK, Color.RED);
		addMouseListener(this);
	}
	
	public Station(ClientController clientController, Integer locX, Integer locY, Integer sz, String n) {
		super(clientController, locX, locY, sz);
		
		this.setType("S");
		this.setResource(0);
		costWater = 5;
		costMineral = 5;
		costGas = 5;
		this.setNum(n);
		this.setColors(Color.GREEN, Color.BLACK, Color.RED);
		addMouseListener(this);
	}
	
	public int getAoI() {
		return AreaOfInfluence;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(fillCol);
		g.fillRect(0, 0, s, s);	
		
		// outline
		g.setColor(borderCol);
		g.drawRect(0, 0, s, s);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		switch (control.getStatus()) {
		case "Claiming": { // Claiming space stations
				if(this.owner == null) { // make current player owner
					setOwner(control.getPlayer()); 
					repaint();
					control.setStatus("");
					control.printToInstructionArea(control.getStatus());
				}
				else { // already owned: cannot claim
					control.printToInstructionArea("This is not the space station you're looking for! It has already been claimed by " + owner.getName());
				}
				return;
			}
		case "Upgrade": { // Upgrading space stations
			if (this.owner == null) { // not owned: EVENTUALLY IMPLEMENT BUYING SYSTEM
				control.printToHoverArea("You don't own this station.");
			}
			else if (this.owner != control.getPlayer()) { // station owned by opponent
				control.printToHoverArea("Woah woah stop right there! This space station is owned by " + owner.getName() + ". Don't waste your resources on them.");
			}
			else if (level > maxLevel) { // At max level, can't upgrade
				control.printToHoverArea("This space station is at max level. It's as high tech as you can get in this era!");
			}
			else { // station owned by current player
				// do you have the necessary resources? 
				if (canPurchase()) {
					upgradeSatellite();
					AreaOfInfluence = (int)((float)(AreaOfInfluence) * 1.5);
					control.setStatus("collectResources");
					control.printToHoverArea("Upgraded station to level " + level + ".");
					control.printToPlayerArea();
				}
				else { // you don't have the resources
					control.printToHoverArea("You don't have enough resources to upgrade this station. Patience, " + owner.getName());
				}
			}
			return;
		} // end case
		} // end switch
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		switch (control.getStatus()) {
		case "Claiming": { // Claiming a station
			if(this.owner == null) { // not owned
				switchColors();
				control.drawAoI(this);
				repaint(); }
			if (this.owner == control.getOpponent()) {
				control.printToHoverArea("This station is owned by " + control.getOpponent().getName() + ". 100% off limits.");
			}
			else { 
				control.printToHoverArea("You do not own this space station. But you could, for the price of a click of a button!"); }
			return;	
			}
		case "Wait": {
			switchColors();
			control.removeAoI();
			repaint();
			String str = ".";
			if (owner != null) {
				str = " owned by " + owner.getName();
			}
			control.printToHoverArea("A level " + level + " space station");
		}
		case "Upgrade": { // Upgrading a station
			if(this.owner == control.getPlayer()) { // draw AoI
				switchColors();
				control.drawAoI(this);
				repaint(); 
				control.printToHoverArea("Your level " + level + " " + "station costs : " + costWater + " water, " + costMineral + " metal, and " + costGas + " gas.");
				}
			else if (this.owner == control.getOpponent()) {
				control.printToHoverArea("This station is owned by " + control.getOpponent().getName() + ". ");
			}
			else {control.printToHoverArea("This space station is unmanned! Would you like to build here?"); }
			return;
			} // end case
		} // end switch
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		switch (control.getStatus()) {
		case "Claiming": { // Claiming a station
				if(this.owner == null || this.owner == control.getPlayer()) {
					switchColors();
					control.removeAoI();
					control.printToHoverArea();
					repaint(); }
				return;
			}
		case "Wait": {
			switchColors();
			control.removeAoI();
			repaint();
			control.printToHoverArea();
		}
		case "Upgrade": { // Upgrading a station
			if(this.owner == control.getPlayer()) { // remove AoI
				switchColors();
				control.removeAoI();
				repaint();
				}
			control.printToHoverArea();
			return;
			}
		}
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
