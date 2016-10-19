import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.Control;

public class Station extends Satellite implements MouseListener {

	private static final long serialVersionUID = 1L;
	protected int AreaOfInfluence = 100;
	protected double gasInfluence = 1;
	protected double mineralInfluence = 1;
	protected double waterInfluence = 1;
	
	protected int gasAccess;
	protected int mineralAccess;
	protected int waterAccess;
	protected int gasSector;
	protected int mineralSector;
	protected int waterSector;
	protected int gasGenerated;
	protected int mineralGenerated;
	protected int waterGenerated;
	//protected int upgradeCost; this is in satellite now
	protected int gasStart;
	protected int mineralStart;
	protected int waterStart;
	
	private String planetsToCreate = "";
	private char[] positionsPossible = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	private String positions = "";
	
	
	public Station(Integer locX, Integer locY, Integer sz, String n) {
		super(locX, locY);
		setSz(sz);
		this.setType("S");

		this.setNum(n);
		this.setColors(Color.GREEN, Color.BLACK, Color.RED);
		addMouseListener(this);
	}
	
	public Station(ClientController clientController, Integer locX, Integer locY, Integer sz, String n) {
		super(clientController, locX, locY);
		setSz(sz);
		this.setType("S");
		
		this.setNum(n);
		this.setColors(Color.GREEN, Color.BLACK, Color.RED);
		addMouseListener(this);
	}
	
	public Station(ClientController clientController, String [] s_array, int pos) {
		super(clientController, Integer.parseInt(s_array[pos+4]), Integer.parseInt(s_array[pos+5]));
		this.setColors(Color.GREEN, Color.BLACK, Color.RED);
		update(s_array, pos);
		
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

	protected void setup() {
		setupResources('G', gasGenerated);
		setupResources('M', mineralGenerated);
		setupResources('W', waterGenerated);
		
	}
	
	private void setupResources(char res, int generate) {
		int resourceProduced = 0;
		switch (generate) {
		
		case 1: {
			resourceProduced = 2;
			break;}
		case 2: {
			resourceProduced = 3;
			break;}
		case 3: {
			resourceProduced = 4;
			break;}
		case 4: {
			resourceProduced = 5;
			break;}
		case 5: {
			resourceProduced = 6;
			break;
		}
		}
		if (res == 'G') {
			gasResource = resourceProduced;
		}
		else if (res == 'M') {
			mineralResource = resourceProduced;
		}
		else
			waterResource = resourceProduced;
	}
	
	@Override
	public void startingResources(Player p) {
		p.addGas(gasStart);
		p.addMineral(mineralStart);
		p.addWater(waterStart);
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
	
	public String dividePlanets (String type, int num) {
		Random rand = new Random();
		int r = rand.nextInt(3);
		switch (num) {
		case(0): 
			return "";
		case(1): // 1 s planet
			return type + "s";
		case(2): // 1 m planet or 2 s planets
			if (r == 0)
				return type + "m";
			else
				return type + "s" + type + "s";
		case(3): // 1 l planet or 1 s planet and 1 m planet
			if (r == 0)
				return type + "l";
			else
				return type + "s" + type + "m";
		case(4): // 2 m planets or 1 s planet and 1 l planet
			if (r == 0)
				return type + "m" + type + "m";
			else
				return type + "s" + type + "l";
		default:
			return "";
		}
		
	}
	
	public void createPlanets() {
		planetsToCreate += dividePlanets("G", gasAccess);
		planetsToCreate += dividePlanets("M", mineralAccess);
		planetsToCreate += dividePlanets("W", waterAccess);
		
		int numPlanets = planetsToCreate.length()/2;
		Random rand = new Random();
		for (int i = 0; i < numPlanets; i++) {
			int p = rand.nextInt(16);
			if (positionsPossible[p] != '.') {
				positions += Character.toString(positionsPossible[p]);
				int j = p-1;
				int k = p+1;
				if (p == 0) {
					j = 15;
				}
				else if (p == 15) {
					k = 0;
				}
				positionsPossible[j] = '.';
				positionsPossible[p] = '.';
				positionsPossible[k] = '.';
			}
			else
				i--;
		}
		System.out.print("PositionsPossible = ");
		for (int i = 0; i < 16; i++)
			System.out.print(positionsPossible[i]);
		System.out.println("");
		System.out.println("Positions = " + positions);
		System.out.println("planetsToCreate = " + planetsToCreate);
	}
	
	public String getPlanetsToCreate() {
		return planetsToCreate;
	}
	
	public void placePlanet(Satellite p, int planetPos) {
		// change x/y coordinates to place planet correctly w/in AoI
		//TODO  Harry
		int planetSize = p.getSz();
		p.placeX(x - planetSize - 50);
		p.placeY(y - 90);
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
