import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Satellite extends JComponent {

	private static final long serialVersionUID = 1L;
	
	private Integer x;
	private Integer y;
	private Integer s = 100; // size of sphere
	private Color fillCol;
	private Color borderCol;
	private Color selectCol;
	
	public Satellite(Integer locX, Integer locY, Integer sz) {
		// default: sun
		x = locX;
		y = locY;
		s = sz;
	}
	

	public void setColors (Color fill, Color border, Color select) {
		fillCol = fill;
		borderCol = border;
		selectCol = select;
	}
	
	public Integer getLocX() {
		return x;
	}
	
	public Integer getLocY() {
		return y;
	}
	
	public Integer getSz() {
		return s;
	}
	



}
