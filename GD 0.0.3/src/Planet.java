import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Planet extends Satellite implements MouseListener {
	
	int largeSize = Globals.winSize / 20;
	int medSize = Globals.winSize / 35;
	int smallSize = Globals.winSize / 40;
	
	public Planet(Integer locX, Integer locY, String sz, String ty) {
		super(locX, locY);
		planetSetup(sz, ty);
		
		addMouseListener(this); 
	}
	
	public Planet(ClientController clientController, Integer locX, Integer locY, String sz, String ty) {
		super(clientController, locX, locY);
		control = clientController;
		
		planetSetup(sz, ty);
		
		addMouseListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (image == null) {
			g.setColor(fillCol);
			g.fillOval(0, 0, s, s);	
		}
		else {
			g.drawImage(image, 0, 0, s, s, null);
		}
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
		aList.add(num);
		aList.add(name);
		aList.add(Integer.toString(x));
		aList.add(Integer.toString(y));
		aList.add(Integer.toString(s));
		aList.add(Integer.toString(costWater));
		aList.add(Integer.toString(costMineral));
		aList.add(Integer.toString(costGas));
		aList.add(Integer.toString(gasResource));
		aList.add(Integer.toString(mineralResource));
		aList.add(Integer.toString(waterResource));
		aList.add(ownerNum);
		aList.add(Integer.toString(level));
		aList.add(imageName);

		return Globals.addDelims(aList);
	}
	
	@Override
	public int update(String[] ary, int i) {
		// planet, type, x, y, size, num, name, costWater, costMineral, costGas, resource, ownerNum, level
		if (! ary[i++].equals("planet"))
			return -1;
		t = ary[i++];
		num = ary[i++];
		name = ary[i++];
		x = Integer.parseInt(ary[i++]);
		y = Integer.parseInt(ary[i++]);
		s = Integer.parseInt(ary[i++]);
		costWater = Integer.parseInt(ary[i++]);
		costMineral = Integer.parseInt(ary[i++]);
		costGas = Integer.parseInt(ary[i++]);
		gasResource = Integer.parseInt(ary[i++]);
		mineralResource = Integer.parseInt(ary[i++]);
		waterResource = Integer.parseInt(ary[i++]);
		ownerNum = ary[i++];
		level = Integer.parseInt(ary[i++]);
		imageName = ary[i++];
		if (image==null) {
			System.out.println("Setting image: " + imageName);
			setImage(imageName);
		}
		if(owner == null)
			setOwner(ownerNum);
		return i;
	}
	
	public String info() {
		/* return info of planet */
		String str = "";
		if (t.equals("G")) {
			str = "Gas planet " + getNum() + " (" + getName() + ") is level " + level + ".\nProduces: " + gasResource + " gas\nUpgrade cost: " + costGas + "g " + costMineral + "m " + costWater + "w";			
		}
		else if (t.equals("M")) {

			str = "Mineral planet " + getNum() + " (" + getName() + ") is level " + level + ".\nProduces: " + mineralResource + " mineral\nUpgrade cost: " + costGas + "g " + costMineral + "m " + costWater + "w";	
		}
		else {

			str = "Water planet " + getNum() + " (" + getName() + ") is level " + level + ".\nProduces: " + waterResource + " water\nUpgrade cost: " + costGas + "g " + costMineral + "m " + costWater + "w";	
		}
		return str;
	}
	
	public String info(String str) {
		/* return str + planet info */
		return str + " " + info();
	}
	
	private void planetSetup(String sz, String ty) {
		setType(ty);
		Random ran = new Random();
		int i = ran.nextInt(Globals.winSize/100);
		if (sz.equals("s")) { // small
			setSz(i + smallSize);
			costWater = 2;
			costMineral = 2;
			costGas = 2;
			setResource(3);
		}
		else if (sz.equals("m")) { // med
			setSz(i + medSize);
			costWater = 3;
			costMineral = 3;
			costGas = 3;
			setResource(4);
		}
		else { // large
			setSz(i + largeSize);
			costWater = 4;
			costMineral = 4;
			costGas = 4;
			setResource(5);
		}
	}
	
	private void setResource(int r) {
		if (t.equals("G"))
			gasResource = r;
		else if (t.equals("M")) {
			mineralResource = r;
		}
		else {
			waterResource = r;
		}
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
					addSatelliteOwner();
					control.setStatus("EndTurn");
					control.printToHoverArea(info());
					control.printToPlayerArea();
				}
				else if (level > maxLevel) {
					control.printToHoverArea("This planet is at max level.");
				}
				else if (this.owner == control.getPlayer() && canPurchase()) { // owned, upgrading planet
					upgradeSatellite();
					control.setStatus("EndTurn");
					control.printToHoverArea(info());
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
