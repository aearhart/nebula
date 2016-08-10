import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Satellite extends JComponent implements MouseListener{
	private Integer x;
	private Integer y;
	private Color fillCol;
	private Color borderCol;
	
	public Satellite(Integer locX, Integer locY) {
		x = locX;
		y = locY;
		fillCol = Color.MAGENTA;
		borderCol = Color.BLACK;
		
		addMouseListener(this);
	}
	
	public Integer getLocX() {
		return x;
	}
	
	public Integer getLocY() {
		return y;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(fillCol);
		g.fillOval(0, 0, 99, 99);	
		g.setColor(borderCol);
		g.drawOval(0, 0, 99, 99);
		g.drawOval(0, 0, 98, 98);
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

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
