import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.sound.sampled.Control;

public class Station extends Satellite implements MouseListener {

	private static final long serialVersionUID = 1L;
	private int AreaOfInfluence = 100;
	private double gasInfluence = 1;
	private double mineralInfluence = 1;
	private double waterInfluence = 1;
	
	public Station(Integer locX, Integer locY, Integer sz, String n) {
		super(locX, locY);
		setSz(sz);
		this.setType("S");
	
		costGas = 5;
		costMineral = 5;
		costWater = 5;
		
		gasResource = 3;
		mineralResource = 3;
		waterResource = 3;
		
		this.setNum(n);
		this.setColors(Color.GREEN, Color.BLACK, Color.RED);
		addMouseListener(this);
	}
	
	public Station(ClientController clientController, Integer locX, Integer locY, Integer sz, String n) {
		super(clientController, locX, locY);
		setSz(sz);
		this.setType("S");
		costGas = 5;
		costMineral = 5;
		costWater = 5;
		
		gasResource = 3;
		mineralResource = 3;
		waterResource = 3;
		
		this.setNum(n);
		this.setColors(Color.GREEN, Color.BLACK, Color.RED);
		addMouseListener(this);
	}
	
	@Override
	public String printState() {
		ArrayList<String> aList = new ArrayList<String>();
		aList.add("station");
		aList.add(t);
		aList.add(num);
		aList.add(name);
		aList.add(Integer.toString(x));
		aList.add(Integer.toString(y));
		aList.add(Integer.toString(s));
		aList.add(Integer.toString(AreaOfInfluence));
		aList.add(Integer.toString(costWater));
		aList.add(Integer.toString(costGas));
		aList.add(Integer.toString(costMineral));
		aList.add(Integer.toString(gasResource));
		aList.add(Integer.toString(mineralResource));
		aList.add(Integer.toString(waterResource));

		aList.add(ownerNum);
		aList.add(Integer.toString(level));
		return Globals.addDelims(aList);
	}
	
	@Override
	public int update(String [] ary, int i) {
		//station, num, name, x, y, s, AoI, costWater, costGas, 
		//costMineral, gasResource, waterResource, mineralResource, ownerNum, level
		if (! ary[i++].equals("station"))
			return -1;
		t = ary[i++];
		num = ary[i++];
		name = ary[i++];
		x = Integer.parseInt(ary[i++]);
		y = Integer.parseInt(ary[i++]);
		s = Integer.parseInt(ary[i++]);
		AreaOfInfluence = Integer.parseInt(ary[i++]);
		costWater = Integer.parseInt(ary[i++]);
		costGas = Integer.parseInt(ary[i++]);
		costMineral = Integer.parseInt(ary[i++]);
		gasResource = Integer.parseInt(ary[i++]);
		mineralResource = Integer.parseInt(ary[i++]);
		waterResource = Integer.parseInt(ary[i++]);
		ownerNum = ary[i++];
		level = Integer.parseInt(ary[i++]);
		if(owner == null)
			setOwner(ownerNum);
		return i;
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
	public String collectResources(Player p) {
		p.addGas((int) (gasResource*gasInfluence));
		p.addMineral((int) (mineralResource*mineralInfluence));
		p.addWater((int) (waterResource*waterInfluence));
		String s = "Station " + num + "produced " + gasResource + "g " + mineralResource + "m " + waterResource + "w.";
		return s;
	}
	
	public void malfunction(char a) {
		if (a == 'g') {
			gasInfluence = 0.5;
		}
	}
	
	public void fixMalfunction() {
		gasInfluence = 1;
		mineralInfluence = 1;
		waterInfluence = 1;
	}
	
	public int getAoI() {
		return AreaOfInfluence;
	}
	
	private void select() {
		switchColors();
		control.drawAoI(this);
		repaint();
	}
	
	private void deselect() {
		switchColors();
		control.removeAoI();
		control.printToHoverArea();
		repaint();
	}
	
	/* MOUSE events */
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
					control.removeAoI();
					control.drawAoI(this);
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
				select(); }
			if (this.owner == control.getOpponent()) {
				control.printToHoverArea("This station is owned by " + control.getOpponent().getName() + ". 100% off limits."); }
			else { 
				control.printToHoverArea("You do not own this space station. But you could, for the price of a click of a button!"); }
			return;	
			}
		case "Wait": {
			select();
			String str = "";
			if (owner != null) {
				str = " owned by " + owner.getName();
			}
			control.printToHoverArea("A level " + level + " space station");
			}
		case "Upgrade": { // Upgrading a station
			if(this.owner == control.getPlayer()) { // draw AoI
				select(); 
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
					deselect(); }
				return;
			}
		case "Wait": {
			deselect();
		}
		case "Upgrade": { // Upgrading a station
			if(this.owner == control.getPlayer()) { // remove AoI
				deselect();
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
