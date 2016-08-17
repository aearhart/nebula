import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Satellite extends JComponent {

	private static final long serialVersionUID = 1L;
	
	private Integer x;
	private Integer y;
	protected Integer s = 100; // size of sphere
	protected Color fillCol;
	protected Color borderCol;
	protected Color selectCol;
	protected String name = "";
	
	public Satellite(Integer locX, Integer locY, Integer sz) {
		x = locX;
		y = locY;
		s = sz;
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
	
	public Color getFillCol() {
		return fillCol;
	}
	
	public Color getBorderCol() {
		return borderCol;
	}
	
	public Integer getLocX() {
		return x;
	}
	
	public Integer getLocY() {
		return y;
	}
	
	public Integer getSz() {
		return s+1;
	}

}
