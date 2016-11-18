import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Planet extends Satellite implements MouseListener {
	
	int largeSize = Globals.mapSize / 22; // 1000/22 = 45
	int medSize = Globals.mapSize / 38;   // 1000/32 = 35
	int smallSize = Globals.mapSize / 40; // 1000/50 = 25
	
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
		//TODO cutting off the bottom right sides of planets (inc. sun), player screens don't match either
		
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
		aList.add(Integer.toString(outputGas));
		aList.add(Integer.toString(outputMineral));
		aList.add(Integer.toString(outputWater));
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
		outputGas = Integer.parseInt(ary[i++]);
		outputMineral = Integer.parseInt(ary[i++]);
		outputWater = Integer.parseInt(ary[i++]);
		ownerNum = ary[i++];
		level = Integer.parseInt(ary[i++]);
		imageName = ary[i++];
		if (image==null) {
			//System.out.println("Setting image: " + imageName);
			setImage(imageName);
		}
		if(owner == null)
			setOwner(ownerNum);
		return i;
	}
	
	@Override
	public String info() {
		/* return info of planet */
		String str = "";
		if (t.equals("G")) {
			str = "Gas planet " + getNum() + " (" + getName() + ") is level " + level + ".\nProduces: " + outputGas + " gas\nUpgrade cost: " + costGas + "g " + costMineral + "m " + costWater + "w";			
		}
		else if (t.equals("M")) {

			str = "Mineral planet " + getNum() + " (" + getName() + ") is level " + level + ".\nProduces: " + outputMineral + " mineral\nUpgrade cost: " + costGas + "g " + costMineral + "m " + costWater + "w";	
		}
		else {

			str = "Water planet " + getNum() + " (" + getName() + ") is level " + level + ".\nProduces: " + outputWater + " water\nUpgrade cost: " + costGas + "g " + costMineral + "m " + costWater + "w";	
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
		int i = ran.nextInt(Globals.mapSize/100);
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
			outputGas = r;
		else if (t.equals("M")) {
			outputMineral = r;
		}
		else {
			outputWater = r;
		}
	}
	
	public Boolean planetWithinAoI() {
		/* check to see if planet is within the current player's AoI */
		Station sat = control.getPlayer().getBase();
		if (control.withinDistance(sat, (Satellite)(this))) {
			return true;
		}
		return false;
	}
	
	public Boolean buyPlanet() {
		Boolean endTurn;
		if (canPurchase()) {
			addSatelliteOwner();
			endTurn = true;
			control.printToHoverArea(info());
			control.printToPlayerArea();
		}
		else {
			endTurn = false;
			control.printToHoverArea("You don't have enough resources to buy this planet");
		}
		return endTurn;
	}
	
	@Override
	public Boolean upgradeSatelliteToNextLevel() {
		// check if within AoI
		Boolean endTurn = false;
		if (planetWithinAoI()) { // it's within current player's AoI
			if (this.owner == control.getOpponent()) { // owned by opponent
				control.printToHoverArea("This planet is owned by someone else. Don't throw resources at " + control.getOpponent().getName() + ".");
			}
			else if (level > maxLevel) {
				control.printToHoverArea("This planet is at max level.");
			}
			else if (this.owner == control.getPlayer() && canPurchase()) { // owned, upgrading planet
				upgradeSatellite();
				endTurn = true;
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
		return endTurn;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		control.infoPanel2.selectSatellite(this);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		switchColors();
		repaint();
		control.getMap().hoverBoxOn(name);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		/* switch back to original */
		switchColors();
		repaint();
		control.getMap().hoverBoxOff();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {

		
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		
	}
}
