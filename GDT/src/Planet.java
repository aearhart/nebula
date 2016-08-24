import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Planet extends Satellite implements MouseListener {

	private Integer numOfResources;
	private Player owner = null;
	private Controller control;
	
	public Planet(Controller ctrl, Integer locX, Integer locY, Integer sz) {
		super(ctrl, locX, locY, sz);
		// TODO Auto-generated constructor stub
		numOfResources = 1;
		control = ctrl;
		addMouseListener(this);
	}

	public Planet(Controller ctrl, Integer locX, Integer locY, Integer sz, Integer numResources) {
		super(ctrl, locX, locY, sz);
		control = ctrl;
		numOfResources = numResources;
		addMouseListener(this); 
	}

	public Integer getResources(){
		return numOfResources;
	}
	
	public void setOwner(Player p) {
		owner = p;
	}
	
	public Player getOwner() {
		return owner;	
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
		control.printToInstructionArea(name + " gives out " + numOfResources);
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
		this.switchColors();
		control.printToHoverArea(this.getName());
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		this.switchColors();
		control.printToHoverArea("Hover over a satellite for more information");
		repaint();
	}

}
