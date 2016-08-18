import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.sound.sampled.Control;

public class Station extends Satellite implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Planet[] planetsInAOI;
	private int AreaOfInfluence;
	private Player controller;
	
	public Station(Controller ctrl, Integer locX, Integer locY, Integer sz) {
		super(ctrl, locX, locY, sz);
		// TODO Auto-generated constructor stub
		
		this.setColors(Color.GREEN, Color.BLACK, Color.BLACK);
	}
	
	public Planet[] getPInAOI(){
		return planetsInAOI;
	}
	
	public void setPInAOI(Planet[] planets) {
		planetsInAOI = planets;
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//TODO ADD Image?
		
		
		
		g.setColor(fillCol);
		g.fillRect(0, 0, s, s);	
		g.setColor(borderCol);
		g.drawRect(0, 0, s, s);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		 
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
