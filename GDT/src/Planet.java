import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Planet extends Satellite implements MouseListener {

	private Integer numOfResources;
	
	public Planet(Integer locX, Integer locY, Integer sz) {
		super(locX, locY, sz);
		// TODO Auto-generated constructor stub
		numOfResources = 1;
		addMouseListener(this);
	}

	public Planet(Integer locX, Integer locY, Integer sz, Integer numResources) {
		super(locX, locY, sz);
		numOfResources = numResources;
		addMouseListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(fillCol);
		g.fillOval(0, 0, s, s);	
		g.setColor(borderCol);
		g.drawOval(0, 0, s, s);
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
