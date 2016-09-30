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
	protected String name = " ";
	protected String num = "s0";
	protected String t = "O"; // O for sun
	protected Integer resource = 0;
	
	protected Integer x;
	protected Integer y;
	protected Integer s = 100; // size of sphere
	
	protected Color fillCol;
	protected Color borderCol;
	protected Color selectCol;

	protected String ownerNum = "0"; // default not owned
	protected Player owner = null;
	protected Color ownerColor;
	
	int costWater = 0;
	int costGas = 0;
	int costMineral = 0;

	protected int level = 0;
	protected int maxLevel = 5;
	
	protected BufferedImage image = null;
	
	public Satellite(Integer locX, Integer locY, Integer sz) {
		x = locX;
		y = locY;
		s = sz;
	}
	
	public Satellite(ClientController clientController, Integer locX, Integer locY, Integer sz) {
		control = clientController;
		x = locX;
		y = locY;
		s = sz;
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
		return i;
	}
	
	public void setOwner(String own) {
		/* set owner given string determining owner --used for UPDATES */
		if (control == null)
			return; // don't need to set owner variable
		if ((own).equals(control.getPlayer().getNum())) { // equals current player
			owner = control.getPlayer();
			ownerNum = owner.getNum();
			setOwnerInfo();
		}
		else if (! own.equals("0")) { // equals opponent
			owner = control.getOpponent();
			ownerNum = owner.getNum();
			setOwnerInfo();
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
		if (t.equals("S")) { // it's a station
			owner.addStation(this);
			this.setColors(owner.getColor(), this.getBorderCol(), this.getSelectCol());
		}
		else { // it's a planet
			ownerColor = owner.getColor();
		}
	}
	
	protected Boolean canPurchase() {
		/* return true or false if player can purchase this satellite */
		return (control.getPlayer().getGas() >= costGas && control.getPlayer().getMineral() >= costMineral && control.getPlayer().getWater() >= costWater);
	}
	
	protected void upgradeSatellite() {
		/* after buying/upgrading update Satellite and Player info*/
		control.getPlayer().subGas(costGas);
		control.getPlayer().subMineral(costMineral);
		control.getPlayer().subWater(costWater);
		level++;
		setOwner(control.getPlayer());
		repaint();
		// cost doubles
		costGas += costGas;
		costMineral += costMineral;
		costWater += costWater;
		this.addResources(3);
	}
	
	/* basic get/set/add methods */
	
	public Integer getLocX() {
		return x;
	}
	
	public Integer getLocY() {
		return y;
	}
	
	public Integer getMidX() {
		return x + s/2;
	}
	
	public Integer getMidY() {
		return y + s/2;
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
	
	public int getResources() {
		return resource;
	}
	
	public void setResource(int n) {
		resource = n;
	}
	
	public void addResources(int n) {
		resource += n;
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
}
