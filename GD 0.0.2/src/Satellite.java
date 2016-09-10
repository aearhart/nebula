import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Satellite extends JComponent {

	private static final long serialVersionUID = 1L;
	
	private Integer x;
	private Integer y;
	protected Integer s = 100; // size of sphere
	protected Color fillCol;
	protected Color borderCol;
	protected Color selectCol;
	private String name = "0";
	protected ClientController control;
	protected BufferedImage image = null;
	public String t = "O"; // O for sun
	public Integer resource = 0;
	public String owned = "0";
	public Player owner = null;
	
	public Satellite(ClientController clientController, Integer locX, Integer locY, Integer sz) {
		control = clientController;
		x = locX;
		y = locY;
		s = sz;;
	}
	
	public void setOwner(String own) {
		if (("P" + own).equals(control.getCurrPlayer())) {
			owner = control.getPlayer();
			owned = owner.getNum();
			setOwnerInfo();
		}
		else if (! own.equals("0")) {
			owned = own;
			owner = control.getOpponent();
			setOwnerInfo();
		}
	}
	
	public void setOwner(Player p) {
		owner = p;
		owned = p.getNum();
		setOwnerInfo();
	}
	
	private void setOwnerInfo() { // ONLY FOR SATELLITES NOT PLANETS YET
		owner.addStation(this);
		this.setColors(owner.getColor(), this.getBorderCol(), this.getSelectCol());;
	}
	
	public void setResource(int n) {
		resource = n;
	}
	
	public int getResources() {
		return resource;
	}
	
	public void addResources(int n) {
		resource += n;
	}
	public void defineType(String str) {
		t = str;
	}
	
	private String padLeft(String s, int n) {
	    return String.format("%1$" + n + "s", s).replace(' ', '0');
	}

	private String padLeft(int s, int n) {
	   // String str = String.format("%1$" + n + "s", Integer.toString(s)).replace(' ', '0');
	   // return str.substring(str.length() - n);
		
		return String.format("%1$" + n + "s", Integer.toString(s)).replace(' ', '0');
	}

	public String printState() {
		// "s__=t_x___y___s___r___n__"
		String str = "s";
		str += padLeft(name, 2);
		str += "=t" + t;
		str += "x" + padLeft(x, 3);
		str += "y" + padLeft(y, 3);
		str += "s" + padLeft(s, 3);
		str += "r" + padLeft(resource, 3);
		str += "o" + owned;
		str += "n" + padLeft(name, 2);
		str += " ";
		return str;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
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
	
	public Color getFillCol() {
		return fillCol;
	}
	
	protected Color getBorderCol() {
		return borderCol;
	}
	
	public Color getSelectCol() {
		return selectCol;
	}
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
}
