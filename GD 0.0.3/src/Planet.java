import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class Planet extends Satellite implements MouseListener {

	protected int costGas = 0;
	protected int costWater = 0;
	protected int costMineral = 0;
	
	public Planet(Integer locX, Integer locY, Integer sz, Integer numResources) {
		super(locX, locY, sz);
		this.setResource(numResources);
		
		costWater = 3;
		costMineral = 3;
		costGas = 3;
		
		addMouseListener(this); 
	}
	
	public Planet(ClientController clientController, Integer locX, Integer locY, Integer sz, Integer numResources) {
		super(clientController, locX, locY, sz);
		control = clientController;
		this.setResource(numResources);
		
		costWater = 3;
		costMineral = 3;
		costGas = 3;
		
		addMouseListener(this); 
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(fillCol);
		g.fillOval(0, 0, s, s);	
		g.setColor(borderCol);
		g.drawOval(0, 0, s, s);
		if (owner != null) {
			g.setColor(ownerColor);
			g.fillOval((s/2)-5, (s/2)-5, 10, 10);
		}
	}
	
	@Override
	public String printState() {
		ArrayList<String> aList = new ArrayList<String>();
		aList.add("planet");
		aList.add(t);
		aList.add(Integer.toString(x));
		aList.add(Integer.toString(y));
		aList.add(Integer.toString(s));
		aList.add(num);
		aList.add(name);
		aList.add(Integer.toString(costWater));
		aList.add(Integer.toString(costMineral));
		aList.add(Integer.toString(costGas));
		aList.add(Integer.toString(resource));
		aList.add(ownerNum);
		aList.add(Integer.toString(level));
		
		return Globals.addDelims(aList);
	}
	
	@Override
	public void update(String s) {
		
	}
	
	public String info() {
		/* return info of planet */
		String str = getName() + ":\nLevel " + level + "\nResources: " + this.getResources() + "\nCost: " + costWater + " water, " + costMineral + " metal, " + costGas + " gas.";
		return str;
	}
	
	public String info(String str) {
		/* return str + planet info */
		return str + " " + info();
	}
	
	public Boolean planetWithinAoI() {
		/* check to see if planet is within the current player's AoI */
		for (Station sat: control.getPlayer().getStations()) {
			if (control.withinDistance(sat, (Satellite)(this))) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		switch (control.getStatus()) {
		case "Claiming": { // Claiming a space station
			control.printToHoverArea(info());
			return;
			}
		case "Wait": { // waiting for next turn
			// do nothing on click
			return;
		}
		case "Upgrade": { // Upgrading a planet
			// check if within AoI
			if (planetWithinAoI()) { // it's within current player's AoI
				if (this.owner == control.getOpponent()) { // owned by opponent
					control.printToHoverArea("This planet is owned by someone else. Don't throw resources at " + control.getOpponent().getName() + ".");
				}
				if (this.owner == null && canPurchase()) { // not owned, buying the planet
					upgradeSatellite();
					control.setStatus("EndTurn");
					control.printToHoverArea(this.getName() + " is now level " + level + ", gives out " + this.getResources() + ".");
					control.printToPlayerArea();
				}
				else if (level > maxLevel) {
					control.printToHoverArea("This planet is at max level.");
				}
				else if (this.owner == control.getPlayer() && canPurchase()) { // owned, upgrading planet
					upgradeSatellite();
					control.setStatus("EndTurn");
					control.printToHoverArea(this.getName() + " is now level " + level + ", gives out " + this.getResources() + ".");
					control.printToPlayerArea();
				}
				else { // couldn't afford it
					control.printToHoverArea("You uh don't have enough resources to build on this planet.");
					}
			}
			else { // not within player's AoI
				if (this.owner == control.getOpponent()) {
					control.printToHoverArea("This planet is too far away to build on and it's owned by " + owner.getName() + " anyways.");
				}
				else { // not owned
					control.printToHoverArea("This planet is too far away! Upgrade your space stations to expand your reach.");
				}
			}
			return;
		} // end case
		} // end switch
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		switchColors();
		repaint();
		switch (control.getStatus()) {
		case "Claiming": {
			control.printToHoverArea(info());
			return;
			}
		case "Wait": {
			control.printToHoverArea(info());
			return;
		}
		case "Upgrade": { // Upgrading a planet
			if (owner == control.getOpponent()) { // owned by opponent
				control.printToHoverArea("This planet is already owned by " + owner.getName());
			}
			else if (planetWithinAoI() == false) { // outside AoI
				control.printToHoverArea(info("This planet is too far away to build on."));
			}
			else if (owner == null) { // not owned
				control.printToHoverArea(info("Not currently owned! Invest away."));
			}
			else { // owned by current player
				control.printToHoverArea(info("You own this planet!"));
			}
			return;
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		/* switch back to original */
		switchColors();
		repaint();
		switch (control.getStatus()) {
		case "Claiming": {
			control.printToHoverArea();
			return;
			}
		case "Wait": {
			control.printToHoverArea();
			return;
		}
		case "Upgrade": {
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
