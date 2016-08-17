import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Satellite extends JComponent implements MouseListener{

	private static final long serialVersionUID = 1L;
	
	private Integer x;
	private Integer y;
	private Integer s = 100; // size of sphere
	private Color fillCol;
	private Color borderCol;
	private Color selectCol;
	private Integer t = 0; // type: 0 = sun, 1 = water
	
	public Satellite(Integer locX, Integer locY) {
		// default: sun
		x = locX;
		y = locY;
		fillCol = Color.YELLOW;
		borderCol = Color.BLACK;
		selectCol = Color.ORANGE;
		
		addMouseListener(this);
	}
	
	public Satellite(Integer ty, Integer locX, Integer locY, Integer sz) {
		x = locX;
		y = locY;
		t = ty;
		s = sz;
		if (t == 1) { // water
			fillCol = Color.BLUE;
			borderCol = Color.BLACK;
			selectCol = Color.CYAN;
		}
		else if (t == 2) { // gas
			fillCol = Color.PINK;
			borderCol = Color.BLACK;
			selectCol = Color.RED;
		}
		
		else if (t == 3) {
			fillCol = Color.GRAY;
			borderCol = Color.BLACK;
			selectCol = Color.LIGHT_GRAY;
		}
		
		addMouseListener(this);
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
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(fillCol);
		g.fillOval(0, 0, s, s);	
		g.setColor(borderCol);
		g.drawOval(0, 0, s, s);
		g.drawOval(0, 0, s-1, s-1);
		g.drawOval(1, 1, s-1, s-1);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		Color temp = borderCol;
		borderCol = selectCol;
		selectCol = temp;
		repaint();
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		Color temp = borderCol;
		borderCol = selectCol;
		selectCol = temp;
		repaint();
	}

}
