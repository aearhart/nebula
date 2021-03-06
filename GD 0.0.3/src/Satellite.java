import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Satellite extends JComponent {

	private static final long serialVersionUID = 1L;
	
	protected ClientController control;
	
	// Attributes
	protected String name = "";
	protected String num = "s0";
	protected String t = "O"; // O for sun
	
	// resources given out by satellite
	protected int outputGas = 0;
	protected int outputMineral = 0;
	protected int outputWater = 0;
	
	// cost of upgrading satellite
	protected int costGas = 0;
	protected int costMineral = 0;
	protected int costWater = 0;
	
	// upgrading satellite leads to change in resources -> percentage (i.e. Resource += Resource*Upgrade)
	protected double upgradePropGas = 1;
	protected double upgradePropMineral = 1;
	protected double upgradePropWater = 1;
	protected double upgradeCost = 1;
	
	// location of satellite
	protected Integer x;
	protected Integer y;
	protected Integer s = 100; // size of sphere
	
	protected Color fillCol;
	protected Color borderCol;
	protected Color selectCol;

	protected String ownerNum = "0"; // default not owned
	protected Player owner = null;
	protected Color ownerColor;

	protected int level = 0;
	protected int maxLevel = 5;
	
	protected BufferedImage image = null;
	public String imageName = " ";

	private Boolean isMalfunctioning = false;
	
	public Satellite(Integer locX, Integer locY) {
		x = locX;
		y = locY;
	}
	
	public Satellite(ClientController clientController, Integer locX, Integer locY) {
		control = clientController;
		x = locX;
		y = locY;
	}
	
	
	public String printState() {
		/* the current state of the satellite */
		// "s__=t_x___y___s___r___n__"
		ArrayList<String> aList = new ArrayList<String>();
		aList.add("satellite");
		aList.add(t);
		aList.add(num);
		aList.add(name);
		aList.add(Integer.toString(x));
		aList.add(Integer.toString(y));
		aList.add(Integer.toString(s));
		aList.add(Integer.toString(outputGas));
		aList.add(Integer.toString(outputMineral));
		aList.add(Integer.toString(outputWater));
		
		return Globals.addDelims(aList);
	}
	
	public int update(String[] ary, int i) {
		/* UPDATE to satellite from server
		 satellite, type, x, y, size   */
		if (! ary[i++].equals("satellite"))
			return -1;
		t = ary[i++];
		num = ary[i++];
		name = ary[i++];
		x = Integer.parseInt(ary[i++]);
		y = Integer.parseInt(ary[i++]);
		s = Integer.parseInt(ary[i++]);
		outputGas = Integer.parseInt(ary[i++]);
		outputMineral = Integer.parseInt(ary[i++]);
		outputWater = Integer.parseInt(ary[i++]);
		this.setBounds(x, y, s, s);
		return i;
	}
	
	public void setOwner(String own) {
		/* set owner given string determining owner --used for UPDATES */
		
		if (control == null)
			return; // don't need to set owner variable
		else if ((own).equals(control.getPlayer().getNum())) { // equals current player
			setOwner(control.getPlayer());
		}
		else if (! own.equals("0")) { // equals opponent
			setOwner(control.getOpponent());
		}
		else // isn't owned, it's free!
			return;
	}
	
	public void setOwner(Player p) {
		/* set owner to given player */
		owner = p;
		ownerNum = p.getNum();
		setOwnerInfo();
	}
	
	private void setOwnerInfo() {
		/* Station: send station info to the player
		 * Planet: set owner color to mark ownership
		 */
		System.out.println("changing owner......");
		if (t.equals("S")) { // it's a station
			owner.addStation(this);
			this.setColors(owner.getColor(), this.getBorderCol(), this.getSelectCol());
		}
		else if (t.equals("SS")) { // it's a spaceship
			owner.setSpaceship(this);
		}
		else { // it's a planet
			if (owner == control.getPlayer()) owner.addPlanet();
			System.out.println("ADDING A PLANET......................................");
			ownerColor = owner.getColor();
		}
	}
	
	protected Boolean canPurchase() {
		/* return true or false if player can purchase this satellite */
		System.out.println("cost: " + costGas + "g " + costMineral + "m " + costWater + "w");
		System.out.println("player: " + control.getPlayer().getGas() + "g " + control.getPlayer().getMineral() + "m" + control.getPlayer().getWater() + "w");
		System.out.println("results: " + (control.getPlayer().getGas() >= costGas) + "(g)" + (control.getPlayer().getMineral() >= costMineral) + "(m)" + (control.getPlayer().getWater() >= costWater) + "(w)");
		return (control.getPlayer().getGas() >= costGas && control.getPlayer().getMineral() >= costMineral && control.getPlayer().getWater() >= costWater);
	}
	
	public void startingResources(Player p) {
		
	}
	
	public Boolean upgradeSatelliteToNextLevel() {
		// do nothing, will be overrided by child classes
		return false;
	}
	
	protected void addSatelliteOwner() {
		setOwner(control.getPlayer());
		upgradeSatellite();
		//if (t.equals("G") || t.equals("M") || t.equals("W")) control.getPlayer().addPlanet();
		startingResources(control.getPlayer()); // if the new owner has any starting resources, add to player
	}
	
	protected void upgradeSatellite() {
		/* after buying/upgrading update Satellite and Player info*/
		control.getPlayer().subGas(costGas);
		control.getPlayer().subMineral(costMineral);
		control.getPlayer().subWater(costWater);
		level++;
		repaint(); 
		costGas += costGas*upgradeCost;
		costMineral += costMineral*upgradeCost;
		costWater += costWater*upgradeCost;
		this.addResources();
	}
	
	protected void addResources() {
		// upon upgrading, increase number of resources produced
		outputGas += outputGas * upgradePropGas;
		outputMineral += outputMineral * upgradePropMineral;
		outputWater += outputWater * upgradePropWater;
	}
	
	public String collectResources(Player p) {
		p.addGas(outputGas);
		p.addMineral(outputMineral);
		p.addWater(outputWater);
		String s = "Planet " + num;
		System.out.println("collecting:" + this.name + ": " + outputGas + outputMineral + outputWater);
		if (t.equals("G")) {
			s += " produced gas" + outputGas;
		}
		else if (t.equals("M")) {
			s += " produced " + outputMineral + " mineral";
		}
		else {
			s += " produced " + outputWater + " water";
		}
		return s;
	}
	
	/* basic get/set/add methods */
	
	public String getOwner() {
		return ownerNum;
	}
	
	public Player getPlayer() {
		return owner;
	}
	
	public Integer getLocX() {
		return x;
	}
	
	public Integer getLocY() {
		return y;
	}
	
	public void placeX(int newX) {
		x = newX;
	}
	
	public void placeY(int newY) {
		y = newY;
	}
	
	public Integer getMidX() {
		return x + s/2;
	}
	
	public Integer getMidY() {
		return y + s/2;
	}
	
	public void setSz(int sz) {

		s = sz;
		this.setBounds(x, y, s, s);
	}
	
	public Integer getSz() {
		return s/2;
	}

	public int getBoundSize() {
		return s+1;
	}
	
	public String getType() {
		return t;
	}
	
	public void setType(String str) {
		t = str;
	}
	
	public String getNum() {
		return num;
	}
	
	public void setNum(String n) {
		num = n;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String n) {
		name = n;
	}

	public Color getFillCol() {
		return fillCol;
	}
	
	protected Color getBorderCol() {
		return borderCol;
	}
	
	public Color getSelectCol() {
		return selectCol;
	}
	
	public void setOwnerColor(Color col) {
		ownerColor = col;
	}
	
	public void setColors (Color fill, Color border, Color select) {
		fillCol = fill;
		borderCol = border;
		selectCol = select;
	}
	
	public void switchColors() {
		Color temp = borderCol;
		borderCol = selectCol;
		selectCol = temp;
	}
	
	protected void setImage(String i) {
		try {                
			image = ImageIO.read(getClass().getResource(i)); } 
		catch (IOException ex) {
			// handle exception...
			System.out.println("IO EXCEPTION: "+ ex); }
	}

	public String info() {
		return "It's the sun. You can feel its blazing heat just looking at it.";
	}

	public Boolean isMalfunctioning() {
		return isMalfunctioning ;
	}
}
